package modtweaker.bloodmagic;

import static modtweaker.util.ItemHelper.areEqual;
import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRecipe;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;

public class BindingRemoveRecipe extends TweakerBaseFunction {
	public static final BindingRemoveRecipe INSTANCE = new BindingRemoveRecipe();
	private BindingRemoveRecipe() {
		super("bloodmagic.binding.removeRecipe");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(1, args)) {
			ItemStack output = getItem();
			Tweaker.apply(new Action(output));
		} else throwException(this, 1);
	}

	private static class Action implements IUndoableAction {
		private final ItemStack output;
		private BindingRecipe recipe;
		public Action(ItemStack output) {
			this.output = output;
		}

		@Override
		public void apply() {		
			for(BindingRecipe r: BindingRegistry.bindingRecipes) {
				if(areEqual(output, r.outputItem)) {
					recipe = r;
					break;
				}
			}
			
			if(recipe != null) {
				BindingRegistry.bindingRecipes.remove(recipe);
			}
		}

		@Override
		public boolean canUndo() {
			return BindingRegistry.bindingRecipes != null;
		}

		@Override
		public void undo() {
			BindingRegistry.bindingRecipes.add(recipe);
		}

		@Override
		public String describe() {
			return "Removing Binding Recipe: " + output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring Binding Recipe: " + output.getDisplayName();
		}
	}
}
