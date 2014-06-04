package modtweaker.exnihilo.function;

import static modtweaker.util.TweakerHelper.GetItemOld;
import static modtweaker.util.TweakerHelper.getInt;
import modtweaker.exnihilo.action.SieveAddRecipeAction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;
import exnihilo.registries.helpers.SiftReward;

public class SieveAddRecipeFunction extends TweakerFunction {
	public static final SieveAddRecipeFunction INSTANCE = new SieveAddRecipeFunction();
	
	private SieveAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 3) {
			ItemStack input = GetItemOld(0, arguments).get();
			ItemStack output = GetItemOld(1, arguments).get();
			int rarity = Math.max(1, getInt(2, arguments));
			Tweaker.apply(new SieveAddRecipeAction(new SiftReward(input.itemID, input.getItemDamage(), output.itemID, output.getItemDamage(), rarity)));
		} else {
			throw new TweakerExecuteException(toString() + " requires 3 arguments");
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "exnihilo.sieve.addRecipe";
	}
}
