package modtweaker.mekanism;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.getString;
import static modtweaker.util.TweakerHelper.throwException;
import mekanism.api.infuse.InfuseRegistry;
import mekanism.api.infuse.InfusionInput;
import mekanism.api.infuse.InfusionOutput;
import mekanism.common.recipe.RecipeHandler.Recipe;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class Infuser {
	public static class Add extends TweakerBaseFunction {
		public static final Add INSTANCE = new Add();
		
		private Add() {
			super("mekanism.infuser.addRecipe");
		}
		
		@Override
		public void perform(TweakerValue... args) {
			if (canContinue(4, args)) {
				String type = getString();
				int infuse = getInt();
				ItemStack input = getItem();
				ItemStack output = getItem();
				InfusionInput infusion = new InfusionInput(InfuseRegistry.get(type), infuse, input);
				Tweaker.apply(new MekanismAddRecipeAction(Recipe.METALLURGIC_INFUSER, infusion, new InfusionOutput(infusion, output)));
			} else throwException(this, 4);
		}
	}
	
	public static class Remove extends TweakerMekanismBaseRemove {
		public static final Remove INSTANCE = new Remove();
		
		private Remove() {
			super("mekanism.infuser.removeRecipe", Recipe.METALLURGIC_INFUSER);
		}
	}
}
