package modtweaker.bloodmagic;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;

public class AltarAddRecipe extends TweakerBaseFunction {
	public static final AltarAddRecipe INSTANCE = new AltarAddRecipe();
	private AltarAddRecipe() {
		super("bloodmagic.altar.addRecipe");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(new int[] { 4, 5, 6 }, args)) {
			ItemStack input = getItem();
			ItemStack output = getItem();
			int tier = getInt();
			int lp = getInt();
			int consumption = 5;
			int drain = 5;
			if(args.length >= 5) {
				consumption = getInt();
				if(args.length >= 6) {
					drain = getInt();
				}
			}
			
			Tweaker.apply(new Action(new AltarRecipe(output, input, tier, lp, consumption, drain, false)));
		} else throwException(this, new int[] { 5, 6 });
	}

	private static class Action implements IUndoableAction {
		private final AltarRecipe recipe;
		public Action(AltarRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			AltarRecipeRegistry.altarRecipes.add(recipe);
		}

		@Override
		public boolean canUndo() {
			return AltarRecipeRegistry.altarRecipes != null;
		}

		@Override
		public void undo() {
			AltarRecipeRegistry.altarRecipes.remove(recipe);
		}
		
		@Override
		public String describe() {
			return "Adding Altar Recipe: " + recipe.result.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Altar Recipe: " + recipe.result.getDisplayName();
		}
	}
}
