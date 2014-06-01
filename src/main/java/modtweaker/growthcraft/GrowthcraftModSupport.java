package modtweaker.growthcraft;

import modtweaker.growthcraft.values.GrowthcraftValue;
import stanhebben.minetweaker.api.MineTweakerInterface;

public class GrowthcraftModSupport extends MineTweakerInterface {
	public static final GrowthcraftModSupport INSTANCE = new GrowthcraftModSupport();
	
	GrowthcraftModSupport() {
		super("growthcraft", GrowthcraftValue.INSTANCE);
	}
}
