package modtweaker;

import java.util.List;

import minetweaker.IBracketHandler;
import minetweaker.MineTweakerAPI;
import minetweaker.mc172.brackets.ItemBracketHandler;
import minetweaker.mc172.brackets.LiquidBracketHandler;
import minetweaker.mc172.brackets.OreBracketHandler;
import minetweaker.runtime.GlobalRegistry;
import modtweaker.bloodmagic.BloodMagic;
import modtweaker.botania.Botania;
import modtweaker.exnihilo.ExNihilo;
import modtweaker.mariculture.Mariculture;
import modtweaker.metallurgy.Metallurgy;
import modtweaker.tconstruct.TConstruct;
import modtweaker.thaumcraft.Thaumcraft;
import modtweaker.util.TweakerPlugin;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;

@Mod(modid = "ModTweaker", name = "ModTweaker", dependencies = "required-after:MineTweaker3")
public class ModTweaker {
    @EventHandler
    public void init(FMLInitializationEvent event) {
        TweakerPlugin.register("AWWayofTime", BloodMagic.class);
        TweakerPlugin.register("Botania", Botania.class);
        TweakerPlugin.register("exnihilo", ExNihilo.class);
        TweakerPlugin.register("Mariculture", Mariculture.class);
        TweakerPlugin.register("Metallurgy", Metallurgy.class);
        TweakerPlugin.register("TConstruct", TConstruct.class);
        TweakerPlugin.register("Thaumcraft", Thaumcraft.class);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        try {
            //In theory this should only ever return true in a development environment
            if (((List<IBracketHandler>) ReflectionHelper.getPrivateValue(GlobalRegistry.class, null, "bracketHandlers")).size() <= 0) {
                MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
                MineTweakerAPI.registerBracketHandler(new LiquidBracketHandler());
                MineTweakerAPI.registerBracketHandler(new OreBracketHandler());
            }
        } catch (Exception e) {}
    }
}
