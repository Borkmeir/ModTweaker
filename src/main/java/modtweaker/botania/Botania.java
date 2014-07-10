package modtweaker.botania;

import minetweaker.MineTweakerAPI;

public class Botania {
    public Botania() {
        MineTweakerAPI.registerClass(Apothecary.class);
        MineTweakerAPI.registerClass(ElvenTrade.class);
        MineTweakerAPI.registerClass(ManaInfusion.class);
        MineTweakerAPI.registerClass(RuneAltar.class);
    }
}
