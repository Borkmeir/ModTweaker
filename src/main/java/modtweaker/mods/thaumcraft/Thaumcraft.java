package modtweaker.mods.thaumcraft;

import minetweaker.MineTweakerAPI;
import modtweaker.mods.thaumcraft.handlers.*;

public class Thaumcraft {
    public Thaumcraft() {
        MineTweakerAPI.registerClass(Arcane.class);
        MineTweakerAPI.registerClass(Aspects.class);
        MineTweakerAPI.registerClass(Crucible.class);
        MineTweakerAPI.registerClass(Infusion.class);
        MineTweakerAPI.registerClass(Research.class);
        MineTweakerAPI.registerClass(Warp.class);

    }
}
