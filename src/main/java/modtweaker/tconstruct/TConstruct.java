package modtweaker.tconstruct;

import modtweaker.tconstruct.values.TConstructValue;
import stanhebben.minetweaker.api.MineTweakerInterface;

public class TConstruct extends MineTweakerInterface {
	public static final TConstruct INSTANCE = new TConstruct();
	
	TConstruct() {
		super("tconstruct", TConstructValue.INSTANCE);
	}
}
