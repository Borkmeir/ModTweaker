package modtweaker.bigreactors.action;

import modtweaker.bigreactors.BigReactorsHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import erogenousbeef.bigreactors.common.BRRegistry;
import erogenousbeef.bigreactors.common.multiblock.helpers.ReactorInteriorData;

public class ReactorInteriorFluidRemoveAction implements IUndoableAction {
	private final String name;
	private ReactorInteriorData data;

	public ReactorInteriorFluidRemoveAction(String name) {
		this.name = name;
	}

	@Override
	public void apply() {
		data = BRRegistry.getReactorInteriorFluidData(name);
		BigReactorsHacks.fluids.remove(name);
	}

	@Override
	public boolean canUndo() {
		return BigReactorsHacks.fluids != null;
	}

	@Override
	public void undo() {
		BigReactorsHacks.fluids.put(name, data);
	}

	@Override
	public String describe() {
		return "Removing Reactor Interior Fluid: " + name;
	}

	@Override
	public String describeUndo() {
		return "Restoring Reactor Interior Fluid: " + name;
	}
}
