package modtweaker.exnihilo.values;

import modtweaker.exnihilo.function.HeatAddRecipeFunction;
import modtweaker.exnihilo.function.HeatRemoveRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class HeatValue extends TweakerValue {
	public static final HeatValue INSTANCE = new HeatValue();
	
	private HeatValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe")) return HeatAddRecipeFunction.INSTANCE;
		if(index.equals("removeRecipe")) return HeatRemoveRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "exnihilo.heat";
	}
}
