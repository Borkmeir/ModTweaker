package modtweaker.mods.hee.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.helpers.ReflectionHelper;
import modtweaker.mods.hee.HEEHelper;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.hee.EssenceAltar")
public class EssenceAltar {
    @ZenMethod
    public static void addRecipe(IItemStack source, IItemStack result, int cost) {
        MineTweakerAPI.tweaker.apply(new Add(toStack(result), HEEHelper.getAltarRecipe(toStack(source), toStack(result), cost)));
    }

    private static class Add extends BaseListAddition {
        private final ItemStack result;

        public Add(ItemStack result, Object recipe) {
            super("Essence Altar", HEEHelper.altar, recipe);
            this.result = result;
        }

        @Override
        public String getRecipeInfo() {
            return result.getDisplayName();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Remove(toStack(output)));
    }

    private static class Remove extends BaseListRemoval {
        public Remove(ItemStack stack) {
            super("Essence Altar", HEEHelper.altar, stack);
        }

        //Returns the output ItemStack
        private ItemStack getOutput(Object o) {
            return (ItemStack) ReflectionHelper.getObject(o, "result");
        }

        @Override
        public void apply() {
            for (Object r : list) {
                ItemStack output1 = getOutput(r);
                if (output1 != null && output1.isItemEqual(stack)) {
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
