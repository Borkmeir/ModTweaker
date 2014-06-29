package modtweaker.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class Helper {
	//Conversion helpers for ItemStacks
	public static ItemStack ItemStack(IItemStack iStack) {
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
		return (FluidStack) iStack.getInternal();
	}
	
	public static FluidStack[] FluidStack(IIngredient[] input) {
		return FluidStack((IItemStack[])input);
	}
	
	public static FluidStack[] FluidStack(ILiquidStack[] iStack) {
		FluidStack[] stack = new FluidStack[iStack.length];
		for(int i = 0; i < stack.length; i++)
			stack[i] = (FluidStack) iStack[i].getInternal();
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
}
