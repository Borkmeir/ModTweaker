package modtweaker.growthcraft;

import modtweaker.growthcraft.values.GrowthcraftValue;
import stanhebben.minetweaker.api.MineTweakerInterface;

public class Growthcraft extends MineTweakerInterface {
	public static final Growthcraft INSTANCE = new Growthcraft();
	
	Growthcraft() {
		super("growthcraft", GrowthcraftValue.INSTANCE);
	}
}
