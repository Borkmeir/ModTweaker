package modtweaker.thermalexpansion;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getFluid;
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

/** NO WORKY WORKY !!! BIG SADFACE **/
public class TransposerRemoveFillRecipe extends TweakerBaseFunction {
	public static final TransposerRemoveFillRecipe INSTANCE = new TransposerRemoveFillRecipe();

	private TransposerRemoveFillRecipe() {
		super("thermalexpansion.transposer.removeFillRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(2, args)) {
			ItemStack input = getItem();
			FluidStack fluid = getFluid();
			Tweaker.apply(new Action(new ComparableItemStackSafe(input), fluid));
		} else
			throwException(this, 2);
	}

	private static class Action implements IUndoableAction {
		private final List key;
		private final ComparableItemStackSafe input;
		private RecipeTransposer recipe;

		public Action(ComparableItemStackSafe input, FluidStack fluid) {
			this.key = Arrays.asList(new Integer[] { Integer.valueOf(input.hashCode()), Integer.valueOf(fluid.fluidID) });
			this.input = input;
		}

		@Override
		public void apply() {
			recipe = TEHacks.transposerFill.get(key);
			TEHacks.transposerFill.remove(key);
			TEHacks.transposerValidation.remove(input);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.transposerFill != null && TEHacks.transposerValidation != null;
		}

		@Override
		public void undo() {
			TEHacks.transposerFill.put(key, recipe);
			TEHacks.transposerValidation.add(input);
		}

		@Override
		public String describe() {
			return "Removing TE Transposer Recipe: " + new ItemStack(input.itemID, input.stackSize, input.metadata).getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring TE Transposer Recipe: " + new ItemStack(input.itemID, input.stackSize, input.metadata).getDisplayName();
		}
	}
}
