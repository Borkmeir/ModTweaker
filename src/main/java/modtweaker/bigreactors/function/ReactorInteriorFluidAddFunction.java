package modtweaker.bigreactors.function;

import static modtweaker.util.TweakerHelper.getFloat;
import static modtweaker.util.TweakerHelper.getString;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.bigreactors.action.ReactorInteriorFluidAddAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class ReactorInteriorFluidAddFunction extends TweakerFunction {
	public static final ReactorInteriorFluidAddFunction INSTANCE = new ReactorInteriorFluidAddFunction();
	
	private ReactorInteriorFluidAddFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 5) throwException(toString(), 5); 
		String name = getString(0, arguments);
		float absorption = getFloat(1, arguments);
		float heatEfficiency = getFloat(2, arguments);
		float moderation = getFloat(3, arguments);
		float heatConductivity = getFloat(4, arguments);
		Tweaker.apply(new ReactorInteriorFluidAddAction(name, absorption, heatEfficiency, moderation, heatConductivity));
		return null;
	}

	@Override
	public String toString() {
		return "bigreactors.addInteriorFluid";
	}
}
