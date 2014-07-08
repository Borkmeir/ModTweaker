package modtweaker.thaumcraft;

import minetweaker.MineTweakerAPI;

public class Thaumcraft {
    public Thaumcraft() {
        MineTweakerAPI.registerClass(Aspects.class);
        MineTweakerAPI.registerClass(Crucible.class);
        MineTweakerAPI.registerClass(Infusion.class);
    }
}
