package modtweaker.bloodmagic.values;

import modtweaker.bloodmagic.function.AltarAddRecipeFunction;
import modtweaker.bloodmagic.function.AltarRemoveRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class AltarValue extends TweakerValue {
	public static final AltarValue INSTANCE = new AltarValue();
	
	private AltarValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe")) return AltarAddRecipeFunction.INSTANCE;
		if(index.equals("removeRecipe")) return AltarRemoveRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "bloodmagic.altar";
	}
}
