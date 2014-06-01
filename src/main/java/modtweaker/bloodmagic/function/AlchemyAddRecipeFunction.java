package modtweaker.bloodmagic.function;

import static modtweaker.helpers.TweakerHelper.getArray;
import static modtweaker.helpers.TweakerHelper.getInt;
import static modtweaker.helpers.TweakerHelper.getItem;
import modtweaker.bloodmagic.action.AlchemicalAddRecipeAction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerArray;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerValue;
import WayofTime.alchemicalWizardry.api.alchemy.AlchemyRecipe;

public class AlchemyAddRecipeFunction extends TweakerFunction {
	public static final AlchemyAddRecipeFunction INSTANCE = new AlchemyAddRecipeFunction();
	
	private AlchemyAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 4) {
			TweakerItem output = getItem(0, arguments);
			TweakerArray array = getArray(1, arguments);
			ItemStack[] input = new ItemStack[array.size()];
			for(int i = 0; i < array.size(); i++) {
				input[i] = array.get(0).asItemStack().get();
			}
			
			int tier = getInt(2, arguments);
			int lp = getInt(3, arguments);
			Tweaker.apply(new AlchemicalAddRecipeAction(new AlchemyRecipe(output.make(), lp, input, tier)));
		} else {
			throw new TweakerExecuteException(toString() + " requires 4 arguments");
		}
		return null;
	}

	@Override
	public String toString() {
		return "bloodmagic.alchemy.addRecipe";
	}
}
