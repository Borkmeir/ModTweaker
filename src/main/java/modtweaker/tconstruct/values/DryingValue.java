package modtweaker.tconstruct.values;

import modtweaker.tconstruct.function.DryingRackAddRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class DryingValue extends TweakerValue {
	public static final DryingValue INSTANCE = new DryingValue();
	
	private DryingValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe")) return DryingRackAddRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "tconstruct.drying";
	}
}
