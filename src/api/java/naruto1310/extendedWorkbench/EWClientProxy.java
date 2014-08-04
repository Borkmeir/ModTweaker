package naruto1310.extendedWorkbench;

import static naruto1310.extendedWorkbench.EWFMLPlugin.NEI;
import static naruto1310.extendedWorkbench.EWFMLPlugin.biggerTools;
import static naruto1310.extendedWorkbench.EWFMLPlugin.log;
import naruto1310.extendedWorkbench.block.GuiExtended;
import naruto1310.extendedWorkbench.item.EntityExtendedFishHook;
import naruto1310.extendedWorkbench.item.GuiExtendedCompass;
import naruto1310.extendedWorkbench.item.GuiExtendedCompassHelp;
import naruto1310.extendedWorkbench.item.RenderExtendedCompass;
import naruto1310.extendedWorkbench.item.RenderExtendedFishingHook;
import naruto1310.extendedWorkbench.item.RenderExtendedTool;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class EWClientProxy extends EWCommonProxy
{
	@Override
	public void init()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityExtendedFishHook.class, new RenderExtendedFishingHook());
		MinecraftForgeClient.registerItemRenderer(mod_ExtendedWorkbench.extendedCompass, new RenderExtendedCompass());
		
		RenderExtendedTool render = new RenderExtendedTool();
		if(biggerTools != 0)
			for(int i = 0; i < (biggerTools == 1 ? 1 : 5); i++)
				for(int j = 0; j < 5; j++)
					MinecraftForgeClient.registerItemRenderer(mod_ExtendedWorkbench.item[i][j], render);
		
		ClientRegistry.registerKeyBinding(mod_ExtendedWorkbench.zoomIn = new KeyBinding("key.extendedWorkbench.zoomIn", Keyboard.KEY_ADD, "key.categories.extendedWorkbench"));
		ClientRegistry.registerKeyBinding(mod_ExtendedWorkbench.zoomOut = new KeyBinding("key.extendedWorkbench.zoomOut", Keyboard.KEY_SUBTRACT, "key.categories.extendedWorkbench"));
		FMLCommonHandler.instance().bus().register(new EWTickHandler());

		if(NEI)
		{
			try
			{
				Class<?> handler = Class.forName("naruto1310.extendedWorkbench.crafting.ExtendedShapedRecipeHandler");
				Class<?> api = Class.forName("codechicken.nei.api.API");
				api.getMethod("registerRecipeHandler", Class.forName("codechicken.nei.recipe.ICraftingHandler")).invoke(null, handler.newInstance());
				api.getMethod("registerUsageHandler", Class.forName("codechicken.nei.recipe.IUsageHandler")).invoke(null, handler.newInstance());
				log.info("ExtendedWorkbench has successfully registered NEI recipe and usage handler.");
			}
			catch(Exception e)
			{
				log.info("ExtendedWorkbench could not register NEI recipe and usage handler.");
			}
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
		case 0:
			return new GuiExtended(player.inventory, world, x, y, z);
		case 1:
			return new GuiExtendedCompass(player);
		case 2:
			return new GuiExtendedCompassHelp(player);
		}
		
		return null;
	}
}
