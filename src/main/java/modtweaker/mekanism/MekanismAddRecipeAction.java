package modtweaker.mekanism;

import mekanism.api.gas.GasStack;
import mekanism.common.recipe.RecipeHandler.Recipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;

public class MekanismAddRecipeAction implements IUndoableAction{
	public final Object input;
	public final Object output;
	public final Recipe handler;
	public MekanismAddRecipeAction(Recipe handler, Object input, Object output) {
		this.handler = handler;
		this.input = input;
		this.output = output;
	}
	
	@Override
	public void apply() {
		handler.put(input, output);
	}
	
	@Override
	public boolean canUndo() {
		return handler.get() != null;
	}
	
	@Override
	public void undo() {
		handler.get().remove(input);
	}
	
	@Override
	public String describe() {
		if(output instanceof ItemStack) {
			return "Adding Mekanism Recipe: " + handler.toString() + " : " + ((ItemStack)output).getDisplayName();
		} else if (output instanceof FluidStack) {
			return "Adding Mekanism Recipe: " + handler.toString() + " : " + ((FluidStack)output).getFluid().getName();
		} else if (output instanceof GasStack) {
			return "Adding Mekanism Recipe: " + handler.toString() + " : " + ((GasStack)output).getGas().getName();
		} else return "Adding Mekanism Recipe: " + output.toString();
	}

	@Override
	public String describeUndo() {
		if(output instanceof ItemStack) {
			return "Removing Mekanism Recipe: " + handler.toString() + " : " + ((ItemStack)output).getDisplayName();
		} else if (output instanceof FluidStack) {
			return "Removing Mekanism Recipe: " + handler.toString() + " : " + ((FluidStack)output).getFluid().getName();
		} else if (output instanceof GasStack) {
			return "Removing Mekanism Recipe: " + handler.toString() + " : " + ((GasStack)output).getGas().getName();
		} else return "Removing Mekanism Recipe: " + output.toString();
	}
}
