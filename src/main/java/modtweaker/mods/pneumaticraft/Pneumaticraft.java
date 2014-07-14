package modtweaker.mods.pneumaticraft;

import minetweaker.MineTweakerAPI;
import modtweaker.mods.pneumaticraft.handlers.Assembly;
import modtweaker.mods.pneumaticraft.handlers.Pressure;

public class Pneumaticraft {
    public Pneumaticraft() {
        MineTweakerAPI.registerClass(Assembly.class);
        MineTweakerAPI.registerClass(Pressure.class);
    }
}
