package modtweaker.bigreactors.action;

import modtweaker.bigreactors.BigReactorsHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import erogenousbeef.bigreactors.common.BRRegistry;

public class CoilPartAddAction implements IUndoableAction {
	private final String name;
	private final float efficiency;
	private final float bonus;
	private final float rate;

	public CoilPartAddAction(String name, float efficiency, float bonus, float rate) {
		this.name = name;
		this.efficiency = efficiency;
		this.bonus = bonus;
		this.rate = rate;
	}

	@Override
	public void apply() {
		BRRegistry.registerCoilPart(name, efficiency, bonus, rate);
	}

	@Override
	public boolean canUndo() {
		return BigReactorsHacks.coils != null;
	}

	@Override
	public void undo() {
		BigReactorsHacks.coils.remove(name);
	}

	@Override
	public String describe() {
		return "Adding Reactor Coil: " + name;
	}

	@Override
	public String describeUndo() {
		return "Removing Reactor Coil: " + name;
	}
}
