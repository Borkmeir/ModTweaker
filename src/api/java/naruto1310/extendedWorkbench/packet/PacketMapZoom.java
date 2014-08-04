package naruto1310.extendedWorkbench.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import naruto1310.extendedWorkbench.item.ExtendedMapData;
import naruto1310.extendedWorkbench.item.ItemExtendedMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketMapZoom extends AbstractPacket
{
	private boolean send_in;
	private short send_uniqueID;
	
	private short get_uniqueID;
	private byte get_in;

	public PacketMapZoom()
	{
		this((short)-1, false);
	}
	
	public PacketMapZoom(short uniqueID, boolean in)
	{
		this.send_uniqueID = uniqueID;
		this.send_in = in;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		if(this.send_uniqueID == -1)
			return;
		
		buffer.writeShort(this.send_uniqueID);
		if(this.send_in)
			buffer.writeByte(1);
		else
			buffer.writeByte(3);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.get_uniqueID = buffer.readShort();
		this.get_in = buffer.readByte();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		ExtendedMapData map = ItemExtendedMap.getMPMapData(this.get_uniqueID, player.worldObj);
		map.setScale(map.scale + (this.get_in - 2));

		//send updated colors back
		if(player instanceof EntityPlayerMP)
			mod_ExtendedWorkbench.packetPipeline.sendTo(new PacketMapZoom2(this.get_uniqueID, map), (EntityPlayerMP)player);
	}
}
