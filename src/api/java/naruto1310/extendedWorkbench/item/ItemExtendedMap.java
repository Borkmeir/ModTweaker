package naruto1310.extendedWorkbench.item;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import naruto1310.extendedWorkbench.packet.PacketMapUpdate;
import naruto1310.extendedWorkbench.packet.PacketMapZoom;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multisets;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemExtendedMap extends ItemMap
{
	private static Method func_147915_b = null, func_147914_a = null;
	private static Field mapBackgroundTextures = null;
	
	private IIcon[] icons;
	private static boolean pressed = false;
	
    @SideOnly(Side.CLIENT)
    public static ExtendedMapData getMPMapData(short id, World world)
    {
        String name = "extendedMap_" + id;
        ExtendedMapData mapData = (ExtendedMapData)world.loadItemData(ExtendedMapData.class, name);

        if(mapData == null)
        {
            mapData = new ExtendedMapData(name);
            world.setItemData(name, mapData);
        }

        return mapData;
    }

    @Override
    public ExtendedMapData getMapData(ItemStack stack, World world)
    {
        String name = "extendedMap_" + stack.getItemDamage();
        ExtendedMapData mapData = (ExtendedMapData)world.loadItemData(ExtendedMapData.class, name);

        if(mapData == null && !world.isRemote)
        {
            stack.setItemDamage(world.getUniqueDataId("extendedMap"));
            name = "extendedMap_" + stack.getItemDamage();
            mapData = new ExtendedMapData(name);
            mapData.scale = 3;
            mapData.xCenter = world.getWorldInfo().getSpawnX();
            mapData.zCenter = world.getWorldInfo().getSpawnZ();
            mapData.dimension = world.provider.dimensionId;
            mapData.markDirty();
            world.setItemData(name, mapData);
        }

        return mapData;
    }
    
    //TODO createMapDataPacket
    @Override
	public Packet func_150911_c(ItemStack stack, World world, EntityPlayer player)
    {
    	if(player instanceof EntityPlayerMP)
    		mod_ExtendedWorkbench.packetPipeline.sendTo(new PacketMapUpdate(stack, world, player), (EntityPlayerMP)player);
        return null;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean held)
    {
    	if(world.isRemote)
    	{
    		sendZoomPacket(stack.getItemDamage());
    		return;
    	}
    	
		ExtendedMapData map = getMapData(stack, world);
		autoUpdate(stack, map);		//update square update stuff
    	if(map.squareUpdate)
    	{
    		if(!stack.hasTagCompound())
    			stack.setTagCompound(new NBTTagCompound());
    		stack.getTagCompound().setBoolean("autoUpdate", true);
    	}

        if(entity instanceof EntityPlayer)
        {
            EntityPlayer var7 = (EntityPlayer)entity;
            map.updateVisiblePlayers(var7, stack);
        }

        if(held && world.provider.dimensionId == map.dimension && entity instanceof EntityPlayer)
        {
        	for(byte i = 0; i < 5; i++)
        		updateMapData2(world, entity, map, i);
        	map.colors = map.colorsByScale[map.scale];
        }
    }

    @Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
    	ExtendedMapData map = getMapData(stack, world);
    	
    	if(world.isRemote || !autoUpdate(stack, map))
    		return stack;
    	
    	updateSquare(world, player, map, map.scale);
    	map.colors = map.colorsByScale[map.scale];

		return super.onItemRightClick(stack, world, player);
	}

    public void updateMapData2(World world, Entity entity, ExtendedMapData map, int scale)
    {
        if(world.provider.dimensionId == map.dimension && entity instanceof EntityPlayer)
        {
        	int mapScale = 1 << scale;	//Except for this line and the two ones later with colorsByScale, this method is copied from ItemMap. Kind of a waste actually.
            int xCenter = map.xCenter;
            int zCenter = map.zCenter;
            int xPos = MathHelper.floor_double(entity.posX - xCenter) / mapScale + 64;
            int zPos = MathHelper.floor_double(entity.posZ - zCenter) / mapScale + 64;
            int j1 = 128 / mapScale;

            if(world.provider.hasNoSky)
                j1 /= 2;

            MapData.MapInfo mapinfo = map.func_82568_a((EntityPlayer)entity);
            ++mapinfo.field_82569_d;

            for(int k1 = xPos - j1 + 1; k1 < xPos + j1; ++k1)
            {
                if((k1 & 15) == (mapinfo.field_82569_d & 15))
                {
                    int l1 = 255;
                    int i2 = 0;
                    double d0 = 0.0D;

                    for(int j2 = zPos - j1 - 1; j2 < zPos + j1; ++j2)
                    {
                        if(k1 >= 0 && j2 >= -1 && k1 < 128 && j2 < 128)
                        {
                            int k2 = k1 - xPos;
                            int l2 = j2 - zPos;
                            boolean flag = k2 * k2 + l2 * l2 > (j1 - 2) * (j1 - 2);
                            int i3 = (xCenter / mapScale + k1 - 64) * mapScale;
                            int j3 = (zCenter / mapScale + j2 - 64) * mapScale;
                            HashMultiset<MapColor> hashmultiset = HashMultiset.create();
                            Chunk chunk = world.getChunkFromBlockCoords(i3, j3);

                            if(!chunk.isEmpty())
                            {
                                int k3 = i3 & 15;
                                int l3 = j3 & 15;
                                int i4 = 0;
                                double d1 = 0.0D;
                                int j4;

                                if(world.provider.hasNoSky)
                                {
                                    j4 = i3 + j3 * 231871;
                                    j4 = j4 * j4 * 31287121 + j4 * 11;

                                    if((j4 >> 20 & 1) == 0)
                                        hashmultiset.add(Blocks.dirt.getMapColor(0), 10);
                                    else
                                        hashmultiset.add(Blocks.stone.getMapColor(0), 100);

                                    d1 = 100.0D;
                                }
                                else
                                {
                                    for(j4 = 0; j4 < mapScale; ++j4)
                                    {
                                        for(int k4 = 0; k4 < mapScale; ++k4)
                                        {
                                            int l4 = chunk.getHeightValue(j4 + k3, k4 + l3) + 1;
                                            Block block = Blocks.air;
                                            int i5 = 0;

                                            if(l4 > 1)
                                            {
                                                do
                                                {
                                                    --l4;
                                                    block = chunk.getBlock(j4 + k3, l4, k4 + l3);
                                                    i5 = chunk.getBlockMetadata(j4 + k3, l4, k4 + l3);
                                                }
                                                while (block.getMapColor(i5) == MapColor.airColor && l4 > 0);

                                                if(l4 > 0 && block.getMaterial().isLiquid())
                                                {
                                                    int j5 = l4 - 1;
                                                    Block block1;

                                                    do
                                                    {
                                                        block1 = chunk.getBlock(j4 + k3, j5--, k4 + l3);
                                                        ++i4;
                                                    }
                                                    while (j5 > 0 && block1.getMaterial().isLiquid());
                                                }
                                            }

                                            d1 += (double)l4 / (double)(mapScale * mapScale);
                                            hashmultiset.add(block.getMapColor(i5));
                                        }
                                    }
                                }

                                i4 /= mapScale * mapScale;
                                double d2 = (d1 - d0) * 4.0D / (mapScale + 4) + ((k1 + j2 & 1) - 0.5D) * 0.4D;
                                byte b1 = 1;

                                if(d2 > 0.6D)
                                    b1 = 2;

                                if(d2 < -0.6D)
                                    b1 = 0;

                                MapColor mapcolor = Iterables.getFirst(Multisets.copyHighestCountFirst(hashmultiset), MapColor.airColor);

                                if(mapcolor == MapColor.waterColor)
                                {
                                    d2 = i4 * 0.1D + (k1 + j2 & 1) * 0.2D;
                                    b1 = 1;

                                    if(d2 < 0.5D)
                                        b1 = 2;

                                    if(d2 > 0.9D)
                                        b1 = 0;
                                }

                                d0 = d1;

                                if(j2 >= 0 && k2 * k2 + l2 * l2 < j1 * j1 && (!flag || (k1 + j2 & 1) != 0))
                                {
                                    byte b0 = map.colorsByScale[scale][k1 + j2 * 128];
                                    byte b2 = (byte)(mapcolor.colorIndex * 4 + b1);

                                    if(b0 != b2)
                                    {
                                        if(l1 > j2)
                                            l1 = j2;

                                        if(i2 < j2)
                                            i2 = j2;

                                        map.colorsByScale[scale][k1 + j2 * 128] = b2;
                                    }
                                }
                            }
                        }
                    }

                    if(l1 <= i2)
                        map.setColumnDirty(k1, l1, i2);
                }
            }
        }
    }

    public void updateSquare(World world, Entity entity, ExtendedMapData map, int scale)
    {
        if(world.provider.dimensionId == map.dimension && entity instanceof EntityPlayer)
        {
        	int mapScale = 1 << scale;
            int xCenter = map.xCenter;
            int zCenter = map.zCenter;
            int xPos = MathHelper.floor_double(entity.posX - xCenter) / mapScale + 64;
            int zPos = MathHelper.floor_double(entity.posZ - zCenter) / mapScale + 64;
            //int j1 = 128 / mapScale;

            //if(world.provider.hasNoSky)
                //j1 /= 2;

            MapData.MapInfo mapinfo = map.func_82568_a((EntityPlayer)entity);
            ++mapinfo.field_82569_d;

            
            int radius;

            if(scale == 4)
            {	
            	radius = 16;
            			 
            	xPos = (xPos < 32 ? 0 : xPos < 64 ? 32 : xPos < 96 ? 64 : 96) + radius;
            	zPos = (zPos < 32 ? 0 : zPos < 64 ? 32 : zPos < 96 ? 64 : 96) + radius;
            }
            else
            {
            	if(scale == 3)
            	{	
            		radius = 32;
            				 
            		xPos = (xPos < 64 ? 0 : 64) + radius;
            		zPos = (zPos < 64 ? 0 : 64) + radius;
            	}
            	else
            	{
            		xPos = zPos = radius = 64;
            	}            
            }

            
            radius++;
            
            for(int k1 = xPos - radius + 1; k1 < xPos + radius; ++k1)
            //for(int k1 = xPos - j1 + 1; k1 < xPos + j1; ++k1)
            {
                //if((k1 & 15) == (mapinfo.field_82569_d & 15))
                {
                    int l1 = 255;
                    int i2 = 0;
                    double d0 = 0.0D;

                    for(int j2 = zPos - radius - 1; j2 < zPos + radius; ++j2)
                    //for(int j2 = zPos - j1 - 1; j2 < zPos + j1; ++j2)
                    {
                        //if(k1 >= 0 && j2 >= -1 && k1 < 128 && j2 < 128)
                        {
                            //int k2 = k1 - xPos;
                            //int l2 = j2 - zPos;
                            //boolean flag = k2 * k2 + l2 * l2 > (j1 - 2) * (j1 - 2);
                            int i3 = (xCenter / mapScale + k1 - 64) * mapScale;
                            int j3 = (zCenter / mapScale + j2 - 64) * mapScale;
                            HashMultiset<MapColor> hashmultiset = HashMultiset.create();
                            Chunk chunk = world.getChunkFromBlockCoords(i3, j3);

                            if(!chunk.isEmpty())
                            {
                                int k3 = i3 & 15;
                                int l3 = j3 & 15;
                                int i4 = 0;
                                double d1 = 0.0D;
                                int j4;

                                if(world.provider.hasNoSky)
                                {
                                    j4 = i3 + j3 * 231871;
                                    j4 = j4 * j4 * 31287121 + j4 * 11;

                                    if((j4 >> 20 & 1) == 0)
                                        hashmultiset.add(Blocks.dirt.getMapColor(0), 10);
                                    else
                                        hashmultiset.add(Blocks.stone.getMapColor(0), 100);

                                    d1 = 100.0D;
                                }
                                else
                                {
                                    for(j4 = 0; j4 < mapScale; ++j4)
                                    {
                                        for(int k4 = 0; k4 < mapScale; ++k4)
                                        {
                                            int l4 = chunk.getHeightValue(j4 + k3, k4 + l3) + 1;
                                            Block block = Blocks.air;
                                            int i5 = 0;

                                            if(l4 > 1)
                                            {
                                                do
                                                {
                                                    --l4;
                                                    block = chunk.getBlock(j4 + k3, l4, k4 + l3);
                                                    i5 = chunk.getBlockMetadata(j4 + k3, l4, k4 + l3);
                                                }
                                                while (block.getMapColor(i5) == MapColor.airColor && l4 > 0);

                                                if(l4 > 0 && block.getMaterial().isLiquid())
                                                {
                                                    int j5 = l4 - 1;
                                                    Block block1;

                                                    do
                                                    {
                                                        block1 = chunk.getBlock(j4 + k3, j5--, k4 + l3);
                                                        ++i4;
                                                    }
                                                    while (j5 > 0 && block1.getMaterial().isLiquid());
                                                }
                                            }

                                            d1 += (double)l4 / (double)(mapScale * mapScale);
                                            hashmultiset.add(block.getMapColor(i5));
                                        }
                                    }
                                }

                                i4 /= mapScale * mapScale;
                                double d2 = (d1 - d0) * 4.0D / (mapScale + 4) + ((k1 + j2 & 1) - 0.5D) * 0.4D;
                                byte b1 = 1;

                                if(d2 > 0.6D)
                                    b1 = 2;

                                if(d2 < -0.6D)
                                    b1 = 0;

                                MapColor mapcolor = Iterables.getFirst(Multisets.copyHighestCountFirst(hashmultiset), MapColor.airColor);

                                if(mapcolor == MapColor.waterColor)
                                {
                                    d2 = i4 * 0.1D + (k1 + j2 & 1) * 0.2D;
                                    b1 = 1;

                                    if(d2 < 0.5D)
                                        b1 = 2;

                                    if(d2 > 0.9D)
                                        b1 = 0;
                                }

                                d0 = d1;

                                if(j2 >= 0			&& k1 + j2 * 128 < map.colors.length)//		 && k2 * k2 + l2 * l2 < j1 * j1 && (!flag || (k1 + j2 & 1) != 0))
                                {
                                    byte b0 = map.colorsByScale[scale][k1 + j2 * 128];
                                    byte b2 = (byte)(mapcolor.colorIndex * 4 + b1);

                                    if(b0 != b2)
                                    {
                                        if(l1 > j2)
                                            l1 = j2;

                                        if(i2 < j2)
                                            i2 = j2;

                                        map.colorsByScale[scale][k1 + j2 * 128] = b2;
                                    }
                                }
                            }
                        }
                    }

                    if(l1 <= i2 			&& k1 < 128)
                        map.setColumnDirty(k1, l1, i2);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void sendZoomPacket(int d)
    {
    	boolean in = mod_ExtendedWorkbench.zoomIn.isPressed();
    	boolean out = mod_ExtendedWorkbench.zoomOut.isPressed();
    	
    	if((in || out) && !pressed)
    	{
	    	pressed = true;

	    	//send update to server (and receive new colors in response)
	    	mod_ExtendedWorkbench.packetPipeline.sendToServer(new PacketMapZoom((short)d, in));
    	}
    	
    	if(!in && !out)
    		pressed = false;
    }

    private boolean autoUpdate(ItemStack stack, ExtendedMapData map)
    {
    	//map == null -> getInformation (called before crafting)	-> return stuff from nbt
    	//map != null -> update (called after crafting)				-> write nbt to map, return stuff from map
    	
    	if(map != null && map.squareUpdate)
    		return true; 
    	
    	if(!stack.hasTagCompound())
    		return false;
    	NBTTagCompound nbt = stack.getTagCompound();
    	if(!nbt.hasKey("autoUpdate"))
    		return false;
    	if(!nbt.getBoolean("autoUpdate"))
    		return false;
    	
    	if(map != null)
    		map.squareUpdate = true;
    	return true;
    }

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return "Extended Map #" + stack.getItemDamage();
	}

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
    	this.icons = new IIcon[6];
    	for(int i = 0; i < 6; i++)
    		this.icons[i] = register.registerIcon("extendedWorkbench:map" + i);
    	this.itemIcon = this.icons[0];
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int d)
	{
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		if(player.getCurrentEquippedItem() == null || !(player.getCurrentEquippedItem().getItem() instanceof ItemExtendedMap))
			return this.itemIcon;
		ExtendedMapData map = getMapData(player.getCurrentEquippedItem(), player.worldObj);
		if(map == null)
			return this.itemIcon;
		return this.icons[player.getCurrentEquippedItem().getItemDamage() == d ? (map.scale + 1) : 0];
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean adv)
	{
		if(autoUpdate(stack, null))
			list.add("Square update mode is enabled.");
	}

	
	public static boolean renderInItemFrame(RenderItemFrame render, EntityItemFrame frame, double par2, double par4, double par6)
	{
		if(frame.getDisplayedItem() == null || frame.getDisplayedItem().getItem() != mod_ExtendedWorkbench.extendedMap)
			return false;

		try
		{
			if(func_147915_b == null)
			{
		        for(Method m : RenderItemFrame.class.getDeclaredMethods())
		        {
		        	/*
		        	if(m.getReturnType() == void.class && m.getParameterTypes().length == 1 && m.getParameterTypes()[0] == EntityItemFrame.class)
		        	{
		        		m.setAccessible(true);
		        		if(func_147915_b == null)
		        			func_147915_b = m;
		        	}
		        	//	*/
		        	if(m.getName().contentEquals("func_147915_b"))
		        	{
		        		m.setAccessible(true);
		        		func_147915_b = m;
		        	}
		        			
		        	if(m.getReturnType() == void.class && m.getParameterTypes().length == 4
		        			&& m.getParameterTypes()[0] == EntityItemFrame.class
		        			&& m.getParameterTypes()[1] == double.class
		        			&& m.getParameterTypes()[2] == double.class
		        			&& m.getParameterTypes()[3] == double.class)
		        	{
		        		m.setAccessible(true);
		        		func_147914_a = m;
		        	}
		        }
		        for(Field f : RenderItemFrame.class.getDeclaredFields())
		        {
		        	if(f.getType() == ResourceLocation.class)
		        	{
		        		f.setAccessible(true);
		        		mapBackgroundTextures = f;
		        	}
		        }
			}
			
	        GL11.glPushMatrix();
	        double d3 = frame.posX - par2 - 0.5D;
	        double d4 = frame.posY - par4 - 0.5D;
	        double d5 = frame.posZ - par6 - 0.5D;
	        int i = frame.field_146063_b + Direction.offsetX[frame.hangingDirection];
	        int j = frame.field_146064_c;
	        int k = frame.field_146062_d + Direction.offsetZ[frame.hangingDirection];
	        GL11.glTranslated(i - d3, j - d4, k - d5);
	
	        
	        func_147915_b.invoke(render, frame);
	        
	        {
	        	ItemStack itemstack = frame.getDisplayedItem();

	            if(itemstack != null)
	            {
	                EntityItem entityitem = new EntityItem(frame.worldObj, 0.0D, 0.0D, 0.0D, itemstack);
	                entityitem.getEntityItem().stackSize = 1;
	                entityitem.hoverStart = 0.0F;
	                GL11.glPushMatrix();
	                GL11.glTranslatef(-0.453125F * Direction.offsetX[frame.hangingDirection], -0.18F, -0.453125F * Direction.offsetZ[frame.hangingDirection]);
	                GL11.glRotatef(180.0F + frame.rotationYaw, 0.0F, 1.0F, 0.0F);
	                GL11.glRotatef(-90 * frame.getRotation(), 0.0F, 0.0F, 1.0F);

	                switch(frame.getRotation())
	                {
	                    case 1:
	                        GL11.glTranslatef(-0.16F, -0.16F, 0.0F);
	                        break;
	                    case 2:
	                        GL11.glTranslatef(0.0F, -0.32F, 0.0F);
	                        break;
	                    case 3:
	                        GL11.glTranslatef(0.16F, -0.16F, 0.0F);
	                }

                    RenderManager.instance.renderEngine.bindTexture((ResourceLocation)mapBackgroundTextures.get(render));
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    float f = 0.0078125F;
                    GL11.glScalef(f, f, f);

                    switch(frame.getRotation())
                    {
                        case 0:
                            GL11.glTranslatef(-64.0F, -87.0F, -1.5F);
                            break;
                        case 1:
                            GL11.glTranslatef(-66.5F, -84.5F, -1.5F);
                            break;
                        case 2:
                            GL11.glTranslatef(-64.0F, -82.0F, -1.5F);
                            break;
                        case 3:
                            GL11.glTranslatef(-61.5F, -84.5F, -1.5F);
                    }

                    GL11.glNormal3f(0.0F, 0.0F, -1.0F);
                    MapData mapdata = ((ItemExtendedMap)mod_ExtendedWorkbench.extendedMap).getMapData(entityitem.getEntityItem(), frame.worldObj);
                    GL11.glTranslatef(0.0F, 0.0F, -1.0F);

                    if(mapdata != null)
                        Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().func_148250_a(mapdata, true);

	                GL11.glPopMatrix();
	            }
	        }
	        
	        GL11.glPopMatrix();

	        func_147914_a.invoke(render, frame, par2 + Direction.offsetX[frame.hangingDirection] * 0.3F, par4 - 0.25D, par6 + Direction.offsetZ[frame.hangingDirection] * 0.3F);
		}
		catch(ReflectiveOperationException e)
		{
			e.printStackTrace();
		}

		return true;
	}
	
	/*
	public static boolean renderInItemFrame(EntityItemFrame itemframe, double par2, double par4, double par6, float par8, float par9)
	{
		if(itemframe.getDisplayedItem() == null || itemframe.getDisplayedItem().getItem() != mod_ExtendedWorkbench.extendedMap)
			return false;
		
		GL11.glPushMatrix();
        double d3 = itemframe.posX - par2 - 0.5D;
        double d4 = itemframe.posY - par4 - 0.5D;
        double d5 = itemframe.posZ - par6 - 0.5D;
        int i = itemframe.field_146063_b + Direction.offsetX[itemframe.hangingDirection];
        int j = itemframe.field_146064_c;
        int k = itemframe.field_146062_d + Direction.offsetZ[itemframe.hangingDirection];
        GL11.glTranslated(i - d3, j - d4, k - d5);

        {
	        GL11.glPushMatrix();
	        GL11.glRotatef(itemframe.rotationYaw, 0.0F, 1.0F, 0.0F);
	        RenderManager.instance.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
	        Block block = Blocks.planks;
	        float f = 0.0625F;
	        float f1 = 1.0F;
	        float f2 = f1 / 2.0F;
	        GL11.glPushMatrix();
	        field_147916_f.overrideBlockBounds(0.0D, 0.5F - f2 + 0.0625F, 0.5F - f2 + 0.0625F, f, 0.5F + f2 - 0.0625F, 0.5F + f2 - 0.0625F);
	        field_147916_f.setOverrideBlockTexture(field_94147_f);
	        field_147916_f.renderBlockAsItem(block, 0, 1.0F);
	        field_147916_f.clearOverrideBlockTexture();
	        field_147916_f.unlockBlockBounds();
	        GL11.glPopMatrix();
	        field_147916_f.setOverrideBlockTexture(Blocks.planks.getIcon(1, 2));
	        GL11.glPushMatrix();
	        field_147916_f.overrideBlockBounds(0.0D, 0.5F - f2, 0.5F - f2, f + 1.0E-4F, f + 0.5F - f2, 0.5F + f2);
	        field_147916_f.renderBlockAsItem(block, 0, 1.0F);
	        GL11.glPopMatrix();
	        GL11.glPushMatrix();
	        field_147916_f.overrideBlockBounds(0.0D, 0.5F + f2 - f, 0.5F - f2, f + 1.0E-4F, 0.5F + f2, 0.5F + f2);
	        field_147916_f.renderBlockAsItem(block, 0, 1.0F);
	        GL11.glPopMatrix();
	        GL11.glPushMatrix();
	        field_147916_f.overrideBlockBounds(0.0D, 0.5F - f2, 0.5F - f2, f, 0.5F + f2, f + 0.5F - f2);
	        field_147916_f.renderBlockAsItem(block, 0, 1.0F);
	        GL11.glPopMatrix();
	        GL11.glPushMatrix();
	        field_147916_f.overrideBlockBounds(0.0D, 0.5F - f2, 0.5F + f2 - f, f, 0.5F + f2, 0.5F + f2);
	        field_147916_f.renderBlockAsItem(block, 0, 1.0F);
	        GL11.glPopMatrix();
	        field_147916_f.unlockBlockBounds();
	        field_147916_f.clearOverrideBlockTexture();
	        GL11.glPopMatrix();
		}
        
        {
        	ItemStack itemstack = itemframe.getDisplayedItem();

            if(itemstack != null)
            {
                EntityItem entityitem = new EntityItem(itemframe.worldObj, 0.0D, 0.0D, 0.0D, itemstack);
                entityitem.getEntityItem().stackSize = 1;
                entityitem.hoverStart = 0.0F;
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.453125F * Direction.offsetX[itemframe.hangingDirection], -0.18F, -0.453125F * Direction.offsetZ[itemframe.hangingDirection]);
                GL11.glRotatef(180.0F + itemframe.rotationYaw, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-90 * itemframe.getRotation(), 0.0F, 0.0F, 1.0F);

                switch(itemframe.getRotation())
                {
                    case 1:
                        GL11.glTranslatef(-0.16F, -0.16F, 0.0F);
                        break;
                    case 2:
                        GL11.glTranslatef(0.0F, -0.32F, 0.0F);
                        break;
                    case 3:
                        GL11.glTranslatef(0.16F, -0.16F, 0.0F);
                }

                RenderManager.instance.renderEngine.bindTexture(mapBackgroundTextures);
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                float f = 0.0078125F;
                GL11.glScalef(f, f, f);

                switch(itemframe.getRotation())
                {
                    case 0:
                        GL11.glTranslatef(-64.0F, -87.0F, -1.5F);
                        break;
                    case 1:
                        GL11.glTranslatef(-66.5F, -84.5F, -1.5F);
                        break;
                    case 2:
                        GL11.glTranslatef(-64.0F, -82.0F, -1.5F);
                        break;
                    case 3:
                        GL11.glTranslatef(-61.5F, -84.5F, -1.5F);
                }

                GL11.glNormal3f(0.0F, 0.0F, -1.0F);
                MapData mapdata = ((ItemExtendedMap)mod_ExtendedWorkbench.extendedMap).getMapData(entityitem.getEntityItem(), itemframe.worldObj);
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);

                if(mapdata != null)
                    Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().func_148250_a(mapdata, true);


                GL11.glPopMatrix();
            }
        }

        GL11.glPopMatrix();
        
        {
            double p_147914_2_ = par2 + (Direction.offsetX[itemframe.hangingDirection] * 0.3F);
            double p_147914_4_ = par4 - 0.25D;
            double p_147914_6_ = par6 + (Direction.offsetZ[itemframe.hangingDirection] * 0.3F);

            if (Minecraft.isGuiEnabled() && itemframe.getDisplayedItem() != null && itemframe.getDisplayedItem().hasDisplayName() && RenderManager.instance.field_147941_i == itemframe)
            {
                float f = 1.6F;
                float f1 = 0.016666668F * f;
                double d3_ = itemframe.getDistanceSqToEntity(RenderManager.instance.livingPlayer);
                float f2 = itemframe.isSneaking() ? 32.0F : 64.0F;

                if (d3_ < f2 * f2)
                {
                    String s = itemframe.getDisplayedItem().getDisplayName();

                    if (itemframe.isSneaking())
                    {
                        FontRenderer fontrenderer = RenderManager.instance.getFontRenderer();
                        GL11.glPushMatrix();
                        GL11.glTranslatef((float)p_147914_2_, (float)p_147914_4_ + itemframe.height + 0.5F, (float)p_147914_6_);
                        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
                        GL11.glScalef(-f1, -f1, f1);
                        GL11.glDisable(GL11.GL_LIGHTING);
                        GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
                        GL11.glDepthMask(false);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        Tessellator tessellator = Tessellator.instance;
                        GL11.glDisable(GL11.GL_TEXTURE_2D);
                        tessellator.startDrawingQuads();
                        int i3 = fontrenderer.getStringWidth(s) / 2;
                        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                        tessellator.addVertex(-i3 - 1, -1.0D, 0.0D);
                        tessellator.addVertex(-i3 - 1, 8.0D, 0.0D);
                        tessellator.addVertex(i3 + 1, 8.0D, 0.0D);
                        tessellator.addVertex(i3 + 1, -1.0D, 0.0D);
                        tessellator.draw();
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glDepthMask(true);
                        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
                        GL11.glEnable(GL11.GL_LIGHTING);
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glPopMatrix();
                    }
                    else
                    {
                    	double d33 = itemframe.getDistanceSqToEntity(RenderManager.instance.livingPlayer);

                        if (d33 <= 64 * 64)
                        {
                            FontRenderer fontrenderer = RenderManager.instance.getFontRenderer();
                            float f13 = 0.016666668F * 1.6F;
                            GL11.glPushMatrix();
                            GL11.glTranslatef((float)p_147914_2_ + 0.0F, (float)p_147914_4_ + itemframe.height + 0.5F, (float)p_147914_6_);
                            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                            GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
                            GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
                            GL11.glScalef(-f13, -f13, f13);
                            GL11.glDisable(GL11.GL_LIGHTING);
                            GL11.glDepthMask(false);
                            GL11.glDisable(GL11.GL_DEPTH_TEST);
                            GL11.glEnable(GL11.GL_BLEND);
                            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                            Tessellator tessellator = Tessellator.instance;
                            byte b0 = 0;

                            GL11.glDisable(GL11.GL_TEXTURE_2D);
                            tessellator.startDrawingQuads();
                            int j3 = fontrenderer.getStringWidth(s) / 2;
                            tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                            tessellator.addVertex(-j3 - 1, -1 + b0, 0.0D);
                            tessellator.addVertex(-j3 - 1, 8 + b0, 0.0D);
                            tessellator.addVertex(j3 + 1, 8 + b0, 0.0D);
                            tessellator.addVertex(j3 + 1, -1 + b0, 0.0D);
                            tessellator.draw();
                            GL11.glEnable(GL11.GL_TEXTURE_2D);
                            fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, b0, 553648127);
                            GL11.glEnable(GL11.GL_DEPTH_TEST);
                            GL11.glDepthMask(true);
                            fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, b0, -1);
                            GL11.glEnable(GL11.GL_LIGHTING);
                            GL11.glDisable(GL11.GL_BLEND);
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                            GL11.glPopMatrix();
                        }
                    }
                }
            }
        }
		
		return true;
	}
	//	*/
}
