package modtweaker.exnihilo.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.exnihilo.action.CrucibleRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleRemoveRecipeFunction extends TweakerFunction {
	public static final CrucibleRemoveRecipeFunction INSTANCE = new CrucibleRemoveRecipeFunction();
	
	private CrucibleRemoveRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItemStack output = GetItemOld(0, arguments);
		Tweaker.apply(new CrucibleRemoveRecipeAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "exnihilo.crucible.removeRecipe";
	}
}
