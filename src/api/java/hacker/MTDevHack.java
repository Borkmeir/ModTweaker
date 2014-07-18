package hacker;

import minetweaker.MineTweakerAPI;
import minetweaker.mc1710.brackets.ItemBracketHandler;
import minetweaker.mc1710.brackets.LiquidBracketHandler;
import minetweaker.mc1710.brackets.OreBracketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "MTDevHack", name = "MTDevHack", dependencies = "required-after:MineTweaker3")
public class MTDevHack {
    @EventHandler
    public void init(FMLPostInitializationEvent event) {
        MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
        MineTweakerAPI.registerBracketHandler(new LiquidBracketHandler());
        MineTweakerAPI.registerBracketHandler(new OreBracketHandler());
    }
}
