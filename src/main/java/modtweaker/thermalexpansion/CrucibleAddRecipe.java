package modtweaker.thermalexpansion;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import thermalexpansion.util.crafting.CrucibleManager.RecipeCrucible;
import cofh.util.inventory.ComparableItemStackSafe;

public class CrucibleAddRecipe extends TweakerBaseFunction {
	public static final CrucibleAddRecipe INSTANCE = new CrucibleAddRecipe();

	private CrucibleAddRecipe() {
		super("thermalexpansion.crucible.addRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(3, args)) {
			ItemStack input = getItem();
			FluidStack output = getFluid();
			int energy = getInt();
			Tweaker.apply(new Action(new ComparableItemStackSafe(input), TEHelper.getCrucibleRecipe(input, output, energy)));
		} else
			throwException(this, 3);
	}

	private static class Action implements IUndoableAction {
		private final ComparableItemStackSafe key;
		private final RecipeCrucible recipe;

		public Action(ComparableItemStackSafe key, RecipeCrucible recipe) {
			this.key = key;
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			TEHacks.crucible.put(key, recipe);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.crucible != null;
		}

		@Override
		public void undo() {
			TEHacks.crucible.remove(key);
		}

		@Override
		public String describe() {
			return "Adding TE Crucible Recipe: " + recipe.getInput().getDisplayName() + " to " + recipe.getOutput().getFluid().getName();
		}

		@Override
		public String describeUndo() {
			return "Removing TE Crucible Recipe: " + recipe.getInput().getDisplayName() + " to " + recipe.getOutput().getFluid().getName();
		}
	}
}
