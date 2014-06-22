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
import thermalexpansion.util.crafting.SawmillManager.ComparableItemStackSawmill;
import thermalexpansion.util.crafting.SawmillManager.RecipeSawmill;

public class SawmillAddRecipe extends TweakerBaseFunction {
	public static final SawmillAddRecipe INSTANCE = new SawmillAddRecipe();

	private SawmillAddRecipe() {
		super("thermalexpansion.sawmill.addRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(new int[] { 3, 5 }, args)) {
			ItemStack input = getItem();
			ItemStack output = getItem();
			int energy = getInt();
			ItemStack secondary = null;
			int chance = 0;
			if(args.length > 3) {
				secondary = getItem();
				chance = getInt();
			}
			
			Tweaker.apply(new Action(new ComparableItemStackSawmill(input), TEHelper.getSawmillRecipe(input, output, energy, secondary, chance)));
		} else
			throwException(this, new int[] { 3, 5 });
	}

	private static class Action implements IUndoableAction {
		private final ComparableItemStackSawmill key;
		private final RecipeSawmill recipe;

		public Action(ComparableItemStackSawmill key, RecipeSawmill recipe) {
			this.key = key;
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			TEHacks.sawmill.put(key, recipe);
		}

		@Override
		public boolean canUndo() {
			return TEHacks.sawmill != null;
		}

		@Override
		public void undo() {
			TEHacks.sawmill.remove(key);
		}

		@Override
		public String describe() {
			return "Adding TE Sawmill Recipe: " + recipe.getInput().getDisplayName() + " to " + recipe.getPrimaryOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing TE Sawmill Recipe: " + recipe.getInput().getDisplayName() + " to " + recipe.getPrimaryOutput().getDisplayName();
		}
	}
}
