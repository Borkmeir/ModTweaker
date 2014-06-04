package modtweaker.bloodmagic;

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

public class BindingAddRecipe extends TweakerBaseFunction {
	public static final BindingAddRecipe INSTANCE = new BindingAddRecipe();
	private BindingAddRecipe() {
		super("bloodmagic.binding.addRecipe");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(2, args)) {
			ItemStack input = getItem();
			ItemStack output = getItem();
			Tweaker.apply(new Action(new BindingRecipe(output, input)));
		} else throwException(this, 2);
	}

	private static class Action implements IUndoableAction {
		private final BindingRecipe recipe;
		public Action(BindingRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			BindingRegistry.bindingRecipes.add(recipe);
		}

		@Override
		public boolean canUndo() {
			return BindingRegistry.bindingRecipes != null;
		}

		@Override
		public void undo() {
			BindingRegistry.bindingRecipes.remove(recipe);
		}
		
		@Override
		public String describe() {
			return "Adding Binding Recipe: " + recipe.outputItem.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Binding Recipe: " + recipe.outputItem.getDisplayName();
		}
	}
}
