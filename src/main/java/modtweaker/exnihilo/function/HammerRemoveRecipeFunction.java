package modtweaker.exnihilo.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.exnihilo.action.HammerRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class HammerRemoveRecipeFunction extends TweakerFunction {
	public static final HammerRemoveRecipeFunction INSTANCE = new HammerRemoveRecipeFunction();
	
	private HammerRemoveRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItemStack output = GetItemOld(0, arguments);
		Tweaker.apply(new HammerRemoveRecipeAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "exnihilo.hammer.removeRecipe";
	}
}
