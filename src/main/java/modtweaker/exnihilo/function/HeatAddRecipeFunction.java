package modtweaker.exnihilo.function;

import static modtweaker.helpers.TweakerHelper.getFloat;
import static modtweaker.helpers.TweakerHelper.getItem;
import modtweaker.exnihilo.action.HeatAddRecipeAction;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;
import exnihilo.registries.helpers.HeatSource;

public class HeatAddRecipeFunction extends TweakerFunction {
	public static final HeatAddRecipeFunction INSTANCE = new HeatAddRecipeFunction();
	
	private HeatAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 2) {
			ItemStack input = getItem(0, arguments).get();
			if(input.itemID >= 4096 || Block.blocksList[input.itemID] == null) throw new TweakerExecuteException("Heat source must be a block");
			float value = getFloat(1, arguments);
			Tweaker.apply(new HeatAddRecipeAction(new HeatSource(input.itemID, input.getItemDamage(), value)));
		} else {
			throw new TweakerExecuteException(toString() + " requires 2 arguments");
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "exnihilo.heat.addRecipe";
	}
}
