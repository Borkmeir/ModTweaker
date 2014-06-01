package modtweaker;

import modtweaker.bloodmagic.BloodMagicModSupport;
import modtweaker.exnihilo.ExNihiloModSupport;
import modtweaker.growthcraft.GrowthcraftModSupport;
import modtweaker.mariculture.MaricultureModSupport;
import stanhebben.minetweaker.api.Tweaker;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = "ModTweaker", name="ModTweaker", dependencies="required-after:MineTweaker")
public class ModTweaker {	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		if(Loader.isModLoaded("Mariculture")) {
			Tweaker.registerModInterface(MaricultureModSupport.INSTANCE);
		}
		
		if(Loader.isModLoaded("AWWayofTime")) {
			Tweaker.registerModInterface(BloodMagicModSupport.INSTANCE);
		}
		
		if(Loader.isModLoaded("crowley.skyblock")) {
			Tweaker.registerModInterface(ExNihiloModSupport.INSTANCE);
		}
		
		if(Loader.isModLoaded("Growthcraft|Fishtrap")) {
			Tweaker.registerModInterface(GrowthcraftModSupport.INSTANCE);
		}
	}
}
