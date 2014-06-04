package modtweaker.bigreactors.values;

import modtweaker.bigreactors.function.CoilPartAddFunction;
import modtweaker.bigreactors.function.CoilPartRemoveFunction;
import modtweaker.bigreactors.function.FuelsAddFunction;
import modtweaker.bigreactors.function.FuelsRemoveFunction;
import modtweaker.bigreactors.function.ReactorInteriorBlockAddFunction;
import modtweaker.bigreactors.function.ReactorInteriorBlockRemoveFunction;
import modtweaker.bigreactors.function.ReactorInteriorFluidAddFunction;
import modtweaker.bigreactors.function.ReactorInteriorFluidRemoveFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class BigReactorsValue extends TweakerValue {
	public static final BigReactorsValue INSTANCE = new BigReactorsValue();
	
	private BigReactorsValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addFuel")) return FuelsAddFunction.INSTANCE;
		if(index.equals("removeFuel")) return FuelsRemoveFunction.INSTANCE;
		if(index.equals("addCoilPart")) return CoilPartAddFunction.INSTANCE;
		if(index.equals("removeCoilPart")) return CoilPartRemoveFunction.INSTANCE;
		if(index.equals("addInteriorBlock")) return ReactorInteriorBlockAddFunction.INSTANCE;
		if(index.equals("removeInteriorBlock")) return ReactorInteriorBlockRemoveFunction.INSTANCE;
		if(index.equals("addInteriorFluid")) return ReactorInteriorFluidAddFunction.INSTANCE;
		if(index.equals("removeInteriorFluid")) return ReactorInteriorFluidRemoveFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "bigreactors";
	}
}
