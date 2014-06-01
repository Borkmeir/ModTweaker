package modtweaker.mariculture.values;

import modtweaker.mariculture.function.CrucibleAddAlloyFunction;
import modtweaker.mariculture.function.CrucibleAddFluidFuel;
import modtweaker.mariculture.function.CrucibleAddItemFuel;
import modtweaker.mariculture.function.CrucibleAddMeltingFunction;
import modtweaker.mariculture.function.CrucibleRemoveAlloyFunction;
import modtweaker.mariculture.function.CrucibleRemoveFluidFuel;
import modtweaker.mariculture.function.CrucibleRemoveItemFuel;
import modtweaker.mariculture.function.CrucibleRemoveMeltingFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CrucibleValue extends TweakerValue {
	public static final CrucibleValue INSTANCE = new CrucibleValue();
	
	private CrucibleValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("addMelting")) return CrucibleAddMeltingFunction.INSTANCE;
		if(index.equals("addAlloy")) return CrucibleAddAlloyFunction.INSTANCE;
		if(index.equals("removeMelting")) return CrucibleRemoveMeltingFunction.INSTANCE;
		if(index.equals("removeAlloy")) return CrucibleRemoveAlloyFunction.INSTANCE;
		if(index.equals("addFluidFuel")) return CrucibleAddFluidFuel.INSTANCE;
		if(index.equals("addItemFuel")) return CrucibleAddItemFuel.INSTANCE;
		if(index.equals("removeFluidFuel")) return CrucibleRemoveFluidFuel.INSTANCE;
		if(index.equals("removeItemFuel")) return CrucibleRemoveItemFuel.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "mariculture.crucible";
	}
}
