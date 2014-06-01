package modtweaker.exnihilo.action;

import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import exnihilo.registries.HammerRegistry;
import exnihilo.registries.helpers.Smashable;

public class HammerAddRecipeAction implements IUndoableAction {
	private final Smashable recipe;
	public HammerAddRecipeAction(Smashable recipe) {
		this.recipe = recipe;
	}

	@Override
	public void apply() {
		HammerRegistry.rewards.add(recipe);
	}

	@Override
	public boolean canUndo() {
		return HammerRegistry.rewards != null;
	}

	@Override
	public void undo() {
		HammerRegistry.rewards.remove(recipe);
	}
	
	@Override
	public String describe() {
		return "Adding Hammer Recipe: " + new ItemStack(recipe.id, 1, recipe.meta).getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Adding Hammer Recipe: " + new ItemStack(recipe.id, 1, recipe.meta).getDisplayName();
	}
}
