package modtweaker.tconstruct.function;

import static modtweaker.util.TweakerHelper.getArray;
import static modtweaker.util.TweakerHelper.getFluid;
import modtweaker.tconstruct.actions.AlloyAddRecipeAction;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerArray;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class AlloyAddRecipeFunction extends TweakerFunction {
	public static final AlloyAddRecipeFunction INSTANCE = new AlloyAddRecipeFunction();
	
	private AlloyAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 2) {
			FluidStack output = getFluid(0, arguments).get();
			TweakerArray array = getArray(1, arguments);
			FluidStack[] input = new FluidStack[array.size()];
			for(int i = 0; i < array.size(); i++) {
				input[i] = array.get(0).asFluidStack().get();
			}
			
			Tweaker.apply(new AlloyAddRecipeAction(output, input));
		} else {
			throw new TweakerExecuteException(toString() + " requires 2 arguments");
		}
		return null;
	}

	@Override
	public String toString() {
		return "tconstruct.smeltery.addAlloy";
	}
}
