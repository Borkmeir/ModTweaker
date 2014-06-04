package modtweaker.bigreactors;

import modtweaker.bigreactors.values.BigReactorsValue;
import stanhebben.minetweaker.api.MineTweakerInterface;

public class BigReactors extends MineTweakerInterface {
	public static final BigReactors INSTANCE = new BigReactors();
	
	BigReactors() {
		super("bigreactors", BigReactorsValue.INSTANCE);
	}
}
