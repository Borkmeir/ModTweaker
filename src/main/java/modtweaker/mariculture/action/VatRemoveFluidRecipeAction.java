package modtweaker.mariculture.action;

import static modtweaker.util.ItemHelper.getName;

import java.util.List;
import java.util.Map.Entry;

import mariculture.api.core.RecipeVat;
import modtweaker.mariculture.MaricultureHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;

public class VatRemoveFluidRecipeAction implements IUndoableAction {
	private final TweakerLiquidStack output;
	private List<? extends Object> remove;
	private RecipeVat recipe;
	
	public VatRemoveFluidRecipeAction(TweakerLiquidStack output) {
		this.output = output;
	}

	@Override
	public void apply() {
		remove = null;
		for (Entry<List<? extends Object>, RecipeVat> vat : MaricultureHacks.vat.entrySet()) {
			if(getName(vat.getValue().outputFluid).equals(getName(output.get()))) {
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
		return "Removing Vat Fluid Recipe: " + output.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Vat Fluid Recipe: " + output.getDisplayName();
	}
}
