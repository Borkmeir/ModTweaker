package tconstruct.util.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mantle.common.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tconstruct.tools.logic.ToolForgeLogic;
import tconstruct.tools.logic.ToolStationLogic;
import cpw.mods.fml.common.network.ByteBufUtils;

public class PacketToolStation extends AbstractPacket
{

    private int x, y, z;
    private String toolName;

    public PacketToolStation()
    {
    }

    public PacketToolStation(int x, int y, int z, String toolName)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.toolName = toolName;
    }

    @Override
    public void encodeInto (ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        ByteBufUtils.writeUTF8String(buffer, toolName);
    }

    @Override
    public void decodeInto (ChannelHandlerContext ctx, ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        toolName = ByteBufUtils.readUTF8String(buffer);
    }

    @Override
    public void handleClientSide (EntityPlayer player)
    {
    }

    @Override
    public void handleServerSide (EntityPlayer player)
    {
        World world = player.worldObj;
        TileEntity te = world.getTileEntity(x, y, z);

        if (te instanceof ToolStationLogic)
        {
            ((ToolStationLogic) te).setToolname(toolName);
        }
        if (te instanceof ToolForgeLogic)
        {
            ((ToolForgeLogic) te).setToolname(toolName);
        }
    }

}
