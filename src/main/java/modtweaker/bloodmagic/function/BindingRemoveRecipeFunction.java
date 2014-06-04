package modtweaker.bloodmagic.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.bloodmagic.action.BindingRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class BindingRemoveRecipeFunction extends TweakerFunction {
	public static final BindingRemoveRecipeFunction INSTANCE = new BindingRemoveRecipeFunction();
	
	private BindingRemoveRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItemStack output = getItem(0, arguments);
		Tweaker.apply(new BindingRemoveRecipeAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "bloodmagic.binding.removeRecipe";
	}
}
