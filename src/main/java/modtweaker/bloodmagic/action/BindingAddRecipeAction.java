package modtweaker.bloodmagic.action;

import stanhebben.minetweaker.api.IUndoableAction;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRecipe;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;

public class BindingAddRecipeAction implements IUndoableAction {
	private final BindingRecipe recipe;
	public BindingAddRecipeAction(BindingRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void apply() {
		BindingRegistry.bindingRecipes.add(recipe);
	}

	@Override
	public boolean canUndo() {
		return BindingRegistry.bindingRecipes != null;
	}

	@Override
	public void undo() {
		BindingRegistry.bindingRecipes.remove(recipe);
	}
	
	@Override
	public String describe() {
		return "Adding Binding Recipe: " + recipe.outputItem.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Binding Recipe: " + recipe.outputItem.getDisplayName();
	}
}
