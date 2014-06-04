package modtweaker.mariculture.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.throwException;
import mariculture.api.core.RecipeVat;
import modtweaker.mariculture.action.VatAddRecipeAction;
import modtweaker.mariculture.values.VatValue;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class VatAllTheThingsFunction extends TweakerFunction {
	public static final VatAllTheThingsFunction INSTANCE = new VatAllTheThingsFunction();
	
	private VatAllTheThingsFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 6) throwException(toString(), 6); 
		TweakerLiquidStack fluid = getFluid(0, arguments);
		TweakerLiquidStack fluid2 = getFluid(1, arguments);
		TweakerItemStack input = GetItemOld(2, arguments);
		TweakerLiquidStack fluid3 = getFluid(3, arguments);
		TweakerItemStack output = GetItemOld(4, arguments);
		int time = getInt(5, arguments);
		Tweaker.apply(new VatAddRecipeAction(new RecipeVat(input.get(), fluid.get(), fluid2.get(), fluid3.get(), output.get(), time)));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.vat." + VatValue.ADD_ALL_MAKE_ALL;
	}
}
