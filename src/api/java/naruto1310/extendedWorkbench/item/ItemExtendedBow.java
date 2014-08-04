package naruto1310.extendedWorkbench.item;

import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseBowDamage;
import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseBowDurability;
import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseBowStrength;
import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseBowTime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemExtendedBow extends ItemBow
{
    private IIcon[] icons;
    
    public ItemExtendedBow()
    {
        super();
        setMaxDamage((int)(getMaxDamage() * increaseBowDurability));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int par4)
    {
        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if(flag || player.inventory.hasItem(Items.arrow))
        {
            int i = getMaxItemUseDuration(stack) - par4;
            i /= increaseBowTime;
            float f = i / 20F;
            f = (f * f + f * 2F) / 3F;

            if(f < 0.10000000000000001D * increaseBowTime)
                return;

            boolean superCharge = false;

            if(f > 1.0F)
            {
                if(f > 9.0f)
                    superCharge = true;

                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(world, player, f * 2 * (superCharge ? 2 : increaseBowStrength));

            if(f >= 1.0F)
                entityarrow.setIsCritical(true);

            int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            if(j > 0)
                entityarrow.setDamage(entityarrow.getDamage() + j * 0.5D + 0.5D);

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            if(k > 0)
                entityarrow.setKnockbackStrength(k);

            if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
                entityarrow.setFire(100);

            stack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            entityarrow.setDamage(entityarrow.getDamage() * increaseBowDamage);
            
            if(!flag)
                player.inventory.consumeInventoryItem(Items.arrow);
            else
                entityarrow.canBePickedUp = 2;

            if(!world.isRemote)
                world.spawnEntityInWorld(entityarrow);
        }
    }

    
    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
		if(Minecraft.getMinecraft().gameSettings.thirdPersonView != 0)
		{
            GL11.glTranslatef(0.0F, -0.6F, -0.025F);
	        GL11.glRotatef(-17.0F, 0.0F, 0.0F, 1.0F);
	        GL11.glRotatef(14.0F, 1.0F, 0.0F, 0.0F);
	        GL11.glRotatef(4.5F, 0.0F, 1.0F, 0.0F);
		}
    	
        int itemInUseCount =  player.getItemInUseDuration();

        if(itemInUseCount >= 100)
            return this.icons[4];

        if(itemInUseCount >= 18 * increaseBowTime)
            return this.icons[3];

        if(itemInUseCount > 13 * increaseBowTime)
            return this.icons[2];

        if(itemInUseCount > 0)
            return this.icons[1];

        return this.icons[0];
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
    	this.icons = new IIcon[5];
    	for(int i = 0; i < 5; i++)
    		this.icons[i] = register.registerIcon("extendedWorkbench:bow" + i);
    	this.itemIcon = this.icons[0];
    }
}
