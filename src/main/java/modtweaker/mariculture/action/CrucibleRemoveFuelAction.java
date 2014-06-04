package modtweaker.mariculture.action;

import static modtweaker.util.ItemHelper.getName;
import mariculture.api.core.FuelInfo;
import mariculture.core.helpers.OreDicHelper;
import modtweaker.mariculture.MaricultureHacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;

public class CrucibleRemoveFuelAction implements IUndoableAction {
	private final Object fuel;
	private FuelInfo info;
	public CrucibleRemoveFuelAction(Object fuel) {
		this.fuel = fuel;
	}

	@Override
	public void apply() {	
		Object key = null;
		if(fuel instanceof ItemStack)
			key = OreDicHelper.convert((ItemStack)fuel);
		if(fuel instanceof FluidStack)
			key = getName((FluidStack) fuel);
		info = (FuelInfo) MaricultureHacks.fuels.get(fuel);
		if(key != null) {
			MaricultureHacks.fuels.remove(key);
		}
	}

	@Override
	public boolean canUndo() {
		return MaricultureHacks.fuels != null;
	}

	@Override
	public void undo() {
		MaricultureHacks.fuels.put(fuel, info);
	}
	
	public String asString() {
		if(fuel instanceof ItemStack)
			return ((ItemStack)fuel).getDisplayName();
		if(fuel instanceof FluidStack)
			return((FluidStack) fuel).getFluid().getLocalizedName();
		return "";
	}

	@Override
	public String describe() {
		return "Removing Crucible Furnace Fuel: " + asString();
	}

	@Override
	public String describeUndo() {
		return "Restoring Crucible Furnace Fuel: " + asString();
	}
}
