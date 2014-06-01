package modtweaker.growthcraft.action.function;

import static modtweaker.helpers.TweakerHelper.getInt;
import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import growthcraft.api.fishtrap.FishTrapEntry;
import modtweaker.growthcraft.action.FishTrapAddLootAction;
import modtweaker.growthcraft.action.FishTrapAddLootAction.Rarity;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class FishTrapAddTreasureFunction extends TweakerFunction {
	public static final FishTrapAddTreasureFunction INSTANCE = new FishTrapAddTreasureFunction();
	
	private FishTrapAddTreasureFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 2) throwException(toString(), 5); 
		ItemStack fish = getItem(0, arguments).make();
		int weight = getInt(1, arguments);
		Tweaker.apply(new FishTrapAddLootAction(new FishTrapEntry(fish ,weight), Rarity.TREASURE));
		return null;
	}

	@Override
	public String toString() {
		return "growthcraft.fishtrap.addTreasure";
	}
}
