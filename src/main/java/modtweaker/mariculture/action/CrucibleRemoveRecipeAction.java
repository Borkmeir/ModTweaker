package modtweaker.mariculture.action;

import java.util.Map.Entry;

import mariculture.api.core.RecipeSmelter;
import mariculture.core.helpers.OreDicHelper;
import modtweaker.mariculture.MaricultureHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItem;

public class CrucibleRemoveRecipeAction implements IUndoableAction {
	private final TweakerItem input;
	private final TweakerItem input2;
	private String remove;
	private RecipeSmelter recipe;
	
	public CrucibleRemoveRecipeAction(TweakerItem input, TweakerItem input2) {
		this.input = input;
		this.input2 = input2;
	}

	@Override
	public void apply() {		
		remove = null;
		for (Entry<String, RecipeSmelter> crucible : MaricultureHacks.crucible.entrySet()) {
			RecipeSmelter rep = crucible.getValue();
			if(OreDicHelper.convert(rep.input).equals(OreDicHelper.convert(input.make()))) {
				if(rep.input2 == null) {
					remove = crucible.getKey();
					break;
				} else if(input2 != null) {
					if(OreDicHelper.convert(rep.input2).equals(OreDicHelper.convert(input2.make()))) {
						remove = crucible.getKey();
						break;
					}
				}
			}
		}
				
		recipe = MaricultureHacks.crucible.get(remove);
		MaricultureHacks.crucible.remove(remove);
	}

	@Override
	public boolean canUndo() {
		return MaricultureHacks.crucible != null;
	}

	@Override
	public void undo() {
		MaricultureHacks.crucible.put(remove, recipe);
	}
	
	public String asString() {
		String str = "";
		if(input != null) {
			str = str + input.getDisplayName();
		}
		
		if(input2 != null) {
			str = str + " + " + input2.getDisplayName();
		}
		
		return str;
	}

	@Override
	public String describe() {
		return "Removing Crucible Furnace Recipe: " + asString();
	}

	@Override
	public String describeUndo() {
		return "Restoring Crucible Furnace Recipe: " + asString();
	}
}
