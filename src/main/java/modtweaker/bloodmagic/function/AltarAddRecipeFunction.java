package modtweaker.bloodmagic.function;

import static modtweaker.helpers.TweakerHelper.getInt;
import static modtweaker.helpers.TweakerHelper.getItem;
import modtweaker.bloodmagic.action.AltarAddRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerValue;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;

public class AltarAddRecipeFunction extends TweakerFunction {
	public static final AltarAddRecipeFunction INSTANCE = new AltarAddRecipeFunction();
	
	private AltarAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length >= 4 && arguments.length <= 6) {
			TweakerItem input = getItem(0, arguments);
			TweakerItem output = getItem(1, arguments);
			int tier = getInt(2, arguments);
			int lp = getInt(3, arguments);
			int consumption = 5;
			int drain = 5;
			if(arguments.length >= 5) {
				consumption = getInt(4, arguments);
				if(arguments.length >= 6) {
					drain = getInt(5, arguments);
				}
			}
			
			Tweaker.apply(new AltarAddRecipeAction(new AltarRecipe(output.make(), input.make(), tier, lp, consumption, drain, false)));
		} else {
			throw new TweakerExecuteException(toString() + " requires 4 to 6 arguments");
		}
		return null;
	}

	@Override
	public String toString() {
		return "bloodmagic.altar.addRecipe";
	}
}
