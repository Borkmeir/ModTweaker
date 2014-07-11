package modtweaker.mods.metallurgy;

import minetweaker.MineTweakerAPI;

public class Metallurgy {
    public Metallurgy() {
        MineTweakerAPI.registerClass(Alloyer.class);
        MineTweakerAPI.registerClass(Crusher.class);
    }
}
