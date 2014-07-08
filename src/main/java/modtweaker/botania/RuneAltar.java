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
import vazkii.botania.api.recipe.RecipeRuneAltar;

@ZenClass("mods.botania.RuneAltar")
public class RuneAltar {
    @ZenMethod
    public static void addRecipe(IItemStack output, int mana, Object[] input) {
        MineTweakerAPI.tweaker.apply(new Add(new RecipeRuneAltar(ItemStack(output), mana, Helper.fix(input))));
    }

    private static class Add extends BaseListAddition {
        public Add(RecipeRuneAltar recipe) {
            super("Botania Rune Altar", BotaniaAPI.runeAltarRecipes, recipe);
        }

        @Override
        public String getRecipeInfo() {
            return ((RecipeRuneAltar) recipe).getOutput().getDisplayName();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Remove(ItemStack(output)));
    }

    private static class Remove extends BaseListRemoval {
        public Remove(ItemStack stack) {
            super("Botania Rune Altar", BotaniaAPI.runeAltarRecipes, stack);
        }

        @Override
        public void apply() {
            for (RecipeRuneAltar r : BotaniaAPI.runeAltarRecipes) {
                if (r.getOutput() != null && r.getOutput().isItemEqual(stack)) {
                    recipe = r;
                    break;
                }
            }

            BotaniaAPI.runeAltarRecipes.remove(recipe);
        }

        @Override
        public String getRecipeInfo() {
            return stack.getDisplayName();
        }
    }
}
