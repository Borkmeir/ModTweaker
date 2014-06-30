package exnihilo.registries.helpers;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class Meltable {
	public Block block;
	public int meta;
	public float solidVolume;
	public Fluid fluid;
	public float fluidVolume;
	public Block appearance;
	
	public Meltable(Block block, int meta, float solidAmount, Fluid fluid, float fluidAmount, Block appearance)
	{
		this.block = block;
		this.meta = meta;
		this.solidVolume = solidAmount;
		this.fluid = fluid;
		this.fluidVolume = fluidAmount;
		
		this.appearance = appearance;
	}
	
	public IIcon getIcon()
	{
		return appearance.getIcon(0, 0);
	}
}
