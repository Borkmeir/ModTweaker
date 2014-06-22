package modtweaker.thermalexpansion;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import thermalexpansion.util.crafting.TransposerManager.RecipeTransposer;
import cofh.util.inventory.ComparableItemStackSafe;

/** NO WORKY WORKY !!! BIG SADFACE **/
public class TransposerRemoveExtractRecipe extends TweakerBaseFunction {
	public static final TransposerRemoveExtractRecipe INSTANCE = new TransposerRemoveExtractRecipe();

	private TransposerRemoveExtractRecipe() {
		super("thermalexpansion.transposer.removeFillRecipe");
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
		private final ComparableItemStackSafe input;
		private RecipeTransposer recipe;

		public Action(ComparableItemStackSafe input) {
			this.input = input;
		}

		@Override
		public void apply() {
			recipe = TEHacks.transposerExtract.get(input);
			TEHacks.transposerExtract.remove(input);
			TEHacks.transposerValidation.remove(input);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.transposerExtract != null && TEHacks.transposerValidation != null;
		}

		@Override
		public void undo() {
			TEHacks.transposerExtract.put(input, recipe);
			TEHacks.transposerValidation.add(input);
		}

		@Override
		public String describe() {
			return "Removing TE Transposer Extraction Recipe: " + new ItemStack(input.itemID, input.stackSize, input.metadata).getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring TE Transposer Extraction Recipe: " + new ItemStack(input.itemID, input.stackSize, input.metadata).getDisplayName();
		}
	}
}
