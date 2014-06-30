package modtweaker.exnihilo;

import minetweaker.MineTweakerAPI;
import modtweaker.bloodmagic.Alchemy;

public class ExNihilo {
    public ExNihilo() {
        MineTweakerAPI.registerClass(Crucible.class);
        MineTweakerAPI.registerClass(Hammer.class);
        MineTweakerAPI.registerClass(Sieve.class);
    }
}
