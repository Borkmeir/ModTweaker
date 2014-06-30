package modtweaker.exnihilo;

import minetweaker.MineTweakerAPI;

public class ExNihilo {
    public ExNihilo() {
        MineTweakerAPI.registerClass(Compost.class);
        MineTweakerAPI.registerClass(Crucible.class);
        MineTweakerAPI.registerClass(Hammer.class);
        MineTweakerAPI.registerClass(Sieve.class);
    }
}
