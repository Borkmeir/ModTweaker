package modtweaker.mods.tconstruct;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.library.crafting.DryingRackRecipes.DryingRecipe;
import tconstruct.library.crafting.PatternBuilder.ItemKey;
import tconstruct.library.tools.ToolMaterial;

public class TConstructHelper {
    public static HashMap<String, Integer> mappings = new HashMap();
    public static ArrayList<AlloyMix> alloys = null;
    public static ArrayList<CastingRecipe> basinCasting = null;
    public static ArrayList<CastingRecipe> tableCasting = null;
    public static HashMap<List<Integer>, FluidStack> smeltingList = null;
    public static HashMap<List<Integer>, Integer> temperatureList = null;
    public static HashMap<List<Integer>, ItemStack> renderIndex = null;

    static {
        try {
            alloys = tconstruct.library.crafting.Smeltery.getAlloyList();
            smeltingList = tconstruct.library.crafting.Smeltery.getSmeltingList();
            temperatureList = tconstruct.library.crafting.Smeltery.getTemperatureList();
            renderIndex = tconstruct.library.crafting.Smeltery.getRenderIndex();
            basinCasting = TConstructRegistry.getBasinCasting().getCastingRecipes();
            tableCasting = TConstructRegistry.getTableCasting().getCastingRecipes();

            for (Map.Entry<Integer, ToolMaterial> entry : TConstructRegistry.toolMaterials.entrySet()) {
                mappings.put(entry.getValue().materialName, entry.getKey());
            }
            
        } catch (Exception e) {}
    }

    private TConstructHelper() {}

    public static int getIDFromString(String material) {
        if (!mappings.containsKey(material)) {
            return -1;
        } else return mappings.get(material);
    }

    //Returns a Drying Recipe, using reflection as the constructor is not visible
    public static DryingRecipe getDryingRecipe(ItemStack input, int time, ItemStack output) {
        try {
            Constructor constructor = DryingRecipe.class.getDeclaredConstructor(ItemStack.class, int.class, ItemStack.class);
            constructor.setAccessible(true);
            return (DryingRecipe) constructor.newInstance(input, time, output);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("Failed to instantiate DryingRecipe");
        }
    }

    public static String getMaterialFromID(int id) {
        return TConstructRegistry.toolMaterials.get(id).materialName;
    }

    public static ItemKey getItemKey(Item item, int damage, int value, String material) {
        try {
            Constructor constructor = ItemKey.class.getDeclaredConstructor(ItemStack.class, int.class, int.class, String.class);
            constructor.setAccessible(true);
            return (ItemKey) constructor.newInstance(item, damage, value, material);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("Failed to instantiate ItemKey");
        }
    }
}
