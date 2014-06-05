package modtweaker.mariculture;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.throwException;
import mariculture.api.core.RecipeCasting.RecipeBlockCasting;
import modtweaker.util.TweakerBaseFunction;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CastingRemoveBlock extends TweakerBaseFunction {
	public static final CastingRemoveBlock INSTANCE = new CastingRemoveBlock();
	private CastingRemoveBlock() {
		super("mariculture.casting.removeBlockCasting");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(1,  args)) {
			FluidStack input = getFluid();
			Tweaker.apply(new Action(input));
		} else throwException(this, 1);
	}
	
	private static class Action implements IUndoableAction {
		private RecipeBlockCasting casting;
		private final FluidStack input;
		public Action(FluidStack input) {
			this.input = input;
		}

		@Override
		public void apply() {
			casting = (RecipeBlockCasting) MaricultureHacks.blockCasting.get(input.getFluid().getName());
			MaricultureHacks.blockCasting.remove(input.getFluid().getName());
		}

		@Override
		public boolean canUndo() {
			return MaricultureHacks.blockCasting != null;
		}

		@Override
		public void undo() {
			MaricultureHacks.blockCasting.put(input.getFluid().getName(), casting);
		}

		@Override
		public String describe() {
			return "Removing Block Casting Recipe: " + casting.output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring Block Casting Recipe: " + casting.output.getDisplayName();
		}
	}
}
