package modtweaker.exnihilo.action;

import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import exnihilo.registries.SieveRegistry;
import exnihilo.registries.helpers.SiftReward;

public class SieveAddRecipeAction implements IUndoableAction {
	private final SiftReward recipe;
	public SieveAddRecipeAction(SiftReward recipe) {
		this.recipe = recipe;
	}

	@Override
	public void apply() {
		SieveRegistry.rewards.add(recipe);
	}

	@Override
	public boolean canUndo() {
		return SieveRegistry.rewards != null;
	}

	@Override
	public void undo() {
		SieveRegistry.rewards.remove(recipe);
	}
	
	@Override
	public String describe() {
		return "Adding Sieve Output: " + new ItemStack(recipe.id, 1, recipe.meta).getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Sieve Output: " + new ItemStack(recipe.id, 1, recipe.meta).getDisplayName();
	}
}
