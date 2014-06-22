package modtweaker.thermalexpansion;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;

import java.util.Arrays;
import java.util.List;

import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import thermalexpansion.util.crafting.SmelterManager.RecipeSmelter;
import cofh.util.inventory.ComparableItemStackSafe;

/** NO WORKY WORKY !!! BIG SADFACE **/
public class SmelterRemoveRecipe extends TweakerBaseFunction {
	public static final SmelterRemoveRecipe INSTANCE = new SmelterRemoveRecipe();

	private SmelterRemoveRecipe() {
		super("thermalexpansion.smelter.removeRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(2, args)) {
			ItemStack input = getItem();
			ItemStack input2 = getItem();
			Tweaker.apply(new Action(new ComparableItemStackSafe(input), new ComparableItemStackSafe(input2)));
		} else
			throwException(this, 2);
	}

	private static class Action implements IUndoableAction {
		private final List key;
		private final ComparableItemStackSafe primary;
		private final ComparableItemStackSafe secondary;
		private RecipeSmelter recipe;

		public Action(ComparableItemStackSafe primary, ComparableItemStackSafe secondary) {
			this.key = Arrays.asList(new ComparableItemStackSafe[] { primary, secondary });
			this.primary = primary;
			this.secondary = secondary;
		}

		@Override
		public void apply() {
			recipe = TEHacks.smelter.get(key);
			TEHacks.smelter.remove(key);
			TEHacks.smelterValidation.remove(primary);
			TEHacks.smelterValidation.remove(secondary);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.smelter != null && TEHacks.smelterValidation != null;
		}

		@Override
		public void undo() {
			TEHacks.smelter.put(key, recipe);
			TEHacks.smelterValidation.add(primary);
			TEHacks.smelterValidation.add(secondary);
		}

		@Override
		public String describe() {
			return "Removing TE Smelter Recipe for the combo: " + new ItemStack(primary.itemID, primary.stackSize, primary.metadata).getDisplayName() + " + " + new ItemStack(secondary.itemID, secondary.stackSize, secondary.metadata).getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring TE Smelter Recipe: " + new ItemStack(primary.itemID, primary.stackSize, primary.metadata).getDisplayName() + " + " + new ItemStack(secondary.itemID, secondary.stackSize, secondary.metadata).getDisplayName();
		}
	}
}
