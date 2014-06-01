package modtweaker.exnihilo.action;

import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import exnihilo.registries.CompostRegistry;
import exnihilo.registries.helpers.Compostable;

public class CompostAddRecipeAction implements IUndoableAction {
	private final Compostable recipe;
	public CompostAddRecipeAction(Compostable recipe) {
		this.recipe = recipe;
	}

	@Override
	public void apply() {
		CompostRegistry.entries.put(recipe.id + ":" + recipe.meta, recipe);
	}

	@Override
	public boolean canUndo() {
		return CompostRegistry.entries != null;
	}

	@Override
	public void undo() {
		CompostRegistry.entries.remove(recipe.id + ":" + recipe.meta);
	}
	
	@Override
	public String describe() {
		return "Adding Compostable: " + new ItemStack(recipe.id, 1, recipe.meta).getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Compostable: " + new ItemStack(recipe.id, 1, recipe.meta).getDisplayName();
	}
}
