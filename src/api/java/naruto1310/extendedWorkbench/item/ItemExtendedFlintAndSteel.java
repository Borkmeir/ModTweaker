package naruto1310.extendedWorkbench.item;

import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseFlintAndSteelDurability;
import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemExtendedFlintAndSteel extends ItemFlintAndSteel
{
    public ItemExtendedFlintAndSteel()
    {
        super();
        this.setMaxDamage((int)(getMaxDamage() * increaseFlintAndSteelDurability));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float f0, float f1, float f2)
    {
		if(side == 0)	y--;
        if(side == 1)	y++;
        if(side == 2)	z--;
        if(side == 3)	z++;
        if(side == 4)	x--;
        if(side == 5)	x++;

        
        if(!player.canPlayerEdit(x, y, z, side, stack))
            return false;
        if(world.isAirBlock(x, y, z))
        {
        	world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
        	world.setBlock(x, y, z, mod_ExtendedWorkbench.extendedFire);
        }
        stack.damageItem(1, player);
        return true;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
    	entity.attackEntityFrom(DamageSource.causeMobDamage(entity), 0);
        entity.setFire(4);
        entity.worldObj.playSoundAtEntity(entity, "fire.ignite", 1.0F, 1.0F);

        stack.damageItem(1, entity);
        
        return true;
    }
}
