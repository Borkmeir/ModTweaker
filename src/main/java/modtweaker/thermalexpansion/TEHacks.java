package modtweaker.thermalexpansion;

import static modtweaker.util.ReflectionHelper.getPrivateStaticObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import thermalexpansion.util.crafting.CrucibleManager.RecipeCrucible;
import thermalexpansion.util.crafting.FurnaceManager.RecipeFurnace;
import thermalexpansion.util.crafting.PulverizerManager.ComparableItemStackPulverizer;
import thermalexpansion.util.crafting.PulverizerManager.RecipePulverizer;
import thermalexpansion.util.crafting.SawmillManager.ComparableItemStackSawmill;
import thermalexpansion.util.crafting.SawmillManager.RecipeSawmill;
import thermalexpansion.util.crafting.SmelterManager.RecipeSmelter;
import thermalexpansion.util.crafting.TransposerManager.RecipeTransposer;
import cofh.util.inventory.ComparableItemStackSafe;

public class TEHacks {
	public static HashMap<ComparableItemStackSafe, RecipeCrucible> crucible = null;
	public static HashMap<ComparableItemStackSafe, RecipeFurnace> furnace = null;
	public static HashMap<ComparableItemStackPulverizer, RecipePulverizer> pulverizer = null;
	public static HashMap<ComparableItemStackSawmill, RecipeSawmill> sawmill = null;

	// Smelter Stuff
	public static Map<List, RecipeSmelter> smelter = null;
	public static Set<ComparableItemStackSafe> smelterValidation = null;

	// Transposer Stuff
	public static Map<List, RecipeTransposer> transposerFill = null;
	public static Map<ComparableItemStackSafe, RecipeTransposer> transposerExtract = null;
	public static Set<ComparableItemStackSafe> transposerValidation = null;

	static {
		try {
			crucible = getPrivateStaticObject(Class.forName("thermalexpansion.util.crafting.CrucibleManager"), "recipeMap");
			furnace = getPrivateStaticObject(Class.forName("thermalexpansion.util.crafting.FurnaceManager"), "recipeMap");
			pulverizer = getPrivateStaticObject(Class.forName("thermalexpansion.util.crafting.PulverizerManager"), "recipeMap");
			sawmill = getPrivateStaticObject(Class.forName("thermalexpansion.util.crafting.SawmillManager"), "recipeMap");

			// Smelter
			smelter = getPrivateStaticObject(Class.forName("thermalexpansion.util.crafting.SmelterManager"), "recipeMap");
			smelterValidation = getPrivateStaticObject(Class.forName("thermalexpansion.util.crafting.SmelterManager"), "validation");

			// Transposer
			transposerFill = getPrivateStaticObject(Class.forName("thermalexpansion.util.crafting.TransposerManager"), "recipeMapFill");
			transposerExtract = getPrivateStaticObject(Class.forName("thermalexpansion.util.crafting.TransposerManager"), "recipeMapExtract");
			transposerValidation = getPrivateStaticObject(Class.forName("thermalexpansion.util.crafting.TransposerManager"), "validation");
		} catch (ClassCastException ex) {
			ex.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TEHacks() {
	}
}
