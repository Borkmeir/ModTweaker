package modtweaker.botania;

import static modtweaker.util.Helper.ItemStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import modtweaker.util.Helper;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeElvenTrade;

@ZenClass("mods.botania.ElvenTrade")
public class ElvenTrade {
    @ZenMethod
    public static void addRecipe(IItemStack output, Object[] input) {
        MineTweakerAPI.tweaker.apply(new Add(new RecipeElvenTrade(ItemStack(output), Helper.fix(input))));
    }

    private static class Add extends BaseListAddition {
        public Add(RecipeElvenTrade recipe) {
            super("Botania Eleven Trade", BotaniaAPI.elvenTradeRecipes, recipe);
        }

        @Override
        public String getRecipeInfo() {
            return ((RecipeElvenTrade) recipe).getOutput().getDisplayName();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Remove(ItemStack(output)));
    }

    private static class Remove extends BaseListRemoval {
        public Remove(ItemStack stack) {
            super("Botania Elven Trade", BotaniaAPI.elvenTradeRecipes, stack);
        }

        @Override
        public void apply() {
            for (RecipeElvenTrade r : BotaniaAPI.elvenTradeRecipes) {
                if (r.getOutput() != null && r.getOutput().isItemEqual(stack)) {
                    recipe = r;
                    break;
                }
            }

            BotaniaAPI.elvenTradeRecipes.remove(recipe);
        }

        @Override
        public String getRecipeInfo() {
            return stack.getDisplayName();
        }
    }
}
