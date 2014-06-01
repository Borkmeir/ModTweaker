package modtweaker.mariculture.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.mariculture.action.CrucibleRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleRemoveAlloyFunction extends TweakerFunction {
	public static final CrucibleRemoveAlloyFunction INSTANCE = new CrucibleRemoveAlloyFunction();
	
	private CrucibleRemoveAlloyFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 2) throwException(toString(), 2);
		TweakerItem output = getItem(0, arguments);
		TweakerItem output2 = getItem(1, arguments);
		Tweaker.apply(new CrucibleRemoveRecipeAction(output, output2));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.crucible.removeAlloy";
	}
}
