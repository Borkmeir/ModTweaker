package modtweaker.exnihilo.function;

import static modtweaker.helpers.TweakerHelper.getFloat;
import static modtweaker.helpers.TweakerHelper.getItem;
import modtweaker.exnihilo.action.HammerAddRecipeAction;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;
import exnihilo.registries.helpers.Smashable;

public class HammerAddRecipeFunction extends TweakerFunction {
	public static final HammerAddRecipeFunction INSTANCE = new HammerAddRecipeFunction();
	
	private HammerAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 4) {
			ItemStack input = getItem(0, arguments).make();
			if(input.itemID >= 4096 || Block.blocksList[input.itemID] == null) throw new TweakerExecuteException("Block smashing requires the input to be a block");
			ItemStack output = getItem(1, arguments).make();
			float chance = getFloat(2, arguments);
			float luck = getFloat(3, arguments);
			Tweaker.apply(new HammerAddRecipeAction(new Smashable(input.itemID, input.getItemDamage(), output.itemID, output.getItemDamage(), chance, luck)));
		} else {
			throw new TweakerExecuteException(toString() + " requires 4 arguments");
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "exnihilo.hammer.addRecipe";
	}
}
