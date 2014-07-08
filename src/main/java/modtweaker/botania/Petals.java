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
import vazkii.botania.api.recipe.RecipePetals;

@ZenClass("mods.botania.Petals")
public class Petals {
    @ZenMethod
    public static void addRecipe(IItemStack output, Object[] input) {
        MineTweakerAPI.tweaker.apply(new Add(new RecipePetals(ItemStack(output), Helper.fix(input))));
    }

    private static class Add extends BaseListAddition {
        public Add(RecipePetals recipe) {
            super("Botania Petal Recipe", BotaniaAPI.petalRecipes, recipe);
        }

        @Override
        public String getRecipeInfo() {
            return ((RecipePetals) recipe).getOutput().getDisplayName();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Remove(ItemStack(output)));
    }

    private static class Remove extends BaseListRemoval {
        public Remove(ItemStack stack) {
            super("Botania Petal recipe", BotaniaAPI.petalRecipes, stack);
        }

        @Override
        public void apply() {
            for (RecipePetals r : BotaniaAPI.petalRecipes) {
                if (r.getOutput() != null && r.getOutput().isItemEqual(stack)) {
                    recipe = r;
                    break;
                }
            }

            BotaniaAPI.petalRecipes.remove(recipe);
        }

        @Override
        public String getRecipeInfo() {
            return stack.getDisplayName();
        }
    }
}
