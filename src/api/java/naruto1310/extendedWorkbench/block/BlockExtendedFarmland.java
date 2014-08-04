package naruto1310.extendedWorkbench.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockExtendedFarmland extends BlockFarmland
{
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if(!BlockExtendedFarmland.isWaterNearby(world, x, y, z) && !world.canLightningStrikeAt(x, y + 1, z))
        {
            int m = world.getBlockMetadata(x, y, z);

            if(m > 0)
                world.setBlockMetadataWithNotify(x, y, z, m - 1, 0);
            else if(!BlockExtendedFarmland.isCropsNearby(world, x, y, z))
                world.setBlock(x, y, z, Blocks.dirt);
        }
        else
        {
            if(world.getBlockMetadata(x, y, z) < 7)
                world.setBlockMetadataWithNotify(x, y, z, 7, 3);
        }
    }

    public static boolean isCropsNearby(World par1World, int par2, int par3, int par4)
    {
        byte var5 = 0;

        for(int var6 = par2 - var5; var6 <= par2 + var5; ++var6)
            for(int var7 = par4 - var5; var7 <= par4 + var5; ++var7)
            {
                Block var8 = par1World.getBlock(var6, par3 + 1, var7);
                if(var8 == Blocks.wheat || var8 == Blocks.melon_stem || var8 == Blocks.pumpkin_stem)
                    return true;
           }

        return false;
    }

    public static boolean isWaterNearby(World world, int x, int y, int z)
    {
        for(int i = x - 4; i <= x + 4; i++)
            for(int j = y; j <= y + 1; j++)
                for(int k = z - 4; k <= z + 4; k++)
                    if(world.getBlock(i, j, k).getMaterial() == Material.water)
                        return true;

        return false;
    }
}
