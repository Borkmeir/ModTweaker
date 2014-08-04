package naruto1310.extendedWorkbench;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

/*
 * Adapted from AleXndrTheGr8st
 * https://github.com/AleXndrTheGr8st/SimpleOres-2/blob/master/alexndr/SimpleOres/core/handlers/HandlerTick.java
 * https://github.com/AleXndrTheGr8st/SimpleOres-2/blob/master/alexndr/SimpleOres/core/handlers/ProxyClient.java
 * 2014-01-11 (MC 1.6)
 */
public class EWTickHandler
{
    private static float zoomAmount;
	private static float fovModifierHand = 0F;
	
    //TODO check obfuscated field name (last check: 1.7.2)
	private static final String fovModifierHandName = "fovModifierHand"; 
	private static final String fovModifierHandNameObf = "field_78507_R";	//EntityRenderer.fovModifierHand 

	@SubscribeEvent
	public void tick(RenderTickEvent event)
    {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            
            if(event.type == TickEvent.Type.RENDER)
            {
                    if(player != null && player.getCurrentEquippedItem() != null && player.isUsingItem())
                    {
                            ItemStack item = player.getItemInUse();
                            if(item.getItem() == mod_ExtendedWorkbench.extendedBow)
                            {
                            	float f = 1.0F;

                                if(player.capabilities.isFlying)
                                    f *= 1.1F;
                                
                                int i = player.getItemInUseDuration();
                                float f1 = i / 20F;

                                if(f1 > 1.0F)
                                    f1 = 1.0F;
                                else
                                    f1 *= f1;

                                f *= 1.0F - f1 * EWTickHandler.zoomAmount;
                                EWTickHandler.fovModifierHand = EWTickHandler.fovModifierHand > 0.001F ? EWTickHandler.fovModifierHand : (Float)ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, fovModifierHandName, fovModifierHandNameObf);
                                EWTickHandler.fovModifierHand += (f - EWTickHandler.fovModifierHand) * 0.5F;

                                if(EWTickHandler.fovModifierHand > 1.5F)
                                    EWTickHandler.fovModifierHand = 1.5F;
                                if(EWTickHandler.fovModifierHand < 0.1F)
                                	EWTickHandler.fovModifierHand = 0.1F;
                                
                                ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, EWTickHandler.fovModifierHand, fovModifierHandName, fovModifierHandNameObf);
                                EWTickHandler.zoomAmount = 0.25F;
                            }
                    }
                    else
                    {
                    	EWTickHandler.fovModifierHand = 0F;
                    }
            }
    }
}