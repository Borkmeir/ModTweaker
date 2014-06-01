package modtweaker.mariculture;

import modtweaker.mariculture.values.MaricultureValue;
import stanhebben.minetweaker.api.MineTweakerInterface;

public class MaricultureModSupport extends MineTweakerInterface {
	public static final MaricultureModSupport INSTANCE = new MaricultureModSupport();
	
	MaricultureModSupport() {
		super("mariculture", MaricultureValue.INSTANCE);
	}
}
