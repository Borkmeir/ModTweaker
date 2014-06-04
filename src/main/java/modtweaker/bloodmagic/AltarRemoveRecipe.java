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
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;

public class AltarRemoveRecipe extends TweakerBaseFunction {
	public static final AltarRemoveRecipe INSTANCE = new AltarRemoveRecipe();
	private AltarRemoveRecipe() {
		super("bloodmagic.altar.removeRecipe");
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
		private AltarRecipe recipe;
		
		public Action(ItemStack output) {
			this.result = output;
		}

		@Override
		public void apply() {		
			for(AltarRecipe r: AltarRecipeRegistry.altarRecipes) {
				if(areEqual(result, r.result)) {
					recipe = r;
					break;
				}
			}
			
			if(recipe != null) {
				AltarRecipeRegistry.altarRecipes.remove(recipe);
			}
		}

		@Override
		public boolean canUndo() {
			return AltarRecipeRegistry.altarRecipes != null;
		}

		@Override
		public void undo() {
			AltarRecipeRegistry.altarRecipes.add(recipe);
		}

		@Override
		public String describe() {
			return "Removing Altar Recipe: " + result.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring Altar Recipe: " + result.getDisplayName();
		}
	}
}
