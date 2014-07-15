package modtweaker.handlers;

import static modtweaker.helpers.InputHelper.toStack;

import java.util.ArrayList;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.helpers.ForgeHelper;
import modtweaker.helpers.ReflectionHelper;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void addChestLoot(String chest, IItemStack stack, int min, int max, int weight) {
        MineTweakerAPI.tweaker.apply(new AddLoot(chest, new WeightedRandomChestContent(toStack(stack), min, max, weight)));
    }

    private static class AddLoot extends BaseListAddition {
        public AddLoot(String chest, WeightedRandomChestContent content) {
            super("Chest Loot for " + chest + " : ", (ArrayList<WeightedRandomChestContent>) ReflectionHelper.getObject(ForgeHelper.loot.get(chest), "contents"), content);
        }

        public String getRecipeInfo() {
            return ((WeightedRandomChestContent) recipe).theItemId.getDisplayName();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeChestLoot(String chest, IItemStack stack) {
        MineTweakerAPI.apply(new RemoveLoot(chest, toStack(stack)));
    }

    private static class RemoveLoot extends BaseListRemoval {
        public RemoveLoot(String chest, ItemStack stack) {
            super("Chest Loot", (ArrayList<WeightedRandomChestContent>) ReflectionHelper.getObject(ForgeHelper.loot.get(chest), "contents"), stack);
        }

        @Override
        public void apply() {
            for (WeightedRandomChestContent r : (ArrayList<WeightedRandomChestContent>) list) {
                if (r.theItemId != null && r.theItemId.isItemEqual(stack)) {
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
