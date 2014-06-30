package modtweaker.tconstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.CastingRecipe;

public class TConstructHacks {
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
        } catch (Exception e) {}
    }

    private TConstructHacks() {}
}
