package modtweaker.growthcraft.action.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.throwException;
import growthcraft.api.fishtrap.FishTrapEntry;
import modtweaker.growthcraft.action.FishTrapAddLootAction;
import modtweaker.growthcraft.action.FishTrapAddLootAction.Rarity;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class FishTrapAddJunkFunction extends TweakerFunction {
	public static final FishTrapAddJunkFunction INSTANCE = new FishTrapAddJunkFunction();
	
	private FishTrapAddJunkFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 2) throwException(toString(), 5); 
		ItemStack fish = GetItemOld(0, arguments).get();
		int weight = getInt(1, arguments);
		Tweaker.apply(new FishTrapAddLootAction(new FishTrapEntry(fish ,weight), Rarity.JUNK));
		return null;
	}

	@Override
	public String toString() {
		return "growthcraft.fishtrap.addJunk";
	}
}
