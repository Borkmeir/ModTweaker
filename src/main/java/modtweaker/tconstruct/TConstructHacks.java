package modtweaker.tconstruct;

import static modtweaker.util.ReflectionHelper.getPrivateFinalObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.Smeltery;

public class TConstructHacks {
	public static ArrayList<AlloyMix> alloys = null;
	public static HashMap<List<Integer>, FluidStack> smeltingList = null;
	public static HashMap<List<Integer>, Integer> temperatureList = null;
	public static HashMap<List<Integer>, ItemStack> renderIndex = null;
	
	static {
		try {			
			alloys = getPrivateFinalObject(Smeltery.instance, "alloys");
			smeltingList = getPrivateFinalObject(Smeltery.instance, "smeltingList");
			temperatureList = getPrivateFinalObject(Smeltery.instance, "temperatureList");
			renderIndex = getPrivateFinalObject(Smeltery.instance, "renderIndex");
		} catch (ClassCastException ex) {
			ex.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TConstructHacks() {}
}
