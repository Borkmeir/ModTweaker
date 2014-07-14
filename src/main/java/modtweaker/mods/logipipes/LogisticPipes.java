package modtweaker.mods.logipipes;

import minetweaker.MineTweakerAPI;
import modtweaker.mods.logipipes.handlers.Soldering;

public class LogisticPipes {
    public LogisticPipes() {
        MineTweakerAPI.registerClass(Soldering.class);
    }
}
