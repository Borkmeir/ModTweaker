package modtweaker.mariculture.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.getItemNull;
import mariculture.api.core.RecipeSmelter;
import modtweaker.mariculture.action.CrucibleAddRecipeAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleAddAlloyFunction extends TweakerFunction {
	public static final CrucibleAddAlloyFunction INSTANCE = new CrucibleAddAlloyFunction();
	
	private CrucibleAddAlloyFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 4 || arguments.length == 6) {
			int temp = Math.max(1, getInt(0, arguments));
			TweakerItemStack item = GetItemOld(1, arguments);
			TweakerItemStack item2 = GetItemOld(2, arguments);
			TweakerLiquidStack fluid = getFluid(3, arguments);
			TweakerItemStack output = null;
			int chance = 0;
			if(arguments.length == 6) {
				output = getItemNull(4, arguments);
				chance = getInt(5, arguments);
				if(chance <= 0) output = null;
			}
			
			if(output == null) {
				Tweaker.apply(new CrucibleAddRecipeAction(new RecipeSmelter(item.get(), item2.get(), temp, fluid.get(), null, chance)));
			} else {
				Tweaker.apply(new CrucibleAddRecipeAction(new RecipeSmelter(item.get(), item2.get(), temp, fluid.get(), output.get(), chance)));
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
