package modtweaker;

import modtweaker.bloodmagic.BloodMagic;
import modtweaker.exnihilo.ExNihilo;
import modtweaker.tconstruct.TConstruct;
import modtweaker.util.TweakerPlugin;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = "ModTweaker", name = "ModTweaker", dependencies = "required-after:MineTweaker3")
public class ModTweaker {
    @EventHandler
    public void init(FMLInitializationEvent event) {
        TweakerPlugin.register("AWWayofTime", BloodMagic.class);
        TweakerPlugin.register("exnihilo", ExNihilo.class);
        TweakerPlugin.register("TConstruct", TConstruct.class);
    }
}
