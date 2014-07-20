package mods.railcraft.common.util.crafting;

import mods.railcraft.api.crafting.IBlastFurnaceRecipe;
import net.minecraft.item.ItemStack;

public class BlastFurnaceCraftingManager {
    public static class BlastFurnaceRecipe implements IBlastFurnaceRecipe {
        public BlastFurnaceRecipe(ItemStack input, boolean matchDamage, boolean matchNBT, int cookTime, ItemStack output) {
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
        public ItemStack getOutput() {
            return null;
        }

        @Override
        public int getOutputStackSize() {
            return 0;
        }

        @Override
        public boolean isRoomForOutput(ItemStack outputSlot) {
            return false;
        }
    }
}
