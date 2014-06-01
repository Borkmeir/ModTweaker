package modtweaker.exnihilo.action;

import static modtweaker.helpers.ItemHelper.areEqual;

import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItem;
import exnihilo.registries.CrucibleRegistry;
import exnihilo.registries.helpers.Meltable;

public class CrucibleRemoveRecipeAction implements IUndoableAction {
	private final TweakerItem result;
	private Meltable recipe;
	
	public CrucibleRemoveRecipeAction(TweakerItem result) {
		this.result = result;
	}

	@Override
	public void apply() {		
		for (Entry<String, Meltable> registry : CrucibleRegistry.entries.entrySet()) {
			Meltable r = registry.getValue();
			if(areEqual(result.make(), new ItemStack(r.id, 1, r.meta))) {
				recipe = r;
				break;
			}
		}
		
		if(recipe != null) {
			CrucibleRegistry.entries.remove(recipe.id + ":" + recipe.meta);
		}
	}

	@Override
	public boolean canUndo() {
		return CrucibleRegistry.entries != null;
	}

	@Override
	public void undo() {
		CrucibleRegistry.entries.put(recipe.id + ":" + recipe.meta, recipe);
	}

	@Override
	public String describe() {
		return "Removing Crucible Recipe: " + result.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Restoring Crucible Recipe: " + result.getDisplayName();
	}
}
