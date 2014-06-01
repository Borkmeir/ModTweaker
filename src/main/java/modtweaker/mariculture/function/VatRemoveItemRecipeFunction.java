package modtweaker.mariculture.function;

import modtweaker.mariculture.action.VatRemoveItemRecipeAction;
import modtweaker.mariculture.values.VatValue;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerValue;

public class VatRemoveItemRecipeFunction extends TweakerFunction {
	public static final VatRemoveItemRecipeFunction INSTANCE = new VatRemoveItemRecipeFunction();
	
	private VatRemoveItemRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throw new TweakerExecuteException("vat.removeItemRecipe requires 1 argument");
		TweakerItem output = notNull(arguments[0], "item cannot be null").toItem("item must be an item");
		Tweaker.apply(new VatRemoveItemRecipeAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.vat." + VatValue.RMV_ITEM;
	}
}
