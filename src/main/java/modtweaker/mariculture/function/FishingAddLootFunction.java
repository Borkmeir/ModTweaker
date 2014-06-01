package modtweaker.mariculture.function;

import static modtweaker.helpers.TweakerHelper.getBoolean;
import static modtweaker.helpers.TweakerHelper.getDimension;
import static modtweaker.helpers.TweakerHelper.getInt;
import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.getString;
import static modtweaker.helpers.TweakerHelper.throwException;
import static modtweaker.mariculture.MaricultureHelper.getQuality;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import modtweaker.mariculture.action.FishingAddLootAction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class FishingAddLootFunction extends TweakerFunction {
	public static final FishingAddLootFunction INSTANCE = new FishingAddLootFunction();
	
	private FishingAddLootFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 5) throwException(toString(), 5); 
		ItemStack input = getItem(0, arguments).make();
		int chance = Math.max(1, getInt(1, arguments));
		String quality = getString(2, arguments);
		boolean exact = getBoolean(3, arguments);
		int dimension = getDimension(4, arguments);
		Tweaker.apply(new FishingAddLootAction(new Loot(input, getQuality(quality), Rarity.GOOD, chance, dimension, exact)));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.fishing.addLoot";
	}
}
