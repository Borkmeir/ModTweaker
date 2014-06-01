package modtweaker.bloodmagic.action;

import stanhebben.minetweaker.api.IUndoableAction;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;

public class AltarAddRecipeAction implements IUndoableAction {
	private final AltarRecipe recipe;
	public AltarAddRecipeAction(AltarRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void apply() {
		AltarRecipeRegistry.altarRecipes.add(recipe);
	}

	@Override
	public boolean canUndo() {
		return AltarRecipeRegistry.altarRecipes != null;
	}

	@Override
	public void undo() {
		AltarRecipeRegistry.altarRecipes.remove(recipe);
	}
	
	@Override
	public String describe() {
		return "Adding Altar Recipe: " + recipe.result.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Altar Recipe: " + recipe.result.getDisplayName();
	}
}
