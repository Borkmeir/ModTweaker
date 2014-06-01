package modtweaker.mariculture.function;

import static modtweaker.helpers.TweakerHelper.getFluid;
import static modtweaker.helpers.TweakerHelper.getInt;
import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.getItemNull;
import mariculture.api.core.RecipeSmelter;
import modtweaker.mariculture.action.CrucibleAddRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleAddAlloyFunction extends TweakerFunction {
	public static final CrucibleAddAlloyFunction INSTANCE = new CrucibleAddAlloyFunction();
	
	private CrucibleAddAlloyFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 4 || arguments.length == 6) {
			int temp = Math.max(1, getInt(0, arguments));
			TweakerItem item = getItem(1, arguments);
			TweakerItem item2 = getItem(2, arguments);
			TweakerLiquidStack fluid = getFluid(3, arguments);
			TweakerItem output = null;
			int chance = 0;
			if(arguments.length == 6) {
				output = getItemNull(4, arguments);
				chance = getInt(5, arguments);
				if(chance <= 0) output = null;
			}
			
			if(output == null) {
				Tweaker.apply(new CrucibleAddRecipeAction(new RecipeSmelter(item.make(), item2.make(), temp, fluid.get(), null, chance)));
			} else {
				Tweaker.apply(new CrucibleAddRecipeAction(new RecipeSmelter(item.make(), item2.make(), temp, fluid.get(), output.make(), chance)));
			}		
		} else {
			throw new TweakerExecuteException(toString() + " requires 4 or 6 arguments");
		}
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.crucible.addAlloy";
	}
}
