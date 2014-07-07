package modtweaker.botania;

import static modtweaker.util.Helper.FluidStack;
import static modtweaker.util.Helper.ItemStack;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;

public class BotaniaHelper {
    public static Object[] fixInput(Object[] input) {
        Object cloned[] = new Object[input.length];
        for (int i = 0; i < input.length; i++) {
            cloned[i] = fixInput(input[i]);
        }

        return cloned;
    }

    public static Object fixInput(Object input) {
        Object cloned = null;
        if (input instanceof IItemStack) cloned = ItemStack((IItemStack) input);
        else if (input instanceof ILiquidStack) cloned = FluidStack((ILiquidStack) input);
        else cloned = input;
        return cloned;
    }
}
