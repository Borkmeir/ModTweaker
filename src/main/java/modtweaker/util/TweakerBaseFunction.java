package modtweaker.util;

import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public abstract class TweakerBaseFunction extends TweakerFunction {
	private String str;
	public TweakerBaseFunction(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}
	
	public abstract void perform(TweakerValue... args);
	
	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		perform(arguments);
		return null;
	}
}
