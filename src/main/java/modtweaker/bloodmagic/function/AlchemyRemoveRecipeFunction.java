package modtweaker.bloodmagic.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.bloodmagic.action.AlchemicalRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class AlchemyRemoveRecipeFunction extends TweakerFunction {
	public static final AlchemyRemoveRecipeFunction INSTANCE = new AlchemyRemoveRecipeFunction();
	
	private AlchemyRemoveRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItemStack output = getItem(0, arguments);
		Tweaker.apply(new AlchemicalRemoveRecipeAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "bloodmagic.alchemy.removeRecipe";
	}
}
