package modtweaker.exnihilo.function;

import static modtweaker.helpers.TweakerHelper.getFloat;
import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.getString;
import modtweaker.exnihilo.action.CompostAddRecipeAction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;
import exnihilo.registries.helpers.Color;
import exnihilo.registries.helpers.Compostable;

public class CompostAddRecipeFunction extends TweakerFunction {
	public static final CompostAddRecipeFunction INSTANCE = new CompostAddRecipeFunction();
	
	private CompostAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 3) {
			ItemStack input = getItem(0, arguments).make();
			float rarity = getFloat(1, arguments);
			String hex = getString(2, arguments);
			Tweaker.apply(new CompostAddRecipeAction(new Compostable(input.itemID, input.getItemDamage(), rarity, new Color(hex))));
		} else {
			throw new TweakerExecuteException(toString() + " requires 3 arguments");
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "exnihilo.compost.addRecipe";
	}
}
