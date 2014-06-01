package modtweaker.mariculture.action;

import static modtweaker.helpers.ItemHelper.getName;

import java.util.Arrays;
import java.util.List;

import mariculture.api.core.RecipeVat;
import modtweaker.mariculture.MaricultureHacks;
import stanhebben.minetweaker.api.IUndoableAction;

public class VatAddRecipeAction implements IUndoableAction {
	private List<? extends Object> key;
	private final RecipeVat recipe;
	public VatAddRecipeAction(RecipeVat recipe) {
		this.recipe = recipe;
	}

	@Override
	public void apply() {		
		if(recipe.inputFluid2 != null && recipe.inputItem != null)
			key = Arrays.asList(getName(recipe.inputFluid1), getName(recipe.inputFluid2), getName(recipe.inputItem));
		else if(recipe.inputItem != null)
			key = Arrays.asList(getName(recipe.inputFluid1), getName(recipe.inputItem));
		else if(recipe.inputFluid2 != null)
			key = Arrays.asList(getName(recipe.inputFluid1), getName(recipe.inputFluid2));
		else 
			key = Arrays.asList(recipe.inputFluid1);
		
		MaricultureHacks.vat.put(key, recipe);
	}

	@Override
	public boolean canUndo() {
		return MaricultureHacks.vat != null;
	}

	@Override
	public void undo() {
		MaricultureHacks.vat.remove(key);
	}
	
	public String asString(RecipeVat vat) {
		String str = "";
		if(vat.inputFluid1 != null) str = str + vat.inputFluid1.getFluid().getLocalizedName();
		if(vat.inputFluid2 != null) str = str + " + " + vat.inputFluid2.getFluid().getLocalizedName();
		if(vat.inputItem != null) str = str + " + " + vat.inputItem.getDisplayName();
		str = str + " = ";
		if(vat.outputFluid != null) str = str + " " + vat.outputFluid.getFluid().getLocalizedName();
		if(vat.outputItem != null) str = str + (vat.outputFluid == null? "" : " + ") + vat.outputItem.getDisplayName();
		return str;
	}

	@Override
	public String describe() {
		return "Adding Vat Recipe: " + asString(recipe);
	}

	@Override
	public String describeUndo() {
		return "Removing Vat Recipe: " + asString(recipe);
	}
}
