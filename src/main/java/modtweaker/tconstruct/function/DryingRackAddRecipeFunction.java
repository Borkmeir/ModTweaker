package modtweaker.tconstruct.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.getInt;
import modtweaker.tconstruct.actions.DryingRackAddRecipeAction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class DryingRackAddRecipeFunction extends TweakerFunction {
	public static final DryingRackAddRecipeFunction INSTANCE = new DryingRackAddRecipeFunction();
	
	private DryingRackAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 3) {
			ItemStack input = GetItemOld(0, arguments).get();
			ItemStack output = GetItemOld(1, arguments).get();
			int time = getInt(2, arguments);
			Tweaker.apply(new DryingRackAddRecipeAction(input, time, output));
		} else {
			throw new TweakerExecuteException(toString() + " requires 3 arguments");
		}
		return null;
	}

	@Override
	public String toString() {
		return "tconstruct.drying.addRecipe";
	}
}
