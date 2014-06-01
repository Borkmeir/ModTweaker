package modtweaker.exnihilo.values;

import modtweaker.exnihilo.function.CrucibleAddRecipeFunction;
import modtweaker.exnihilo.function.CrucibleRemoveRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleValue extends TweakerValue {
	public static final CrucibleValue INSTANCE = new CrucibleValue();
	
	private CrucibleValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe")) return CrucibleAddRecipeFunction.INSTANCE;
		if(index.equals("removeRecipe")) return CrucibleRemoveRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "exnihilo.crucible";
	}
}
