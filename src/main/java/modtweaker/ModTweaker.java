package modtweaker;

import modtweaker.bloodmagic.BloodMagic;
import modtweaker.exnihilo.ExNihilo;
import modtweaker.growthcraft.Growthcraft;
import modtweaker.mariculture.Mariculture;
import modtweaker.mekanism.GasGroupValue;
import modtweaker.mekanism.Mekanism;
import modtweaker.metallurgy.Metallurgy;
import modtweaker.steelworks.Steelworks;
import modtweaker.tconstruct.TConstruct;
import modtweaker.thermalexpansion.ThermalExpansion;
import stanhebben.minetweaker.MineTweaker;
import stanhebben.minetweaker.api.Tweaker;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = "ModTweaker", name = "ModTweaker", dependencies = "required-after:MineTweaker; after:ThermalExpansion")
public class ModTweaker {
	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (Loader.isModLoaded("Mariculture")) {
			Tweaker.registerModInterface(Mariculture.INSTANCE);
		}

		if (Loader.isModLoaded("AWWayofTime")) {
			Tweaker.registerModInterface(BloodMagic.INSTANCE);
		}

		if (Loader.isModLoaded("crowley.skyblock")) {
			Tweaker.registerModInterface(ExNihilo.INSTANCE);
		}

		if (Loader.isModLoaded("Growthcraft|Fishtrap")) {
			Tweaker.registerModInterface(Growthcraft.INSTANCE);
		}
		
		if (Loader.isModLoaded("Mekanism")) {
			MineTweaker.instance.getGlobal().put("gas", new GasGroupValue("gas"));
			MineTweaker.instance.getGlobal().put("gases", new GasGroupValue());
			Tweaker.registerModInterface(Mekanism.INSTANCE);
		}

		if (Loader.isModLoaded("Metallurgy3Machines")) {
			Tweaker.registerModInterface(Metallurgy.INSTANCE);
		}
		
		if (Loader.isModLoaded("TConstruct")) {
			Tweaker.registerModInterface(TConstruct.INSTANCE);
		}
		
		if (Loader.isModLoaded("TSteelworks")) {
			Tweaker.registerModInterface(Steelworks.INSTANCE);
		}
		
		if (Loader.isModLoaded("ThermalExpansion")) {
			Tweaker.registerModInterface(ThermalExpansion.INSTANCE);
		}
	}
}
