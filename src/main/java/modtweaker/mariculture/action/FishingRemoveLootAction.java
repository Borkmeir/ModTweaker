package modtweaker.mariculture.action;

import static modtweaker.helpers.ItemHelper.areEqual;
import mariculture.api.fishery.Loot;
import modtweaker.mariculture.MaricultureHacks;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.value.TweakerItem;

public class FishingRemoveLootAction implements IUndoableAction {
	private final TweakerItem output;
	private boolean bad;
	private Loot loot;
	
	public FishingRemoveLootAction(TweakerItem output) {
		this.output = output;
	}

	@Override
	public void apply() {
		int removed = -1;
		for(int i = 0; i < MaricultureHacks.badLoot.size(); i++) {
			if(MaricultureHacks.badLoot.get(i) != null) {
				if(areEqual(output.make(), MaricultureHacks.badLoot.get(i).loot)) {
					bad = true;
					loot = MaricultureHacks.badLoot.get(i);
					removed = i;
					break;
				}
			}
		}
		
		if(removed < 0) {
			for(int i = 0; i < MaricultureHacks.goodLoot.size(); i++) {
				if(MaricultureHacks.goodLoot.get(i) != null) {
					if(areEqual(output.make(), MaricultureHacks.goodLoot.get(i).loot)) {
						bad = false;
						loot = MaricultureHacks.goodLoot.get(i);
						removed = i;
						break;
					}
				}
			}
			
			MaricultureHacks.goodLoot.remove(removed);
		} else {
			MaricultureHacks.badLoot.remove(removed);
		}
	}

	@Override
	public boolean canUndo() {
		if(bad) return MaricultureHacks.badLoot != null;
		else return MaricultureHacks.goodLoot != null;
	}

	@Override
	public void undo() {
		if(bad) MaricultureHacks.badLoot.add(loot);
		else MaricultureHacks.goodLoot.add(loot);
	}

	@Override
	public String describe() {
		return "Removing Fishing Loot: " + output.getDisplayName();
	}

	@Override
	public String describeUndo() {
		return "Restoring Fishing Loot: " + output.getDisplayName();
	}
}
