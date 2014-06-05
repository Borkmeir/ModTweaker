package modtweaker.tconstruct;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import tconstruct.library.crafting.DryingRackRecipes;

public class DryingAddRecipe extends TweakerBaseFunction {
	public static final DryingAddRecipe INSTANCE = new DryingAddRecipe();
	private DryingAddRecipe() {
		super("tconstruct.drying.addRecipe");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(3, args)) {
			ItemStack input = getItem();
			ItemStack output = getItem();
			int time = getInt();
			Tweaker.apply(new Action(input, time, output));
		} else throwException(this, 3);
	}
	
	private static class Action implements IUndoableAction {
		private final ItemStack input;
		private final int time;
		private final ItemStack output;
		public Action(ItemStack input, int time, ItemStack output) {
			this.input = input;
			this.time = time;
			this.output = output;
		}
		
		@Override
		public void apply() {
			DryingRackRecipes.addDryingRecipe(input, time, output);
		}

		@Override
		public boolean canUndo() {
			return false;
		}

		@Override
		public void undo() {
			return;
		}
		
		@Override
		public String describe() {
			return "Adding Drying Rack Recipe: " + output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Drying Rack Recipe: " + output.getDisplayName();
		}
	}
}
