package modtweaker.tconstruct.actions;

import modtweaker.tconstruct.TConstructHacks;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.Smeltery;

public class AlloyRemoveRecipeAction implements IUndoableAction {
	private final FluidStack output;
	private FluidStack[] mixers;

	public AlloyRemoveRecipeAction(FluidStack output) {
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
