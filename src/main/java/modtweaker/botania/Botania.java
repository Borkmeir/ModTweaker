package modtweaker.botania;

import minetweaker.MineTweakerAPI;

public class Botania {
    public Botania() {
        MineTweakerAPI.registerClass(ElvenTrade.class);
        MineTweakerAPI.registerClass(ManaInfusion.class);
        MineTweakerAPI.registerClass(Petals.class);
        MineTweakerAPI.registerClass(RuneAltar.class);
    }
}
