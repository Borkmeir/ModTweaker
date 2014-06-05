package modtweaker.mariculture;

import modtweaker.mariculture.function.AnvilAddRecipeFunction;
import modtweaker.mariculture.function.AnvilRemoveRecipeFunction;
import modtweaker.mariculture.function.CrucibleAddAlloyFunction;
import modtweaker.mariculture.function.CrucibleAddFluidFuel;
import modtweaker.mariculture.function.CrucibleAddItemFuel;
import modtweaker.mariculture.function.CrucibleAddMeltingFunction;
import modtweaker.mariculture.function.CrucibleRemoveAlloyFunction;
import modtweaker.mariculture.function.CrucibleRemoveFluidFuel;
import modtweaker.mariculture.function.CrucibleRemoveItemFuel;
import modtweaker.mariculture.function.CrucibleRemoveMeltingFunction;
import modtweaker.mariculture.function.FishingAddJunkFunction;
import modtweaker.mariculture.function.FishingAddLootFunction;
import modtweaker.mariculture.function.FishingRemoveLootFunction;
import modtweaker.mariculture.function.VatAddRecipeFunction;
import modtweaker.mariculture.function.VatAllTheThingsFunction;
import modtweaker.mariculture.function.VatAlloyFunction;
import modtweaker.mariculture.function.VatAlloyMakesAllFunction;
import modtweaker.mariculture.function.VatDissolveFunction;
import modtweaker.mariculture.function.VatRemoveFluidRecipeFunction;
import modtweaker.mariculture.function.VatRemoveItemRecipeFunction;
import modtweaker.mariculture.function.VatSettleFunction;
import modtweaker.mariculture.function.VatSoakFunction;
import modtweaker.util.TweakerBaseValue;
import stanhebben.minetweaker.api.MineTweakerInterface;
import stanhebben.minetweaker.api.value.TweakerValue;

public class Mariculture extends MineTweakerInterface {
	public static final Mariculture INSTANCE = new Mariculture();
	
	Mariculture() {
		super("mariculture", MaricultureValue.INSTANCE);
	}
	
	/** Base Mariculture Handler **/
	public static class MaricultureValue extends TweakerBaseValue {
		public static final MaricultureValue INSTANCE = new MaricultureValue();
		private MaricultureValue() {
			super("mariculture");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("anvil"))    return AnvilValue.INSTANCE;
			if(index.equals("casting"))  return CastingValue.INSTANCE;
			if(index.equals("crucible")) return CrucibleValue.INSTANCE;
			if(index.equals("fishing"))  return FishingValue.INSTANCE;
			if(index.equals("vat"))      return VatValue.INSTANCE;
			return super.index(index);
		}
	}
	
	/** Anvil Handler **/
	public static class AnvilValue extends TweakerBaseValue {
		public static final AnvilValue INSTANCE = new AnvilValue();
		private AnvilValue() {
			super("mariculture.anvil");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addRecipe"))    return AnvilAddRecipeFunction.INSTANCE;
			if(index.equals("removeRecipe")) return AnvilRemoveRecipeFunction.INSTANCE;
			return super.index(index);
		}
	}
	
	/** Casting Handler **/
	public static class CastingValue extends TweakerBaseValue {
		public static final CastingValue INSTANCE = new CastingValue();
		private CastingValue() {
			super("mariculture.casting");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addIngotCasting"))	    return CastingAddIngot.INSTANCE;
			if(index.equals("removeIngotCasting"))	return CastingRemoveIngot.INSTANCE;
			if(index.equals("addBlockCasting"))     return CastingAddBlock.INSTANCE;
			if(index.equals("removeBlockCasting"))  return CastingRemoveBlock.INSTANCE;
			return super.index(index);
		}
	}
	
	/** Crucible Handler **/
	public static class CrucibleValue extends TweakerBaseValue {
		public static final CrucibleValue INSTANCE = new CrucibleValue();
		private CrucibleValue() {
			super("mariculture.crucible");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addMelting"))      return CrucibleAddMeltingFunction.INSTANCE;
			if(index.equals("addAlloy"))        return CrucibleAddAlloyFunction.INSTANCE;
			if(index.equals("removeMelting"))   return CrucibleRemoveMeltingFunction.INSTANCE;
			if(index.equals("removeAlloy"))     return CrucibleRemoveAlloyFunction.INSTANCE;
			if(index.equals("addFluidFuel"))    return CrucibleAddFluidFuel.INSTANCE;
			if(index.equals("addItemFuel"))     return CrucibleAddItemFuel.INSTANCE;
			if(index.equals("removeFluidFuel")) return CrucibleRemoveFluidFuel.INSTANCE;
			if(index.equals("removeItemFuel"))  return CrucibleRemoveItemFuel.INSTANCE;
			return super.index(index);
		}
	}

	/** Fishing Handler **/
	public static class FishingValue extends TweakerBaseValue {
		public static final FishingValue INSTANCE = new FishingValue();
		private FishingValue() {
			super("mariculture.fishing");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addJunk")) return FishingAddJunkFunction.INSTANCE;
			if(index.equals("addLoot")) return FishingAddLootFunction.INSTANCE;
			if(index.equals("remove"))  return FishingRemoveLootFunction.INSTANCE;
			return super.index(index);
		}
	}
	
	/** Vat Handler **/
	public static class VatValue extends TweakerBaseValue {
		public static final String ADD_RECIPE = "addRecipe";
		public static final String ADD_ALL_MAKE_ALL = "addAllTheThings";
		public static final String ADD_FLUIDS_MAKE_ALL = "addAlloySolidifying";
		public static final String ADD_ITEM_FLUID = "addDissolving";
		public static final String ADD_FLUIDS_ITEM = "addSettling";
		public static final String ADD_ITEM = "addSoaking";
		public static final String ADD_ALLOY = "addAlloy";
		public static final String RMV_ITEM = "removeItem";
		public static final String RMV_FLUID = "removeFluid";
		
		public static final VatValue INSTANCE = new VatValue();
		private VatValue() {
			super("mariculture.vat");
		}
		
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
	}
}
