package naruto1310.extendedWorkbench.item;

import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import naruto1310.extendedWorkbench.packet.PacketCompassUpdate;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiExtendedCompass extends GuiScreen
{
	private static final ResourceLocation tex = new ResourceLocation("extendedworkbench:textures/gui/extendedCompass.png"); 
	int x, y;
	EntityPlayer player;
	NBTTagCompound nbt;
	
    public GuiExtendedCompass(EntityPlayer player)
    {
    	this.player = player;
    	this.nbt = player.getCurrentEquippedItem().getTagCompound();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void initGui()
    {
        this.x = (this.width) / 2 - 88;
        this.y = (this.height) / 2 - 83;
        this.buttonList.clear();
        
        
        for(int k = 0; k < 4; k++)
        {
        	boolean shown;
        	boolean enabled;

        	if(this.nbt == null || !this.nbt.hasKey("needle" + k))
        	{
        		shown = enabled = false;
        	}
        	else
        	{
        		enabled = true;
        		shown = this.nbt.getString("needle" + k).charAt(0) == '1';
        	}
        	
        	GuiButton del = new GuiButton(k * 2, this.x + 85, this.y + 31 + k * 21, this.fontRendererObj.getStringWidth("Delete") + 10, 20, "Delete");
        	GuiButton show = new GuiButton(k * 2 + 1, this.x + 130, this.y + 31 + k * 21, this.fontRendererObj.getStringWidth("Show") + 10, 20, shown ? "Hide" : "Show");
        	del.enabled = show.enabled = enabled;
        	
        	this.buttonList.add(del);
        	this.buttonList.add(show);
        }
        
        this.buttonList.add(new GuiButton(11, this.x + 130, this.y + 115, this.fontRendererObj.getStringWidth("Show") + 10, 20, this.nbt.getBoolean("drawSpawnNeedle") ? "Hide" : "Show"));
        this.buttonList.add(new GuiButton(12, this.x + 96, this.y + 140, 50, 20, "Close"));
        this.buttonList.add(new GuiButton(13, this.x + 30, this.y + 140, 50, 20, "Help"));
    }

    @Override
	public void actionPerformed(GuiButton button)
    {
    	if(button.id == 12)
    	{
    		this.mc.displayGuiScreen(null);
    		return;
    	}
    	
    	if(button.id == 13)
    	{
    		this.player.openGui(mod_ExtendedWorkbench.instance, 2, this.player.worldObj, 0, 0, 0);
    		return;
    	}

    	if(button.id == 11)
    		this.nbt.setBoolean("drawSpawnNeedle", !this.nbt.getBoolean("drawSpawnNeedle"));

    	
		int k = button.id / 2;
    	
    	if(button.id % 2 == 0)
    	{
    		this.nbt.removeTag("needle" + k);
    		ItemExtendedCompass.shiftNeedles(this.nbt);
    	}

    	if(button.id % 2 == 1 && button.id != 11)
    	{
        	String s = this.nbt.getString("needle" + k);
        	this.nbt.setString("needle" + k, (s.charAt(0) == '0' ? "1" : "0") + s.substring(1, s.length()));
    	}
    	
    	mod_ExtendedWorkbench.packetPipeline.sendToServer(new PacketCompassUpdate(this.nbt));
        initGui();
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
    	drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(tex);
        drawTexturedModalRect(this.x, this.y, 0, 0, 176, 166);
        this.fontRendererObj.drawString("Extended Compass Settings", this.width / 2 - this.fontRendererObj.getStringWidth("Extended Compass Settings") / 2, this.y + 10, 0x404040);
        
        for(int k = 0; k < 4; k++)
        {
        	String s = this.nbt.hasKey("needle" + k) ? this.nbt.getString("needle" + k) : null;
        	this.fontRendererObj.drawString(s == null ? "Unused" : s.substring(s.indexOf("e") + 1, s.length()), this.x + 7, this.y + 37 + k * 21, s == null ? 0x404040 : Integer.valueOf(s.substring(s.indexOf("d") + 1, s.indexOf("e"))).intValue());
        }
        this.fontRendererObj.drawString("Spawn Point", this.x + 7, this.y + 121, 0xff1414);
        super.drawScreen(i, j, f);
    }
}
