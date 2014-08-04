package naruto1310.extendedWorkbench;

import static naruto1310.extendedWorkbench.EWFMLPlugin.biggerTools;
import naruto1310.extendedWorkbench.block.BlockExtendedFire;
import naruto1310.extendedWorkbench.crafting.ExtendedCraftingManager;
import naruto1310.extendedWorkbench.item.EWRecipes;
import naruto1310.extendedWorkbench.item.EntityExtendedFishHook;
import naruto1310.extendedWorkbench.item.ItemExtendedArmor;
import naruto1310.extendedWorkbench.item.ItemExtendedAxe;
import naruto1310.extendedWorkbench.item.ItemExtendedBow;
import naruto1310.extendedWorkbench.item.ItemExtendedCompass;
import naruto1310.extendedWorkbench.item.ItemExtendedFishingRod;
import naruto1310.extendedWorkbench.item.ItemExtendedFlintAndSteel;
import naruto1310.extendedWorkbench.item.ItemExtendedHoe;
import naruto1310.extendedWorkbench.item.ItemExtendedMap;
import naruto1310.extendedWorkbench.item.ItemExtendedMapEmpty;
import naruto1310.extendedWorkbench.item.ItemExtendedPickaxe;
import naruto1310.extendedWorkbench.item.ItemExtendedShears;
import naruto1310.extendedWorkbench.item.ItemExtendedShovel;
import naruto1310.extendedWorkbench.item.ItemExtendedSword;
import naruto1310.extendedWorkbench.packet.PacketCompassUpdate;
import naruto1310.extendedWorkbench.packet.PacketMapUpdate;
import naruto1310.extendedWorkbench.packet.PacketMapZoom;
import naruto1310.extendedWorkbench.packet.PacketMapZoom2;
import naruto1310.extendedWorkbench.packet.PacketPipeline;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.RecipeSorter;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "extendedWorkbench", name = "Extended Workbench", version = "1.2.0.4")
public class mod_ExtendedWorkbench 
{
	public static class extendedValues
	{
		public static float increaseToolReach = 1.5f;
		
		//sword
		public static float increaseSwordDurability = 1.5f;
		public static float increaseSwordMiningSpeed = 1.5f;
		public static float increaseSwordDamage = 2f;

		//tool + hoe
		public static float increaseToolDurability = 2f;
		public static float increaseToolPower = 2f;
		public static float increaseToolAttackDamage = 1.2f;
		public static float increaseHoeDurability = 2f;
		
		//bow
		public static float increaseBowDurability = 1.5f;
		public static float increaseBowStrength = 1.5f;
		public static float increaseBowTime = 1.2f;
		public static float increaseBowDamage = 1.5f;
		
		//armor
		public static float increaseArmorDurability = 1.5f;
		//damage reduction increased
		
		//fishing rod
		public static float increaseFishHookThrowSpeed = 1.5f;
		public static float increaseFishingSpeed = 0.8f;
		public static float increaseFishingRodDurability = 1.5f;
		public static float chanceToCatchCookedFish = 0.5f;
		
		//flint and steel
		public static float increaseFlintAndSteelDurability = 1.5f;
		//burn time is doubled
		//it can set mobs directly on fire
		//fire does not go out in rain
		
		//shears
		public static float increaseShearDurability = 1.5f;
		public static float increaseShearMiningSpeed = 1.5f;
		//efficiency is doubled
	}
	
	public static Item[][] item = new Item[5][5];
	public static ItemArmor[][] armor = new ItemArmor[5][5];

	public static Item extendedBow;
	public static Item extendedFishingRod;
	public static Item extendedFlintAndSteel;
	public static Item extendedShears;
	public static Item extendedMap;
	public static Item extendedMapEmpty;
	public static Item extendedCompass;
	
	public static Block extendedFire;
	
	@SideOnly(Side.CLIENT)
	public static KeyBinding zoomIn;
	@SideOnly(Side.CLIENT)
	public static KeyBinding zoomOut;
	
	@SidedProxy(serverSide="naruto1310.extendedWorkbench.EWCommonProxy", clientSide="naruto1310.extendedWorkbench.EWClientProxy")
	public static EWCommonProxy proxy;

	public static final PacketPipeline packetPipeline = new PacketPipeline();
	
	@Instance("extendedWorkbench")
	public static mod_ExtendedWorkbench instance;
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void load(@SuppressWarnings("unused") FMLPreInitializationEvent event)
	{
		if(!EWFMLPlugin.FMLPLuginLoaded)
			throw new RuntimeException("[ExtendedWorkbench]FMLPlugin was not loaded. Cannot continue loading.");
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);

		{
			Object[] material = new Object [] {Blocks.planks, Blocks.cobblestone, Items.iron_ingot, Items.diamond, Items.gold_ingot};
			Object[] handle = new Object[] {Items.stick, Blocks.planks, Blocks.cobblestone, Items.iron_ingot, Items.iron_ingot};
			String s1 = "extendedWorkbench.tool";
			String s2 = "extendedWorkbench:tool" + (biggerTools == 0 ? "" : "b");
			
			for(int i = 0; i < 5; i++)
			{
				item[0][i] = new ItemExtendedSword	(Item.ToolMaterial.values()[i]).setUnlocalizedName(s1 + "0" + i).setTextureName(s2 + "0" + i);
				item[1][i] = new ItemExtendedShovel	(Item.ToolMaterial.values()[i]).setUnlocalizedName(s1 + "1" + i).setTextureName(s2 + "1" + i);
				item[2][i] = new ItemExtendedPickaxe(Item.ToolMaterial.values()[i]).setUnlocalizedName(s1 + "2" + i).setTextureName(s2 + "2" + i);
				item[3][i] = new ItemExtendedAxe	(Item.ToolMaterial.values()[i]).setUnlocalizedName(s1 + "3" + i).setTextureName(s2 + "3" + i);
				item[4][i] = new ItemExtendedHoe	(Item.ToolMaterial.values()[i]).setUnlocalizedName(s1 + "4" + i).setTextureName(s2 + "4" + i);
				
				ExtendedCraftingManager.addRecipe(new ItemStack(item[0][i], 1), new Object[] {" X ", " X ", " X ", " X ", "YXY", " Y ", ('X'), material[i], ('Y'), handle[i]});
				ExtendedCraftingManager.addRecipe(new ItemStack(item[1][i], 1), new Object[] {" X ", " X ", " Y ", " Y ", " Y ", " Y ", ('X'), material[i], ('Y'), handle[i]});
				ExtendedCraftingManager.addRecipe(new ItemStack(item[2][i], 1), new Object[] {"XX ", " XX", " YX", " Y ", " Y ", " Y ", ('X'), material[i], ('Y'), handle[i]});
				ExtendedCraftingManager.addRecipe(new ItemStack(item[3][i], 1), new Object[] {"X  ", "XXX", "XY ", " Y ", " Y ", " Y ", ('X'), material[i], ('Y'), handle[i]});
				ExtendedCraftingManager.addRecipe(new ItemStack(item[4][i], 1), new Object[] {" X ", " XX", " Y ", " Y ", " Y ", " Y ", ('X'), material[i], ('Y'), handle[i]});
				
				for(int j = 0; j < 5; j++)
					GameRegistry.registerItem(item[j][i], "tool" + j + i);
			}
		}

		extendedBow = new ItemExtendedBow().setUnlocalizedName("extendedWorkbench.bow").setTextureName("extendedWorkbench:bow").setFull3D();
		ExtendedCraftingManager.addRecipe(new ItemStack(extendedBow, 1), new Object[] {" YX", "Y X", "Y X", "Y X", "Y X", " YX", ('X'), Items.string, ('Y'), Items.stick});
		GameRegistry.registerItem(extendedBow, "bow");	
		
		extendedFishingRod = new ItemExtendedFishingRod().setUnlocalizedName("extendedWorkbench.fishingRod").setTextureName("extendedWorkbench:fishingRod");
		ExtendedCraftingManager.addRecipe(new ItemStack(extendedFishingRod, 1), new Object[] {"  X", " XY", "X Y", "X Y", "X Y", ('X'), Items.stick, ('Y'), Items.string});
		EntityRegistry.registerGlobalEntityID(EntityExtendedFishHook.class, "extendedFishingHook", EntityRegistry.findGlobalUniqueEntityId());
		GameRegistry.registerItem(extendedFishingRod, "fishingRod");	
		
		extendedFlintAndSteel = new ItemExtendedFlintAndSteel().setUnlocalizedName("extendedWorkbench.flintAndSteel").setTextureName("extendedWorkbench:flintAndSteel");
		ExtendedCraftingManager.addRecipe(new ItemStack(extendedFlintAndSteel, 1), new Object[] {"X ", "X ", " Y", " Y", ('X'), Items.flint, ('Y'), Items.iron_ingot});
		GameRegistry.registerItem(extendedFlintAndSteel, "flintAndSteel");	
		extendedFire = new BlockExtendedFire().setHardness(0.0F).setLightLevel(1.0F).setStepSound(Block.soundTypeWood).setBlockName("fire").setBlockTextureName("fire");
		GameRegistry.registerBlock(extendedFire, "extendedFire");
		
		extendedShears = new ItemExtendedShears().setUnlocalizedName("extendedWorkbench.shears").setTextureName("extendedWorkbench:shears");
		ExtendedCraftingManager.addRecipe(new ItemStack(extendedShears, 1), new Object[] {"X ", " X", "X ", " X", "X ", ('X'), Items.iron_ingot});
		GameRegistry.registerItem(extendedShears, "shears");	

		extendedMapEmpty = new ItemExtendedMapEmpty().setUnlocalizedName("extendedWorkbench.map.empty").setTextureName("extendedWorkbench:map_empty");
		ExtendedCraftingManager.addRecipe(new ItemStack(extendedMapEmpty, 1, -1), new Object[] {"XXX", "XYX", "XZX", "XYX", "XXX", ('X'), Items.paper, ('Y'), Items.redstone, ('Z'), Items.compass});
		GameRegistry.registerItem(extendedMapEmpty, "map_empty");	

		extendedMap = new ItemExtendedMap().setUnlocalizedName("extendedWorkbench.map");
		RecipeSorter.register("extendedWorkbench:mapUpdate", EWRecipes.mapUpdate.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		CraftingManager.getInstance().getRecipeList().add(new EWRecipes.mapUpdate());
		RecipeSorter.register("extendedWorkbench:extendedMapCloning",   EWRecipes.RecipeExtendedMapCloning.class,   RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		CraftingManager.getInstance().getRecipeList().add(new EWRecipes.RecipeExtendedMapCloning());
		GameRegistry.registerItem(extendedMap, "map");	
		
		extendedCompass = new ItemExtendedCompass().setUnlocalizedName("extendedWorkbench.compass").setTextureName("extendedWorkbench:compass");
		ExtendedCraftingManager.addRecipe(new ItemStack(extendedCompass), new Object[] {" X ", "XYX", " X ", "XYX", " X ", ('X'), Items.iron_ingot, ('Y'), Items.redstone});
		GameRegistry.registerItem(extendedCompass, "compass");	
		
		int armorRenderer[] = new int[5];
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
			armorRenderer =	new int[]	{	RenderingRegistry.addNewArmourRendererPrefix("extendedCloth"),
											-1, /* Chainmail armor */
											RenderingRegistry.addNewArmourRendererPrefix("extendedIron"),
											RenderingRegistry.addNewArmourRendererPrefix("extendedGold"),
											RenderingRegistry.addNewArmourRendererPrefix("extendedDiamond")
										};

		{
			Object[] material = new Object[] {Items.leather, Blocks.fire, Items.iron_ingot, Items.gold_ingot, Items.diamond};
			
			for(int i = 0; i < 5; i++)
			{
				if(i == 1)
					continue; // skip chainmail
	
				for(int j = 0; j < 4; j++)
				{
					armor[i][j] = (ItemArmor) new ItemExtendedArmor(ItemArmor.ArmorMaterial.values()[i], armorRenderer[i], j).setUnlocalizedName("extendedWorkbench.armor" + i + j).setTextureName("extendedWorkbench:armor" + i + j);
					GameRegistry.registerItem(armor[i][j], "armor" + i + j);	
				}
	
				ExtendedCraftingManager.addRecipe(new ItemStack(armor[i][3], 1), new Object[] {"X X", "X X", "X X",			('X'), material[i]});
				ExtendedCraftingManager.addRecipe(new ItemStack(armor[i][2], 1), new Object[] {"XXX", "XXX", "X X", "X X",	('X'), material[i]});
				ExtendedCraftingManager.addRecipe(new ItemStack(armor[i][1], 1), new Object[] {"X X", "XXX", "XXX", "XXX",	('X'), material[i]});
				ExtendedCraftingManager.addRecipe(new ItemStack(armor[i][0], 1), new Object[] {"XXX", "XXX", "X X",			('X'), material[i]});
			}
		}
		
		proxy.init();
		
	    packetPipeline.init();
	    packetPipeline.registerPacket(PacketCompassUpdate.class);	//send to server when client changes needles
	    packetPipeline.registerPacket(PacketMapUpdate.class);		//send to client when map updates
	    packetPipeline.registerPacket(PacketMapZoom.class);			//send to server when client zooms
	    packetPipeline.registerPacket(PacketMapZoom2.class);		//send back to client after updating zoom
	    packetPipeline.postInit();
	}
	
	public static double extendReach(double d)
	{
		if(Minecraft.getMinecraft().thePlayer != null)
		{
			ItemStack stack = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
	        if(d < 6 && stack != null && stack.toString().toLowerCase().contains("extendedworkbench"))
	        	d *= mod_ExtendedWorkbench.extendedValues.increaseToolReach;
		}
        return d;
	}
}