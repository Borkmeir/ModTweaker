package modtweaker.bloodmagic.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.bloodmagic.action.AltarRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerValue;

public class AltarRemoveRecipeFunction extends TweakerFunction {
	public static final AltarRemoveRecipeFunction INSTANCE = new AltarRemoveRecipeFunction();
	
	private AltarRemoveRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItem output = getItem(0, arguments);
		Tweaker.apply(new AltarRemoveRecipeAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "bloodmagic.altar.removeRecipe";
	}
}
