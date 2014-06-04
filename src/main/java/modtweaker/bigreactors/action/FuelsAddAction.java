package modtweaker.bigreactors.action;

import modtweaker.bigreactors.BigReactorsHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import erogenousbeef.bigreactors.api.IReactorFuel;
import erogenousbeef.bigreactors.common.BRRegistry;

public class FuelsAddAction implements IUndoableAction {
	private final String name;
	private final IReactorFuel fuel;

	public FuelsAddAction(String name, IReactorFuel fuel) {
		this.name = name;
		this.fuel = fuel;
	}

	@Override
	public void apply() {
		BRRegistry.registerReactorFluidData(name, fuel);
	}

	@Override
	public boolean canUndo() {
		return BigReactorsHacks.fuels != null;
	}

	@Override
	public void undo() {
		BigReactorsHacks.fuels.remove(name);
	}

	@Override
	public String describe() {
		return "Adding Reactor Fuel: " + name;
	}

	@Override
	public String describeUndo() {
		return "Removing Reactor Fuel: " + name;
	}
}
