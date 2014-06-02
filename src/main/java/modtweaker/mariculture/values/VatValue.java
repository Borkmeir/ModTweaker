package modtweaker.mariculture.values;

import modtweaker.mariculture.function.VatAddRecipeFunction;
import modtweaker.mariculture.function.VatAllTheThingsFunction;
import modtweaker.mariculture.function.VatAlloyFunction;
import modtweaker.mariculture.function.VatAlloyMakesAllFunction;
import modtweaker.mariculture.function.VatDissolveFunction;
import modtweaker.mariculture.function.VatRemoveFluidRecipeFunction;
import modtweaker.mariculture.function.VatRemoveItemRecipeFunction;
import modtweaker.mariculture.function.VatSettleFunction;
import modtweaker.mariculture.function.VatSoakFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class VatValue extends TweakerValue {
	public static final VatValue INSTANCE = new VatValue();
	public static final String ADD_RECIPE = "addRecipe";
	public static final String ADD_ALL_MAKE_ALL = "addAllTheThings";
	public static final String ADD_FLUIDS_MAKE_ALL = "addAlloySolidifying";
	public static final String ADD_ITEM_FLUID = "addDissolving";
	public static final String ADD_FLUIDS_ITEM = "addSettling";
	public static final String ADD_ITEM = "addSoaking";
	public static final String ADD_ALLOY = "addAlloy";
	public static final String RMV_ITEM = "removeItem";
	public static final String RMV_FLUID = "removeFluid";
	
	private VatValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals(ADD_RECIPE)) return VatAddRecipeFunction.INSTANCE;
		if(index.equals(ADD_ALL_MAKE_ALL)) return VatAllTheThingsFunction.INSTANCE;
		if(index.equals(ADD_FLUIDS_MAKE_ALL)) return VatAlloyMakesAllFunction.INSTANCE;
		if(index.equals(ADD_ITEM_FLUID)) return VatDissolveFunction.INSTANCE;
		if(index.equals(ADD_FLUIDS_ITEM)) return VatSettleFunction.INSTANCE;
		if(index.equals(ADD_ITEM)) return VatSoakFunction.INSTANCE;
		if(index.equals(ADD_ALLOY)) return VatAlloyFunction.INSTANCE;
		if(index.equals(RMV_ITEM)) return VatRemoveItemRecipeFunction.INSTANCE;
		if(index.equals(RMV_FLUID)) return VatRemoveFluidRecipeFunction.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "mariculture.vat";
	}
}
