package modtweaker.mariculture;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.throwException;
import mariculture.api.core.RecipeCasting.RecipeIngotCasting;
import modtweaker.util.TweakerBaseFunction;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CastingRemoveIngot extends TweakerBaseFunction {
	public static final CastingRemoveIngot INSTANCE = new CastingRemoveIngot();
	private CastingRemoveIngot() {
		super("mariculture.casting.removeIngotCasting");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(1,  args)) {
			FluidStack input = getFluid();
			Tweaker.apply(new Action(input));
		} else throwException(this, 1);
	}
	
	private static class Action implements IUndoableAction {
		private RecipeIngotCasting casting;
		private final FluidStack input;
		public Action(FluidStack input) {
			this.input = input;
		}

		@Override
		public void apply() {
			casting = (RecipeIngotCasting) MaricultureHacks.ingotCasting.get(input.getFluid().getName());
			MaricultureHacks.ingotCasting.remove(input.getFluid().getName());
		}

		@Override
		public boolean canUndo() {
			return MaricultureHacks.ingotCasting != null;
		}

		@Override
		public void undo() {
			MaricultureHacks.ingotCasting.put(input.getFluid().getName(), casting);
		}

		@Override
		public String describe() {
			return "Removing Ingot Casting Recipe: " + casting.output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring Ingot Casting Recipe: " + casting.output.getDisplayName();
		}
	}
}
