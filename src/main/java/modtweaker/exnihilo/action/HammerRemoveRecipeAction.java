package modtweaker.exnihilo.action;

import static modtweaker.util.ItemHelper.areEqual;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import exnihilo.registries.HammerRegistry;
import exnihilo.registries.helpers.Smashable;

public class HammerRemoveRecipeAction implements IUndoableAction {
	private final TweakerItemStack result;
	private Smashable recipe;
	
	public HammerRemoveRecipeAction(TweakerItemStack output) {
		this.result = output;
	}

	@Override
	public void apply() {		
		for(Smashable r: HammerRegistry.rewards) {
			if(areEqual(result.get(), new ItemStack(r.id, 1, r.meta))) {
				recipe = r;
				break;
			}
		}
		
		if(recipe != null) {
			HammerRegistry.rewards.remove(recipe);
		}
	}

	@Override
	public boolean canUndo() {
		return HammerRegistry.rewards != null;
	}

	@Override
	public void undo() {
		HammerRegistry.rewards.add(recipe);
	}

	@Override
	public String describe() {
		return "Removing Hammer Recipe: " + result.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Restoring Hammer Recipe: " + result.getDisplayName();
	}
}
