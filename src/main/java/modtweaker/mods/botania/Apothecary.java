package modtweaker.mods.botania;

import static modtweaker.helpers.InputHelper.toObjects;
import static modtweaker.helpers.InputHelper.toStack;
import static modtweaker.helpers.StackHelper.areEqual;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

@ZenClass("mods.botania.Apothecary")
public class Apothecary {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient[] input) {
        MineTweakerAPI.tweaker.apply(new Add(new RecipePetals(toStack(output), toObjects(input))));
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
        MineTweakerAPI.tweaker.apply(new Remove(toStack(output)));
    }

    @ZenMethod
    public static void removeRecipe(String output) {
        MineTweakerAPI.tweaker.apply(new Remove(output));
    }

    private static class Remove extends BaseListRemoval {
        public Remove(Object stack) {
            super("Botania Petal recipe", BotaniaAPI.petalRecipes, (ItemStack) (stack instanceof String ? ItemBlockSpecialFlower.ofType((String) stack) : stack));
        }

        @Override
        public void apply() {
            for (RecipePetals r : BotaniaAPI.petalRecipes) {
                if (areEqual(r.getOutput(), stack)) {
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
