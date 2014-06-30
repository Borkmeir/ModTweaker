package modtweaker.tconstruct;

import minetweaker.MineTweakerAPI;

public class TConstruct {
    public TConstruct() {
        MineTweakerAPI.registerClass(Casting.class);
        MineTweakerAPI.registerClass(Smeltery.class);
        MineTweakerAPI.registerClass(TiCTweaks.class);
    }
}
