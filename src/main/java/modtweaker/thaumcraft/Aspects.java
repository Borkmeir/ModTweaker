package modtweaker.thaumcraft;

import static modtweaker.util.Helper.ItemStack;

import java.util.Arrays;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.util.BaseDescriptionAddition;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.AspectList;

@ZenClass("mods.thaumcraft.Aspects")
public class Aspects {
    /** Add/Remove/Set Aspects for items **/
    @ZenMethod
    public static void add(IItemStack stack, String aspects) {
        MineTweakerAPI.tweaker.apply(new AddItemAspects(ItemStack(stack), aspects, false));
    }

    @ZenMethod
    public static void set(IItemStack stack, String aspects) {
        MineTweakerAPI.tweaker.apply(new AddItemAspects(ItemStack(stack), aspects, true));
    }

    //Adds or sets Aspects
    private static class AddItemAspects extends BaseDescriptionAddition {
        private final ItemStack stack;
        private final String aspects;
        private final boolean replace;
        private AspectList oldList;
        private AspectList newList;

        public AddItemAspects(ItemStack stack, String aspects, boolean replace) {
            super("Adding Aspects");
            this.stack = stack;
            this.aspects = aspects;
            this.replace = replace;
        }

        @Override
        public void apply() {
            oldList = ThaumcraftApiHelper.getObjectAspects(stack);
            if (!replace) newList = ThaumcraftHelper.parseAspects(oldList, aspects);
            else newList = ThaumcraftHelper.parseAspects(aspects);
            ThaumcraftApi.objectTags.put(Arrays.asList(stack.getItem(), stack.getItemDamage()), newList);
        }

        @Override
        public void undo() {
            if (oldList == null) {
                ThaumcraftApi.objectTags.remove(Arrays.asList(stack.getItem(), stack.getItemDamage()));
            } else ThaumcraftApi.objectTags.put(Arrays.asList(stack.getItem(), stack.getItemDamage()), oldList);
        }

        @Override
        public String getRecipeInfo() {
            return stack.getDisplayName();
        }
    }
}
