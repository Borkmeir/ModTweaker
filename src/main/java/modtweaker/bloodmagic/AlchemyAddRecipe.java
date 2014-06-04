package modtweaker.bloodmagic;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.getItems;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import WayofTime.alchemicalWizardry.api.alchemy.AlchemyRecipe;
import WayofTime.alchemicalWizardry.api.alchemy.AlchemyRecipeRegistry;

public class AlchemyAddRecipe extends TweakerBaseFunction {
	public static final AlchemyAddRecipe INSTANCE = new AlchemyAddRecipe();
	private AlchemyAddRecipe() {
		super("bloodmagic.alchemy.addRecipe");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(4, args)) {
			ItemStack output = getItem();
			ItemStack[] input = getItems();
			int tier = getInt();
			int lp = getInt();
			Tweaker.apply(new Action(new AlchemyRecipe(output, lp, input, tier)));
		} else throwException(this, 4);
	}
	
	private static class Action implements IUndoableAction {
		private final AlchemyRecipe recipe;
		public Action(AlchemyRecipe recipe) {
			this.recipe = recipe;
		}
		
		@Override
		public void apply() {
			AlchemyRecipeRegistry.recipes.add(recipe);
		}
	
		@Override
		public boolean canUndo() {
			return AlchemyRecipeRegistry.recipes != null;
		}
	
		@Override
		public void undo() {
			AlchemyRecipeRegistry.recipes.remove(recipe);
		}
	
		@Override
		public String describe() {
			return "Adding Alchemy Recipe: " + recipe.getResult().getDisplayName();
		}
	
		@Override
		public String describeUndo() {
			return "Removing Alchemy Recipe: " + recipe.getResult().getDisplayName();
		}
	}
}
