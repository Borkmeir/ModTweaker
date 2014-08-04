package naruto1310.extendedWorkbench;

import naruto1310.extendedWorkbench.block.ContainerExtended;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class EWCommonProxy implements IGuiHandler
{
	public void init() {}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
			case 0:
				return new ContainerExtended(player.inventory, world, x, y, z);	
		}
		
		return null;
		
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
}
