package modtweaker.mariculture.function;

import static modtweaker.helpers.TweakerHelper.getFluid;
import static modtweaker.helpers.TweakerHelper.getFluidNull;
import static modtweaker.helpers.TweakerHelper.getInt;
import static modtweaker.helpers.TweakerHelper.getItemNull;
import static modtweaker.helpers.TweakerHelper.throwException;
import mariculture.api.core.RecipeVat;
import modtweaker.mariculture.action.VatAddRecipeAction;
import modtweaker.mariculture.values.VatValue;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class VatAddRecipeFunction extends TweakerFunction {
	public static final VatAddRecipeFunction INSTANCE = new VatAddRecipeFunction();
	
	private VatAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 6) throwException(toString(), 6); 
		TweakerLiquidStack fluid = getFluid(0, arguments);
		TweakerLiquidStack fluid2 = getFluidNull(1, arguments);
		TweakerItem input = getItemNull(2, arguments);
		TweakerLiquidStack fluid3 = getFluidNull(3, arguments);
		TweakerItem output = getItemNull(4, arguments);
		int time = getInt(5, arguments);
		Tweaker.apply(new VatAddRecipeAction(new RecipeVat(input.make(), fluid.get(), fluid2.get(), fluid3.get(), output.make(), time)));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.vat." + VatValue.ADD_RECIPE;
	}
}
