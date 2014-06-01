package modtweaker.exnihilo.action;

import static modtweaker.helpers.ItemHelper.areEqual;

import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItem;
import exnihilo.registries.CompostRegistry;
import exnihilo.registries.helpers.Compostable;

public class CompostRemoveRecipeAction implements IUndoableAction {
	private final TweakerItem result;
	private Compostable recipe;
	
	public CompostRemoveRecipeAction(TweakerItem result) {
		this.result = result;
	}

	@Override
	public void apply() {		
		for (Entry<String, Compostable> registry : CompostRegistry.entries.entrySet()) {
			Compostable r = registry.getValue();
			if(areEqual(result.make(), new ItemStack(r.id, 1, r.meta))) {
				recipe = r;
				break;
			}
		}
		
		if(recipe != null) {
			CompostRegistry.entries.remove(recipe.id + ":" + recipe.meta);
		}
	}

	@Override
	public boolean canUndo() {
		return CompostRegistry.entries != null;
	}

	@Override
	public void undo() {
		CompostRegistry.entries.put(recipe.id + ":" + recipe.meta, recipe);
	}

	@Override
	public String describe() {
		return "Removing Compostable: " + result.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Restoring Compostable: " + result.getDisplayName();
	}
}
