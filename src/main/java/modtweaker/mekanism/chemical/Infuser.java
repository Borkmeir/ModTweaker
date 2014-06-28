package modtweaker.mekanism.chemical;

import static modtweaker.mekanism.GasHelper.getGas;
import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.throwException;
import mekanism.api.ChemicalPair;
import mekanism.api.gas.GasStack;
import mekanism.common.recipe.RecipeHandler.Recipe;
import modtweaker.mekanism.MekanismAddRecipeAction;
import modtweaker.mekanism.TweakerMekanismBaseRemove;
import modtweaker.util.TweakerBaseFunction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class Infuser {
	public static class Add extends TweakerBaseFunction {
		public static final Add INSTANCE = new Add();
		
		private Add() {
			super("mekanism.chemical.infuser.addRecipe");
		}
		
		@Override
		public void perform(TweakerValue... args) {
			if (canContinue(3, args)) {
				GasStack gas1 = getGas();
				GasStack gas2 = getGas();
				GasStack output = getGas();
				Tweaker.apply(new MekanismAddRecipeAction(Recipe.CHEMICAL_INFUSER, new ChemicalPair(gas1, gas2), output));
			} else throwException(this, 3);
		}
	}
	
	public static class Remove extends TweakerMekanismBaseRemove {
		public static final Remove INSTANCE = new Remove();
		
		private Remove() {
			super("mekanism.chemical.infuser.removeRecipe", Recipe.CHEMICAL_INFUSER);
		}
	}
}
