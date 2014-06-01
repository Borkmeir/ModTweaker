package modtweaker.exnihilo.values;

import modtweaker.exnihilo.function.CompostAddRecipeFunction;
import modtweaker.exnihilo.function.CompostRemoveRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CompostValue extends TweakerValue {
	public static final CompostValue INSTANCE = new CompostValue();
	
	private CompostValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe")) return CompostAddRecipeFunction.INSTANCE;
		if(index.equals("removeRecipe")) return CompostRemoveRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "exnihilo.compost";
	}
}
