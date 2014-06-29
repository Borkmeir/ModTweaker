package modtweaker.util;

import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;

public class Helper {
	//Conversion helpers
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
	
	//
}
