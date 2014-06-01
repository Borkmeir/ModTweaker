package modtweaker.bloodmagic;

import modtweaker.bloodmagic.values.BloodMagicValue;
import stanhebben.minetweaker.api.MineTweakerInterface;

public class BloodMagicModSupport extends MineTweakerInterface {
	public static final BloodMagicModSupport INSTANCE = new BloodMagicModSupport();
	
	BloodMagicModSupport() {
		super("bloodmagic", BloodMagicValue.INSTANCE);
	}
}
