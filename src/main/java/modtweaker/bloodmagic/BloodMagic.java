package modtweaker.bloodmagic;

import minetweaker.MineTweakerAPI;

public class BloodMagic {
	public BloodMagic() {
		MineTweakerAPI.registerClass(Alchemy.class);
		MineTweakerAPI.registerClass(BloodAltar.class);
	}
}
