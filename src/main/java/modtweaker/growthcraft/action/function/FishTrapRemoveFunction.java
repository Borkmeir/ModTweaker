package modtweaker.growthcraft.action.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.growthcraft.action.FishTrapAddLootAction.Rarity;
import modtweaker.growthcraft.action.FishTrapRemoveLootAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerValue;

public class FishTrapRemoveFunction extends TweakerFunction {
	public static final FishTrapRemoveFunction INSTANCE = new FishTrapRemoveFunction();
	
	private FishTrapRemoveFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 1) throwException(toString(), 1);
		TweakerItem output = getItem(0, arguments);
		Tweaker.apply(new FishTrapRemoveLootAction(output, Rarity.FISH));
		Tweaker.apply(new FishTrapRemoveLootAction(output, Rarity.JUNK));
		Tweaker.apply(new FishTrapRemoveLootAction(output, Rarity.TREASURE));
		return null;
	}

	@Override
	public String toString() {
		return "growthcraft.fishtrap.remove";
	}
}
