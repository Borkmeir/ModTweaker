package modtweaker.mekanism;

import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.value.TweakerNull;
import stanhebben.minetweaker.api.value.TweakerValue;

public class TweakerGasBase extends TweakerValue {
	public TweakerGas asGas() {
		return null;
	}
	
	public TweakerGasStack asGasStack() {
		return null;
	}
	
	@Override
	public String toString() {
		return "";
	}
	
	public static TweakerGasBase notNull(TweakerValue value, String error) {
		if (value == null || value == TweakerNull.INSTANCE || (!(value instanceof TweakerGasBase))) throw new TweakerExecuteException(error);
		return (TweakerGasBase) value;
	}

	public TweakerGasStack toGasStack(String string) {
		TweakerGasStack result = asGasStack();
		if (result == null) {
			throw new TweakerExecuteException(string);
		}
		return result;
	}
}
