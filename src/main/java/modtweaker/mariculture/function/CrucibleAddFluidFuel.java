package modtweaker.mariculture.function;

import static modtweaker.helpers.TweakerHelper.getFluid;
import static modtweaker.helpers.TweakerHelper.getInt;
import static modtweaker.helpers.TweakerHelper.throwException;
import mariculture.api.core.FuelInfo;
import modtweaker.mariculture.action.CrucibleAddFuelAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleAddFluidFuel extends TweakerFunction {
	public static final CrucibleAddFluidFuel INSTANCE = new CrucibleAddFluidFuel();
	
	private CrucibleAddFluidFuel() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 4) throwException(toString(), 4); 
		TweakerLiquidStack fluid = getFluid(0, arguments);
		int max = getInt(1, arguments);
		int per = getInt(2, arguments);
		int time = getInt(3, arguments);
		Tweaker.apply(new CrucibleAddFuelAction(fluid.get(), new FuelInfo(max, per, time)));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.crucible.addFluidFuel";
	}
}
