package modtweaker.bigreactors.action;

import modtweaker.bigreactors.BigReactorsHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import erogenousbeef.bigreactors.common.BRRegistry;

public class ReactorInteriorFluidAddAction implements IUndoableAction {
	private final String name;
	private final float absorption;
	private final float heatEfficiency;
	private final float moderation;
	private final float heatConductivity;

	public ReactorInteriorFluidAddAction(String name, float absorption, float heatEfficiency, float moderation, float heatConductivity) {
		this.name = name;
		this.absorption = absorption;
		this.heatEfficiency = heatEfficiency;
		this.moderation = moderation;
		this.heatConductivity = heatConductivity;
	}

	@Override
	public void apply() {
		BRRegistry.registerReactorInteriorFluid(name, absorption, heatEfficiency, moderation, heatConductivity);
	}

	@Override
	public boolean canUndo() {
		return BigReactorsHacks.fluids != null;
	}

	@Override
	public void undo() {
		BigReactorsHacks.fluids.remove(name);
	}

	@Override
	public String describe() {
		return "Adding Reactor Interior Fluid: " + name;
	}

	@Override
	public String describeUndo() {
		return "Removing Reactor Interior Fluid: " + name;
	}
}
