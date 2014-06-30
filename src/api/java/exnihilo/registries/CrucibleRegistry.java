package exnihilo.registries;

import java.util.Hashtable;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import exnihilo.registries.helpers.Meltable;

public class CrucibleRegistry {
	public static Hashtable<String, Meltable> entries = new Hashtable<String, Meltable>();
	
	public static void register(Block block, int meta, float solidAmount, Fluid fluid, float fluidAmount, Block appearance)
	{
		Meltable entry = new Meltable(block, meta, solidAmount, fluid, fluidAmount, appearance);
		entries.put(block + ":" + meta, entry);
	}
	
	public static boolean containsItem(Block block, int meta)
	{
		return entries.containsKey(block + ":" + meta);
	}
	
	
	public static Meltable getItem(Block block, int meta)
	{
		return entries.get(block + ":" + meta);
	}
	
	public static void load(Configuration config)
	{
		//TODO Allow the ratios to be set in the config?
		//TODO Before that, make sure to handle the edge case where you get more fluid out than solids that you put in.
	}

	public static void registerMeltables() 
	{
		register(Blocks.cobblestone, 0, 2000, FluidRegistry.LAVA, 250, Blocks.cobblestone);
		register(Blocks.stone, 0, 2000, FluidRegistry.LAVA, 250, Blocks.stone);
		register(Blocks.gravel, 0, 2000, FluidRegistry.LAVA, 250, Blocks.gravel);
		register(Blocks.netherrack, 0, 2000, FluidRegistry.LAVA, 1000, Blocks.netherrack);
		
		register(Blocks.snow, 0, 2000, FluidRegistry.WATER, 500, Blocks.snow);
		register(Blocks.ice, 0, 2000, FluidRegistry.WATER, 1000, Blocks.ice);
	}
}
