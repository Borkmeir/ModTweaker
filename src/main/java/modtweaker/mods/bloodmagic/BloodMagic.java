package modtweaker.mods.bloodmagic;

import minetweaker.MineTweakerAPI;

public class BloodMagic {
    public BloodMagic() {
        MineTweakerAPI.registerClass(Alchemy.class);
        MineTweakerAPI.registerClass(Binding.class);
        MineTweakerAPI.registerClass(BloodAltar.class);
    }
}
