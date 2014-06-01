package modtweaker.mariculture.action;

import mariculture.api.core.IAnvilHandler.RecipeAnvil;
import mariculture.core.helpers.OreDicHelper;
import modtweaker.mariculture.MaricultureHacks;
import stanhebben.minetweaker.api.IUndoableAction;

public class AnvilAddRecipeAction implements IUndoableAction {
	private final RecipeAnvil recipe;
	public AnvilAddRecipeAction(RecipeAnvil recipe) {
		this.recipe = recipe;
	}

	@Override
	public void apply() {
		MaricultureHacks.anvil.put(OreDicHelper.convert(recipe.input), recipe);
	}

	@Override
	public boolean canUndo() {
		return MaricultureHacks.anvil != null;
	}

	@Override
	public void undo() {
		MaricultureHacks.anvil.remove(OreDicHelper.convert(recipe.input));
	}

	@Override
	public String describe() {
		return "Adding Anvil Recipe: " + recipe.output.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Fishing Loot: " + recipe.output.getDisplayName();
	}
}
