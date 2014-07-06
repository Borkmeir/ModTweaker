package modtweaker.metallurgy;

import static modtweaker.util.Helper.getPrivateFinalObject;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgy.recipes.CrusherRecipes;

public class MetallurgyHelper {
    public static HashMap<String, ItemStack> crusherMetaList = null;
    public static HashMap<String, ItemStack[]> crusherInputList = null;

    static {
        try {
            crusherMetaList = getPrivateFinalObject(CrusherRecipes.getInstance(), "metaList");
            crusherInputList = getPrivateFinalObject(CrusherRecipes.getInstance(), "inputList");
        } catch (Exception e) {}
    }

    private MetallurgyHelper() {}

    public static String getCrusherKey(ItemStack input) {
        return input.getUnlocalizedName();
    }
}
