package modtweaker.thermalexpansion;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import thermalexpansion.util.crafting.CrucibleManager.RecipeCrucible;
import cofh.util.inventory.ComparableItemStackSafe;

/** NO WORKY WORKY !!! BIG SADFACE **/
public class CrucibleRemoveRecipe extends TweakerBaseFunction {
	public static final CrucibleRemoveRecipe INSTANCE = new CrucibleRemoveRecipe();

	private CrucibleRemoveRecipe() {
		super("thermalexpansion.crucible.removeRecipe");
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
		private RecipeCrucible recipe;

		public Action(ComparableItemStackSafe key) {
			this.key = key;
		}

		@Override
		public void apply() {
			recipe = TEHacks.crucible.get(key);
			TEHacks.crucible.remove(key);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.crucible != null;
		}

		@Override
		public void undo() {
			TEHacks.crucible.put(key, recipe);
		}

		@Override
		public String describe() {
			return "Removing TE Crucible Recipe: " + new ItemStack(key.itemID, key.stackSize, key.metadata).getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring TE Crucible Recipe: " + new ItemStack(key.itemID, key.stackSize, key.metadata).getDisplayName();
		}
	}
}
