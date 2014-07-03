package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeSmelter {
    public int temp;
    public ItemStack input;
    public ItemStack input2;
    public FluidStack fluid;
    public ItemStack output;
    public int chance;
    public FluidStack[] random;
    public Integer[] rands;

    public RecipeSmelter(ItemStack input, ItemStack input2, int temp, FluidStack fluid, ItemStack output, int chance) {
        this.input = input;
        this.input2 = input2;
        this.temp = temp;
        this.fluid = fluid;
        this.output = output;
        this.chance = chance;
    }

    public RecipeSmelter(ItemStack input, int temp, FluidStack[] fluids, Integer rands[], ItemStack output, int chance) {
        fluid = fluids[0];
        this.input = input;
        this.temp = temp;
        random = fluids;
        this.rands = rands;
        this.output = output;
        this.chance = chance;
    }

    public RecipeSmelter(ItemStack input, ItemStack input2, int temp, FluidStack fluid, ItemStack output, int chance, Integer[] rands) {
        this(input, input2, temp, fluid, output, chance);
        this.rands = rands;
    }
}
