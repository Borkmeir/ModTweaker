package modtweaker.mods.tconstruct;

import minetweaker.MineTweakerAPI;

public class TConstruct {
    public TConstruct() {
        MineTweakerAPI.registerClass(Casting.class);
        MineTweakerAPI.registerClass(Drying.class);
        MineTweakerAPI.registerClass(Smeltery.class);
        MineTweakerAPI.registerClass(TiCTweaks.class);
        MineTweakerAPI.registerClass(ToolStats.class);
    }
}
