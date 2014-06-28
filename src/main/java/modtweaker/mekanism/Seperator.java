package modtweaker.mekanism;

import static modtweaker.mekanism.GasHelper.getGas;
import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.throwException;
import mekanism.api.ChemicalPair;
import mekanism.api.gas.GasStack;
import mekanism.common.recipe.RecipeHandler.Recipe;
import modtweaker.util.TweakerBaseFunction;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class Seperator {
	public static class Add extends TweakerBaseFunction {
		public static final Add INSTANCE = new Add();
		
		private Add() {
			super("mekanism.seperator.addRecipe");
		}
		
		@Override
		public void perform(TweakerValue... args) {
			if (canContinue(3, args)) {
				FluidStack fluid = getFluid();
				GasStack gas1 = getGas();
				GasStack gas2 = getGas();
				Tweaker.apply(new MekanismAddRecipeAction(Recipe.ELECTROLYTIC_SEPARATOR, fluid, new ChemicalPair(gas1, gas2)));
			} else throwException(this, 3);
		}
	}
	
	public static class Remove extends TweakerMekanismBaseRemove {
		public static final Remove INSTANCE = new Remove();
		
		private Remove() {
			super("mekanism.seperator.removeRecipe", Recipe.ELECTROLYTIC_SEPARATOR);
		}
		
		@Override
		public void perform(TweakerValue... args) {
			if (canContinue(2, args)) {
				GasStack gas1 = getGas();
				GasStack gas2 = getGas();
				Tweaker.apply(new MekanismRemoveRecipeAction(recipe, new ChemicalPair(gas1, gas2)));
			} else throwException(this, 2);
		}
	}
}
