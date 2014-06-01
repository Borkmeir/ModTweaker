package modtweaker.mariculture;

import mariculture.api.fishery.RodQuality;
import mariculture.plugins.PluginAWWayofTime;
import cpw.mods.fml.common.Loader;

public class MaricultureHelper {
	public static RodQuality getQuality(String str) {
		if(str.equalsIgnoreCase("old")) return RodQuality.OLD;
		if(str.equals("good")) return RodQuality.GOOD;
		if(str.equals("super")) return RodQuality.SUPER;
		if(str.equals("flux")) return RodQuality.FLUX;
		if(Loader.isModLoaded("AWWayofTime") && str.equals("blood")) return PluginAWWayofTime.BLOOD;
		else return RodQuality.OLD;
	}
}
