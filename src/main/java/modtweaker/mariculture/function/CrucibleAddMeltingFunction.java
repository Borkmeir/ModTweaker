package modtweaker.mariculture.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.getInt;
import mariculture.api.core.RecipeSmelter;
import modtweaker.mariculture.action.CrucibleAddRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleAddMeltingFunction extends TweakerFunction {
	public static final CrucibleAddMeltingFunction INSTANCE = new CrucibleAddMeltingFunction();
	
	private CrucibleAddMeltingFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 3 || arguments.length == 5) {
			int temp = Math.max(1, getInt(0, arguments));
			TweakerItemStack item = GetItemOld(1, arguments);
			TweakerLiquidStack fluid = getFluid(2, arguments);
			TweakerItemStack output = null;
			int chance = 0;
			if(arguments.length == 5) {
				output = GetItemOld(3, arguments);
				chance = getInt(4, arguments);
				if(chance <= 0) output = null;
			}
			
			if(output == null) {
				Tweaker.apply(new CrucibleAddRecipeAction(new RecipeSmelter(item.get(), null, temp, fluid.get(), null, chance)));
			} else {
				Tweaker.apply(new CrucibleAddRecipeAction(new RecipeSmelter(item.get(), null, temp, fluid.get(), output.get(), chance)));
			}
		} else {
			throw new TweakerExecuteException(toString() + " requires 3 or 5 arguments");
		}
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.crucible.addMelting";
	}
}
