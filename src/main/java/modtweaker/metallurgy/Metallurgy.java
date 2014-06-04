package modtweaker.metallurgy;

import modtweaker.metallurgy.values.MetallurgyValue;
import stanhebben.minetweaker.api.MineTweakerInterface;

public class Metallurgy extends MineTweakerInterface {
	public static final Metallurgy INSTANCE = new Metallurgy();
	
	Metallurgy() {
		super("metallurgy", MetallurgyValue.INSTANCE);
	}
}
