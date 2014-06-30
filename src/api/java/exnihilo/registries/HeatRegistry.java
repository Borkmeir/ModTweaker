package exnihilo.registries;

import java.util.Hashtable;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import exnihilo.registries.helpers.HeatSource;

public class HeatRegistry {
	public static Hashtable<String, HeatSource> entries = new Hashtable<String, HeatSource>();
	
	public static void register(Block block, int meta, float value)
	{
		HeatSource entry = new HeatSource(block, meta, value);
		entries.put(block + ":" + meta, entry);
	}
	
	public static void register(Block block, float value)
	{
		for(int x = 0; x <= 15; x++)
		{
			register(block, x, value);
		}
	}
	
	public static boolean containsItem(Block block, int meta)
	{
		return entries.containsKey(block + ":" + meta);
	}
	
	public static HeatSource getItem(Block block, int meta)
	{
		return entries.get(block + ":" + meta);
	}
	
	public static void registerVanillaHeatSources()
	{
		register(Blocks.torch, 0.1f);
		register(Blocks.lava, 0.2f);
		register(Blocks.flowing_lava, 0.1f);
		register(Blocks.lit_furnace, 0.15f);
		register(Blocks.fire, 0.3f);
	}
}
