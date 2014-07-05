/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.player;

import minetweaker.MineTweakerAPI;
import minetweaker.api.MineTweakerMC;
import minetweaker.api.chat.IChatMessage;
import minetweaker.api.data.IData;
import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import minetweaker.mc172.MineTweakerMod;
import minetweaker.mc172.data.NBTConverter;
import minetweaker.mc172.network.MineTweakerOpenBrowserPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IChatComponent;

/**
 *
 * @author Stan
 */
public class MCPlayer implements IPlayer {
	private final EntityPlayer player;
	
	public MCPlayer(EntityPlayer player) {
		if (player == null) throw new IllegalArgumentException("player cannot be null");
		this.player = player;
	}
	
	public EntityPlayer getInternal() {
		return player;
	}
	
	@Override
	public String getId() {
		return null;
	}

	@Override
	public String getName() {
		return player.getCommandSenderName();
	}

	@Override
	public IData getData() {
		return NBTConverter.from(player.getEntityData(), true);
	}
	
	@Override
	public void update(IData data) {
		NBTConverter.updateMap(player.getEntityData(), data);
	}

	@Override
	public void sendChat(IChatMessage message) {
		Object internal = message.getInternal();
		if (!(internal instanceof IChatComponent)) {
			MineTweakerAPI.getLogger().logError("not a valid chat message");
			return;
		}
		player.addChatMessage((IChatComponent) internal);
	}

	@Override
	public int getHotbarSize() {
		return 9;
	}

	@Override
	public IItemStack getHotbarStack(int i) {
		return i < 0 || i >= 9 ? null : MineTweakerMC.getIItemStack(player.inventory.getStackInSlot(i));
	}

	@Override
	public int getInventorySize() {
		return player.inventory.getSizeInventory();
	}

	@Override
	public IItemStack getInventoryStack(int i) {
		return MineTweakerMC.getIItemStack(player.inventory.getStackInSlot(i));
	}

	@Override
	public IItemStack getCurrentItem() {
		return MineTweakerMC.getIItemStack(player.getCurrentEquippedItem());
	}

	@Override
	public void openBrowser(String url) {
		if (player instanceof EntityPlayerMP) {
			MineTweakerMod.NETWORK.sendTo(
					new MineTweakerOpenBrowserPacket(url),
					(EntityPlayerMP) player);
		}
	}
}
