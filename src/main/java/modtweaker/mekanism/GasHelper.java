package modtweaker.mekanism;

import mekanism.api.gas.GasStack;
import modtweaker.util.TweakerHelper;
import stanhebben.minetweaker.api.value.TweakerValue;
import cpw.mods.fml.common.Optional;

public class GasHelper extends TweakerHelper {
	/* Returns a gasstack, if it's not null then it will increase the index */
	@Optional.Method(modid = "Mekanism")
	public static GasStack getGas() {
		GasStack stack = ((TweakerGasBase)notNull(arguments[index], index)).toGasStack("argument " + index + " must be a gas").get();
		if (stack != null) {
			index++;
		}

		return stack;
	}
	
	public static TweakerValue notNull(TweakerValue value, int arg) {
		if(value instanceof TweakerGasBase) return TweakerGasBase.notNull(value, "argument " + arg + " must not be null");
		return TweakerValue.notNull(value, "argument " + arg + " must not be null");
	}
}
