package modtweaker.tconstruct.values;

import stanhebben.minetweaker.api.value.TweakerValue;

public class TConstructValue extends TweakerValue {
	public static final TConstructValue INSTANCE = new TConstructValue();
	
	private TConstructValue() {}
	
	@Override
	public TweakerValue index(String index) {
		//if(index.equals("crusher")) return CrusherValue.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "tconstruct";
	}
}
