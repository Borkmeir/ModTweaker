package modtweaker.mariculture;

import static stanhebben.minetweaker.MineTweakerUtil.getPrivateStaticObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mariculture.api.core.IAnvilHandler.RecipeAnvil;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.core.RecipeVat;
import mariculture.api.fishery.Loot;

public class MaricultureHacks {
	public static Map fuels = null;
	public static HashMap<String, RecipeAnvil> anvil = null;
	public static HashMap<String, RecipeSmelter> crucible = null;
	public static HashMap<List<? extends Object>, RecipeVat> vat = null;
	public static ArrayList<Loot> goodLoot = null;
	public static ArrayList<Loot> badLoot = null;
	static {
		try {
			Class<?> registry = Class.forName("mariculture.fishery.LootHandler");
			anvil = MaricultureHandlers.anvil.getRecipes();
			crucible = MaricultureHandlers.smelter.getRecipes();
			vat = MaricultureHandlers.vat.getRecipes();
			goodLoot = getPrivateStaticObject(registry, "goodies");
			badLoot = getPrivateStaticObject(registry, "junk");
			fuels = (Map) getPrivateStaticFinalObject(MaricultureHandlers.smelter, "fuels");
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
	
	public static <T> Object getPrivateStaticFinalObject(Object o, String... fieldName) {
		Class cls = o.getClass();
		for (String field : fieldName) {
			try {
				Field result = cls.getDeclaredField(field);
				result.setAccessible(true);
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(result, result.getModifiers() & ~Modifier.FINAL);
				return result.get(o);
			} catch (NoSuchFieldException ex) {
				
			} catch (SecurityException ex) {
				
			} catch (IllegalAccessException ex) {
				
			}
		}
		return null;
	}

	private MaricultureHacks() {}
}
