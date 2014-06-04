package modtweaker.exnihilo.function;

import static modtweaker.helpers.TweakerHelper.getFluid;
import static modtweaker.helpers.TweakerHelper.getItem;
import modtweaker.exnihilo.action.CrucibleAddRecipeAction;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.TweakerNameSpace;
import stanhebben.minetweaker.api.value.TweakerFunction;
import stanhebben.minetweaker.api.value.TweakerValue;
import exnihilo.registries.helpers.Meltable;

public class CrucibleAddRecipeFunction extends TweakerFunction {
	public static final CrucibleAddRecipeFunction INSTANCE = new CrucibleAddRecipeFunction();
	
	private CrucibleAddRecipeFunction() {}

	@Override
	public TweakerValue call(TweakerNameSpace namespace, TweakerValue... arguments) {
		if(arguments.length == 2) {
			ItemStack input = getItem(0, arguments).get();
			if(input.itemID >= 4096 || Block.blocksList[input.itemID] == null) throw new TweakerExecuteException("Must add a block to Crucible Melting, not items");
			FluidStack fluid = getFluid(1, arguments).get();
			Tweaker.apply(new CrucibleAddRecipeAction(new Meltable(input.itemID, input.getItemDamage(), 2000, fluid.getFluid(), fluid.amount, Block.blocksList[input.itemID])));
		} else {
			throw new TweakerExecuteException(toString() + " requires 2 arguments");
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "exnihilo.crucible.addRecipe";
	}
}
