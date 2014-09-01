package modtweaker.mods.factorization.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import static modtweaker.helpers.StackHelper.areEqual;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.helpers.ReflectionHelper;
import modtweaker.mods.factorization.FactorizationHelper;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.factorization.SlagFurnace")
public class SlagFurnace {
    @ZenMethod
    public static void addRecipe(IItemStack input, IItemStack output1, double chance1, IItemStack output2, double chance2) {
        Object recipe = FactorizationHelper.getSlagFurnaceRecipe(toStack(input), (float) chance1, toStack(output1), (float) chance2, toStack(output2));
        MineTweakerAPI.apply(new Add(toStack(input), recipe));
    }

    private static class Add extends BaseListAddition {
        private final ItemStack input;

        public Add(ItemStack input, Object recipe) {
            super("Slag Furnace", FactorizationHelper.slag, recipe);
            this.input = input;
        }

        @Override
        public String getRecipeInfo() {
            return input.getDisplayName();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeRecipe(IItemStack input) {
        MineTweakerAPI.apply(new Remove(toStack(input)));
    }

    private static class Remove extends BaseListRemoval {
        public Remove(ItemStack stack) {
            super("Slag Furnace", FactorizationHelper.slag, stack);
        }

        //Returns the output ItemStack
        private ItemStack getInput(Object o) {
            return (ItemStack) ReflectionHelper.getObject(o, "input");
        }

        @Override
        public void apply() {
            for (Object r : list) {
                ItemStack input = getInput(r);
                if (input != null && areEqual(input, stack)) {
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
