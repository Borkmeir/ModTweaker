package modtweaker.tconstruct.actions;

import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import tconstruct.library.crafting.DryingRackRecipes;

public class DryingRackAddRecipeAction implements IUndoableAction {
	private final ItemStack input;
	private final int time;
	private final ItemStack output;
	public DryingRackAddRecipeAction(ItemStack input, int time, ItemStack output) {
		this.input = input;
		this.time = time;
		this.output = output;
	}
	
	@Override
	public void apply() {
		DryingRackRecipes.addDryingRecipe(input, time, output);
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public void undo() {
		return;
	}
	
	@Override
	public String describe() {
		return "Adding Drying Rack Recipe: " + output.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Drying Rack Recipe: " + output.getDisplayName();
	}
}
