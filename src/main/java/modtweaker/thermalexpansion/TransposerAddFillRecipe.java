package modtweaker.thermalexpansion;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;

import java.util.Arrays;
import java.util.List;

import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import thermalexpansion.util.crafting.TransposerManager.RecipeTransposer;
import cofh.util.inventory.ComparableItemStackSafe;

public class TransposerAddFillRecipe extends TweakerBaseFunction {
	public static final TransposerAddFillRecipe INSTANCE = new TransposerAddFillRecipe();

	private TransposerAddFillRecipe() {
		super("thermalexpansion.transposer.addFillRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(4, args)) {
			ItemStack input = getItem();
			ItemStack output = getItem();
			FluidStack fluid = getFluid();
			int energy = getInt();
			Tweaker.apply(new Action(new ComparableItemStackSafe(input), TEHelper.getTransposerRecipe(input, output, fluid, energy, 100)));
		} else
			throwException(this, 4);
	}

	private static class Action implements IUndoableAction {
		private final List key;
		private final ComparableItemStackSafe input;
		private final RecipeTransposer recipe;

		public Action(ComparableItemStackSafe input, RecipeTransposer recipe) {
			this.key = Arrays.asList(new Integer[] { Integer.valueOf(input.hashCode()), Integer.valueOf(recipe.getFluid().fluidID) });
			this.input = input;
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			TEHacks.transposerFill.put(key, recipe);
			TEHacks.transposerValidation.add(input);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.transposerFill != null && TEHacks.transposerValidation != null;
		}

		@Override
		public void undo() {
			TEHacks.transposerFill.remove(key);
			TEHacks.transposerValidation.remove(input);
		}

		@Override
		public String describe() {
			return "Adding TE Transposer Filling Recipe: " + recipe.getOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing TE Transposer Filling Recipe: " + recipe.getOutput().getDisplayName();
		}
	}
}
