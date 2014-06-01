package modtweaker.growthcraft.values;

import modtweaker.growthcraft.action.function.FishTrapAddFishFunction;
import modtweaker.growthcraft.action.function.FishTrapAddJunkFunction;
import modtweaker.growthcraft.action.function.FishTrapAddTreasureFunction;
import modtweaker.growthcraft.action.function.FishTrapRemoveFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class FishTrapValue extends TweakerValue {
	public static final FishTrapValue INSTANCE = new FishTrapValue();
	
	private FishTrapValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addJunk")) return FishTrapAddJunkFunction.INSTANCE;
		if(index.equals("addTreasure")) return FishTrapAddTreasureFunction.INSTANCE;
		if(index.equals("addFish")) return FishTrapAddFishFunction.INSTANCE;
		if(index.equals("remove")) return FishTrapRemoveFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "growthcraft.fishtrap";
	}
}
