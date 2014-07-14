package modtweaker.mods.factorization.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.helpers.ReflectionHelper;
import modtweaker.mods.factorization.FactorizationHelper;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.factorization.SlagFurnace")
public class SlagFurnace {
    @ZenMethod
    public static void addRecipe(IItemStack input, IItemStack output1, double chance1, IItemStack output2, double chance2) {
        Object recipe = FactorizationHelper.getSlagFurnaceRecipe(toStack(input), toStack(output1), (float) chance1, toStack(output2), (float) chance2);
        MineTweakerAPI.tweaker.apply(new Add(toStack(output1), recipe));
    }

    private static class Add extends BaseListAddition {
        private final ItemStack output;

        public Add(ItemStack output, Object recipe) {
            super("Slag Furnace", FactorizationHelper.slag, recipe);
            this.output = output;
        }

        @Override
        public String getRecipeInfo() {
            return output.getDisplayName();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeRecipe(IItemStack output1, @Optional IItemStack output2) {
        MineTweakerAPI.tweaker.apply(new Remove(toStack(output1), toStack(output2)));
    }

    private static class Remove extends BaseListRemoval {
        private final ItemStack stack2;

        public Remove(ItemStack stack1, ItemStack stack2) {
            super("Lacerator", FactorizationHelper.lacerator, stack1);
            this.stack2 = stack2;
        }

        //Returns the output ItemStack
        private ItemStack getOutput(Object o, int number) {
            return (ItemStack) ReflectionHelper.getObject(o, "output" + number);
        }

        @Override
        public void apply() {
            for (Object r : list) {
                ItemStack output1 = getOutput(r, 1);
                if (stack2 == null || (stack2 != null && stack2.isItemEqual(getOutput(r, 2)))) {
                    if (output1 != null && output1.isItemEqual(stack)) {
                        recipe = r;
                        break;
                    }
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
