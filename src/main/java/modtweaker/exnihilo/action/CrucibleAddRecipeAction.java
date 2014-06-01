package modtweaker.exnihilo.action;

import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import exnihilo.registries.CrucibleRegistry;
import exnihilo.registries.helpers.Meltable;

public class CrucibleAddRecipeAction implements IUndoableAction {
	private final Meltable recipe;
	public CrucibleAddRecipeAction(Meltable recipe) {
		this.recipe = recipe;
	}

	@Override
	public void apply() {
		CrucibleRegistry.entries.put(recipe.id + ":" + recipe.meta, recipe);
	}

	@Override
	public boolean canUndo() {
		return CrucibleRegistry.entries != null;
	}

	@Override
	public void undo() {
		CrucibleRegistry.entries.remove(recipe.id + ":" + recipe.meta);
	}
	
	@Override
	public String describe() {
		return "Adding Crucible Recipe: " + new ItemStack(recipe.id, 1, recipe.meta).getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Crucible Recipe: " + new ItemStack(recipe.id, 1, recipe.meta).getDisplayName();
	}
}
