package modtweaker.tconstruct.values;

import modtweaker.tconstruct.function.AlloyAddRecipeFunction;
import modtweaker.tconstruct.function.AlloyRemoveRecipeFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class SmelteryValue extends TweakerValue {
	public static final SmelteryValue INSTANCE = new SmelteryValue();
	
	private SmelteryValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addAlloy")) return AlloyAddRecipeFunction.INSTANCE;
		if(index.equals("removeAlloy")) return AlloyRemoveRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "tconstruct.smeltery";
	}
}
