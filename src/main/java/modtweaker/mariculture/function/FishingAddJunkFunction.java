package modtweaker.mariculture.function;

import static modtweaker.mariculture.MaricultureHelper.getQuality;
import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.getBoolean;
import static modtweaker.util.TweakerHelper.getDimension;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.getString;
import static modtweaker.util.TweakerHelper.throwException;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import modtweaker.mariculture.action.FishingAddLootAction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class FishingAddJunkFunction extends TweakerFunction {
	public static final FishingAddJunkFunction INSTANCE = new FishingAddJunkFunction();
	
	private FishingAddJunkFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 5) throwException(toString(), 5); 
		ItemStack input = GetItemOld(0, arguments).get();
		int chance = getInt(1, arguments);
		String quality = getString(2, arguments);
		boolean exact = getBoolean(3, arguments);
		int dimension = getDimension(4, arguments);
		Tweaker.apply(new FishingAddLootAction(new Loot(input, getQuality(quality), Rarity.JUNK, chance, dimension, exact)));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.fishing.addJunk";
	}
}
