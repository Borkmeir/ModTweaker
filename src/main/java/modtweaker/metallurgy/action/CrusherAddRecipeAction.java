package modtweaker.metallurgy.action;

import modtweaker.metallurgy.MetallurgyHacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.minetweaker.api.IUndoableAction;

public class CrusherAddRecipeAction implements IUndoableAction {
	private final ItemStack input;
	private final ItemStack output;

	public CrusherAddRecipeAction(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void apply() {		
		if (input.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
			MetallurgyHacks.crusher.put(Integer.valueOf(input.itemID), output);
		} else {
			MetallurgyHacks.crusherMeta.put(input.itemID, input.getItemDamage(), output);
		}
	}

	@Override
	public boolean canUndo() {
		return input.getItemDamage() == OreDictionary.WILDCARD_VALUE ? MetallurgyHacks.crusher != null : MetallurgyHacks.crusherMeta != null;
	}

	@Override
	public void undo() {
		if (input.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
			MetallurgyHacks.crusher.remove(input.itemID);
		} else {
			MetallurgyHacks.crusherMeta.remove(input.itemID, input.getItemDamage());
		}
	}

	@Override
	public String describe() {
		return "Adding Crusher Recipe: " + output.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Crusher Recipe: " + output.getDisplayName();
	}
}
