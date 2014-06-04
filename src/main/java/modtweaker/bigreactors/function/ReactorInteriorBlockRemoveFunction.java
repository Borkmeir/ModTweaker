package modtweaker.bigreactors.function;

import static modtweaker.helpers.TweakerHelper.getString;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.bigreactors.action.ReactorInteriorBlockRemoveAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class ReactorInteriorBlockRemoveFunction extends TweakerFunction {
	public static final ReactorInteriorBlockRemoveFunction INSTANCE = new ReactorInteriorBlockRemoveFunction();

	private ReactorInteriorBlockRemoveFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if (arguments.length != 1) throwException(toString(), 1);
		String name = getString(0, arguments);
		Tweaker.apply(new ReactorInteriorBlockRemoveAction(name));
		return null;
	}

	@Override
	public String toString() {
		return "bigreactors.removeInteriorBlock";
	}
}
