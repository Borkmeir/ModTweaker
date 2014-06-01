package modtweaker.exnihilo.action;

import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import exnihilo.registries.HeatRegistry;
import exnihilo.registries.helpers.HeatSource;

public class HeatAddRecipeAction implements IUndoableAction {
	private final HeatSource recipe;
	public HeatAddRecipeAction(HeatSource recipe) {
		this.recipe = recipe;
	}

	@Override
	public void apply() {
		HeatRegistry.entries.put(recipe.id + ":" + recipe.meta, recipe);
	}

	@Override
	public boolean canUndo() {
		return HeatRegistry.entries != null;
	}

	@Override
	public void undo() {
		HeatRegistry.entries.remove(recipe.id + ":" + recipe.meta);
	}
	
	@Override
	public String describe() {
		return "Adding Heat Source: " + new ItemStack(recipe.id, 1, recipe.meta).getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Heat Source: " + new ItemStack(recipe.id, 1, recipe.meta).getDisplayName();
	}
}
