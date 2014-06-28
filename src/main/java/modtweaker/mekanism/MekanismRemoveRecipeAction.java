package modtweaker.mekanism;

import java.util.Iterator;
import java.util.Map;

import mekanism.api.ChanceOutput;
import mekanism.api.ChemicalPair;
import mekanism.api.gas.GasStack;
import mekanism.api.infuse.InfusionOutput;
import mekanism.common.recipe.RecipeHandler.Recipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;

public class MekanismRemoveRecipeAction implements IUndoableAction{
	public Object input;
	public Object output;
	public final Object tmpOutput;
	public final Recipe handler;
	public MekanismRemoveRecipeAction(Recipe handler, Object output) {
		this.handler = handler;
		this.output = output;
		this.tmpOutput = output;
	}
	
	@Override
	public void apply() {		
		try {
			Iterator it = handler.get().entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        Object value = pairs.getValue();
		        if(value != null) {
					if(tmpOutput instanceof ItemStack && value instanceof ItemStack) {
						if(((ItemStack)tmpOutput).isItemEqual((ItemStack) value)) {
							input = pairs.getKey();
							break;
						}
					}
					
					if(tmpOutput instanceof FluidStack && value instanceof FluidStack) {
						if(((FluidStack)tmpOutput).isFluidEqual((FluidStack) value)) {
							input = pairs.getKey();
							break;
						}
					}
					
					if(tmpOutput instanceof GasStack && value instanceof GasStack) {
						if(((GasStack)tmpOutput).isGasEqual((GasStack) value)) {
							input = pairs.getKey();
							break;
						}
					}
					
					if(tmpOutput instanceof ItemStack && value instanceof InfusionOutput) {
						if(((ItemStack) tmpOutput).isItemEqual(((InfusionOutput)value).resource)) {
							input = pairs.getKey();
							break;
						}
					}
					
					if(tmpOutput instanceof ChemicalPair && value instanceof ChemicalPair) {
						ChemicalPair par1 = (ChemicalPair) tmpOutput;
						ChemicalPair par2 = (ChemicalPair) value;
						if(par1.leftGas.isGasEqual(par2.leftGas) && par1.rightGas.isGasEqual(par2.rightGas)) {
							input = pairs.getKey();
							break;
						}
					}
					
					if(tmpOutput instanceof ChanceOutput && value instanceof ChanceOutput) {
						ChanceOutput par1 = (ChanceOutput) tmpOutput;
						ChanceOutput par2 = (ChanceOutput) value;
						if(par1.primaryOutput.isItemEqual(par2.primaryOutput)) {
							if(par1.secondaryOutput == null || (par1.secondaryOutput != null && par2.secondaryOutput != null && par1.secondaryOutput.isItemEqual(par2.secondaryOutput))) {
								input = pairs.getKey();
								break;
							}
						}
					}
				}
		        
		        it.remove();
		    }
	    
		    output = handler.get().get(input);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		handler.get().remove(input);
	}
	
	@Override
	public boolean canUndo() {
		return handler.get() != null;
	}
	
	@Override
	public void undo() {
		handler.put(input, output);
	}
	
	@Override
	public String describe() {
		if(output instanceof ItemStack) {
			return "Removing Mekanism Recipe: " + handler.toString() + " : " + ((ItemStack)output).getDisplayName();
		} else if (output instanceof FluidStack) {
			return "Removing Mekanism Recipe: " + handler.toString() + " : " + ((FluidStack)output).getFluid().getName();
		} else if (output instanceof GasStack) {
			return "Removing Mekanism Recipe: " + handler.toString() + " : " + ((GasStack)output).getGas().getName();
		} else return "Removing Mekanism Recipe: " + output.toString();
	}

	@Override
	public String describeUndo() {
		if(output instanceof ItemStack) {
			return "Restoring Mekanism Recipe: " + handler.toString() + " : " + ((ItemStack)output).getDisplayName();
		} else if (output instanceof FluidStack) {
			return "Restoring Mekanism Recipe: " + handler.toString() + " : " + ((FluidStack)output).getFluid().getName();
		} else if (output instanceof GasStack) {
			return "Restoring Mekanism Recipe: " + handler.toString() + " : " + ((GasStack)output).getGas().getName();
		} else return "Restoring Mekanism Recipe: " + output.toString();
	}
}
