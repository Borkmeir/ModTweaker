package modtweaker.bloodmagic;

import modtweaker.bloodmagic.values.BloodMagicValue;
import stanhebben.minetweaker.api.MineTweakerInterface;

public class BloodMagic extends MineTweakerInterface {
	public static final BloodMagic INSTANCE = new BloodMagic();
	
	BloodMagic() {
		super("bloodmagic", BloodMagicValue.INSTANCE);
	}
}
