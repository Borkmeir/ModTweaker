package modtweaker.exnihilo.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.exnihilo.action.CompostRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CompostRemoveRecipeFunction extends TweakerFunction {
	public static final CompostRemoveRecipeFunction INSTANCE = new CompostRemoveRecipeFunction();
	
	private CompostRemoveRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItemStack output = getItem(0, arguments);
		Tweaker.apply(new CompostRemoveRecipeAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "exnihilo.compost.removeRecipe";
	}
}
