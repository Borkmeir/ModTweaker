package modtweaker.mariculture.function;

import static modtweaker.helpers.TweakerHelper.getFluid;
import static modtweaker.helpers.TweakerHelper.getInt;
import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import mariculture.api.core.RecipeVat;
import modtweaker.mariculture.action.VatAddRecipeAction;
import modtweaker.mariculture.values.VatValue;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class VatSoakFunction extends TweakerFunction {
	public static final VatSoakFunction INSTANCE = new VatSoakFunction();
	
	private VatSoakFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 4) throwException(toString(), 4); 
		TweakerItemStack input = getItem(0, arguments);
		TweakerLiquidStack fluid = getFluid(1, arguments);
		TweakerItemStack output = getItem(2, arguments);
		int time = getInt(3, arguments);
		Tweaker.apply(new VatAddRecipeAction(new RecipeVat(input.get(), fluid.get(), output.get(), time)));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.vat." + VatValue.ADD_ITEM;
	}
}
