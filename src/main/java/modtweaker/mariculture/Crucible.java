package modtweaker.mariculture;

import static modtweaker.util.Helper.FluidStack;
import static modtweaker.util.Helper.ItemStack;
import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import modtweaker.util.BaseMapAddition;
import modtweaker.util.BaseMapRemoval;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mariculture.Crucible")
public class Crucible {

    /********************************************** Mariculture Crucible Recipes **********************************************/

    //Adding a Mariculture Crucible recipe
    @ZenMethod
    public static void addRecipe(int temp, @NotNull IItemStack input, @NotNull ILiquidStack fluid, @Optional IItemStack output, @Optional int chance) {
        ItemStack out = output != null? ItemStack(output): null;
        MineTweakerAPI.tweaker.apply(new AddRecipe(new RecipeSmelter(ItemStack(input), null, temp, FluidStack(fluid), out, chance)));
    }

    //Passes the list to the base list implementation, and adds the recipe
    private static class AddRecipe extends BaseListAddition {
        public AddRecipe(RecipeSmelter recipe) {
            super("Mariculture Crucible", MaricultureHandlers.crucible.getRecipes(), recipe);
        }

        @Override
        public String getRecipeInfo() {
            return ((RecipeSmelter) recipe).input.getDisplayName();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Removing a Mariculture Crucible recipe
    @ZenMethod
    public static void removeRecipe(IItemStack input) {
        MineTweakerAPI.tweaker.apply(new RemoveRecipe(ItemStack(input)));
    }

    //Removes a recipe, apply is never the same for anything, so will always need to override it
    private static class RemoveRecipe extends BaseListRemoval {
        public RemoveRecipe(ItemStack stack) {
            super("Mariculture Crucible", MaricultureHandlers.crucible.getRecipes(), stack);
        }

        //Loops through the registry, to find the item that matches, saves that recipe then removes it
        @Override
        public void apply() {
            for (RecipeSmelter r : MaricultureHandlers.crucible.getRecipes()) {
                if (r.input != null && r.input.isItemEqual(stack)) {
                    recipe = r;
                    break;
                }
            }

            MaricultureHandlers.crucible.getRecipes().remove(recipe);
        }

        @Override
        public String getRecipeInfo() {
            return stack.getDisplayName();
        }
    }

    /********************************************** Crucible Fuels **********************************************/
    @ZenMethod
    public static void addFuel(IItemStack input, int max, int per, int time) {
        MineTweakerAPI.tweaker.apply(new AddFuel(ItemStack(input), new FuelInfo(max, per, time)));
    }
    
    @ZenMethod
    public static void addFuel(ILiquidStack input, int max, int per, int time) {
        MineTweakerAPI.tweaker.apply(new AddFuel(FluidStack(input), new FuelInfo(max, per, time)));
    }
    
    @ZenMethod
    public static void addFuel(String input, int max, int per, int time) {
        MineTweakerAPI.tweaker.apply(new AddFuel(input, new FuelInfo(max, per, time)));
    }

    //Passes the list to the base map implementation, and adds the recipe
    private static class AddFuel extends BaseMapAddition {
        public AddFuel(Object o, FuelInfo info) {
            super("Mariculture Crucible Fuel", MaricultureHelper.fuels, MaricultureHelper.getKey(o), info);
        }

        @Override
        public String getRecipeInfo() {
            return (String) key;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @ZenMethod
    public static void removeFuel(IItemStack fuel) {
        MineTweakerAPI.tweaker.apply(new RemoveFuel(fuel));
    }
    
    @ZenMethod
    public static void removeFuel(ILiquidStack fuel) {
        MineTweakerAPI.tweaker.apply(new RemoveFuel(fuel));
    }
    
    @ZenMethod
    public static void removeFuel(String fuel) {
        MineTweakerAPI.tweaker.apply(new RemoveFuel(fuel));
    }

    //Removes a recipe, will always remove the key, so all should be good
    private static class RemoveFuel extends BaseMapRemoval {
        public RemoveFuel(Object o) {
            super("Mariculture Crucible Fuel", MaricultureHelper.fuels, MaricultureHelper.getKey(o), null);
        }

        @Override
        public String getRecipeInfo() {
            return (String) key;
        }
    }
}
