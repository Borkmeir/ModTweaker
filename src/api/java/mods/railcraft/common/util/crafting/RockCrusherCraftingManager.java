package mods.railcraft.common.util.crafting;

import java.util.List;
import java.util.Map.Entry;

import mods.railcraft.api.crafting.IRockCrusherRecipe;
import net.minecraft.item.ItemStack;

public class RockCrusherCraftingManager {
    public static class CrusherRecipe implements IRockCrusherRecipe {
        public CrusherRecipe(ItemStack stack, boolean matchDamage, boolean matchNBT) {
            return;
        }

        @Override
        public ItemStack getInput() {
            return null;
        }

        @Override
        public void addOutput(ItemStack output, float chance) {
            
        }

        @Override
        public List<Entry<ItemStack, Float>> getOutputs() {
            return null;
        }

        @Override
        public List<ItemStack> getPossibleOuputs() {
            return null;
        }

        @Override
        public List<ItemStack> getRandomizedOuputs() {
            return null;
        }
    }
}
