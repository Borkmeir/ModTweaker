package modtweaker.mariculture;

import minetweaker.MineTweakerAPI;
import modtweaker.bloodmagic.Alchemy;

public class Mariculture {
    public Mariculture() {
        MineTweakerAPI.registerClass(Anvil.class);
        MineTweakerAPI.registerClass(Casting.class);
        MineTweakerAPI.registerClass(Crucible.class);
        MineTweakerAPI.registerClass(Fishing.class);
        MineTweakerAPI.registerClass(Vat.class);
    }
}
