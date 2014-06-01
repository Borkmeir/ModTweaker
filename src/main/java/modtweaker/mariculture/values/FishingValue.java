package modtweaker.mariculture.values;

import modtweaker.mariculture.function.FishingAddJunkFunction;
import modtweaker.mariculture.function.FishingAddLootFunction;
import modtweaker.mariculture.function.FishingRemoveLootFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class FishingValue extends TweakerValue {
	public static final FishingValue INSTANCE = new FishingValue();
	
	private FishingValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addJunk")) return FishingAddJunkFunction.INSTANCE;
		if(index.equals("addLoot")) return FishingAddLootFunction.INSTANCE;
		if(index.equals("remove")) return FishingRemoveLootFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "mariculture.fishing";
	}
}
