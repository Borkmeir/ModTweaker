package modtweaker.mariculture.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.mariculture.action.AnvilRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class AnvilRemoveRecipeFunction extends TweakerFunction {
	public static final AnvilRemoveRecipeFunction INSTANCE = new AnvilRemoveRecipeFunction();
	
	private AnvilRemoveRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItemStack output = GetItemOld(0, arguments);
		Tweaker.apply(new AnvilRemoveRecipeAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.anvil.removeRecipe";
	}
}
