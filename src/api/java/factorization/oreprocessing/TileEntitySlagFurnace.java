package factorization.oreprocessing;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class TileEntitySlagFurnace {
    public static class SmeltingResult {
        public SmeltingResult(ItemStack input, float prob1, ItemStack output1, float prob2, ItemStack output2) {

        }
    }

    public static class SlagRecipes {
        public static ArrayList<TileEntitySlagFurnace.SmeltingResult> smeltingResults = new ArrayList();
    }
}
