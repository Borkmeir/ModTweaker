package modtweaker.tconstruct.actions;

import modtweaker.tconstruct.TConstructHacks;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.Smeltery;

public class AlloyAddRecipeAction implements IUndoableAction {
	private final FluidStack output;
	private final FluidStack[] mixers;

	public AlloyAddRecipeAction(FluidStack output, FluidStack[] mixers) {
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
