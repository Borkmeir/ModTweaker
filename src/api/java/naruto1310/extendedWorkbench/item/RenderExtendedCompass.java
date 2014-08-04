package naruto1310.extendedWorkbench.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderExtendedCompass implements IItemRenderer
{
    double spawnI, spawnJ;
	private int tileSizeBase = 16;
	
	private short[] imageDataR = new short[256];
	private short[] imageDataG = new short[256];
	private short[] imageDataB = new short[256];
	private RenderItem renderItem = new RenderItem();
	
	@Override
	public boolean handleRenderType(ItemStack stack, ItemRenderType type)
	{
		return type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper)
	{
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data)
	{
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			renderEquippedItem(stack.getItem().getIconIndex(stack), 0, 0, 1, 1, 1);
		if(type == ItemRenderType.INVENTORY)
			renderInventoryItem(stack.getItem().getIconIndex(stack), 0, 0, 1, 1, 1);
		
		ItemExtendedCompass.setupNBT(stack);
		
        if(stack.getTagCompound().getLong("lastUpdate") < System.currentTimeMillis() - 50)
        {
        	stack.getTagCompound().setLong("lastUpdate", System.currentTimeMillis());
        	updateNeedles(stack.getTagCompound(), true);
        }
        else
        	updateNeedles(stack.getTagCompound(), false);
        
        GL11.glScalef(1/16f, 1/16f, 1.0001f);
        for(int i = 0; i < 256; i++)
        	if(this.imageDataR[i] != -1 && this.imageDataG[i] != -1 && this.imageDataB[i] != -1)
        	{
        		if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        			renderEquippedItem(ItemExtendedCompass.empty, 14 - i / 16, -1 + i % 16, this.imageDataR[i] / 256f, this.imageDataG[i] / 256f, this.imageDataB[i] / 256f);
        		if(type == ItemRenderType.INVENTORY)
        			renderInventoryItem(ItemExtendedCompass.empty, i / 16, 16 - i % 16, this.imageDataR[i] / 256f, this.imageDataG[i] / 256f, this.imageDataB[i] / 256f);
        	}
	}
	
	private void renderEquippedItem(IIcon icon, int x, int y, float red, float green, float blue)
	{
        GL11.glColor3f(red, green, blue);
        GL11.glTranslatef(x, y, 0);
        ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
		GL11.glTranslatef(-x, -y, 0);
	}
	
	private void renderInventoryItem(IIcon icon, int x, int y, float red, float green, float blue)
	{
        GL11.glColor3f(red, green, blue);
        GL11.glTranslatef(x, y, 0);
        this.renderItem.renderIcon(x * 16, y * 16, icon, x == 0 ? 16 : 17, x == 0 ? 16 : 17);
		GL11.glTranslatef(-x, -y, 0);
	}

    private void updateNeedles(NBTTagCompound nbt, boolean update)
    {
    	for(int i = 0; i < 256; i++)
    		this.imageDataR[i] = this.imageDataG[i] = this.imageDataB[i] = -1;

    	
		for(int k = 0; k < 4; k++)
			if(nbt.hasKey("needle" + k))
			{
				String s = nbt.getString("needle" + k);
    			if(s.length() > 0 && s.charAt(0) == '1')
    			{
					double i = Double.valueOf(s.substring(1, s.indexOf("a"))).doubleValue();
					double j = Double.valueOf(s.substring(s.indexOf("a") + 1, s.indexOf("b"))).doubleValue();
					int x = Integer.valueOf(s.substring(s.indexOf("b") + 1, s.indexOf("c"))).intValue();
					int z = Integer.valueOf(s.substring(s.indexOf("c") + 1, s.indexOf("d"))).intValue();
					int c = Integer.valueOf(s.substring(s.indexOf("d") + 1, s.indexOf("e"))).intValue();

					s = "1" + drawNeedle(new ChunkCoordinates(x, 0, z), i, j, c) + "b" + x + "c" + z + "d"+  c + "e" + s.substring(s.indexOf("e") + 1, s.length());
					if(update)
						nbt.setString("needle" + k, s);
    			}
    		}
    		
		if(nbt.getBoolean("drawSpawnNeedle"))
		{
    		String s = drawNeedle(Minecraft.getMinecraft().theWorld.getSpawnPoint(), this.spawnI, this.spawnJ, 0xff1414);
    		if(update)
    		{
	    		this.spawnI = Double.valueOf(s.substring(0, s.indexOf("a"))).doubleValue();
	    		this.spawnJ = Double.valueOf(s.substring(s.indexOf("a") + 1, s.length())).doubleValue();
    		}
		}
    }
    
    private String drawNeedle(ChunkCoordinates coords, double i, double j, int color)
    {
    	double angle = getNeedleAngle(coords) - Math.PI / 2 - i;
    	
    	while(angle < -Math.PI)	angle += (Math.PI * 2D);
        while(angle >= Math.PI)	angle -= (Math.PI * 2D);

        j += Math.min(1, Math.max(-1, angle)) * 0.1D;
        j *= 0.8D;
        i += j;
        double sin = Math.sin(i);
        double cos = Math.cos(i);
        
        for(int f = -(this.tileSizeBase >> 2); f <= (this.tileSizeBase >> 2); f++)
        {
            int x = (int)((this.tileSizeBase >> 1) + 0.5D + cos * f * 0.3D);
            int y = (int)((this.tileSizeBase >> 1) - 0.5D - sin * f * 0.3D * 0.5D);
            int xy = y * this.tileSizeBase + x;

            this.imageDataR[xy] = this.imageDataG[xy] = this.imageDataB[xy] = 100;
        }
        
        for(int f = -(this.tileSizeBase>>2); f <= this.tileSizeBase; f++)
        {
            int x = (int)((this.tileSizeBase >> 1) + 0.5D + sin * f * 0.3D);
            int y = (int)((this.tileSizeBase >> 1) - 0.5D + cos * f * 0.3D);
            int xy = y * this.tileSizeBase + x;

            this.imageDataR[xy] = (short)(f >= 0 ? ((color & 0xff0000) >> 16) : 100);
            this.imageDataG[xy] = (short)(f >= 0 ? ((color & 0x00ff00) >> 8) : 100);
            this.imageDataB[xy] = (short)(f >= 0 ? (color & 0x0000ff) : 100);
        }

        return i + "a" + j;
    }
    
    private double getNeedleAngle(ChunkCoordinates coords)
    {
        double angle = 0.0D;

        if(Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null)
        {
            double x = coords.posX - Minecraft.getMinecraft().thePlayer.posX;
            double z = coords.posZ - Minecraft.getMinecraft().thePlayer.posZ;
            angle = (Minecraft.getMinecraft().thePlayer.rotationYaw - 90.0F) * Math.PI / 180.0D - Math.atan2(z, x);

            if(!Minecraft.getMinecraft().theWorld.provider.isSurfaceWorld())
                angle = Math.random() * Math.PI * 2.0D;
        }
        
        return angle;
    }
}
