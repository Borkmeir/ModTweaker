package modtweaker.tconstruct;

import modtweaker.util.TweakerBaseValue;
import stanhebben.minetweaker.api.MineTweakerInterface;
import stanhebben.minetweaker.api.value.TweakerValue;

public class TConstruct extends MineTweakerInterface {
	public static final TConstruct INSTANCE = new TConstruct();
	
	TConstruct() {
		super("tconstruct", TConstructValue.INSTANCE);
	}
	
	/** Base TiCon Handler **/
	public static class TConstructValue extends TweakerBaseValue {
		public static final TConstructValue INSTANCE = new TConstructValue();
		private TConstructValue() {
			super("tconstruct");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("casting")) return CastingValue.INSTANCE;
			if(index.equals("drying"))   return DryingValue.INSTANCE;
			if(index.equals("smeltery")) return SmelteryValue.INSTANCE;
			return super.index(index);
		}
	}
	
	/** Casting Handler **/
	public static class CastingValue extends TweakerBaseValue {
		public static final CastingValue INSTANCE = new CastingValue();
		private CastingValue() {
			super("tconstruct.casting");
		}
		
		@Override
		public TweakerValue index(String index) {
			//if(index.equals("addRecipe"))    return CastingAddRecipe.INSTANCE;
			//if(index.equals("removeRecipe")) return CastingRemove.INSTANCE;
			return super.index(index);
		}
	}
	
	/** Drying Handler **/
	public static class DryingValue extends TweakerBaseValue {
		public static final DryingValue INSTANCE = new DryingValue();
		private DryingValue() {
			super("tconstruct.drying");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addRecipe"))      return DryingAddRecipe.INSTANCE;
		  //if(index.equals("removeRecipe"))   return DryingRemoveRecipe.INSTANCE;
			return super.index(index);
		}
	}

	/** Smeltery Handler **/
	public static class SmelteryValue extends TweakerBaseValue {
		public static final SmelteryValue INSTANCE = new SmelteryValue();
		private SmelteryValue() {
			super("tconstruct.smeltery");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addAlloy"))      return AlloyAddRecipe.INSTANCE;
			if(index.equals("removeAlloy"))   return AlloyRemoveRecipe.INSTANCE;
			if(index.equals("addMelting"))    return MeltingAddRecipe.INSTANCE;
			if(index.equals("removeMelting")) return MeltingRemoveRecipe.INSTANCE;
			return super.index(index);
		}
	}
}