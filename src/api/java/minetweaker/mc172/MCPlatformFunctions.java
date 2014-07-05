/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172;

import minetweaker.IPlatformFunctions;
import minetweaker.api.chat.IChatMessage;
import minetweaker.api.item.IItemDefinition;
import static minetweaker.mc172.MineTweakerMod.NETWORK;
import minetweaker.mc172.chat.MCChatMessage;
import minetweaker.mc172.item.MCItemDefinition;
import minetweaker.mc172.network.MineTweakerLoadScriptsPacket;
import net.minecraft.item.Item;

/**
 *
 * @author Stan
 */
public class MCPlatformFunctions implements IPlatformFunctions {
	public static final MCPlatformFunctions INSTANCE = new MCPlatformFunctions();
	
	private MCPlatformFunctions() {}
	
	@Override
	public IChatMessage getMessage(String message) {
		return new MCChatMessage(message);
	}

	@Override
	public void distributeScripts(byte[] data) {
		NETWORK.sendToAll(new MineTweakerLoadScriptsPacket(data));
	}

	@Override
	public IItemDefinition getItemDefinition(int id) {
		Item item = Item.getItemById(id);
		if (item == null) return null;
		String sid = Item.itemRegistry.getNameForObject(item);
		return new MCItemDefinition(sid, item);
	}
}
