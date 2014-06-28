package modtweaker.mekanism.chemical;

import static modtweaker.mekanism.GasHelper.getGas;
import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.throwException;
import mekanism.api.gas.GasStack;
import mekanism.common.recipe.RecipeHandler.Recipe;
import modtweaker.mekanism.MekanismAddRecipeAction;
import modtweaker.mekanism.TweakerMekanismBaseRemove;
import modtweaker.util.TweakerBaseFunction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class Washer {
	public static class Add extends TweakerBaseFunction {
		public static final Add INSTANCE = new Add();
		
		private Add() {
			super("mekanism.chemical.washer.addRecipe");
		}
		
		@Override
		public void perform(TweakerValue... args) {
			if (canContinue(2, args)) {
				GasStack input = getGas();
				GasStack output = getGas();
				Tweaker.apply(new MekanismAddRecipeAction(Recipe.CHEMICAL_WASHER, input, output));
			} else throwException(this, 2);
		}
	}
	
	public static class Remove extends TweakerMekanismBaseRemove {
		public static final Remove INSTANCE = new Remove();
		
		private Remove() {
			super("mekanism.chemical.washer.removeRecipe", Recipe.CHEMICAL_WASHER);
		}
	}
}
