package modtweaker.exnihilo;

import modtweaker.exnihilo.values.ExNihiloValue;
import stanhebben.minetweaker.api.MineTweakerInterface;

public class ExNihilo extends MineTweakerInterface {
	public static final ExNihilo INSTANCE = new ExNihilo();
	
	ExNihilo() {
		super("exnihilo", ExNihiloValue.INSTANCE);
	}
}
