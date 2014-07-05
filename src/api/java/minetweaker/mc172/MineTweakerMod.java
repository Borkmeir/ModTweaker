/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172;

import java.io.File;

import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.logger.FileLogger;
import minetweaker.mc172.brackets.ItemBracketHandler;
import minetweaker.mc172.brackets.LiquidBracketHandler;
import minetweaker.mc172.brackets.OreBracketHandler;
import minetweaker.mc172.furnace.FuelTweaker;
import minetweaker.mc172.furnace.MCFurnaceManager;
import minetweaker.mc172.game.MCGame;
import minetweaker.mc172.mods.MCLoadedMods;
import minetweaker.mc172.network.MineTweakerLoadScriptsHandler;
import minetweaker.mc172.network.MineTweakerLoadScriptsPacket;
import minetweaker.mc172.network.MineTweakerOpenBrowserHandler;
import minetweaker.mc172.network.MineTweakerOpenBrowserPacket;
import minetweaker.mc172.oredict.MCOreDict;
import minetweaker.mc172.recipes.MCRecipeManager;
import minetweaker.mc172.server.MCServer;
import minetweaker.mc172.util.MineTweakerHacks;
import minetweaker.runtime.IScriptProvider;
import minetweaker.runtime.providers.ScriptProviderCascade;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Main mod class. Performs some general logic, initialization of the API and
 * FML event handling.
 * 
 * @author Stan Hebben
 */
@Mod(modid = MineTweakerMod.MODID, version = MineTweakerMod.MCVERSION + "-3.0.0")
public class MineTweakerMod {
	public static final String MODID = "MineTweaker3";
	public static final String MCVERSION = "1.7.2";
	
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
	
	private static final String[] REGISTRIES = {
		"minetweaker.mods.ic2.ClassRegistry",
		"minetweaker.mods.nei.ClassRegistry"
	};
	
	static {
		NETWORK.registerMessage(MineTweakerLoadScriptsHandler.class, MineTweakerLoadScriptsPacket.class, 0, Side.CLIENT);
		NETWORK.registerMessage(MineTweakerOpenBrowserHandler.class, MineTweakerOpenBrowserPacket.class, 1, Side.CLIENT);
	}
	
	@Mod.Instance(MODID)
	public static MineTweakerMod INSTANCE;
	
	public final MCRecipeManager recipes;
	private final IScriptProvider scriptsGlobal;
	
	public MineTweakerMod() {
		MineTweakerAPI.oreDict = new MCOreDict();
		MineTweakerAPI.recipes = recipes = new MCRecipeManager();
		MineTweakerImplementationAPI.logger.addLogger(new FileLogger(new File("minetweaker.log")));
		MineTweakerAPI.game = MCGame.INSTANCE;
		MineTweakerAPI.furnace = new MCFurnaceManager();
		MineTweakerAPI.loadedMods = new MCLoadedMods();
		
		MineTweakerImplementationAPI.platform = MCPlatformFunctions.INSTANCE;
		
		File globalDir = new File("scripts");
		if (!globalDir.exists()) {
			globalDir.mkdirs();
		}
		
		scriptsGlobal = new ScriptProviderDirectory(globalDir);
		MineTweakerAPI.tweaker.setScriptProvider(scriptsGlobal);
	}
	
	// ##########################
	// ### FML Event Handlers ###
	// ##########################
	
	@EventHandler
	public void onLoad(FMLPreInitializationEvent ev) {
		MinecraftForge.EVENT_BUS.register(new FMLEventHandler());
		FMLCommonHandler.instance().bus().register(new FMLEventHandler());
	}
	
	@EventHandler
	public void onPostInit(FMLPostInitializationEvent ev) {
		MineTweakerAPI.registerClassRegistry(MineTweakerRegistry.class);
		MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
		MineTweakerAPI.registerBracketHandler(new LiquidBracketHandler());
		MineTweakerAPI.registerBracketHandler(new OreBracketHandler());
		
		for (String registry : REGISTRIES) {
			MineTweakerAPI.registerClassRegistry(registry);
		}
		
		FuelTweaker.INSTANCE.register();
	}
	
	@EventHandler
	public void onServerAboutToStart(FMLServerAboutToStartEvent ev) {
		// starts before loading worlds
		// perfect place to start MineTweaker!
		System.out.println("[MineTweaker] Server about to start");
		
		File scriptsDir = new File(MineTweakerHacks.getWorldDirectory(ev.getServer()), "scripts");
		if (!scriptsDir.exists()) {
			scriptsDir.mkdir();
		}
		
		MineTweakerAPI.server = new MCServer(ev.getServer());
		
		IScriptProvider scriptsLocal = new ScriptProviderDirectory(scriptsDir);
		IScriptProvider cascaded = new ScriptProviderCascade(scriptsGlobal, scriptsLocal);
		MineTweakerAPI.tweaker.setScriptProvider(cascaded);
		
		MineTweakerImplementationAPI.onServerStart();
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent ev) {
		
	}
	
	@EventHandler
	public void onServerStopped(FMLServerStoppedEvent ev) {
		System.out.println("[MineTweaker] Server stopped");
		
		MineTweakerAPI.server = null;
		MineTweakerImplementationAPI.onServerStop();
	}
}
