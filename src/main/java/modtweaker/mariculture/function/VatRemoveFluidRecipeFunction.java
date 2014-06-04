package modtweaker.mariculture.function;

import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.mariculture.action.VatRemoveFluidRecipeAction;
import modtweaker.mariculture.values.VatValue;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class VatRemoveFluidRecipeFunction extends TweakerFunction {
	public static final VatRemoveFluidRecipeFunction INSTANCE = new VatRemoveFluidRecipeFunction();
	
	private VatRemoveFluidRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerLiquidStack output = getFluid(0, arguments);
		Tweaker.apply(new VatRemoveFluidRecipeAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.vat." + VatValue.RMV_FLUID;
	}
}
