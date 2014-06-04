package modtweaker.bloodmagic;

import modtweaker.util.TweakerBaseValue;
import stanhebben.minetweaker.api.MineTweakerInterface;
import stanhebben.minetweaker.api.value.TweakerValue;

public class BloodMagic extends MineTweakerInterface {
	public static final BloodMagic INSTANCE = new BloodMagic();
	
	BloodMagic() {
		super("bloodmagic", BloodMagicValue.INSTANCE);
	}
	
	/** Base Blood Magic Handler **/
	public static class BloodMagicValue extends TweakerBaseValue {
		public static final BloodMagicValue INSTANCE = new BloodMagicValue();
		private BloodMagicValue() {
			super("bloodmagic");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("alchemy")) return AlchemyValue.INSTANCE;
			if(index.equals("altar"))   return AltarValue.INSTANCE;
			if(index.equals("binding")) return BindingValue.INSTANCE;
			return super.index(index);
		}
	}
	
	/** Alchemy Handler **/
	public static class AlchemyValue extends TweakerBaseValue {
		public static final AlchemyValue INSTANCE = new AlchemyValue();
		private AlchemyValue() {
			super("bloodmagic.alchemy");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addRecipe"))    return AlchemyAddRecipe.INSTANCE;
			if(index.equals("removeRecipe")) return AlchemyRemoveRecipe.INSTANCE;
			return super.index(index);
		}
	}
	
	/** Altar Handler **/
	public static class AltarValue extends TweakerBaseValue {
		public static final AltarValue INSTANCE = new AltarValue();
		private AltarValue() {
			super("bloodmagic.altar");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addRecipe"))    return AltarAddRecipe.INSTANCE;
			if(index.equals("removeRecipe")) return AltarRemoveRecipe.INSTANCE;
			return super.index(index);
		}
	}

	/** Binding Handler **/
	public static class BindingValue extends TweakerBaseValue {
		public static final BindingValue INSTANCE = new BindingValue();
		private BindingValue() {
			super("bloodmagic.binding");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("addRecipe"))    return BindingAddRecipe.INSTANCE;
			if(index.equals("removeRecipe")) return BindingRemoveRecipe.INSTANCE;
			return super.index(index);
		}
	}
}
