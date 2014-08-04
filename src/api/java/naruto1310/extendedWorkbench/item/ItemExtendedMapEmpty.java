package naruto1310.extendedWorkbench.item;

import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEmptyMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemExtendedMapEmpty extends ItemEmptyMap
{
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        ItemStack newStack = new ItemStack(mod_ExtendedWorkbench.extendedMap, 1, world.getUniqueDataId("extendedMap"));
        String name = "extendedMap_" + newStack.getItemDamage();
        ExtendedMapData map = new ExtendedMapData(name);
        world.setItemData(name, map);
        map.scale = 3;
        map.xCenter = (int)player.posX;
        map.zCenter = (int)player.posZ;
        map.dimension = (byte)world.provider.dimensionId;
        map.markDirty();
        stack.stackSize--;

        if(stack.stackSize <= 0)
            return newStack;
        
        if(!player.inventory.addItemStackToInventory(newStack.copy()))
        	player.dropPlayerItemWithRandomChoice(newStack, false);

        return stack;
    }
}
