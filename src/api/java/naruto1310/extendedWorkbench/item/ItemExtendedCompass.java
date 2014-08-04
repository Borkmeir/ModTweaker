package naruto1310.extendedWorkbench.item;

import java.util.ArrayList;
import java.util.List;

import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemExtendedCompass extends Item
{
	public static IIcon empty;
	
	public ItemExtendedCompass()
	{
		super();
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int l, float f0, float f1, float f2)
	{
		if(world.getBlock(x, y, z) == Blocks.standing_sign || world.getBlock(x, y, z) == Blocks.wall_sign)
		{
			if(FMLCommonHandler.instance().getEffectiveSide().isClient())
				return true;
			
			setupNBT(stack);
			TileEntitySign entity = (TileEntitySign)world.getTileEntity(x, y, z);
			if(!stack.getTagCompound().hasKey("needle3"))
			{
				String name = entity.signText[0];
				if(name.startsWith("[") && name.endsWith("]"))
					name = name.substring(1, name.length() - 1);
				String colorText  = entity.signText[1];
				if(colorText.startsWith("0x"))
					colorText = colorText.substring(2);
				else
					if(colorText.startsWith("#"))
						colorText = colorText.substring(1);
				int color = -1;
				try
				{
					color = Integer.parseInt(entity.signText[1], 16);
				}
				catch(NumberFormatException e)
				{
					//TODO dyeColorNames
					for(int i2 = 0; i2 < ItemDye.field_150923_a.length; i2++)
						if(ItemDye.field_150923_a[i2].equalsIgnoreCase(entity.signText[1]))
							//TODO dyeColors
							color = ItemDye.field_150922_c[i2];

					if(color == -1)
					{
						player.addChatMessage(new ChatComponentText("\"" + entity.signText[1] + "\" is not a valid color."));
						return true;
					}
				}

				for(int k = 0; k < 4; k++)
					if(!stack.getTagCompound().hasKey("needle" +  k))
					{
						stack.getTagCompound().setString("needle" + k, "10a0b" + x + "c" + z + "d" + color + "e" + name);
						break;
					}
				
				player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
			}
			return true;
		}
		onItemRightClick(stack, world, player);
		
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		setupNBT(stack);
		player.openGui(mod_ExtendedWorkbench.instance, 1, world, 0, 0, 0);
		return stack;
	}
	
	public static void shiftNeedles(NBTTagCompound nbt)
	{
		List<String> needles = new ArrayList<String>();
		for(int k = 0; k < 4; k++)
			if(nbt.hasKey("needle" + k))
			{
				needles.add(nbt.getString("needle" + k));
				nbt.removeTag("needle" + k);
			}
		for(int k = 0; k < needles.size(); k++)
			nbt.setString("needle" + k, needles.get(k));
	}

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
    	this.itemIcon = register.registerIcon("extendedWorkbench:compass");
    	empty = register.registerIcon("extendedWorkbench:empty");
    }
    
    @Override
    public boolean getShareTag()
    {
    	return true;
    }
    
    public static void setupNBT(ItemStack stack)
    {
    	if(!stack.hasTagCompound())
    		stack.setTagCompound(new NBTTagCompound());
    	if(!stack.getTagCompound().hasKey("drawSpawnNeedle"))
    		stack.getTagCompound().setBoolean("drawSpawnNeedle", true);
    	if(!stack.getTagCompound().hasKey("lastUpdate"))
    		stack.getTagCompound().setLong("lastUpdate", 0);
    }
}
