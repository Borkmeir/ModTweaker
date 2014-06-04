package modtweaker.util;

import mariculture.core.helpers.OreDicHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHelper {
	public static boolean areEqual(ItemStack stack1, ItemStack stack2) {
		if(stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
			return stack2.itemID == stack1.itemID;
		} else return stack1.itemID == stack2.itemID && stack1.getItemDamage() == stack2.getItemDamage();
	}
	
	public static String getName(FluidStack fluid) {
		return fluid.getFluid().getName();
	}
	
	public static String getName(ItemStack stack) {
		return OreDicHelper.convert(stack);
	}
}
