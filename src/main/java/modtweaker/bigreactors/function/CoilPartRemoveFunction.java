package modtweaker.bigreactors.function;

import static modtweaker.util.TweakerHelper.getString;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.bigreactors.action.CoilPartRemoveAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CoilPartRemoveFunction extends TweakerFunction {
	public static final CoilPartRemoveFunction INSTANCE = new CoilPartRemoveFunction();

	private CoilPartRemoveFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if (arguments.length != 1) throwException(toString(), 1);
		String name = getString(0, arguments);
		Tweaker.apply(new CoilPartRemoveAction(name));
		return null;
	}

	@Override
	public String toString() {
		return "bigreactors.removeCoilPart";
	}
}
