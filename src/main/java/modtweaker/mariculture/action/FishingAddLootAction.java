package modtweaker.mariculture.action;

import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import modtweaker.mariculture.MaricultureHacks;
import stanhebben.minetweaker.api.IUndoableAction;

public class FishingAddLootAction implements IUndoableAction {
	private final Loot loot;
	public FishingAddLootAction(Loot loot) {
		this.loot = loot;
	}

	@Override
	public void apply() {
		if(loot.type == Rarity.GOOD) {
			MaricultureHacks.goodLoot.add(loot);
		} else MaricultureHacks.badLoot.add(loot);
	}

	@Override
	public boolean canUndo() {
		if(loot.type == Rarity.GOOD) return MaricultureHacks.goodLoot != null;
		else return MaricultureHacks.badLoot != null;
	}

	@Override
	public void undo() {
		if(loot.type == Rarity.GOOD) MaricultureHacks.goodLoot.remove(loot);
		else MaricultureHacks.badLoot.remove(loot);
	}

	@Override
	public String describe() {
		return "Adding Fishing Loot: " + loot.loot.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Removing Fishing Loot: " + loot.loot.getDisplayName();
	}
}
