package modtweaker.thermalexpansion;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import thermalexpansion.util.crafting.PulverizerManager.ComparableItemStackPulverizer;
import thermalexpansion.util.crafting.PulverizerManager.RecipePulverizer;

/** NO WORKY WORKY !!! BIG SADFACE **/
public class PulverizerRemoveRecipe extends TweakerBaseFunction {
	public static final PulverizerRemoveRecipe INSTANCE = new PulverizerRemoveRecipe();

	private PulverizerRemoveRecipe() {
		super("thermalexpansion.pulverizer.removeRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(1, args)) {
			ItemStack input = getItem();
			Tweaker.apply(new Action(new ComparableItemStackPulverizer(input)));
		} else
			throwException(this, 1);
	}

	private static class Action implements IUndoableAction {
		private final ComparableItemStackPulverizer key;
		private RecipePulverizer recipe;

		public Action(ComparableItemStackPulverizer key) {
			this.key = key;
		}

		@Override
		public void apply() {
			recipe = TEHacks.pulverizer.get(key);
			TEHacks.pulverizer.remove(key);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.pulverizer != null;
		}

		@Override
		public void undo() {
			TEHacks.pulverizer.put(key, recipe);
		}

		@Override
		public String describe() {
			return "Removing TE Pulverizer Recipe: " + new ItemStack(key.itemID, key.stackSize, key.metadata).getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring TE Pulverizer Recipe: " + new ItemStack(key.itemID, key.stackSize, key.metadata).getDisplayName();
		}
	}
}
