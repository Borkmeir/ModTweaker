package modtweaker.mods.factorization.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.helpers.ReflectionHelper;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import factorization.oreprocessing.TileEntityCrystallizer;
import factorization.oreprocessing.TileEntityCrystallizer.CrystalRecipe;

@ZenClass("mods.factorization.Crystallizer")
public class Crystallizer {
    @ZenMethod
    public static void addRecipe(IItemStack input, IItemStack output, IItemStack solution, double output_count) {
        Object recipe = new CrystalRecipe(toStack(input), toStack(output), (float) output_count, toStack(solution));
        MineTweakerAPI.tweaker.apply(new Add(toStack(input), recipe));
    }

    private static class Add extends BaseListAddition {
        private final ItemStack output;

        public Add(ItemStack output, Object recipe) {
            super("Crystallizer", TileEntityCrystallizer.recipes, recipe);
            this.output = output;
        }

        @Override
        public String getRecipeInfo() {
            return output.getDisplayName();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Remove(toStack(output)));
    }

    private static class Remove extends BaseListRemoval {
        public Remove(ItemStack stack) {
            super("Crystallizer", TileEntityCrystallizer.recipes, stack);
        }

        //Returns the output ItemStack
        private ItemStack getOutput(Object o) {
            return (ItemStack) ReflectionHelper.getObject(o, "output");
        }

        @Override
        public void apply() {
            for (Object r : list) {
                ItemStack output = getOutput(r);
                if (output != null && output.isItemEqual(stack)) {
                    recipe = r;
                    break;
                }
            }

            list.remove(recipe);
        }

        @Override
        public String getRecipeInfo() {
            return stack.getDisplayName();
        }
    }
}
