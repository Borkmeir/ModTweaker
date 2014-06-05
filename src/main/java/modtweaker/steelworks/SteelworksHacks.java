package modtweaker.steelworks;

import static modtweaker.util.ReflectionHelper.getPrivateFinalObject;

import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tsteelworks.lib.crafting.AdvancedSmelting;

public class SteelworksHacks {
	public static HashMap<List<Integer>, FluidStack> smeltingList = null;
	public static HashMap<List<Integer>, Integer> temperatureList = null;
	public static HashMap<List<Integer>, ItemStack> renderIndex = null;
	
	static {
		try {			
			smeltingList = getPrivateFinalObject(AdvancedSmelting.instance, "smeltingList");
			temperatureList = getPrivateFinalObject(AdvancedSmelting.instance, "temperatureList");
			renderIndex = getPrivateFinalObject(AdvancedSmelting.instance, "renderIndex");
		} catch (ClassCastException ex) {
			ex.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SteelworksHacks() {}
}