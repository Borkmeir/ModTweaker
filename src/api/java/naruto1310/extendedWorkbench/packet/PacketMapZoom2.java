package naruto1310.extendedWorkbench.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import naruto1310.extendedWorkbench.item.ExtendedMapData;
import naruto1310.extendedWorkbench.item.ItemExtendedMap;
import net.minecraft.entity.player.EntityPlayer;

public class PacketMapZoom2 extends AbstractPacket
{
	private short send_uniqueID;
	private ExtendedMapData send_map;

	private short get_uniqueID;
	private byte[] get_colors;
	private byte get_scale;

	public PacketMapZoom2()
	{
		this((short)-1, null);
	}
	
	public PacketMapZoom2(short uniqueID, ExtendedMapData map)
	{
		this.send_uniqueID = uniqueID;
		this.send_map = map;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		if(this.send_uniqueID == -1 || this.send_map == null)
			return;
		
		// send whole color array to client
		byte[] colors = this.send_map.colors;

		buffer.writeShort(this.send_uniqueID);
		buffer.writeShort((short) colors.length);
		for (int i = 0; i < colors.length; i++)
			buffer.writeByte(colors[i]);
		buffer.writeByte(this.send_map.scale);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.get_uniqueID = buffer.readShort();
		short s = buffer.readShort();
		this.get_colors = new byte[s];
		for (int i = 0; i < s; i++)
			this.get_colors[i] = buffer.readByte();
		this.get_scale = buffer.readByte();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		ExtendedMapData map = ItemExtendedMap.getMPMapData(this.get_uniqueID, player.worldObj);
		map.setScale(this.get_scale);
		map.colors = this.get_colors;
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
	}
}
