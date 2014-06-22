package modtweaker.thermalexpansion;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getInt;
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

public class SmelterAddRecipe extends TweakerBaseFunction {
	public static final SmelterAddRecipe INSTANCE = new SmelterAddRecipe();

	private SmelterAddRecipe() {
		super("thermalexpansion.smelter.addRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(new int[] { 4, 6 }, args)) {
			ItemStack input = getItem();
			ItemStack input2 = getItem();
			ItemStack output = getItem();
			ItemStack secondary = null;
			int chance = 0;
			int energy = getInt();
			if (args.length > 3) {
				secondary = getItem();
				chance = getInt();
			}

			Tweaker.apply(new Action(new ComparableItemStackSafe(input), new ComparableItemStackSafe(input2), TEHelper.getSmelterRecipe(input, input2, output, energy, secondary, chance)));
		} else
			throwException(this, new int[] { 3, 5 });
	}

	private static class Action implements IUndoableAction {
		private final List key;
		private final ComparableItemStackSafe primary;
		private final ComparableItemStackSafe secondary;
		private final RecipeSmelter recipe;

		public Action(ComparableItemStackSafe primary, ComparableItemStackSafe secondary, RecipeSmelter recipe) {
			this.key = Arrays.asList(new ComparableItemStackSafe[] { primary, secondary });
			this.primary = primary;
			this.secondary = secondary;
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			TEHacks.smelter.put(key, recipe);
			TEHacks.smelterValidation.add(primary);
			TEHacks.smelterValidation.add(secondary);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.smelter != null && TEHacks.smelterValidation != null;
		}

		@Override
		public void undo() {
			TEHacks.smelter.remove(key);
			TEHacks.smelterValidation.remove(primary);
			TEHacks.smelterValidation.remove(secondary);
		}

		@Override
		public String describe() {
			return "Adding TE Smelter Recipe: " + recipe.getPrimaryInput().getDisplayName() + " + " + recipe.getSecondaryInput().getDisplayName() + " to " + recipe.getPrimaryOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing TE Smelter Recipe: " + recipe.getPrimaryInput().getDisplayName() + " + " + recipe.getSecondaryInput().getDisplayName() + " to " + recipe.getPrimaryOutput().getDisplayName();
		}
	}
}
