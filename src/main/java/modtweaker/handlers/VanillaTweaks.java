package modtweaker.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.helpers.ForgeHelper;
import modtweaker.helpers.ReflectionHelper;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("modtweaker")
public class VanillaTweaks {
    @ZenMethod
    public static void addGrassSeed(IItemStack stack, int weight) {
        MineTweakerAPI.apply(new AddSeed(toStack(stack), ForgeHelper.getSeedEntry(toStack(stack), weight)));
    }

    private static class AddSeed extends BaseListAddition {
        private final ItemStack stack;

        public AddSeed(ItemStack stack, Object recipe) {
            super("Seed", ForgeHelper.seeds, recipe);
            this.stack = stack;
        }

        public String getRecipeInfo() {
            return stack.getDisplayName();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @ZenMethod
    public static void removeGrassSeed(IItemStack stack) {
        MineTweakerAPI.apply(new RemoveSeed(toStack(stack)));
    }

    private static class RemoveSeed extends BaseListRemoval {
        public RemoveSeed(ItemStack stack) {
            super("Seed", ForgeHelper.seeds, stack);
        }

        private ItemStack getOutput(Object o) {
            return (ItemStack) ReflectionHelper.getObject(o, "seed");
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
