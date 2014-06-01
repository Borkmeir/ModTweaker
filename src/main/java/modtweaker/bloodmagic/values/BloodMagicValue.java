package modtweaker.bloodmagic.values;

import stanhebben.minetweaker.api.value.TweakerValue;

public class BloodMagicValue extends TweakerValue {
	public static final BloodMagicValue INSTANCE = new BloodMagicValue();
	
	private BloodMagicValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("altar")) return AltarValue.INSTANCE;
		if(index.equals("alchemy")) return AlchemyValue.INSTANCE;
		if(index.equals("binding")) return BindingValue.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "bloodmagic";
	}
}
