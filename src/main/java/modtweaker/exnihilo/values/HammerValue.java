package modtweaker.exnihilo.values;

import modtweaker.exnihilo.function.HammerAddRecipeFunction;
import modtweaker.exnihilo.function.HammerRemoveRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class HammerValue extends TweakerValue {
	public static final HammerValue INSTANCE = new HammerValue();
	
	private HammerValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe")) return HammerAddRecipeFunction.INSTANCE;
		if(index.equals("removeRecipe")) return HammerRemoveRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "exnihilo.hammer";
	}
}
