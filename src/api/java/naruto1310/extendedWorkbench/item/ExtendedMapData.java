package naruto1310.extendedWorkbench.item;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.MapData;

public class ExtendedMapData extends MapData
{
    public byte[][] colorsByScale = new byte[5][16384];
    public boolean squareUpdate = false;

    public ExtendedMapData(String par1Str)
    {
        super(par1Str);
    }
    
    @Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        this.dimension = nbt.getByte("dimension");
        this.xCenter = nbt.getInteger("xCenter");
        this.zCenter = nbt.getInteger("zCenter");
        this.scale = nbt.getByte("scale");
        
        if(this.scale < 0)
            this.scale = 0;

        if(this.scale > 4)
            this.scale = 4;

        for(int i = 0; i < 5; i++)
            this.colorsByScale[i] = nbt.getByteArray("colorsByScale_" + i);
        this.colors = this.colorsByScale[this.scale];
        
        this.squareUpdate = nbt.getBoolean("squareUpdate");
    }

    @Override
	public void writeToNBT(NBTTagCompound nbt)
    {
    	this.colorsByScale[this.scale] = this.colors;
    	
        nbt.setByte("dimension",(byte)this.dimension);
        nbt.setInteger("xCenter", this.xCenter);
        nbt.setInteger("zCenter", this.zCenter);
        nbt.setByte("scale", this.scale);

		for(int i = 0; i < 5; i++)
            nbt.setByteArray("colorsByScale_" + i, this.colorsByScale[i]);
		
		nbt.setBoolean("squareUpdate", this.squareUpdate);
    }

    public void setScale(int s)
    {
        this.colorsByScale[this.scale] = this.colors;
        this.scale = (byte)Math.min(Math.max(0, s), 4);
        this.colors = this.colorsByScale[this.scale];
        
        markDirty();
    }
}
