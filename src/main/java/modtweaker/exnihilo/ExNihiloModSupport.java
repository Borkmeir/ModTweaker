package modtweaker.exnihilo;

import modtweaker.exnihilo.values.ExNihiloValue;
import stanhebben.minetweaker.api.MineTweakerInterface;

public class ExNihiloModSupport extends MineTweakerInterface {
	public static final ExNihiloModSupport INSTANCE = new ExNihiloModSupport();
	
	ExNihiloModSupport() {
		super("exnihilo", ExNihiloValue.INSTANCE);
	}
}
