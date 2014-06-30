package modtweaker.exnihilo;

import static modtweaker.util.Helper.FluidStack;
import static modtweaker.util.Helper.ItemStack;
import static modtweaker.util.Helper.isABlock;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import modtweaker.util.BaseMapAddition;
import modtweaker.util.BaseMapRemoveKey;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import exnihilo.registries.CrucibleRegistry;
import exnihilo.registries.helpers.Meltable;

@ZenClass("mods.exnihilo.Crucible")
public class Crucible {
    //Adding a Ex Nihilo Crucible recipe
    @ZenMethod
    public static void addRecipe(@NotNull IItemStack input, @NotNull ILiquidStack fluid) {
        if (isABlock(input)) {
            Block theBlock = Block.getBlockFromItem(ItemStack(input).getItem());
            int theMeta = ItemStack(input).getItemDamage();
            MineTweakerAPI.tweaker.apply(new Add(new Meltable(theBlock, theMeta, 2000, FluidStack(fluid).getFluid(), FluidStack(fluid).amount, theBlock)));
        }
    }

    //Passes the list to the base list implementation, and adds the recipe
    private static class Add extends BaseMapAddition {
        public Add(Meltable recipe) {
            super("ExNihilo Crucible", CrucibleRegistry.entries, recipe.block + ":" + recipe.meta, recipe);
        }

        @Override
        public String getRecipeInfo() {
            return new ItemStack(((Meltable) recipe).block, 1, ((Meltable) recipe).meta).getDisplayName();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Removing a Ex Nihilo Crucible recipe
    @ZenMethod
    public static void removeRecipe(@NotNull IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Remove(ItemStack(output)));
    }

    //Removes a recipe, will always remove the key, so all should be good
    private static class Remove extends BaseMapRemoveKey {
        public Remove(ItemStack stack) {
            super("ExNihilo Crucible", CrucibleRegistry.entries, Block.getBlockFromItem(stack.getItem()) + ":" + stack.getItemDamage(), stack);
        }
        
        @Override
        public String getRecipeInfo() {
            return stack.getDisplayName();
        }
    }
}
