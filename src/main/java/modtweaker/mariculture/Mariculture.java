package modtweaker.mariculture;

import modtweaker.mariculture.values.MaricultureValue;
import stanhebben.minetweaker.api.MineTweakerInterface;

public class Mariculture extends MineTweakerInterface {
	public static final Mariculture INSTANCE = new Mariculture();
	
	Mariculture() {
		super("mariculture", MaricultureValue.INSTANCE);
	}
}
