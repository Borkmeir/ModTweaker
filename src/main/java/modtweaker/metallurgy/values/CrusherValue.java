package modtweaker.metallurgy.values;

import modtweaker.metallurgy.function.CrusherAddRecipeFunction;
import modtweaker.metallurgy.function.CrusherRemoveRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrusherValue extends TweakerValue {
	public static final CrusherValue INSTANCE = new CrusherValue();
	
	private CrusherValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe")) return CrusherAddRecipeFunction.INSTANCE;
		if(index.equals("removeRecipe")) return CrusherRemoveRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "metallurgy.crusher";
	}
}
