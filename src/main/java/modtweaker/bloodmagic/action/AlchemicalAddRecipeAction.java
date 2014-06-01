package modtweaker.bloodmagic.action;

import stanhebben.minetweaker.api.IUndoableAction;
import WayofTime.alchemicalWizardry.api.alchemy.AlchemyRecipe;
import WayofTime.alchemicalWizardry.api.alchemy.AlchemyRecipeRegistry;

public class AlchemicalAddRecipeAction implements IUndoableAction {
	private final AlchemyRecipe recipe;
	public AlchemicalAddRecipeAction(AlchemyRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void apply() {
		AlchemyRecipeRegistry.recipes.add(recipe);
	}

	@Override
	public boolean canUndo() {
		return AlchemyRecipeRegistry.recipes != null;
	}

	@Override
	public void undo() {
		AlchemyRecipeRegistry.recipes.remove(recipe);
	}
	
	@Override
	public String describe() {
		return "Adding Alchemy Recipe: " + recipe.getResult().getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Alchemy Recipe: " + recipe.getResult().getDisplayName();
	}
}
