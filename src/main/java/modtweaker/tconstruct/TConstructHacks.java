package modtweaker.tconstruct;

import static modtweaker.util.Helper.getPrivateFinalObject;

import java.util.ArrayList;

import tconstruct.library.crafting.AlloyMix;

public class TConstructHacks {
	public static ArrayList<AlloyMix> alloys = null;

	static {
		try {			
			alloys = getPrivateFinalObject(tconstruct.library.crafting.Smeltery.instance, "alloys");
		} catch (Exception e) {}
	}

	private TConstructHacks() {}
}
