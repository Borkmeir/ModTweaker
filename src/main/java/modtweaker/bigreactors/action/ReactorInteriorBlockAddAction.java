package modtweaker.bigreactors.action;

import modtweaker.bigreactors.BigReactorsHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import erogenousbeef.bigreactors.common.BRRegistry;

public class ReactorInteriorBlockAddAction implements IUndoableAction {
	private final String name;
	private final float absorption;
	private final float heatEfficiency;
	private final float moderation;
	private final float heatConductivity;

	public ReactorInteriorBlockAddAction(String name, float absorption, float heatEfficiency, float moderation, float heatConductivity) {
		this.name = name;
		this.absorption = absorption;
		this.heatEfficiency = heatEfficiency;
		this.moderation = moderation;
		this.heatConductivity = heatConductivity;
	}

	@Override
	public void apply() {
		BRRegistry.registerReactorInteriorBlock(name, absorption, heatEfficiency, moderation, heatConductivity);
	}

	@Override
	public boolean canUndo() {
		return BigReactorsHacks.blocks != null;
	}

	@Override
	public void undo() {
		BigReactorsHacks.blocks.remove(name);
	}

	@Override
	public String describe() {
		return "Adding Reactor Interior Block: " + name;
	}

	@Override
	public String describeUndo() {
		return "Removing Reactor Interior Block: " + name;
	}
}
