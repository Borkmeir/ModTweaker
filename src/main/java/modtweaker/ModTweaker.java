package modtweaker;

import minetweaker.MineTweakerAPI;
import modtweaker.mods.bloodmagic.BloodMagic;
import modtweaker.mods.botania.Botania;
import modtweaker.mods.exnihilo.ExNihilo;
import modtweaker.mods.factorization.Factorization;
import modtweaker.mods.hee.HardcoreEnderExpansion;
import modtweaker.mods.mariculture.Mariculture;
import modtweaker.mods.mekanism.Mekanism;
import modtweaker.mods.mekanism.gas.GasLogger;
import modtweaker.mods.metallurgy.Metallurgy;
import modtweaker.mods.pneumaticcraft.PneumaticCraft;
import modtweaker.mods.railcraft.Railcraft;
import modtweaker.mods.tconstruct.TConstruct;
import modtweaker.mods.thaumcraft.Thaumcraft;
import modtweaker.mods.thaumcraft.research.ResearchLogger;
import modtweaker.util.TweakerPlugin;
import modtweaker.vanilla.LootLogger;
import modtweaker.vanilla.SeedLogger;
import modtweaker.vanilla.VanillaTweaks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "ModTweaker", name = "ModTweaker", dependencies = "required-after:MineTweaker3")
public class ModTweaker {
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MineTweakerAPI.registerClass(VanillaTweaks.class);
        TweakerPlugin.register("AWWayofTime", BloodMagic.class);
        TweakerPlugin.register("Botania", Botania.class);
        TweakerPlugin.register("exnihilo", ExNihilo.class);
        TweakerPlugin.register("factorization", Factorization.class);
        TweakerPlugin.register("HardcoreEnderExpansion", HardcoreEnderExpansion.class);
        TweakerPlugin.register("Mariculture", Mariculture.class);
        TweakerPlugin.register("Mekanism", Mekanism.class);
        TweakerPlugin.register("Metallurgy", Metallurgy.class);
        TweakerPlugin.register("PneumaticCraft", PneumaticCraft.class);
        TweakerPlugin.register("Railcraft", Railcraft.class);
        TweakerPlugin.register("TConstruct", TConstruct.class);
        TweakerPlugin.register("Thaumcraft", Thaumcraft.class);
    }
    
    @EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        MineTweakerAPI.server.addMineTweakerCommand("loot", new String[] {
                "/minetweaker loot",
                "    Outputs a list of all dungeon loot in the game to the minetweaker log" }, new LootLogger());
        
        MineTweakerAPI.server.addMineTweakerCommand("seeds", new String[] {
                "/minetweaker seeds",
                "    Outputs a list of all grass drops in the game to the minetweaker log" }, new SeedLogger());
        
        if(TweakerPlugin.isLoaded("Mekanism")) {
            MineTweakerAPI.server.addMineTweakerCommand("gases", new String[] {
                    "/minetweaker gases",
                    "    Outputs a list of all gas names in the game to the minetweaker log" }, new GasLogger());
        }

        if(TweakerPlugin.isLoaded("Thaumcraft")) {
            MineTweakerAPI.server.addMineTweakerCommand("research", new String[] {
                    "/minetweaker research", "/minetweaker research [CATEGORY]",
                    "    Outputs a list of all category names in the game to the minetweaker log,"
                    + " or outputs a list of all research keys in a category to the log."}, new ResearchLogger());
        }
    }
}
