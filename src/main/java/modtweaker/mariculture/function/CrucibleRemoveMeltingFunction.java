package modtweaker.mariculture.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.mariculture.action.CrucibleRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleRemoveMeltingFunction extends TweakerFunction {
	public static final CrucibleRemoveMeltingFunction INSTANCE = new CrucibleRemoveMeltingFunction();
	
	private CrucibleRemoveMeltingFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItemStack output = getItem(0, arguments);
		Tweaker.apply(new CrucibleRemoveRecipeAction(output, null));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.crucible.removeMelting";
	}
}
