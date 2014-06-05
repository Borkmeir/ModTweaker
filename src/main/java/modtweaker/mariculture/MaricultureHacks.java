package modtweaker.mariculture;

import static modtweaker.util.ReflectionHelper.getPrivateFinalObject;
import static modtweaker.util.ReflectionHelper.getPrivateStaticObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mariculture.api.core.IAnvilHandler.RecipeAnvil;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.core.RecipeVat;
import mariculture.api.fishery.Loot;

public class MaricultureHacks {
	public static Map fuels = null;
	public static HashMap<String, RecipeCasting> ingotCasting;
	public static HashMap<String, RecipeCasting> blockCasting;
	public static HashMap<String, RecipeAnvil> anvil = null;
	public static HashMap<String, RecipeSmelter> crucible = null;
	public static HashMap<List<? extends Object>, RecipeVat> vat = null;
	public static ArrayList<Loot> goodLoot = null;
	public static ArrayList<Loot> badLoot = null;
	static {
		try {
			Class<?> registry = Class.forName("mariculture.fishery.LootHandler");
			anvil = MaricultureHandlers.anvil.getRecipes();
			blockCasting = MaricultureHandlers.casting.getBlockRecipes();
			crucible = MaricultureHandlers.smelter.getRecipes();
			ingotCasting = MaricultureHandlers.casting.getIngotRecipes();
			vat = MaricultureHandlers.vat.getRecipes();
			goodLoot = getPrivateStaticObject(registry, "goodies");
			badLoot = getPrivateStaticObject(registry, "junk");
			fuels = getPrivateFinalObject(MaricultureHandlers.smelter, "fuels");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ClassCastException ex) {
			ex.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MaricultureHacks() {}
}
