package modtweaker.bigreactors.action;

import modtweaker.bigreactors.BigReactorsHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import erogenousbeef.bigreactors.api.IReactorFuel;
import erogenousbeef.bigreactors.common.BRRegistry;

public class FuelsRemoveAction implements IUndoableAction {
	private final String name;
	private IReactorFuel fuel;

	public FuelsRemoveAction(String name) {
		this.name = name;
	}

	@Override
	public void apply() {
		fuel = BRRegistry.getReactorFluidInfo(name);
		BigReactorsHacks.fuels.remove(fuel);
	}

	@Override
	public boolean canUndo() {
		return BigReactorsHacks.fuels != null;
	}

	@Override
	public void undo() {
		BigReactorsHacks.fuels.put(name, fuel);
	}

	@Override
	public String describe() {
		return "Removing Reactor Fuel: " + name;
	}

	@Override
	public String describeUndo() {
		return "Restoring Reactor Fuel: " + name;
	}
}
