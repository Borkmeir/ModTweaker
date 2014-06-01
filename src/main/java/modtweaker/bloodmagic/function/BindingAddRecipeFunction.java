package modtweaker.bloodmagic.function;

import static modtweaker.helpers.TweakerHelper.getItem;
import static modtweaker.helpers.TweakerHelper.throwException;
import modtweaker.bloodmagic.action.BindingAddRecipeAction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRecipe;

public class BindingAddRecipeFunction extends TweakerFunction {
	public static final BindingAddRecipeFunction INSTANCE = new BindingAddRecipeFunction();
	
	private BindingAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length != 2) throwException(toString(), 5); 
		ItemStack input = getItem(0, arguments).make();
		ItemStack output = getItem(1, arguments).make();
		Tweaker.apply(new BindingAddRecipeAction(new BindingRecipe(output, input)));
		return null;
	}

	@Override
	public String toString() {
		return "bloodmagic.binding.addRecipe";
	}
}
