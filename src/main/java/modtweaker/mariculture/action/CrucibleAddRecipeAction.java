package modtweaker.mariculture.action;

import mariculture.api.core.RecipeSmelter;
import mariculture.core.helpers.OreDicHelper;
import modtweaker.mariculture.MaricultureHacks;
import stanhebben.minetweaker.api.IUndoableAction;

public class CrucibleAddRecipeAction implements IUndoableAction {
	private String key;
	private final RecipeSmelter recipe;
	public CrucibleAddRecipeAction(RecipeSmelter recipe) {
		this.recipe = recipe;
	}

	@Override
	public void apply() {		
		if(recipe.input2 != null) key = OreDicHelper.convert(recipe.input) + "|" + OreDicHelper.convert(recipe.input2);
		else key = (OreDicHelper.convert(recipe.input));
		MaricultureHacks.crucible.put(key, recipe);
	}

	@Override
	public boolean canUndo() {
		return MaricultureHacks.crucible != null;
	}

	@Override
	public void undo() {
		MaricultureHacks.crucible.remove(key);
	}
	
	public String asString(RecipeSmelter vat) {
		String str = vat.input.getDisplayName();
		if(vat.input2 != null) str = str + " + " + vat.input2.getDisplayName();
		str = str + " = " + vat.fluid.getFluid().getLocalizedName();
		return str;
	}

	@Override
	public String describe() {
		return "Adding Crucible Furnace Recipe: " + asString(recipe);
	}

	@Override
	public String describeUndo() {
		return "Removing Crucible Furnace Recipe: " + asString(recipe);
	}
}
