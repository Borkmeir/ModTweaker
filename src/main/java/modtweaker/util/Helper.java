package modtweaker.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class Helper {
    //Generic Error thrower
    public static boolean isABlock(IItemStack block) {
        if (!(ItemStack(block).getItem() instanceof ItemBlock)) {
            MineTweakerAPI.getLogger().logError("Item must be a block, or you must specify a block to render as when adding a TConstruct Melting recipe");
            return false;
        } else return true;
    }

    //Outputting code taken from MineTweakerMC by stanhebben
    public static ItemStack ItemStack(IItemStack iStack) {
        if (iStack == null) return null;
        else {
            Object internal = iStack.getInternal();
            if (internal == null || !(internal instanceof ItemStack)) {
                MineTweakerAPI.getLogger().logError("Not a valid item stack: " + iStack);
            }

            return (ItemStack) internal;
        }
    }

    //Outputting code taken from MineTweakerMC by stanhebben
    public static ItemStack[] ItemStack(IItemStack[] iStack) {
        if (iStack == null) return null;
        else {
            ItemStack[] output = new ItemStack[iStack.length];
            for (int i = 0; i < iStack.length; i++) {
                Object internal = iStack[i].getInternal();
                if (internal != null && internal instanceof ItemStack) {
                    output[i] = (ItemStack) internal;
                }
            }

            return output;
        }
    }

    //Conversion helps for FluidStacks
    public static FluidStack FluidStack(ILiquidStack iStack) {
        if (iStack == null) {
            return null;
        } else return FluidRegistry.getFluidStack(iStack.getName(), iStack.getAmount());
    }

    public static FluidStack[] FluidStack(IIngredient[] input) {
        return FluidStack((IItemStack[]) input);
    }

    public static FluidStack[] FluidStack(ILiquidStack[] iStack) {
        FluidStack[] stack = new FluidStack[iStack.length];
        for (int i = 0; i < stack.length; i++)
            stack[i] = FluidStack(iStack[i]);
        return stack;
    }

    //Reflection helpers
    public static <T> T getPrivateFinalObject(Object o, String... fieldName) {
        Class cls = o.getClass();
        for (String field : fieldName) {
            try {
                Field result = cls.getDeclaredField(field);
                result.setAccessible(true);
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(result, result.getModifiers() & ~Modifier.FINAL);
                return (T) result.get(o);
            } catch (Exception ex) {}
        }

        return null;
    }

    public static <T> T getPrivateStaticObject(Class cls, String... fieldName) {
        for (String field : fieldName) {
            try {
                Field result = cls.getDeclaredField(field);
                result.setAccessible(true);
                return (T) result.get(null);
            } catch (Exception e) {}
        }

        return null;
    }

    public static void setPrivateValue(Class cls, String field, int var) {
        ReflectionHelper.setPrivateValue(cls, null, var, field);
    }
}
