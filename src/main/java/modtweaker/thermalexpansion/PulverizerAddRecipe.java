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
import thermalexpansion.util.crafting.PulverizerManager.ComparableItemStackPulverizer;
import thermalexpansion.util.crafting.PulverizerManager.RecipePulverizer;

public class PulverizerAddRecipe extends TweakerBaseFunction {
	public static final PulverizerAddRecipe INSTANCE = new PulverizerAddRecipe();

	private PulverizerAddRecipe() {
		super("thermalexpansion.pulverizer.addRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(new int[] { 3, 5 }, args)) {
			ItemStack input = getItem();
			ItemStack output = getItem();
			ItemStack secondary = null;
			int chance = 0;
			int energy = getInt();
			if(args.length > 3) {
				secondary = getItem();
				chance = getInt();
			}
			
			Tweaker.apply(new Action(new ComparableItemStackPulverizer(input), TEHelper.getPulverizerRecipe(input, output, energy, secondary, chance)));
		} else
			throwException(this, new int[] { 3, 5 });
	}

	private static class Action implements IUndoableAction {
		private final ComparableItemStackPulverizer key;
		private final RecipePulverizer recipe;

		public Action(ComparableItemStackPulverizer key, RecipePulverizer recipe) {
			this.key = key;
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			TEHacks.pulverizer.put(key, recipe);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.pulverizer != null;
		}

		@Override
		public void undo() {
			TEHacks.pulverizer.remove(key);
		}

		@Override
		public String describe() {
			return "Adding TE Pulverizer Recipe: " + recipe.getInput().getDisplayName() + " to " + recipe.getPrimaryOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing TE Crucible Recipe: " + recipe.getInput().getDisplayName() + " to " + recipe.getPrimaryOutput().getDisplayName();
		}
	}
}
