package modtweaker.mods.thermalexpansion;

import minetweaker.MineTweakerAPI;
import modtweaker.mods.thermalexpansion.handlers.*;

public class ThermalExpansion {
    public ThermalExpansion(){
        MineTweakerAPI.registerClass(Crucible.class);
        MineTweakerAPI.registerClass(Furnace.class);
        MineTweakerAPI.registerClass(Pulverizer.class);
        MineTweakerAPI.registerClass(Sawmill.class);
        MineTweakerAPI.registerClass(Smelter.class);
        MineTweakerAPI.registerClass(Transposer.class);
    }
}
