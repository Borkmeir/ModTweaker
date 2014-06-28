package modtweaker.thermalexpansion;

import modtweaker.util.TweakerBaseValue;
import stanhebben.minetweaker.api.MineTweakerInterface;
import stanhebben.minetweaker.api.value.TweakerValue;

public class ThermalExpansion extends MineTweakerInterface {
	public static final ThermalExpansion INSTANCE = new ThermalExpansion();
	
	ThermalExpansion() {
		super("thermalexpansion", ThermalExpansionValue.INSTANCE);
	}
	
	/** Base TiCon Handler **/
	public static class ThermalExpansionValue extends TweakerBaseValue {
		public static final ThermalExpansionValue INSTANCE = new ThermalExpansionValue();
		private ThermalExpansionValue() {
			super("thermalexpansion");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("crucible")) return CrucibleValue.INSTANCE;
			if(index.equals("furnace"))   return FurnaceValue.INSTANCE;
			if(index.equals("pulverizer")) return PulverizerValue.INSTANCE;
			if(index.equals("sawmill")) return SawmillValue.INSTANCE;
			if(index.equals("smelter")) return SmelterValue.INSTANCE;
			if(index.equals("transposer")) return TransposerValue.INSTANCE;
			return super.index(index);
		}
	}
	
	public static class CrucibleValue extends TweakerBaseValue {
		public static final CrucibleValue INSTANCE = new CrucibleValue();
		private CrucibleValue() {
			super("thermalexpansion.crucible");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addRecipe"))    return CrucibleAddRecipe.INSTANCE;
			if(index.equals("removeRecipe")) return CrucibleRemoveRecipe.INSTANCE;
			return super.index(index);
		}
	}
	
	public static class FurnaceValue extends TweakerBaseValue {
		public static final FurnaceValue INSTANCE = new FurnaceValue();
		private FurnaceValue() {
			super("thermalexpansion.furnace");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addRecipe"))    return FurnaceAddRecipe.INSTANCE;
			if(index.equals("removeRecipe")) return FurnaceRemoveRecipe.INSTANCE;
			return super.index(index);
		}
	}
	
	public static class PulverizerValue extends TweakerBaseValue {
		public static final PulverizerValue INSTANCE = new PulverizerValue();
		private PulverizerValue() {
			super("thermalexpansion.pulverizer");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addRecipe"))    return PulverizerAddRecipe.INSTANCE;
			if(index.equals("removeRecipe")) return PulverizerRemoveRecipe.INSTANCE;
			return super.index(index);
		}
	}
	
	public static class SawmillValue extends TweakerBaseValue {
		public static final SawmillValue INSTANCE = new SawmillValue();
		private SawmillValue() {
			super("thermalexpansion.sawill");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addRecipe"))    return SawmillAddRecipe.INSTANCE;
			if(index.equals("removeRecipe")) return SawmillRemoveRecipe.INSTANCE;
			return super.index(index);
		}
	}
	
	public static class SmelterValue extends TweakerBaseValue {
		public static final SmelterValue INSTANCE = new SmelterValue();
		private SmelterValue() {
			super("thermalexpansion.smelter");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addRecipe"))    return SmelterAddRecipe.INSTANCE;
			if(index.equals("removeRecipe")) return SmelterRemoveRecipe.INSTANCE;
			return super.index(index);
		}
	}
	
	public static class TransposerValue extends TweakerBaseValue {
		public static final TransposerValue INSTANCE = new TransposerValue();
		private TransposerValue() {
			super("thermalexpansion.transposer");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addFillRecipe"))    	return TransposerAddFillRecipe.INSTANCE;
			if(index.equals("removeFillRecipe")) 	return TransposerRemoveFillRecipe.INSTANCE;
			if(index.equals("addExtractRecipe"))    return TransposerAddExtractRecipe.INSTANCE;
			if(index.equals("removeExtractRecipe")) return TransposerRemoveExtractRecipe.INSTANCE;
			return super.index(index);
		}
	}
}