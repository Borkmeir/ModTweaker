package modtweaker.tconstruct;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.Smeltery;

public class AlloyRemoveRecipe extends TweakerBaseFunction {
	public static final AlloyRemoveRecipe INSTANCE = new AlloyRemoveRecipe();
	private AlloyRemoveRecipe() {
		super("tconstruct.smeltery.removeAlloy");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(1, args)) {
			FluidStack output = getFluid();
			Tweaker.apply(new Action(output));
		} else throwException(this, 1);
	}
	
	private static class Action implements IUndoableAction {
		private final FluidStack output;
		private FluidStack[] mixers;
		public Action(FluidStack output) {
			this.output = output;
		}

		@Override
		public void apply() {
			int k;
			for (k = 0; k < TConstructHacks.alloys.size(); k++) {
				AlloyMix mix = TConstructHacks.alloys.get(k);
				if (mix.result.isFluidStackIdentical(output)) {
					mixers = (FluidStack[]) mix.mixers.toArray();
					break;
				}
			}

			TConstructHacks.alloys.remove(k);
		}

		@Override
		public boolean canUndo() {
			return TConstructHacks.alloys != null;
		}

		@Override
		public void undo() {
			Smeltery.instance.addAlloyMixing(output, mixers);

		}

		@Override
		public String describe() {
			return "Removing Alloy Recipe: " + output.getFluid().getLocalizedName();
		}

		@Override
		public String describeUndo() {
			return "Restoring Alloy Recipe: " + output.getFluid().getLocalizedName();
		}
	}
}
