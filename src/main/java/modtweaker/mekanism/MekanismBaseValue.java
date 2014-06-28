package modtweaker.mekanism;

import modtweaker.util.TweakerBaseFunction;
import modtweaker.util.TweakerBaseValue;
import stanhebben.minetweaker.api.value.TweakerValue;

public class MekanismBaseValue extends TweakerBaseValue {
	private final TweakerBaseFunction add;
	private final TweakerBaseFunction remove;
	protected MekanismBaseValue(String str, TweakerBaseFunction add, TweakerBaseFunction remove) {
		super(str);
		this.add = add;
		this.remove = remove;
	}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addRecipe"))    return add;
		if(index.equals("removeRecipe")) return remove;
		return super.index(index);
	}
}