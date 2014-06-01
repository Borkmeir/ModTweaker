package modtweaker.exnihilo.values;

import modtweaker.exnihilo.function.SieveAddRecipeFunction;
import modtweaker.exnihilo.function.SieveRemoveRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class SieveValue extends TweakerValue {
	public static final SieveValue INSTANCE = new SieveValue();
	
	private SieveValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe")) return SieveAddRecipeFunction.INSTANCE;
		if(index.equals("removeRecipe")) return SieveRemoveRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "exnihilo.sieve";
	}
}
