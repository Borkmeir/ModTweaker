package hacker;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import minetweaker.MineTweakerAPI;
import minetweaker.mc1710.brackets.ItemBracketHandler;
import minetweaker.mc1710.brackets.LiquidBracketHandler;
import minetweaker.mc1710.brackets.OreBracketHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "aMTDevHack", name = "MTDevHack", dependencies = "required-after:MineTweaker3")
public class MTDevHack {

    private static void setLogger(Class clazz, String field) {
        try {
            Field result = clazz.getDeclaredField(field);
            result.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(result, result.getModifiers() & ~Modifier.FINAL);
            result.set(null, HackedLogger.getLogger());
        } catch (Exception e) {}
    }

    @EventHandler
    public void init(FMLPreInitializationEvent event) {
        for (LoggerList.StringClass clazz : LoggerList.clazzes) {
            setLogger(clazz.clazz, clazz.string);
        }
    }

    @EventHandler
    public void init(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("MineTweaker3")) {
            MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
            MineTweakerAPI.registerBracketHandler(new LiquidBracketHandler());
            MineTweakerAPI.registerBracketHandler(new OreBracketHandler());
        }
    }

}
