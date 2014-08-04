package naruto1310.extendedWorkbench.item;

import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseFishingRodDurability;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemExtendedFishingRod extends ItemFishingRod
{
	private IIcon[] icons;
	
    public ItemExtendedFishingRod()
    {
        super();
        setMaxDamage((int)(getMaxDamage() * increaseFishingRodDurability));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if(par3EntityPlayer.fishEntity != null)
        {
        	//TODO catchFish
            int var4 = par3EntityPlayer.fishEntity.func_146034_e();
            par1ItemStack.damageItem(var4, par3EntityPlayer);
            par3EntityPlayer.swingItem();
        }
        else
        {
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if(!par2World.isRemote)
                par2World.spawnEntityInWorld(new EntityExtendedFishHook(par2World, par3EntityPlayer));

            par3EntityPlayer.swingItem();
        }

        return par1ItemStack;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if(player.fishEntity != null)
            return this.icons[1];

        return this.icons[0];
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
    	this.icons = new IIcon[2];
    	for(int i = 0; i < 2; i++)
    		this.icons[i] = register.registerIcon("extendedWorkbench:fishingRod" + i);
    	this.itemIcon = this.icons[0];
    }
}
