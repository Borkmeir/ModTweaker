package modtweaker;

import modtweaker.mods.bloodmagic.BloodMagic;
import modtweaker.mods.botania.Botania;
import modtweaker.mods.exnihilo.ExNihilo;
import modtweaker.mods.factorization.Factorization;
import modtweaker.mods.hee.HardcoreEnderExpansion;
import modtweaker.mods.mariculture.Mariculture;
import modtweaker.mods.mekanism.Mekanism;
import modtweaker.mods.metallurgy.Metallurgy;
import modtweaker.mods.tconstruct.TConstruct;
import modtweaker.mods.thaumcraft.Thaumcraft;
import modtweaker.util.TweakerPlugin;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = "ModTweaker", name = "ModTweaker", dependencies = "required-after:MineTweaker3")
public class ModTweaker {
    @EventHandler
    public void init(FMLInitializationEvent event) {
        TweakerPlugin.register("AWWayofTime", BloodMagic.class);
        TweakerPlugin.register("Botania", Botania.class);
        TweakerPlugin.register("exnihilo", ExNihilo.class);
        TweakerPlugin.register("Factorization", Factorization.class);
        TweakerPlugin.register("HardcoreEnderExpansion", HardcoreEnderExpansion.class);
        TweakerPlugin.register("Mariculture", Mariculture.class);
        TweakerPlugin.register("Mekanism", Mekanism.class);
        TweakerPlugin.register("Metallurgy", Metallurgy.class);
        TweakerPlugin.register("TConstruct", TConstruct.class);
        TweakerPlugin.register("Thaumcraft", Thaumcraft.class);
    }
}
