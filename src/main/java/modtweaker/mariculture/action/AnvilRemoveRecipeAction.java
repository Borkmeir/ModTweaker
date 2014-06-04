package modtweaker.mariculture.action;

import java.util.Map.Entry;

import mariculture.api.core.IAnvilHandler.RecipeAnvil;
import mariculture.core.helpers.OreDicHelper;
import modtweaker.mariculture.MaricultureHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItemStack;

public class AnvilRemoveRecipeAction implements IUndoableAction {
	private final TweakerItemStack output;
	private String remove;
	private RecipeAnvil recipe;
	
	public AnvilRemoveRecipeAction(TweakerItemStack output) {
		this.output = output;
	}

	@Override
	public void apply() {		
		remove = null;
		for (Entry<String, RecipeAnvil> anvil : MaricultureHacks.anvil.entrySet()) {
			RecipeAnvil r = anvil.getValue();
			if(OreDicHelper.convert(r.output).equals(OreDicHelper.convert(output.get()))) {
				remove = anvil.getKey();
				break;
			}
		}
				
		recipe = MaricultureHacks.anvil.get(remove);
		MaricultureHacks.anvil.remove(remove);
	}

	@Override
	public boolean canUndo() {
		return MaricultureHacks.anvil != null;
	}

	@Override
	public void undo() {
		MaricultureHacks.anvil.put(remove, recipe);
	}
	
	@Override
	public String describe() {
		return "Removing Anvil Recipe: " + output.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Restoring Anvil Recipe: " + output.getDisplayName();
	}
}
