package modtweaker.mariculture.values;

import stanhebben.minetweaker.api.value.TweakerValue;

public class MaricultureValue extends TweakerValue {
	public static final MaricultureValue INSTANCE = new MaricultureValue();
	
	private MaricultureValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("vat")) return VatValue.INSTANCE;
		if(index.equals("fishing")) return FishingValue.INSTANCE;
		if(index.equals("crucible")) return CrucibleValue.INSTANCE;
		if(index.equals("anvil")) return AnvilValue.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "mariculture";
	}
}
