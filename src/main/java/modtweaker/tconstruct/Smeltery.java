package modtweaker.tconstruct;

import static modtweaker.util.Helper.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;

import minetweaker.MineTweakerAPI;
import minetweaker.api.liquid.ILiquidStack;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.library.crafting.AlloyMix;

@ZenClass("mods.tconstruct.Smeltery")
public class Smeltery {
	//Adding a TConstruct Alloy recipe
	@ZenMethod
	public static void addAlloy(@NotNull ILiquidStack output, @NotNull ILiquidStack[] input) {
		MineTweakerAPI.tweaker.apply(new Add(new AlloyMix(FluidStack(output), new ArrayList<FluidStack>(Arrays.asList(FluidStack(input))))));
	}
	
	//Passes the list to the base list implementation, and adds the recipe
	private static class Add extends BaseListAddition {
		public Add(AlloyMix recipe) {
			super("Smeltery - Alloy", TConstructHacks.alloys, recipe);
		}

		@Override
		public String getRecipeInfo() {
			return ((AlloyMix)recipe).result.getFluid().getName();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Removing a TConstruct Alloy recipe
	@ZenMethod
	public static void removeRecipe(@NotNull ILiquidStack output) {
		MineTweakerAPI.tweaker.apply(new Remove((FluidStack(output))));
	}
	
	//Removes a recipe, apply is never the same for anything, so will always need to override it
	private static class Remove extends BaseListRemoval {
		public Remove(FluidStack output) {
			super("Smeltery - Alloy", TConstructHacks.alloys, output);
		}

		//Loops through the registry, to find the item that matches, saves that recipe then removes it
		@Override
		public void apply() {
			for(AlloyMix r: TConstructHacks.alloys) {
				if(r.result != null && r.result.isFluidStackIdentical(fluid)) {
					recipe = r; break;
				}
			}
			
			TConstructHacks.alloys.remove(recipe);
		}
		
		@Override
		public String getRecipeInfo() {
			return fluid.getFluid().getName();
		}
	}
}

