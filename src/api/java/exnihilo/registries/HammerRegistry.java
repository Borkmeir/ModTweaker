package exnihilo.registries;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
//import exnihilo.ENBlocks;
//import exnihilo.ENItems;
import exnihilo.registries.helpers.Smashable;

public class HammerRegistry {
public static ArrayList<Smashable> rewards = new ArrayList<Smashable>();
	
	public static void register(Block source, int sourceMeta, Item output, int outputMeta, float chance, float luckMultiplier)
	{
		Smashable entry = new Smashable(source, sourceMeta, output, outputMeta, chance, luckMultiplier);
		rewards.add(entry);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<Smashable> getRewards(Block block, int meta)
	{
		ArrayList<Smashable> rewardList = new ArrayList();

		Iterator<Smashable> it = rewards.iterator();
		while(it.hasNext())
		{
			Smashable reward = it.next();

			if (reward.source == block && reward.sourceMeta == meta && reward.item != null)
			{
				rewardList.add(reward);
			}
		}
		
		return rewardList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Block[] getBlocks()
	{
		ArrayList<Block> blocks = new ArrayList();

		Iterator<Smashable> it = rewards.iterator();
		while(it.hasNext())
		{
			Smashable reward = it.next();

			if (!blocks.contains(reward.source))
			{
				blocks.add(reward.source);
			}
		}
		
		return blocks.toArray(new Block[blocks.size()]);
	}
	
	public static void load(Configuration config)
	{
		//TODO: Load all the vanilla smashables!
	}
	
	public static void registerSmashables()
	{		
		/* register(Blocks.stone, 0, ENItems.Stones, 0, 1.00f, 0.0f);
		register(Blocks.stone, 0, ENItems.Stones, 0, 0.75f, 0.1f);
		register(Blocks.stone, 0, ENItems.Stones, 0, 0.75f, 0.1f);
		register(Blocks.stone, 0, ENItems.Stones, 0, 0.50f, 0.1f);
		register(Blocks.stone, 0, ENItems.Stones, 0, 0.25f, 0.1f);
		register(Blocks.stone, 0, ENItems.Stones, 0, 0.05f, 0.1f);
		
		register(Blocks.cobblestone, 0, Item.getItemFromBlock(Blocks.gravel), 0, 1.0f, 0.0f);
		register(Blocks.gravel, 0, Item.getItemFromBlock(Blocks.sand), 0, 1.0f, 0.0f);
		register(Blocks.sand, 0, Item.getItemFromBlock(ENBlocks.Dust), 0, 1.0f, 0.0f);
		
		register(Blocks.sandstone, 0, Item.getItemFromBlock(Blocks.sand), 0, 1.0f, 0.0f);
		register(Blocks.sandstone, 1, Item.getItemFromBlock(Blocks.sand), 0, 1.0f, 0.0f);
		register(Blocks.sandstone, 2, Item.getItemFromBlock(Blocks.sand), 0, 1.0f, 0.0f);
		
		register(Blocks.stonebrick, 0, Item.getItemFromBlock(Blocks.stonebrick), 2, 1.0f, 0.0f);
		
		register(Blocks.stonebrick, 2, ENItems.Stones, 0, 1.00f, 0.0f);
		register(Blocks.stonebrick, 2, ENItems.Stones, 0, 0.75f, 0.1f);
		register(Blocks.stonebrick, 2, ENItems.Stones, 0, 0.75f, 0.1f);
		register(Blocks.stonebrick, 2, ENItems.Stones, 0, 0.50f, 0.1f);
		register(Blocks.stonebrick, 2, ENItems.Stones, 0, 0.25f, 0.1f);
		register(Blocks.stonebrick, 2, ENItems.Stones, 0, 0.05f, 0.1f);
		
		register(Blocks.end_stone, 0, Item.getItemFromBlock(ENBlocks.EnderGravel), 0, 1.0f, 0.0f);
		register(Blocks.netherrack, 0, Item.getItemFromBlock(ENBlocks.NetherGravel), 0, 1.0f, 0.0f); */
	}
	
	
	public static void registerOre(Block ore, int oreMeta, Item reward, int rewardMeta)
	{
		register(ore, oreMeta, reward, rewardMeta, 1.0f, 0.0f);
		register(ore, oreMeta, reward, rewardMeta, 1.0f, 0.0f);
		register(ore, oreMeta, reward, rewardMeta, 1.0f, 0.0f);
		register(ore, oreMeta, reward, rewardMeta, 1.0f, 0.0f);
		register(ore, oreMeta, reward, rewardMeta, 0.5f, 0.1f);
		register(ore, oreMeta, reward, rewardMeta, 0.05f, 0.1f);
		register(ore, oreMeta, reward, rewardMeta, 0.0f, 0.05f);
	}
}
