package modtweaker.mods.railcraft;

import java.util.List;

import mods.railcraft.api.crafting.IBlastFurnaceRecipe;
import mods.railcraft.api.crafting.ICokeOvenRecipe;
import mods.railcraft.api.crafting.IRockCrusherRecipe;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class RailcraftHelper {
    public static List<? extends IBlastFurnaceRecipe> furnace = null;
    public static List<? extends ICokeOvenRecipe> oven = null;
    public static List<? extends IRockCrusherRecipe> crusher = null;
    public static List<IRecipe> rolling = null;
    static {
        try {
            furnace = RailcraftCraftingManager.blastFurnace.getRecipes();
            oven = RailcraftCraftingManager.cokeOven.getRecipes();
            crusher = RailcraftCraftingManager.rockCrusher.getRecipes();
            rolling = RailcraftCraftingManager.rollingMachine.getRecipeList();
        } catch (Exception e) {}
    }

    private RailcraftHelper() {}
}
