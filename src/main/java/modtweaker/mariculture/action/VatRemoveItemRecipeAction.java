package modtweaker.mariculture.action;

import static modtweaker.helpers.ItemHelper.getName;

import java.util.List;
import java.util.Map.Entry;

import mariculture.api.core.RecipeVat;
import modtweaker.mariculture.MaricultureHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItem;

public class VatRemoveItemRecipeAction implements IUndoableAction {
	private final TweakerItem output;
	private List<? extends Object> remove;
	private RecipeVat recipe;
	
	public VatRemoveItemRecipeAction(TweakerItem output) {
		this.output = output;
	}

	@Override
	public void apply() {
		remove = null;
		for (Entry<List<? extends Object>, RecipeVat> vat : MaricultureHacks.vat.entrySet()) {
			if(getName(vat.getValue().outputItem).equals(getName(output.make()))) {
				remove = vat.getKey();
				break;
			}
		}
		
		if(remove != null) {
			recipe = MaricultureHacks.vat.get(remove);
			MaricultureHacks.vat.remove(remove);
		}
	}

	@Override
	public boolean canUndo() {
		return MaricultureHacks.vat != null;
	}

	@Override
	public void undo() {
		MaricultureHacks.vat.put(remove, recipe);
	}

	@Override
	public String describe() {
		return "Removing Vat Item Recipe: " + output.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Restoring Vat Item Recipe: " + output.getDisplayName();
	}
}
