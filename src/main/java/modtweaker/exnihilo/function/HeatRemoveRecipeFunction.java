package modtweaker.exnihilo.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.exnihilo.action.HeatRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class HeatRemoveRecipeFunction extends TweakerFunction {
	public static final HeatRemoveRecipeFunction INSTANCE = new HeatRemoveRecipeFunction();
	
	private HeatRemoveRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItemStack output = getItem(0, arguments);
		Tweaker.apply(new HeatRemoveRecipeAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "exnihilo.heat.removeRecipe";
	}
}
