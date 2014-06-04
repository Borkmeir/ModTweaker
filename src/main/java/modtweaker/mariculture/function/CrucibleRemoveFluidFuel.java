package modtweaker.mariculture.function;

import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.mariculture.action.CrucibleRemoveFuelAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleRemoveFluidFuel extends TweakerFunction {
	public static final CrucibleRemoveFluidFuel INSTANCE = new CrucibleRemoveFluidFuel();
	
	private CrucibleRemoveFluidFuel() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerLiquidStack output = getFluid(0, arguments);
		Tweaker.apply(new CrucibleRemoveFuelAction(output.get()));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.crucible.removeFluidFuel";
	}
}
