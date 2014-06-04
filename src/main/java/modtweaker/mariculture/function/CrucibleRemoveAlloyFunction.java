package modtweaker.mariculture.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.mariculture.action.CrucibleRemoveRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleRemoveAlloyFunction extends TweakerFunction {
	public static final CrucibleRemoveAlloyFunction INSTANCE = new CrucibleRemoveAlloyFunction();
	
	private CrucibleRemoveAlloyFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 2) throwException(toString(), 2);
		TweakerItemStack output = GetItemOld(0, arguments);
		TweakerItemStack output2 = GetItemOld(1, arguments);
		Tweaker.apply(new CrucibleRemoveRecipeAction(output, output2));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.crucible.removeAlloy";
	}
}
