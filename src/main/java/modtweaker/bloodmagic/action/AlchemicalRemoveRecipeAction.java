package modtweaker.bloodmagic.action;

import static modtweaker.helpers.ItemHelper.areEqual;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItem;
import WayofTime.alchemicalWizardry.api.alchemy.AlchemyRecipe;
import WayofTime.alchemicalWizardry.api.alchemy.AlchemyRecipeRegistry;

public class AlchemicalRemoveRecipeAction implements IUndoableAction {
	private final TweakerItem result;
	private AlchemyRecipe recipe;
	
	public AlchemicalRemoveRecipeAction(TweakerItem result) {
		this.result = result;
	}

	@Override
	public void apply() {		
		for(AlchemyRecipe r: AlchemyRecipeRegistry.recipes) {
			if(areEqual(result.make(), r.getResult())) {
				recipe = r;
				break;
			}
		}
		
		if(recipe != null) {
			AlchemyRecipeRegistry.recipes.remove(recipe);
		}
	}

	@Override
	public boolean canUndo() {
		return AlchemyRecipeRegistry.recipes != null;
	}

	@Override
	public void undo() {
		AlchemyRecipeRegistry.recipes.add(recipe);
	}

	@Override
	public String describe() {
		return "Removing Alchemy Recipe: " + result.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Restoring Alchemy Recipe: " + result.getDisplayName();
	}
}
