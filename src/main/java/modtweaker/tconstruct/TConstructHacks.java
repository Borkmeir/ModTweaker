package modtweaker.tconstruct;

import java.util.ArrayList;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.CastingRecipe;

public class TConstructHacks {
	public static ArrayList<AlloyMix> alloys = null;
	public static ArrayList<CastingRecipe> basinCasting = null;
	public static ArrayList<CastingRecipe> tableCasting = null;

	static {
		try {			
			alloys = tconstruct.library.crafting.Smeltery.getAlloyList();
			basinCasting = TConstructRegistry.getBasinCasting().getCastingRecipes();
			tableCasting = TConstructRegistry.getTableCasting().getCastingRecipes();
		} catch (Exception e) {}
	}

	private TConstructHacks() {}
}
