package exnihilo.registries;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
//import exnihilo.ENBlocks;
//import exnihilo.ENItems;
//import exnihilo.ExNihilo;
import exnihilo.registries.helpers.SiftReward;

public class SieveRegistry {
	public static ArrayList<SiftReward> rewards = new ArrayList<SiftReward>();
	public static boolean dropRubberSeeds = false;
	
	public static void register(Block source, int sourceMeta, Item output, int outputMeta, int rarity)
	{
		SiftReward entry = new SiftReward(source, sourceMeta, output, outputMeta, rarity);
		
		if(source != null)
		{
			rewards.add(entry);
		}else
		{
			//ExNihilo.log.error("An item was added to the SieveRegistry which was not a block");
		}
	}
	
	public static void register(Block source, Item output, int outputMeta, int rarity)
	{
		SiftReward entry = new SiftReward(source, output, outputMeta, rarity);
		
		if(source != null)
		{
			rewards.add(entry);
		}else
		{
			//ExNihilo.log.error("An item was added to the SieveRegistry which was not a block");
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<SiftReward> getRewards(Block block, int meta)
	{
		ArrayList<SiftReward> rewardList = new ArrayList();

		Iterator<SiftReward> it = rewards.iterator();
		while(it.hasNext())
		{
			SiftReward reward = it.next();

			if (reward.source == block && reward.sourceMeta == meta)
			{
				rewardList.add(reward);
			}
		}
		
		return rewardList;
	}
	
	public static boolean Contains(Block block, int meta)
	{
		Iterator<SiftReward> it = rewards.iterator();
		while(it.hasNext())
		{
			SiftReward reward = it.next();

			if (reward.source == block && (reward.sourceMeta == meta || reward.ignoreMeta == true))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean Contains(Block block)
	{
		Iterator<SiftReward> it = rewards.iterator();
		while(it.hasNext())
		{
			SiftReward reward = it.next();

			if (reward.source == block && reward.ignoreMeta == true)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static void load(Configuration config)
	{
			//Load things...
	}
	
	public static void registerRewards()
	{
		/* Dirt!
		register(Blocks.dirt, 0, ENItems.Stones, 0, 1);
		register(Blocks.dirt, 0, ENItems.Stones, 0, 1);
		register(Blocks.dirt, 0, ENItems.Stones, 0, 2);
		register(Blocks.dirt, 0, ENItems.Stones, 0, 2);
		register(Blocks.dirt, 0, ENItems.Stones, 0, 3);
		register(Blocks.dirt, 0, ENItems.Stones, 0, 3);
		register(Blocks.dirt, 0, Items.wheat_seeds, 0, 15);
		register(Blocks.dirt, 0, ENItems.GrassSeeds, 0, 15);
		register(Blocks.dirt, 0, Items.melon_seeds, 0, 32);
		register(Blocks.dirt, 0, Items.pumpkin_seeds, 0, 32);
		register(Blocks.dirt, 0, ENItems.SeedsSugarcane, 0, 32);
		register(Blocks.dirt, 0, ENItems.SeedsCarrot, 0, 64);
		register(Blocks.dirt, 0, ENItems.SeedsPotato, 0, 64);
		register(Blocks.dirt, 0, ENItems.SeedsOak, 0, 64);
		register(Blocks.dirt, 0, ENItems.SeedsAcacia, 0, 90);
		register(Blocks.dirt, 0, ENItems.SeedsSpruce, 0, 90);
		register(Blocks.dirt, 0, ENItems.SeedsBirch, 0, 90);

		//Gravel!
		register(Blocks.gravel, 0, Items.flint, 0, 4);
		register(Blocks.gravel, 0, Items.coal, 0, 8);
		register(Blocks.gravel, 0, Items.dye, 4, 20); //Lapis Lazuli
		register(Blocks.gravel, 0, Items.diamond, 0, 128);
		register(Blocks.gravel, 0, Items.emerald, 0, 150);
		
		//Sand!
		register(Blocks.sand, 0, Items.dye, 3, 32); //Cocoa beans
		register(Blocks.sand, 0, ENItems.SeedsCactus, 0, 32);
		register(Blocks.sand, 0, ENItems.SeedsJungle, 0, 64);
		register(Blocks.sand, 0, ENItems.Spores, 0, 128);
				
		//Soul Sand!
		register(Blocks.soul_sand, 0, Items.quartz, 0, 1);
		register(Blocks.soul_sand, 0, Items.quartz, 0, 3);
		register(Blocks.soul_sand, 0, Items.nether_wart, 0, 20);
		register(Blocks.soul_sand, 0, Items.ghast_tear, 0, 64);
		
		//Dust!
		register(ENBlocks.Dust, 0, Items.dye, 15, 5); //Bone Meal
		register(ENBlocks.Dust, 0, Items.redstone, 0, 8);		
		register(ENBlocks.Dust, 0, Items.gunpowder, 0, 15);
		register(ENBlocks.Dust, 0, Items.glowstone_dust, 0, 16);
		register(ENBlocks.Dust, 0, Items.blaze_powder, 0, 20); */
	}
}