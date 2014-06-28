package modtweaker.mekanism.chemical;

import static modtweaker.mekanism.GasHelper.getGas;
import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import mekanism.api.AdvancedInput;
import mekanism.api.gas.GasStack;
import mekanism.common.recipe.RecipeHandler.Recipe;
import modtweaker.mekanism.MekanismAddRecipeAction;
import modtweaker.mekanism.TweakerMekanismBaseRemove;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class Injection {
	public static class Add extends TweakerBaseFunction {
		public static final Add INSTANCE = new Add();
		
		private Add() {
			super("mekanism.chemical.injection.addRecipe");
		}
		
		@Override
		public void perform(TweakerValue... args) {
			if (canContinue(3, args)) {
				ItemStack input = getItem();
				//Only "water" and "hydrogenChloride" are valid here, or you will crash
				GasStack gas = getGas();
				ItemStack output = getItem();
				Tweaker.apply(new MekanismAddRecipeAction(Recipe.CHEMICAL_INJECTION_CHAMBER, new AdvancedInput(input, gas.getGas()), output));
			} else throwException(this, 3);
		}
	}
	
	public static class Remove extends TweakerMekanismBaseRemove {
		public static final Remove INSTANCE = new Remove();
		
		private Remove() {
			super("mekanism.chemical.injection.removeRecipe", Recipe.CHEMICAL_INJECTION_CHAMBER);
		}
	}
}
