package modtweaker.bigreactors.function;

import static modtweaker.helpers.TweakerHelper.getFloat;
import static modtweaker.helpers.TweakerHelper.getString;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.bigreactors.action.CoilPartAddAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CoilPartAddFunction extends TweakerFunction {
	public static final CoilPartAddFunction INSTANCE = new CoilPartAddFunction();
	
	private CoilPartAddFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 4) throwException(toString(), 4); 
		String name = getString(0, arguments);
		float efficiency = getFloat(1, arguments);
		float bonus = getFloat(2, arguments);
		float rate = getFloat(3, arguments);
		Tweaker.apply(new CoilPartAddAction(name, efficiency, bonus, rate));
		return null;
	}

	@Override
	public String toString() {
		return "bigreactors.addCoilPart";
	}
}
