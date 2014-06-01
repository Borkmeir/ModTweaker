package modtweaker.mariculture.function;

import static modtweaker.helpers.TweakerHelper.getInt;
import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import mariculture.api.core.IAnvilHandler.RecipeAnvil;
import modtweaker.mariculture.action.AnvilAddRecipeAction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;

public class AnvilAddRecipeFunction extends TweakerFunction {
	public static final AnvilAddRecipeFunction INSTANCE = new AnvilAddRecipeFunction();
	
	private AnvilAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 3) throwException(toString(), 3); 
		ItemStack input = getItem(0, arguments).make();
		ItemStack output = getItem(1, arguments).make();
		int hits = getInt(2, arguments);
		Tweaker.apply(new AnvilAddRecipeAction(new RecipeAnvil(input, output, hits)));
		return null;
	}

	@Override
	public String toString() {
		return "mariculture.anvil.addRecipe";
	}
}
