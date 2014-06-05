package modtweaker.tconstruct;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.getFluids;
import static modtweaker.util.TweakerHelper.throwException;
import modtweaker.util.TweakerBaseFunction;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.Smeltery;

public class AlloyAddRecipe extends TweakerBaseFunction {
	public static final AlloyAddRecipe INSTANCE = new AlloyAddRecipe();
	private AlloyAddRecipe() {
		super("tconstruct.smeltery.addAlloy");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(2, args)) {
			FluidStack output = getFluid();
			FluidStack[] input = getFluids();
			Tweaker.apply(new Action(output, input));
		} else throwException(this, 2);
	}
	
	private static class Action implements IUndoableAction {
		private final FluidStack output;
		private final FluidStack[] mixers;
		public Action(FluidStack output, FluidStack[] mixers) {
			this.output = output;
			this.mixers = mixers;
		}

		@Override
		public void apply() {
			Smeltery.instance.addAlloyMixing(output, mixers);
		}

		@Override
		public boolean canUndo() {
			return TConstructHacks.alloys != null;
		}

		@Override
		public void undo() {
			int k;
			for (k = 0; k < TConstructHacks.alloys.size(); k++) {
				AlloyMix mix = TConstructHacks.alloys.get(k);
				if (mix.result.isFluidStackIdentical(output)) {
					int i, j = 0;
					for (i = 0; i < mix.mixers.size(); i++) {
						FluidStack stack = mix.mixers.get(i);
						if (i < mixers.length) {
							if (stack.isFluidStackIdentical(mixers[i])) {
								j++;
							}
						}
					}

					if (j == i) {
						break;
					}
				}
			}

			TConstructHacks.alloys.remove(k);
		}

		@Override
		public String describe() {
			return "Adding Alloy Recipe: " + output.getFluid().getLocalizedName();
		}

		@Override
		public String describeUndo() {
			return "Removing Alloy Recipe: " + output.getFluid().getLocalizedName();
		}
	}
}
