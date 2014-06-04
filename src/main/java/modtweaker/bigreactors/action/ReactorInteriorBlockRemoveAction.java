package modtweaker.bigreactors.action;

import modtweaker.bigreactors.BigReactorsHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import erogenousbeef.bigreactors.common.BRRegistry;
import erogenousbeef.bigreactors.common.multiblock.helpers.ReactorInteriorData;

public class ReactorInteriorBlockRemoveAction implements IUndoableAction {
	private final String name;
	private ReactorInteriorData data;

	public ReactorInteriorBlockRemoveAction(String name) {
		this.name = name;
	}

	@Override
	public void apply() {
		data = BRRegistry.getReactorInteriorBlockData(name);
		BigReactorsHacks.blocks.remove(name);
	}

	@Override
	public boolean canUndo() {
		return BigReactorsHacks.blocks != null;
	}

	@Override
	public void undo() {
		BigReactorsHacks.blocks.put(name, data);
	}

	@Override
	public String describe() {
		return "Removing Reactor Interior Block: " + name;
	}

	@Override
	public String describeUndo() {
		return "Restoring Reactor Interior Block: " + name;
	}
}
