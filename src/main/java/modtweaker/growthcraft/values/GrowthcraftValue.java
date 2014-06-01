package modtweaker.growthcraft.values;

import stanhebben.minetweaker.api.value.TweakerValue;

public class GrowthcraftValue extends TweakerValue {
	public static final GrowthcraftValue INSTANCE = new GrowthcraftValue();
	
	private GrowthcraftValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("fishtrap")) return FishTrapValue.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "growthcraft";
	}
}
