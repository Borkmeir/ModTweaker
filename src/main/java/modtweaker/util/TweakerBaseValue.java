package modtweaker.util;

import modtweaker.mekanism.Combiner;
import stanhebben.minetweaker.api.value.TweakerValue;

public abstract class TweakerBaseValue extends TweakerValue {
	private String str;
	public TweakerBaseValue(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}
}
