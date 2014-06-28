package modtweaker.mekanism;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import mekanism.api.AdvancedInput;
import mekanism.api.gas.GasRegistry;
import mekanism.common.recipe.RecipeHandler.Recipe;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class OsmiumCompressor {
	public static class Add extends TweakerBaseFunction {
		public static final Add INSTANCE = new Add();
		
		private Add() {
			super("mekanism.compressor.addRecipe");
		}
		
		@Override
		public void perform(TweakerValue... args) {
			if (canContinue(2, args)) {
				ItemStack input = getItem();
				ItemStack output = getItem();
				Tweaker.apply(new MekanismAddRecipeAction(Recipe.OSMIUM_COMPRESSOR, new AdvancedInput(input, GasRegistry.getGas("liquidOsmium")), output));
			} else throwException(this, 2);
		}
	}
	
	public static class Remove extends TweakerMekanismBaseRemove {
		public static final Remove INSTANCE = new Remove();
		
		private Remove() {
			super("mekanism.compressor.removeRecipe", Recipe.OSMIUM_COMPRESSOR);
		}
	}
}
