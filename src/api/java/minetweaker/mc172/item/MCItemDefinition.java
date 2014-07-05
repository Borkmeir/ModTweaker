/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.item;

import minetweaker.api.MineTweakerMC;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Stan
 */
public class MCItemDefinition implements IItemDefinition {
	private final String id;
	private final Item item;
	
	public MCItemDefinition(String id, Item item) {
		this.id = id;
		this.item = item;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return item.getUnlocalizedName();
	}

	@Override
	public IItemStack makeStack(int meta) {
		return MineTweakerMC.getIItemStackWildcardSize(new ItemStack(item, 1, meta));
	}
}
