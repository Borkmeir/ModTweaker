package modtweaker.mariculture.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.throwException;
import mariculture.api.core.RecipeVat;
import modtweaker.mariculture.Mariculture.VatValue;
import modtweaker.mariculture.action.VatAddRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class VatSettleFunction extends TweakerFunction {
	public static final VatSettleFunction INSTANCE = new VatSettleFunction();
	
	private VatSettleFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 4) throwException(toString(), 4); 
		TweakerLiquidStack fluid = getFluid(0, arguments);
		TweakerLiquidStack fluid2 = getFluid(1, arguments);
		TweakerItemStack output = GetItemOld(2, arguments);
		int time = getInt(3, arguments);
		Tweaker.apply(new VatAddRecipeAction(new RecipeVat(fluid.get(), fluid2.get(), output.get(), time)));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.vat." + VatValue.ADD_FLUIDS_ITEM;
	}
}
