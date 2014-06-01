package modtweaker.exnihilo.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.exnihilo.action.SieveRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerValue;

public class SieveRemoveRecipeFunction extends TweakerFunction {
	public static final SieveRemoveRecipeFunction INSTANCE = new SieveRemoveRecipeFunction();
	
	private SieveRemoveRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItem output = getItem(0, arguments);
		Tweaker.apply(new SieveRemoveRecipeAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "exnihilo.sieve.removeRecipe";
	}
}
