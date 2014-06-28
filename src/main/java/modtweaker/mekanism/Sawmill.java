package modtweaker.mekanism;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getDouble;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import mekanism.api.ChanceOutput;
import mekanism.common.recipe.RecipeHandler.Recipe;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class Sawmill {
	public static class Add extends TweakerBaseFunction {
		public static final Add INSTANCE = new Add();
		
		private Add() {
			super("mekanism.sawmill.addRecipe");
		}
		
		@Override
		public void perform(TweakerValue... args) {
			if (canContinue(4, args)) {
				ItemStack input = getItem();
				ItemStack output1 = getItem();
				ItemStack output2 = getItem();
				double chance = getDouble() / 100; 
				Tweaker.apply(new MekanismAddRecipeAction(Recipe.PRECISION_SAWMILL, input, new ChanceOutput(output1, output2, chance)));
			} else throwException(this, 4);
		}
	}
	
	public static class Remove extends TweakerMekanismBaseRemove {
		public static final Remove INSTANCE = new Remove();
		
		private Remove() {
			super("mekanism.sawmill.removeRecipe", Recipe.PRECISION_SAWMILL);
		}
		
		@Override
		public void perform(TweakerValue... args) {
			if (canContinue(new int[] { 1, 2 }, args)) {
				ItemStack output1 = getItem();
				ItemStack output2 = null;
				if(args.length == 2) {
					output2 = getItem();
				}
				
				Tweaker.apply(new MekanismRemoveRecipeAction(recipe, new ChanceOutput(output1, output2, 100)));
			} else throwException(this, new int[] { 1, 2 });
		}
	}
}
