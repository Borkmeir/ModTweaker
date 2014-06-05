package modtweaker.steelworks;

import modtweaker.util.TweakerBaseValue;
import stanhebben.minetweaker.api.MineTweakerInterface;
import stanhebben.minetweaker.api.value.TweakerValue;

public class Steelworks extends MineTweakerInterface {
	public static final Steelworks INSTANCE = new Steelworks();
	
	Steelworks() {
		super("steelworks", TConstructValue.INSTANCE);
	}
	
	/** Base TiCon Handler **/
	public static class TConstructValue extends TweakerBaseValue {
		public static final TConstructValue INSTANCE = new TConstructValue();
		private TConstructValue() {
			super("steelworks");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addMelting")) 		return MeltingAddRecipe.INSTANCE;
			if(index.equals("removeMelting"))   return MeltingRemoveRecipe.INSTANCE;
			return super.index(index);
		}
	}
}