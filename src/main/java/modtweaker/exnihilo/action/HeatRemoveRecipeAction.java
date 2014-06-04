package modtweaker.exnihilo.action;

import static modtweaker.util.ItemHelper.areEqual;

import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import exnihilo.registries.HeatRegistry;
import exnihilo.registries.helpers.HeatSource;

public class HeatRemoveRecipeAction implements IUndoableAction {
	private final TweakerItemStack result;
	private HeatSource recipe;
	
	public HeatRemoveRecipeAction(TweakerItemStack output) {
		this.result = output;
	}

	@Override
	public void apply() {		
		for (Entry<String, HeatSource> registry : HeatRegistry.entries.entrySet()) {
			HeatSource r = registry.getValue();
			if(areEqual(result.get(), new ItemStack(r.id, 1, r.meta))) {
				recipe = r;
				break;
			}
		}
		
		if(recipe != null) {
			HeatRegistry.entries.remove(recipe.id + ":" + recipe.meta);
		}
	}

	@Override
	public boolean canUndo() {
		return HeatRegistry.entries != null;
	}

	@Override
	public void undo() {
		HeatRegistry.entries.put(recipe.id + ":" + recipe.meta, recipe);
	}

	@Override
	public String describe() {
		return "Removing Heat Source: " + result.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Restoring Heat Source: " + result.getDisplayName();
	}
}
