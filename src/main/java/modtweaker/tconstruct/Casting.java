package modtweaker.tconstruct;

import static modtweaker.util.Helper.FluidStack;
import static modtweaker.util.Helper.ItemStack;

import java.util.ArrayList;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.library.crafting.CastingRecipe;

@ZenClass("mods.tconstruct.Casting")
public class Casting {
	//Adding a TConstruct Casting recipe
	@ZenMethod
	public static void addBasinRecipe(@NotNull IItemStack output, @NotNull ILiquidStack metal, @Optional IItemStack cast, @Optional boolean consume, @NotNull int delay) {
		MineTweakerAPI.tweaker.apply(new Add(new CastingRecipe(ItemStack(output), FluidStack(metal), ItemStack(cast), consume, delay, null), TConstructHacks.basinCasting));
	}
	
	@ZenMethod
	public static void addTableRecipe(@NotNull IItemStack output, @NotNull ILiquidStack metal, @Optional IItemStack cast, @Optional boolean consume, @NotNull int delay) {
		MineTweakerAPI.tweaker.apply(new Add(new CastingRecipe(ItemStack(output), FluidStack(metal), ItemStack(cast), consume, delay, null), TConstructHacks.tableCasting));
	}
	
	//Passes the list to the base list implementation, and adds the recipe
	private static class Add extends BaseListAddition {
		public Add(CastingRecipe recipe, ArrayList list) {
			super("TConstruct Casting", list, recipe);
		}

		@Override
		public String getRecipeInfo() {
			return ((CastingRecipe)recipe).output.getDisplayName();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Removing a TConstruct Casting recipe
	@ZenMethod
	public static void removeBasinRecipe(@NotNull IItemStack output) {
		MineTweakerAPI.tweaker.apply(new Remove((ItemStack(output)), TConstructHacks.basinCasting));
	}
	
	@ZenMethod
	public static void removeTableRecipe(@NotNull IItemStack output) {
		MineTweakerAPI.tweaker.apply(new Remove((ItemStack(output)), TConstructHacks.tableCasting));
	}
	
	//Removes a recipe, apply is never the same for anything, so will always need to override it
	private static class Remove extends BaseListRemoval {
		public Remove(ItemStack output, ArrayList list) {
			super("TConstruct Casting", list, output);
		}

		//Loops through the registry, to find the item that matches, saves that recipe then removes it
		@Override
		public void apply() {
			for(CastingRecipe r: (ArrayList<CastingRecipe>)list) {
				if(r.output != null && r.output.isItemEqual(stack)) {
					recipe = r; break;
				}
			}
			
			list.remove(recipe);
		}
		
		@Override
		public String getRecipeInfo() {
			return stack.getDisplayName();
		}
	}
}

