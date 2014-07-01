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
            MineTweakerAPI.logger.logError("Item must be a block, or you must specify a block to render as when adding a TConstruct Melting recipe");
            return false;
        } else return true;
    }
    
	//Conversion helpers for ItemStacks
	public static ItemStack ItemStack(IItemStack iStack) {
	    if(iStack == null) MineTweakerAPI.logger.logError("Invalid ItemStack found");
		return (ItemStack) iStack.getInternal();
	}
	
	public static ItemStack[] ItemStack(IIngredient[] input) {
		return ItemStack((IItemStack[])input);
	}
	
	public static ItemStack[] ItemStack(IItemStack[] iStack) {
		ItemStack[] stack = new ItemStack[iStack.length];
		for(int i = 0; i < stack.length; i++)
			stack[i] = (ItemStack) iStack[i].getInternal();
		return stack;
	}
	
	
	//Conversion helps for FluidStacks
	public static FluidStack FluidStack(ILiquidStack iStack) {
	    if(iStack == null) MineTweakerAPI.logger.logError("Invalid FluidStack found");
		return FluidRegistry.getFluidStack(iStack.getName(), iStack.getAmount());
	}
	
	public static FluidStack[] FluidStack(IIngredient[] input) {
		return FluidStack((IItemStack[])input);
	}
	
	public static FluidStack[] FluidStack(ILiquidStack[] iStack) {
		FluidStack[] stack = new FluidStack[iStack.length];
		for(int i = 0; i < stack.length; i++)
			stack[i] = FluidRegistry.getFluidStack(iStack[i].getName(), iStack[i].getAmount());
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
