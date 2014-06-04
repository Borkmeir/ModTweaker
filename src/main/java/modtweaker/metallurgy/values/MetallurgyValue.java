package modtweaker.metallurgy.values;

import stanhebben.minetweaker.api.value.TweakerValue;

public class MetallurgyValue extends TweakerValue {
	public static final MetallurgyValue INSTANCE = new MetallurgyValue();
	
	private MetallurgyValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("crusher")) return CrusherValue.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "metallurgy";
	}
}
