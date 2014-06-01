package modtweaker.mariculture.values;

import modtweaker.mariculture.function.AnvilAddRecipeFunction;
import modtweaker.mariculture.function.AnvilRemoveRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class AnvilValue extends TweakerValue {
	public static final AnvilValue INSTANCE = new AnvilValue();
	
	private AnvilValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe")) return AnvilAddRecipeFunction.INSTANCE;
		if(index.equals("removeRecipe")) return AnvilRemoveRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "mariculture.anvil";
	}
}
