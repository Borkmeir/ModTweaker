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
import WayofTime.alchemicalWizardry.api.alchemy.AlchemyRecipe;
import WayofTime.alchemicalWizardry.api.alchemy.AlchemyRecipeRegistry;

public class AlchemyRemoveRecipe extends TweakerBaseFunction {
	public static final AlchemyRemoveRecipe INSTANCE = new AlchemyRemoveRecipe();
	private AlchemyRemoveRecipe() {
		super("bloodmagic.alchemy.removeRecipe");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(1, args)) {
			ItemStack output = getItem();
			Tweaker.apply(new Action(output));
		} else throwException(this, 1);
	}
	
	private static class Action implements IUndoableAction {
		private final ItemStack result;
		private AlchemyRecipe recipe;
		public Action(ItemStack result) {
			this.result = result;
		}

		@Override
		public void apply() {		
			for(AlchemyRecipe r: AlchemyRecipeRegistry.recipes) {
				if(areEqual(result, r.getResult())) {
					recipe = r;
					break;
				}
			}
			
			if(recipe != null) {
				AlchemyRecipeRegistry.recipes.remove(recipe);
			}
		}

		@Override
		public boolean canUndo() {
			return AlchemyRecipeRegistry.recipes != null;
		}

		@Override
		public void undo() {
			AlchemyRecipeRegistry.recipes.add(recipe);
		}

		@Override
		public String describe() {
			return "Removing Alchemy Recipe: " + result.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring Alchemy Recipe: " + result.getDisplayName();
		}
	}
}
