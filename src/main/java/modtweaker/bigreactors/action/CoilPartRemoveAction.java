package modtweaker.bigreactors.action;

import modtweaker.bigreactors.BigReactorsHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import erogenousbeef.bigreactors.common.BRRegistry;
import erogenousbeef.bigreactors.common.multiblock.helpers.CoilPartData;

public class CoilPartRemoveAction implements IUndoableAction {
	private final String name;
	private CoilPartData data;

	public CoilPartRemoveAction(String name) {
		this.name = name;
	}

	@Override
	public void apply() {
		data = BRRegistry.getCoilPartData(name);
		BigReactorsHacks.coils.remove(name);
	}

	@Override
	public boolean canUndo() {
		return BigReactorsHacks.coils != null;
	}

	@Override
	public void undo() {
		BigReactorsHacks.coils.put(name, data);
	}

	@Override
	public String describe() {
		return "Removing Reactor Coil: " + name;
	}

	@Override
	public String describeUndo() {
		return "Restoring Reactor Coil: " + name;
	}
}
