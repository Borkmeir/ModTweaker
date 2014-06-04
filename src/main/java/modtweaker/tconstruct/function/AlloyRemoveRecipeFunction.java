package modtweaker.tconstruct.function;

import static modtweaker.util.TweakerHelper.getFluid;
import modtweaker.tconstruct.actions.AlloyRemoveRecipeAction;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class AlloyRemoveRecipeFunction extends TweakerFunction {
	public static final AlloyRemoveRecipeFunction INSTANCE = new AlloyRemoveRecipeFunction();
	
	private AlloyRemoveRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 1) {
			FluidStack output = getFluid(0, arguments).get();
			Tweaker.apply(new AlloyRemoveRecipeAction(output));
		} else {
			throw new TweakerExecuteException(toString() + " requires 1 argument");
		}
		return null;
	}

	@Override
	public String toString() {
		return "tconstruct.smeltery.removeAlloy";
	}
}
