package modtweaker.growthcraft.action;

import growthcraft.api.fishtrap.FishTrapEntry;
import modtweaker.growthcraft.GrowthcraftHacks;
import stanhebben.minetweaker.api.IUndoableAction;

public class FishTrapAddLootAction implements IUndoableAction {
	public static enum Rarity {
		FISH, TREASURE, JUNK
	}

	private final Rarity rarity;
	private final FishTrapEntry entry;

	public FishTrapAddLootAction(FishTrapEntry entry, Rarity rarity) {
		this.entry = entry;
		this.rarity = rarity;
	}

	@Override
	public void apply() {
		switch (rarity) {
			case FISH:
				GrowthcraftHacks.fishList.add(entry);
				break;
			case TREASURE:
				GrowthcraftHacks.treasureList.add(entry);
				break;
			case JUNK:
				GrowthcraftHacks.junkList.add(entry);
				break;
		}
	}

	@Override
	public boolean canUndo() {
		switch (rarity) {
			case FISH: 		return GrowthcraftHacks.fishList != null;
			case TREASURE: 	return GrowthcraftHacks.treasureList != null;
			case JUNK: 		return GrowthcraftHacks.junkList != null;
		}
		
		return false;
	}

	@Override
	public void undo() {
		switch (rarity) {
			case FISH:
				GrowthcraftHacks.fishList.remove(entry);
				break;
			case TREASURE:
				GrowthcraftHacks.treasureList.remove(entry);
				break;
			case JUNK:
				GrowthcraftHacks.junkList.remove(entry);
				break;
		}
	}

	@Override
	public String describe() {
		return "Adding Growthcraft Loot: " + entry.fishable.getDisplayName() + " from " + rarity.name().toLowerCase();
	}

	@Override
	public String describeUndo() {
		return "Removing Growthcraft Loot: " + entry.fishable.getDisplayName() + " from " + rarity.name().toLowerCase();
	}
}
