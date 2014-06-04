package modtweaker.growthcraft;

import static modtweaker.util.ReflectionHelper.getPrivateFinalObject;
import growthcraft.api.fishtrap.FishTrapEntry;
import growthcraft.api.fishtrap.FishTrapRegistry;

import java.util.List;

public class GrowthcraftHacks {
	public static List<FishTrapEntry> fishList = null;
	public static List<FishTrapEntry> treasureList = null;
	public static List<FishTrapEntry> junkList = null;
	
	static {
		try {
			fishList = (List<FishTrapEntry>) getPrivateFinalObject(FishTrapRegistry.instance(), "fishList");
			treasureList = (List<FishTrapEntry>) getPrivateFinalObject(FishTrapRegistry.instance(), "treasureList");
			junkList = (List<FishTrapEntry>) getPrivateFinalObject(FishTrapRegistry.instance(), "junkList");
		} catch (ClassCastException ex) {
			ex.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private GrowthcraftHacks() {}
}
