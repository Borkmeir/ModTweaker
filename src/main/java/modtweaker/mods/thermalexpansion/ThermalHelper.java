package modtweaker.mods.thermalexpansion;

import static modtweaker.helpers.ReflectionHelper.getConstructor;
import static modtweaker.helpers.ReflectionHelper.getStaticObject;
import static modtweaker.helpers.ReflectionHelper.setPrivateValue;
import gnu.trove.map.hash.THashMap;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.ItemStack;
import thermalexpansion.util.crafting.SmelterManager;
import thermalexpansion.util.crafting.SmelterManager.RecipeSmelter;
import cofh.util.inventory.ComparableItemStackSafe;

public class ThermalHelper {
    public static Map crucible;
    private static Map<List, RecipeSmelter> smelter;
    public static Set<ComparableItemStackSafe> smelterValid;
    public static Constructor smelterRecipe;

    static {
        try {
            crucible = getStaticObject(Class.forName("thermalexpansion.util.crafting.CrucibleManager"), "recipeMap");
            smelterRecipe = getConstructor("thermalexpansion.util.crafting.SmelterManager$RecipeSmelter", ItemStack.class, ItemStack.class, ItemStack.class, ItemStack.class, int.class, int.class);
        } catch (Exception e) {}
    }
    
    /** Need to perform this reflection on the fly as the map is ALWAYS changing, thanks to the way that te handles stuff */
    public static Map<List, RecipeSmelter> getSmelterMap() {
        try {
            smelter = getStaticObject(Class.forName("thermalexpansion.util.crafting.SmelterManager"), "recipeMap");
            smelterValid = getStaticObject(Class.forName("thermalexpansion.util.crafting.SmelterManager"), "validationSet");
        } catch (Exception e) {}

        return smelter;
    }
    
    public static void setSmelter(THashMap map) {
        setPrivateValue(SmelterManager.class, null, "recipeMap", map);
    }

    public static boolean removeCrucibleRecipe(ItemStack input) {
        return crucible.remove(new ComparableItemStackSafe(input)) != null;
    }

    public static Object getTERecipe(Constructor constructor, Object... items) {
        try {
            return constructor.newInstance(items);
        } catch (Exception e) {}

        return null;
    }
}
