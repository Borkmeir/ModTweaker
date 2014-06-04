package modtweaker.mariculture.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.mariculture.action.FishingRemoveLootAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class FishingRemoveLootFunction extends TweakerFunction {
	public static final FishingRemoveLootFunction INSTANCE = new FishingRemoveLootFunction();
	
	private FishingRemoveLootFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItemStack output = GetItemOld(0, arguments);
		Tweaker.apply(new FishingRemoveLootAction(output));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.fishing.remove";
	}
}
