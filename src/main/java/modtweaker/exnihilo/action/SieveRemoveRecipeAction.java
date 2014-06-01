package modtweaker.exnihilo.action;

import static modtweaker.helpers.ItemHelper.areEqual;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItem;
import exnihilo.registries.SieveRegistry;
import exnihilo.registries.helpers.SiftReward;

public class SieveRemoveRecipeAction implements IUndoableAction {
	private final TweakerItem result;
	private SiftReward recipe;
	
	public SieveRemoveRecipeAction(TweakerItem result) {
		this.result = result;
	}

	@Override
	public void apply() {		
		for(SiftReward r: SieveRegistry.rewards) {
			if(areEqual(result.make(), new ItemStack(r.id, 1, r.meta))) {
				recipe = r;
				break;
			}
		}
		
		if(recipe != null) {
			SieveRegistry.rewards.remove(recipe);
		}
	}

	@Override
	public boolean canUndo() {
		return SieveRegistry.rewards != null;
	}

	@Override
	public void undo() {
		SieveRegistry.rewards.add(recipe);
	}

	@Override
	public String describe() {
		return "Removing Sieve Output: " + result.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Restoring Sieve Output: " + result.getDisplayName();
	}
}
