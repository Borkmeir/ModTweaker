package modtweaker.mariculture;

import static modtweaker.util.Helper.FluidStack;
import static modtweaker.util.Helper.ItemStack;

import java.util.HashMap;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting;
import mariculture.api.core.RecipeCasting.RecipeNuggetCasting;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import modtweaker.util.BaseMapAddition;
import modtweaker.util.BaseMapRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mariculture.Casting")
public class Casting {
    //Adding Mariculture Casting Recipes
    @ZenMethod
    public static void addNuggetRecipe(ILiquidStack input, IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Add(new RecipeNuggetCasting(FluidStack(input), ItemStack(output)), MaricultureHandlers.casting.getNuggetRecipes()));
    }

    @ZenMethod
    public static void addIngotRecipe(ILiquidStack input, IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Add(new RecipeNuggetCasting(FluidStack(input), ItemStack(output)), MaricultureHandlers.casting.getIngotRecipes()));
    }

    @ZenMethod
    public static void addBlockRecipe(ILiquidStack input, IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Add(new RecipeNuggetCasting(FluidStack(input), ItemStack(output)), MaricultureHandlers.casting.getBlockRecipes()));
    }

    private static class Add extends BaseMapAddition {
        public Add(RecipeCasting recipe, HashMap map) {
            super("Mariculture Casting", map, MaricultureHelper.getKey(recipe.fluid), recipe);
        }

        @Override
        public String getRecipeInfo() {
            return ((RecipeCasting) recipe).output.getDisplayName();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Removing Mariculture Casting Recipes
    @ZenMethod
    public static void removeNuggetRecipe(IItemStack input) {
        MineTweakerAPI.tweaker.apply(new Remove(ItemStack(input), MaricultureHandlers.casting.getNuggetRecipes()));
    }

    @ZenMethod
    public static void removeIngotRecipe(IItemStack input) {
        MineTweakerAPI.tweaker.apply(new Remove(ItemStack(input), MaricultureHandlers.casting.getIngotRecipes()));
    }

    @ZenMethod
    public static void removeBlockRecipe(IItemStack input) {
        MineTweakerAPI.tweaker.apply(new Remove(ItemStack(input), MaricultureHandlers.casting.getBlockRecipes()));
    }

    private static class Remove extends BaseMapRemoval {
        public Remove(ItemStack stack, HashMap map) {
            super("Mariculture Casting", map, MaricultureHelper.getKey(stack), stack);
        }

        @Override
        public String getRecipeInfo() {
            return stack.getDisplayName();
        }
    }
}
