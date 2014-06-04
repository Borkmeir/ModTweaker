package modtweaker.growthcraft.action;

import static modtweaker.helpers.ItemHelper.areEqual;
import growthcraft.api.fishtrap.FishTrapEntry;
import modtweaker.growthcraft.GrowthcraftHacks;
import modtweaker.growthcraft.action.FishTrapAddLootAction.Rarity;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItemStack;

public class FishTrapRemoveLootAction implements IUndoableAction {
	private final TweakerItemStack result;
	private final Rarity rarity;
	private FishTrapEntry entry;
	
	public FishTrapRemoveLootAction(TweakerItemStack output, Rarity rarity) {
		this.result = output;
		this.rarity = rarity;
	}

	@Override
	public void apply() {		
		if(rarity == rarity.FISH) {
			for(FishTrapEntry e: GrowthcraftHacks.fishList) {
				if(areEqual(result.get(), e.fishable)) {
					entry = e;
					break;
				}
			}
			
			GrowthcraftHacks.fishList.remove(entry);
		} else if(rarity == rarity.JUNK) {
			for(FishTrapEntry e: GrowthcraftHacks.junkList) {
				if(areEqual(result.get(), e.fishable)) {
					entry = e;
					break;
				}
			}
			
			GrowthcraftHacks.junkList.remove(entry);
		} else if(rarity == rarity.TREASURE) {
			for(FishTrapEntry e: GrowthcraftHacks.treasureList) {
				if(areEqual(result.get(), e.fishable)) {
					entry = e;
					break;
				}
			}
			
			GrowthcraftHacks.treasureList.remove(entry);
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
	public String describe() {
		return "Removing Growthcraft Loot: " + result.getDisplayName() + " from " + rarity.name().toLowerCase();
	}

	@Override
	public String describeUndo() {
		return "Restoring Growthcraft Loot: " + result.getDisplayName() + " from " + rarity.name().toLowerCase();
	}
}
