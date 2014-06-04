package modtweaker.bigreactors.function;

import static modtweaker.helpers.TweakerHelper.getBoolean;
import static modtweaker.helpers.TweakerHelper.getFluid;
import static modtweaker.helpers.TweakerHelper.getHex;
import static modtweaker.helpers.TweakerHelper.getString;
import modtweaker.bigreactors.action.FuelsAddAction;
import net.minecraftforge.fluids.Fluid;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;
import erogenousbeef.bigreactors.common.data.ReactorFuel;

public class FuelsAddFunction extends TweakerFunction {
	public static final FuelsAddFunction INSTANCE = new FuelsAddFunction();

	private FuelsAddFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if (arguments.length == 5 || arguments.length == 6) {
			String name = getString(0, arguments);
			Fluid fluid = getFluid(1, arguments).get().getFluid();
			int color = getHex(2, arguments);
			boolean isFuel = getBoolean(3, arguments);
			boolean isWaste = getBoolean(4, arguments);
			Fluid fluid2 = null;
			if (arguments.length == 6) {
				fluid2 = getFluid(5, arguments).get().getFluid();
			}

			ReactorFuel fuel = null;
			if (fluid2 != null) {
				fuel = new ReactorFuel(fluid, color, isFuel, isWaste);
			} else {
				fuel = new ReactorFuel(fluid, color, isFuel, isWaste, fluid2);
			}

			Tweaker.apply(new FuelsAddAction(name, fuel));
		} else {
			throw new TweakerExecuteException(toString() + " requires 5 or 6 arguments");
		}
		return null;
	}

	@Override
	public String toString() {
		return "bigreactors.addFuel";
	}
}
