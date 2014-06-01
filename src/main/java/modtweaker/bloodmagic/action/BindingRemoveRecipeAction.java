package modtweaker.bloodmagic.action;

import static modtweaker.helpers.ItemHelper.areEqual;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItem;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRecipe;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;

public class BindingRemoveRecipeAction implements IUndoableAction {
	private final TweakerItem result;
	private BindingRecipe recipe;
	
	public BindingRemoveRecipeAction(TweakerItem result) {
		this.result = result;
	}

	@Override
	public void apply() {		
		for(BindingRecipe r: BindingRegistry.bindingRecipes) {
			if(areEqual(result.make(), r.outputItem)) {
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
