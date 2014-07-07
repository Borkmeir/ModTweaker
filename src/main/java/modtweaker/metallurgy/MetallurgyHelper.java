package modtweaker.metallurgy;

import static modtweaker.util.Helper.getPrivateFinalObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgy.recipes.AlloyerRecipes;
import com.teammetallurgy.metallurgy.recipes.AlloyerRecipes.AlloyRecipe;
import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;

public class MetallurgyHelper {
    public static ArrayList<AlloyRecipe> alloyerRecipes = null;
    public static HashMap<String, ItemStack> crusherMetaList = null;
    public static HashMap<String, ItemStack[]> crusherInputList = null;

    static {
        try {
            alloyerRecipes = getPrivateFinalObject(AlloyerRecipes.getInstance(), "recipes");
            crusherMetaList = getPrivateFinalObject(CrusherRecipes.getInstance(), "metaList");
            crusherInputList = getPrivateFinalObject(CrusherRecipes.getInstance(), "inputList");
        } catch (Exception e) {}
    }

    private MetallurgyHelper() {}

    public static String getCrusherKey(ItemStack input) {
        return input.getUnlocalizedName();
    }
    
  //Returns a Drying Recipe, using reflection as the constructor is not visible
    public static AlloyRecipe getAlloyRecipe(ItemStack first, ItemStack base, ItemStack result) {
        try {
            Constructor constructor = AlloyRecipe.class.getDeclaredConstructor(ItemStack.class, ItemStack.class, ItemStack.class);
            constructor.setAccessible(true);
            return (AlloyRecipe) constructor.newInstance(first, base, result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("Failed to instantiate AlloyRecipe");
        }
    }
}
