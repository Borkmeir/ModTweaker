package naruto1310.extendedWorkbench.block;

import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockExtendedWorkbench extends BlockWorkbench
{
    private IIcon[] top;

    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	if(side == 1 && meta != 0 && meta < 5)
    		return this.top[meta - 1];
        
    	return super.getIcon(side, meta);
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
    	super.registerBlockIcons(register);
    	this.top = new IIcon[4];
    	for(int i = 0; i < 4; i++)
    		this.top[i] = register.registerIcon("extendedWorkbench:top" + i);
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int l, float f, float f2, float f3)
    {
        if(world.isRemote)
        	return true;
        
        if(world.getBlockMetadata(i, j, k) == 0)
        	player.displayGUIWorkbench(i, j, k);
        else
        	player.openGui(mod_ExtendedWorkbench.instance, 0, world, i, j, k);

        return true;
    }
    
    @Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		updateExtended(world, x, y, z, false);
	}
    
    @Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		super.onNeighborBlockChange(world, x, y, z, block);
		updateExtended(world, x, y, z, true);
	}

	private void updateExtended(World world, int x, int y, int z, boolean removeOnly)
    {
        switch(world.getBlockMetadata(x, y, z))
        {
            case 1:	if(world.getBlock(x + 1, y, z) == Blocks.crafting_table && world.getBlockMetadata(x + 1, y, z) == 3)	return;		break;
            case 2:	if(world.getBlock(x, y, z + 1) == Blocks.crafting_table && world.getBlockMetadata(x, y, z + 1) == 4)	return;		break;
            case 3:	if(world.getBlock(x - 1, y, z) == Blocks.crafting_table && world.getBlockMetadata(x - 1, y, z) == 1)	return;		break;
            case 4:	if(world.getBlock(x, y, z - 1) == Blocks.crafting_table && world.getBlockMetadata(x, y, z - 1) == 2)	return;
        }
    	
        world.setBlockMetadataWithNotify(x, y, z, 0, 3);

        if(removeOnly)
        	return;
        
        byte d = 0;
        if(world.getBlock(x - 1, y, z) == Blocks.crafting_table && world.getBlockMetadata(x - 1, y, z) == 0) d += 3;
        if(world.getBlock(x, y, z - 1) == Blocks.crafting_table && world.getBlockMetadata(x, y, z - 1) == 0) d += 4;
        if(world.getBlock(x + 1, y, z) == Blocks.crafting_table && world.getBlockMetadata(x + 1, y, z) == 0) d += 5;
        if(world.getBlock(x, y, z + 1) == Blocks.crafting_table && world.getBlockMetadata(x, y, z + 1) == 0) d += 6;
        
        switch(d)
        {
        	case 3:
	        	world.setBlockMetadataWithNotify(x, y, z, 3, 3); world.setBlockMetadataWithNotify(x - 1, y, z, 1, 3);
	        	break;
        	case 4:
	        	world.setBlockMetadataWithNotify(x, y, z, 4, 3); world.setBlockMetadataWithNotify(x, y, z - 1, 2, 3);
	        	break;
        	case 5:
	        	world.setBlockMetadataWithNotify(x, y, z, 1, 3); world.setBlockMetadataWithNotify(x + 1, y, z, 3, 3);
	        	break;
        	case 6:
	        	world.setBlockMetadataWithNotify(x, y, z, 2, 3); world.setBlockMetadataWithNotify(x, y, z + 1, 4, 3);
	        	break;
        }
    }
}
