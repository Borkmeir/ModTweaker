package modtweaker.mariculture.action;

import static modtweaker.util.ItemHelper.getName;
import mariculture.api.core.FuelInfo;
import mariculture.core.helpers.OreDicHelper;
import modtweaker.mariculture.MaricultureHacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;

public class CrucibleAddFuelAction implements IUndoableAction {
	private String key;
	private final Object fuel;
	private final FuelInfo info;
	public CrucibleAddFuelAction(Object fuel, FuelInfo info) {
		this.fuel = fuel;
		this.info = info;
	}

	@Override
	public void apply() {
		if(fuel instanceof ItemStack)
			key = OreDicHelper.convert((ItemStack)fuel);
		if(fuel instanceof FluidStack)
			key = getName((FluidStack) fuel);
		
		MaricultureHacks.fuels.put(key, info);
	}

	@Override
	public boolean canUndo() {
		return MaricultureHacks.fuels != null;
	}

	@Override
	public void undo() {
		MaricultureHacks.fuels.remove(key);
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
		return "Adding Crucible Furnace Fuel: " + asString();
	}

	@Override
	public String describeUndo() {
		return "Removing Crucible Furnace Fuel: " + asString();
	}
}
