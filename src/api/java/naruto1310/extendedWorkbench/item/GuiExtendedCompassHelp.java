package naruto1310.extendedWorkbench.item;

import java.util.ArrayList;
import java.util.List;

import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiExtendedCompassHelp extends GuiScreen
{
	private static final ResourceLocation sign = new ResourceLocation("textures/entity/sign.png");
	private static final ResourceLocation tex = new ResourceLocation("extendedworkbench:textures/gui/extendedCompass.png");
	
	int x, y;
	EntityPlayer player;
	
    public GuiExtendedCompassHelp(EntityPlayer player)
    {
    	this.player = player;
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
        this.buttonList.add(new GuiButton(0, this.x + 30, this.y + 151, 50, 20, "Back"));
        this.buttonList.add(new GuiButton(1, this.x + 96, this.y + 151, 50, 20, "Close"));
    }

    @Override
	public void actionPerformed(GuiButton button)
    {
    	if(button.id == 0)
    	{
    		this.player.openGui(mod_ExtendedWorkbench.instance, 1, this.player.worldObj, 0, 0, 0);
    		return;
    	}

    	if(button.id == 1)
    	{
    		this.mc.displayGuiScreen(null);
    		return;
    	}
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
    	drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        float s = 1.2f;
        GL11.glScalef(1, s, 1);
        this.mc.getTextureManager().bindTexture(tex);
        drawTexturedModalRect(this.x, (int)(this.y * (1/s) - 176*0.1f), 0, 0, 176, 166);
        GL11.glScalef(1, 1/s, 1);
        this.fontRendererObj.drawString("Extended Compass Help", this.width / 2 - this.fontRendererObj.getStringWidth("Extended Compass Help") / 2, this.y - 10, 0x404040);

        String help = "The extended compass is able to store up to four custom nee- dles. " +
        		"To add a needle, create a sign. On the first line write the name of your needle. " +
        		"On the se- cond line write its color. You can use the color prefixes from wool or hexadecimal notation. " +
        		"Then right click the sign with the extended compass. You can remove the sign afterwards.";
        List<String> text = new ArrayList<String>();
        text.add("");
        for(String word : help.split(" "))
        	if(text.get(text.size() - 1).length() + word.length() < 33)
        		text.set(text.size() - 1, text.get(text.size() - 1) + " " + word);
        	else
        		text.add(word);
        text.add("Examples:");

        for(int t = 0; t < text.size(); t++)
        	this.fontRendererObj.drawString(text.get(t), this.x + 87 - this.fontRendererObj.getStringWidth(text.get(t)) / 2, this.y + 4 + t * 8, 0x000000);
        
        String[] signs = new String[] {
        		"Beach",
        		"yellow",
        		"My House",
        		"563af2"
        };
        
        s = 0.8f;
        this.mc.getTextureManager().bindTexture(sign);
        GL11.glScalef(s, s/2,1);
        GL11.glColor3f(1, 1, 1);
        drawTexturedModalRect((int)((this.x + 10) * (1/s)), (int)((this.y + 110) * (1/s*2)), 8, 16, 96, 96);
        drawTexturedModalRect((int)((this.x + 92) * (1/s)), (int)((this.y + 110) * (1/s*2)), 8, 16, 96, 96);
        GL11.glScalef(1/s, 1/s*2, 1);
        //s = 1;
        //GL11.glScalef(s, s, 1);
        for(int t = 0; t < 4; t++)
        	this.fontRendererObj.drawString(signs[t], ((this.x + 48 + (t / 2) * 82 - this.fontRendererObj.getStringWidth(signs[t]) / 2)), (this.y + 112 + (t % 2) * 8), 0x000000);
        	//this.fontRenderer.drawString(signs[t], ((this.x + 51 + (t / 2) * (8 + 96 * s) - this.fontRenderer.getStringWidth(signs[t]) / 2) * (1/s)), (int)((this.y + 112 + (t % 2) * 8) * (1/s)), 0x000000);
        //GL11.glScalef(1/s, 1/s, 1);
        
        super.drawScreen(i, j, f);
    }
}
