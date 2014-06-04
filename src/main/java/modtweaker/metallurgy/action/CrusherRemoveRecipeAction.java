package modtweaker.metallurgy.action;

import modtweaker.metallurgy.MetallurgyHacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.minetweaker.api.IUndoableAction;

public class CrusherRemoveRecipeAction implements IUndoableAction {
	private final ItemStack input;
	private ItemStack output;

	public CrusherRemoveRecipeAction(ItemStack input) {
		this.input = input;
	}

	@Override
	public void apply() {
		if (input.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
			output = MetallurgyHacks.crusher.get(input.itemID);
			MetallurgyHacks.crusher.remove(input.itemID);
		} else {
			output = MetallurgyHacks.crusherMeta.get(input.itemID, input.getItemDamage());
			MetallurgyHacks.crusherMeta.remove(input.itemID, input.getItemDamage());
		}
	}

	@Override
	public boolean canUndo() {
		return input.getItemDamage() == OreDictionary.WILDCARD_VALUE ? MetallurgyHacks.crusher != null : MetallurgyHacks.crusherMeta != null;
	}

	@Override
	public void undo() {
		if (input.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
			MetallurgyHacks.crusher.put(input.itemID, output);
		} else {
			MetallurgyHacks.crusherMeta.put(input.itemID, input.getItemDamage(), output);
		}
	}

	@Override
	public String describe() {
		return "Removing Crusher Recipe: " + output.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Restoring Crusher Recipe: " + output.getDisplayName();
	}
}
