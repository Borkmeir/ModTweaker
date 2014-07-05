/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.item;

import java.util.Collections;
import java.util.List;

import minetweaker.MineTweakerAPI;
import minetweaker.mc172.data.NBTConverter;
import minetweaker.mc172.liquid.MCLiquidStack;
import minetweaker.api.data.IData;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemCondition;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IItemTransformer;
import minetweaker.api.item.IngredientItem;
import minetweaker.api.item.WeightedItemStack;
import minetweaker.api.liquid.ILiquidStack;
import static minetweaker.api.MineTweakerMC.getItemStack;
import minetweaker.mc172.actions.SetTranslationAction;
import minetweaker.util.ArrayUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author Stan
 */
public class MCItemStack implements IItemStack {
	private final ItemStack stack;
	private final List<IItemStack> items;
	private IData tag = null;
	private boolean wildcardSize;
	
	public MCItemStack(ItemStack itemStack) {
		if (itemStack == null) throw new IllegalArgumentException("stack cannot be null");
		
		stack = itemStack.copy();
		items = Collections.<IItemStack>singletonList(this);
	}
	
	public MCItemStack(ItemStack itemStack, boolean wildcardSize) {
		this(itemStack);
		
		this.wildcardSize = wildcardSize;
	}
	
	private MCItemStack(ItemStack itemStack, IData tag) {
		if (itemStack == null) throw new IllegalArgumentException("stack cannot be null");
		
		stack = itemStack;
		items = Collections.<IItemStack>singletonList(this);
		this.tag = tag;
	}

	@Override
	public IItemDefinition getDefinition() {
		return new MCItemDefinition(
				Item.itemRegistry.getNameForObject(stack.getItem()),
				stack.getItem());
	}

	@Override
	public String getName() {
		return stack.getUnlocalizedName();
	}

	@Override
	public String getDisplayName() {
		return stack.getDisplayName();
	}
	
	@Override
	public void setDisplayName(String name) {
		MineTweakerAPI.tweaker.apply(new SetTranslationAction(getName() + ".name", name));
	}

	@Override
	public int getDamage() {
		return stack.getItemDamage();
	}

	@Override
	public IData getTag() {
		if (tag == null) {
			if (stack.stackTagCompound == null) {
				return null;
			}
			
			tag = NBTConverter.from(stack.stackTagCompound, true);
		}
		return tag;
	}

	@Override
	public int getMaxDamage() {
		return stack.getMaxDamage();
	}
	
	@Override
	public ILiquidStack getLiquid() {
		FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(stack);
		return liquid == null ? null : new MCLiquidStack(liquid);
	}

	@Override
	public IIngredient anyDamage() {
		if (stack.getItem().getHasSubtypes()) {
			MineTweakerAPI.getLogger().logWarning("subitems don't have damaged states");
			return this;
		} else {
			ItemStack result = new ItemStack(stack.getItem(), stack.stackSize, OreDictionary.WILDCARD_VALUE);
			result.stackTagCompound = stack.stackTagCompound;
			return new MCItemStack(result, tag);
		}
	}

	@Override
	public IItemStack withDamage(int damage) {
		if (stack.getItem().getHasSubtypes()) {
			MineTweakerAPI.getLogger().logWarning("subitems don't have damaged states");
			return this;
		} else {
			ItemStack result = new ItemStack(stack.getItem(), stack.stackSize, damage);
			result.stackTagCompound = stack.stackTagCompound;
			return new MCItemStack(result, tag);
		}
	}

	@Override
	public IItemStack withAmount(int amount) {
		ItemStack result = new ItemStack(stack.getItem(), amount, stack.getItemDamage());
		result.stackTagCompound = stack.stackTagCompound;
		return new MCItemStack(result, tag);
	}

	@Override
	public IItemStack withTag(IData tag) {
		ItemStack result = new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage());
		if (tag == null) {
			result.stackTagCompound = null;
		} else {
			result.stackTagCompound = (NBTTagCompound) NBTConverter.from(tag);
		}
		return new MCItemStack(result, tag);
	}

	@Override
	public IItemStack updateTag(IData tagUpdate) {
		if (tag == null) {
			if (stack.stackTagCompound == null) {
				return withTag(tagUpdate);	
			}
			
			tag = NBTConverter.from(stack.stackTagCompound, true);
		}
		
		IData updated = tag.update(tagUpdate);
		return withTag(updated);
	}

	@Override
	public String getMark() {
		return null;
	}

	@Override
	public int getAmount() {
		return stack.stackSize;
	}

	@Override
	public List<IItemStack> getItems() {
		return items;
	}

	@Override
	public IItemStack amount(int amount) {
		return withAmount(amount);
	}
	
	@Override
	public WeightedItemStack percent(float chance) {
		return new WeightedItemStack(this, chance * 0.01f);
	}
	
	@Override
	public WeightedItemStack weight(float chance) {
		return new WeightedItemStack(this, chance);
	}

	@Override
	public IIngredient transform(IItemTransformer transformer) {
		return new IngredientItem(this, null, ArrayUtil.EMPTY_CONDITIONS, new IItemTransformer[] { transformer });
	}

	@Override
	public IIngredient only(IItemCondition condition) {
		return new IngredientItem(this, null, new IItemCondition[] { condition }, ArrayUtil.EMPTY_TRANSFORMERS);
	}

	@Override
	public IIngredient marked(String mark) {
		return new IngredientItem(this, mark, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS);
	}

	@Override
	public boolean matches(IItemStack item) {
		ItemStack internal = getItemStack(item);
		return internal.getItem() == stack.getItem()
				&& (wildcardSize || internal.stackSize == stack.stackSize)
				&& (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE
					|| stack.getItemDamage() == internal.getItemDamage()
					|| (!stack.getHasSubtypes() && !stack.getItem().isDamageable()));
	}

	@Override
	public boolean contains(IIngredient ingredient) {
		List<IItemStack> iitems = ingredient.getItems();
		if (iitems == null || iitems.size() != 1) return false;
		return matches(iitems.get(0));
	}

	@Override
	public IItemStack applyTransform(IItemStack item) {
		return item;
	}

	@Override
	public boolean hasTransformers() {
		return false;
	}

	@Override
	public Object getInternal() {
		return stack;
	}
	
	// #############################
	// ### Object implementation ###
	// #############################
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 41 * hash + (this.stack != null ? this.stack.hashCode() : 0);
		hash = 41 * hash + (this.wildcardSize ? 1 : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MCItemStack other = (MCItemStack) obj;
		if (this.stack != other.stack && (this.stack == null || !this.stack.equals(other.stack))) {
			return false;
		}
		if (this.wildcardSize != other.wildcardSize) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append('<');
		result.append(Item.itemRegistry.getNameForObject(stack.getItem()));

		if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
			result.append(":*");
		} else if (stack.getItemDamage() > 0) {
			result.append(':').append(stack.getItemDamage());
		}
		result.append('>');

		if (stack.getTagCompound() != null) {
			result.append(".withTag(");
			result.append(NBTConverter.from(stack.getTagCompound(), wildcardSize).toString());
			result.append(")");
		}
		
		if (!wildcardSize) {
			result.append(" * ").append(stack.stackSize);
		}

		return result.toString();
	}
}
