package modtweaker.mods.thaumcraft;

import minetweaker.MineTweakerAPI;
import modtweaker.mods.thaumcraft.handlers.Aspects;
import modtweaker.mods.thaumcraft.handlers.Crucible;
import modtweaker.mods.thaumcraft.handlers.Infusion;

public class Thaumcraft {
    public Thaumcraft() {
        MineTweakerAPI.registerClass(Aspects.class);
        MineTweakerAPI.registerClass(Crucible.class);
        MineTweakerAPI.registerClass(Infusion.class);
    }
}
