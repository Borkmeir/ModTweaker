package naruto1310.extendedWorkbench.item;

import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseHoeDurability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemExtendedHoe extends ItemHoe
{
    public ItemExtendedHoe(Item.ToolMaterial material)
    {
        super(material);
        this.setMaxDamage((int)(getMaxDamage() * increaseHoeDurability));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int l, float f0, float f1, float f2)
    {
        if(super.onItemUse(stack, player, world, i, j, k, l, f0, f1, f2))
        	return world.setBlock(i, j, k, world.getBlock(i, j, k), 15, 3);
        return false;
    }
}
