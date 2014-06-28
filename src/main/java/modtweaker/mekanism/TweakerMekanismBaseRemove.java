package modtweaker.mekanism;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import mekanism.common.recipe.RecipeHandler.Recipe;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class TweakerMekanismBaseRemove extends TweakerBaseFunction {	
	protected final Recipe recipe;
	protected TweakerMekanismBaseRemove(String str, Recipe recipe) {
		super(str);
		this.recipe = recipe;
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(1, args)) {
			ItemStack stack = getItem();
			Tweaker.apply(new MekanismRemoveRecipeAction(recipe, stack));
		} else throwException(this, 1);
	}
}
