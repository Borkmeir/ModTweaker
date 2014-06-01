package modtweaker.mariculture.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.mariculture.action.CrucibleRemoveFuelAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleRemoveItemFuel extends TweakerFunction {
	public static final CrucibleRemoveItemFuel INSTANCE = new CrucibleRemoveItemFuel();
	
	private CrucibleRemoveItemFuel() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItem output = getItem(0, arguments);
		Tweaker.apply(new CrucibleRemoveFuelAction(output.make()));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.crucible.removeItemFuel";
	}
}
