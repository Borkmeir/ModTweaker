package modtweaker.metallurgy.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.metallurgy.action.CrusherRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrusherRemoveRecipeFunction extends TweakerFunction {
	public static final CrusherRemoveRecipeFunction INSTANCE = new CrusherRemoveRecipeFunction();
	
	private CrusherRemoveRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItemStack output = getItem(0, arguments);
		Tweaker.apply(new CrusherRemoveRecipeAction(output.get()));
		return null;
	}

	@Override
	public String toString() {
		return "metallurgy.crusher.removeRecipe";
	}
}
