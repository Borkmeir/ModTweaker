package naruto1310.extendedWorkbench.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PacketCompassUpdate extends AbstractPacket
{
	private NBTTagCompound send_nbt;
	
	private NBTTagCompound get_nbt;
	
	
	public PacketCompassUpdate()
	{
		this(null);
	}
	
	public PacketCompassUpdate(NBTTagCompound nbt)
	{
		this.send_nbt = nbt;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		if(this.send_nbt == null)
			return;
		
		buffer.writeLong(this.send_nbt.getLong("lastUpdate"));
    	buffer.writeBoolean(this.send_nbt.getBoolean("drawSpawnNeedle"));
    	int mk = -1;
    	for(int k = 0; k < 4; k++)
    		if(this.send_nbt.hasKey("needle" + k))
    			mk = k + 1;
    	buffer.writeByte(mk);
	    for(int k = 0; k < mk; k++)
	    {
	    	String s = this.send_nbt.getString("needle" + k);
	    	buffer.writeByte((byte)s.length());
	    	for(int i = 0; i < s.length(); i++)
	    		buffer.writeChar(s.charAt(i));
	    }
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.get_nbt = new NBTTagCompound();
		this.get_nbt.setLong("lastUpdate", buffer.readLong());
		this.get_nbt.setBoolean("drawSpawnNeedle", buffer.readBoolean());
		int mk = buffer.readByte();
		for(int k = 0; k < mk; k++)
		{
			byte b = buffer.readByte();
			String s = "";
			for(int i = 0 ; i < b; i++)
				s = s + buffer.readChar();
			if(!s.isEmpty())
				this.get_nbt.setString("needle" + k, s);
		}

	}

	@Override
	public void handleClientSide(EntityPlayer player) {}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		ItemStack stack = player.getCurrentEquippedItem();
		if(stack == null || stack.getItem() != mod_ExtendedWorkbench.extendedCompass)
			return;
		
		stack.setTagCompound(this.get_nbt);
		player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
	}

}
