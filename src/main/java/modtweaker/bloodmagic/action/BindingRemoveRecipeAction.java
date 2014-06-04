package modtweaker.bloodmagic.action;

import static modtweaker.helpers.ItemHelper.areEqual;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRecipe;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;

public class BindingRemoveRecipeAction implements IUndoableAction {
	private final TweakerItemStack result;
	private BindingRecipe recipe;
	
	public BindingRemoveRecipeAction(TweakerItemStack output) {
		this.result = output;
	}

	@Override
	public void apply() {		
		for(BindingRecipe r: BindingRegistry.bindingRecipes) {
			if(areEqual(result.get(), r.outputItem)) {
				recipe = r;
				break;
			}
		}
		
		if(recipe != null) {
			BindingRegistry.bindingRecipes.remove(recipe);
		}
	}

	@Override
	public boolean canUndo() {
		return BindingRegistry.bindingRecipes != null;
	}

	@Override
	public void undo() {
		BindingRegistry.bindingRecipes.add(recipe);
	}

	@Override
	public String describe() {
		return "Removing Binding Recipe: " + result.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Restoring Binding Recipe: " + result.getDisplayName();
	}
}
