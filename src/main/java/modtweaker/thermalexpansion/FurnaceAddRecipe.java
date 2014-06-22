package modtweaker.thermalexpansion;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import thermalexpansion.util.crafting.FurnaceManager.RecipeFurnace;
import cofh.util.inventory.ComparableItemStackSafe;

public class FurnaceAddRecipe extends TweakerBaseFunction {
	public static final FurnaceAddRecipe INSTANCE = new FurnaceAddRecipe();

	private FurnaceAddRecipe() {
		super("thermalexpansion.furnace.addRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(3, args)) {
			ItemStack input = getItem();
			ItemStack output = getItem();
			int energy = getInt();
			Tweaker.apply(new Action(new ComparableItemStackSafe(input), TEHelper.getFurnaceRecipe(input, output, energy)));
		} else
			throwException(this, 3);
	}

	private static class Action implements IUndoableAction {
		private final ComparableItemStackSafe key;
		private final RecipeFurnace recipe;

		public Action(ComparableItemStackSafe key, RecipeFurnace recipe) {
			this.key = key;
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			TEHacks.furnace.put(key, recipe);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.furnace != null;
		}

		@Override
		public void undo() {
			TEHacks.furnace.remove(key);
		}

		@Override
		public String describe() {
			return "Adding TE Furnace Recipe: " + recipe.getInput().getDisplayName() + " to " + recipe.getOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Adding TE Furnace Recipe: " + recipe.getInput().getDisplayName() + " to " + recipe.getOutput().getDisplayName();
		}
	}
}
