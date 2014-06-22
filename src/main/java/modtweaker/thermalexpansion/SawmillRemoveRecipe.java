package modtweaker.thermalexpansion;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import thermalexpansion.util.crafting.SawmillManager.ComparableItemStackSawmill;
import thermalexpansion.util.crafting.SawmillManager.RecipeSawmill;

/** NO WORKY WORKY !!! BIG SADFACE **/
public class SawmillRemoveRecipe extends TweakerBaseFunction {
	public static final SawmillRemoveRecipe INSTANCE = new SawmillRemoveRecipe();

	private SawmillRemoveRecipe() {
		super("thermalexpansion.sawmill.removeRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(1, args)) {
			ItemStack input = getItem();
			Tweaker.apply(new Action(new ComparableItemStackSawmill(input)));
		} else
			throwException(this, 1);
	}

	private static class Action implements IUndoableAction {
		private final ComparableItemStackSawmill key;
		private RecipeSawmill recipe;

		public Action(ComparableItemStackSawmill key) {
			this.key = key;
		}

		@Override
		public void apply() {
			recipe = TEHacks.sawmill.get(key);
			TEHacks.sawmill.remove(key);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.sawmill != null;
		}

		@Override
		public void undo() {
			TEHacks.sawmill.put(key, recipe);
		}

		@Override
		public String describe() {
			return "Removing TE Sawmill Recipe: " + new ItemStack(key.itemID, key.stackSize, key.metadata).getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring TE Sawmill Recipe: " + new ItemStack(key.itemID, key.stackSize, key.metadata).getDisplayName();
		}
	}
}
