package modtweaker.tconstruct;

import minetweaker.MineTweakerAPI;
import modtweaker.util.BaseSetVar;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.library.tools.ToolCore;

@ZenClass("mods.tconstruct.Tweaks")
public class TiCTweaks {
    //Set the maximum RF
    @ZenMethod
    public static void setRFCapacity(@NotNull int capacity) {
        MineTweakerAPI.tweaker.apply(new AdjustRF(capacity));
    }

    private static class AdjustRF extends BaseSetVar {
        public AdjustRF(int newValue) {
            super("RF Maximum for Tinkers Tools", ToolCore.class, "capacity", 40000, newValue);
        }
    }
}
