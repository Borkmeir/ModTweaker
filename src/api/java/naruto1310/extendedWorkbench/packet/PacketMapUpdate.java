package naruto1310.extendedWorkbench.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import naruto1310.extendedWorkbench.item.ExtendedMapData;
import naruto1310.extendedWorkbench.item.ItemExtendedMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PacketMapUpdate extends AbstractPacket
{
	private ItemStack send_stack;
	private World send_world;
	private EntityPlayer send_player;
	
	private short get_uniqueID;
	private byte[] get_itemData;

	public PacketMapUpdate()
	{
		this(null, null, null);
	}
	
	public PacketMapUpdate(ItemStack stack, World world, EntityPlayer player)
	{
		this.send_stack = stack;
		this.send_world = world;
		this.send_player = player;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		if(this.send_stack == null || this.send_world == null || this.send_player == null)
			return;
		
		byte[] colors = ((ItemExtendedMap)mod_ExtendedWorkbench.extendedMap).getMapData(this.send_stack, this.send_world).getUpdatePacketData(this.send_stack, this.send_world, this.send_player);
    	
    	if(colors == null)
    	{
    		buffer.writeShort((short)-1);
    		return;
    	}
    	
		buffer.writeShort((short)this.send_stack.getItemDamage());
		buffer.writeShort((short)colors.length);
		for(int i = 0; i < colors.length; i++)    			
			buffer.writeByte(colors[i]);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.get_uniqueID = buffer.readShort();
		if(this.get_uniqueID == -1)
			return;
		short s = buffer.readShort();
		this.get_itemData = new byte[s];
		for(int i = 0; i < s; i++)
			this.get_itemData[i] = buffer.readByte();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		if(this.get_uniqueID != -1)
		{
			ExtendedMapData map = ItemExtendedMap.getMPMapData(this.get_uniqueID, player.worldObj);
			map.updateMPMapData(this.get_itemData);
			
			//I spend one week trying to figure out what was wrong until I noticed that I had to add this line. #notcoolMojang
			Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().func_148246_a(map);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {}
}
