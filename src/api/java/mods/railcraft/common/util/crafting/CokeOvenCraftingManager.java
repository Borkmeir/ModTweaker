package mods.railcraft.common.util.crafting;

import mods.railcraft.api.crafting.ICokeOvenRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CokeOvenCraftingManager {
    public static class CokeOvenRecipe implements ICokeOvenRecipe {
        public CokeOvenRecipe(ItemStack input, boolean matchDamage, boolean matchNBT, ItemStack output, FluidStack fluidOutput, int cookTime) {
            return;
        }

        @Override
        public int getCookTime() {
            return 0;
        }

        @Override
        public ItemStack getInput() {
            return null;
        }

        @Override
        public FluidStack getFluidOutput() {
            return null;
        }

        @Override
        public ItemStack getOutput() {
            return null;
        }
    }
}
