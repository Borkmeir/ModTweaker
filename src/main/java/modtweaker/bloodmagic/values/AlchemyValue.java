package modtweaker.bloodmagic.values;

import modtweaker.bloodmagic.function.AlchemyAddRecipeFunction;
import modtweaker.bloodmagic.function.AlchemyRemoveRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class AlchemyValue extends TweakerValue {
	public static final AlchemyValue INSTANCE = new AlchemyValue();
	
	private AlchemyValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe")) return AlchemyAddRecipeFunction.INSTANCE;
		if(index.equals("removeRecipe")) return AlchemyRemoveRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "bloodmagic.alchemy";
	}
}
