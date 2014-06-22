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
import thermalexpansion.util.crafting.TransposerManager.RecipeTransposer;
import cofh.util.inventory.ComparableItemStackSafe;

public class TransposerAddExtractRecipe extends TweakerBaseFunction {
	public static final TransposerAddExtractRecipe INSTANCE = new TransposerAddExtractRecipe();

	private TransposerAddExtractRecipe() {
		super("thermalexpansion.transposer.addExtractRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(new int[] { 4, 5 }, args)) {
			ItemStack input = getItem();
			ItemStack output = getItem();
			FluidStack fluid = getFluid();
			int energy = getInt();
			int chance = 100;
			if (args.length == 5) {
				chance = getInt();
			}

			Tweaker.apply(new Action(new ComparableItemStackSafe(input), TEHelper.getTransposerRecipe(input, output, fluid, energy, chance)));
		} else
			throwException(this, new int[] { 4, 5 });
	}

	private static class Action implements IUndoableAction {
		private final ComparableItemStackSafe input;
		private final RecipeTransposer recipe;

		public Action(ComparableItemStackSafe input, RecipeTransposer recipe) {
			this.input = input;
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			TEHacks.transposerExtract.put(input, recipe);
			TEHacks.transposerValidation.add(input);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.transposerExtract != null && TEHacks.transposerValidation != null;
		}

		@Override
		public void undo() {
			TEHacks.transposerExtract.remove(input);
			TEHacks.transposerValidation.remove(input);
		}

		@Override
		public String describe() {
			return "Adding TE Transposer Extraction Recipe: " + recipe.getOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing TE Transposer Extraction Recipe: " + recipe.getOutput().getDisplayName();
		}
	}
}
