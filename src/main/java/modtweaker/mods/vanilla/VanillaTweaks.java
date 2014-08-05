package modtweaker.mods.vanilla;

import static modtweaker.helpers.InputHelper.toStack;
import static modtweaker.helpers.StackHelper.areEqual;

import java.util.ArrayList;
import java.util.List;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.helpers.ForgeHelper;
import modtweaker.helpers.ReflectionHelper;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import modtweaker.util.BaseMapAddition;
import modtweaker.util.BaseMapRemoval;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.modtweaker")
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
                if (output != null && areEqual(output, stack)) {
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

    private static class AddLoot extends BaseMapAddition {
        private WeightedRandomChestContent content;

        public AddLoot(String chest, WeightedRandomChestContent content) {
            super("Chest Loot for " + chest + " : ", ForgeHelper.loot, chest, null);
            this.content = content;
        }

        @Override
        public void apply() {
            recipe = ForgeHelper.loot.get(key);
            if (recipe instanceof ChestGenHooks) {
                List contents = ReflectionHelper.getObject(recipe, "contents");
                ((ArrayList<WeightedRandomChestContent>) contents).add(content);
                super.apply();
            }
        }

        @Override
        public boolean canUndo() {
            return recipe instanceof ChestGenHooks;
        }

        @Override
        public void undo() {
            recipe = ForgeHelper.loot.get(key);
            if (recipe instanceof ChestGenHooks) {
                List contents = ReflectionHelper.getObject(recipe, "contents");
                ((ArrayList<WeightedRandomChestContent>) contents).remove(content);
                super.apply();
            }
        }

        public String getRecipeInfo() {
            return content.theItemId.getDisplayName();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeChestLoot(String chest, IItemStack stack) {
        MineTweakerAPI.apply(new RemoveLoot(chest, toStack(stack)));
    }

    private static class RemoveLoot extends BaseMapRemoval {
        private WeightedRandomChestContent content;

        public RemoveLoot(String chest, ItemStack stack) {
            super("Chest Loot", ForgeHelper.loot, chest, stack);
        }

        @Override
        public void apply() {
            recipe = ForgeHelper.loot.get(key);
            if (recipe instanceof ChestGenHooks) {
                List contents = ReflectionHelper.getObject(recipe, "contents");

                for (WeightedRandomChestContent r : (ArrayList<WeightedRandomChestContent>) recipe) {
                    if (r.theItemId != null && areEqual(r.theItemId, (ItemStack) stack)) {
                        content = r;
                        break;
                    }
                }

                ((ArrayList<WeightedRandomChestContent>) contents).remove(content);
                super.undo();
            }
        }

        @Override
        public boolean canUndo() {
            return recipe instanceof ChestGenHooks;
        }

        @Override
        public void undo() {
            recipe = ForgeHelper.loot.get(key);
            if (recipe instanceof ChestGenHooks) {
                List contents = ReflectionHelper.getObject(recipe, "contents");
                ((ArrayList<WeightedRandomChestContent>) contents).add(content);
                super.undo();
            }
        }

        @Override
        public String getRecipeInfo() {
            return ((ItemStack) stack).getDisplayName();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Set Localisation

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void setLocalisation(String key, String text) {
        setLocalization(key, text);
    }

    @ZenMethod
    public static void setLocalisation(String lang, String key, String text) {
        setLocalization(lang, key, text);
    }

    @ZenMethod
    public static void setLocalization(String key, String text) {
        MineTweakerAPI.tweaker.apply(new SetTranslation(null, key, text));
    }

    @ZenMethod
    public static void setLocalization(String lang, String key, String text) {
        MineTweakerAPI.tweaker.apply(new SetTranslation(lang, key, text));
    }

    private static class SetTranslation implements IUndoableAction {
        private String original;
        private final String lang;
        private final String key;
        private final String text;

        public SetTranslation(String lang, String key, String text) {
            this.lang = lang;
            this.key = key;
            this.text = text;
        }

        @Override
        public void apply() {
            if (lang == null || ForgeHelper.isLangActive(lang)) {
                original = (String) ForgeHelper.translate.get(key);
                ForgeHelper.translate.put(key, text);
            }
        }

        @Override
        public boolean canUndo() {
            return ForgeHelper.translate != null && (lang == null || ForgeHelper.isLangActive(lang));
        }

        @Override
        public void undo() {
            if (original == null) ForgeHelper.translate.remove(key);
            else ForgeHelper.translate.put(key, original);
        }

        @Override
        public String describe() {
            return "Setting localisation for the key: " + key + " to " + text;
        }

        @Override
        public String describeUndo() {
            return "Setting localisation for the key: " + key + " to " + original;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
