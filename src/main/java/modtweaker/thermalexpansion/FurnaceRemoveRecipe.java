package modtweaker.thermalexpansion;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import thermalexpansion.util.crafting.FurnaceManager.RecipeFurnace;
import cofh.util.inventory.ComparableItemStackSafe;

/** NO WORKY WORKY !!! BIG SADFACE **/
public class FurnaceRemoveRecipe extends TweakerBaseFunction {
	public static final FurnaceRemoveRecipe INSTANCE = new FurnaceRemoveRecipe();

	private FurnaceRemoveRecipe() {
		super("thermalexpansion.furnace.removeRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(1, args)) {
			ItemStack input = getItem();
			Tweaker.apply(new Action(new ComparableItemStackSafe(input)));
		} else
			throwException(this, 1);
	}

	private static class Action implements IUndoableAction {
		private final ComparableItemStackSafe key;
		private RecipeFurnace recipe;

		public Action(ComparableItemStackSafe key) {
			this.key = key;
		}

		@Override
		public void apply() {
			recipe = TEHacks.furnace.get(key);
			TEHacks.furnace.remove(key);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.furnace != null;
		}

		@Override
		public void undo() {
			TEHacks.furnace.put(key, recipe);
		}

		@Override
		public String describe() {
			return "Removing TE Furnace Recipe: " + new ItemStack(key.itemID, key.stackSize, key.metadata).getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring TE Furnace Recipe: " + new ItemStack(key.itemID, key.stackSize, key.metadata).getDisplayName();
		}
	}
}
