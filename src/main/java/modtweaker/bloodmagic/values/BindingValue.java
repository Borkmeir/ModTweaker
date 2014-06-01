package modtweaker.bloodmagic.values;

import modtweaker.bloodmagic.function.BindingAddRecipeFunction;
import modtweaker.bloodmagic.function.BindingRemoveRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class BindingValue extends TweakerValue {
	public static final BindingValue INSTANCE = new BindingValue();
	
	private BindingValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe")) return BindingAddRecipeFunction.INSTANCE;
		if(index.equals("removeRecipe")) return BindingRemoveRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "bloodmagic.binding";
	}
}
