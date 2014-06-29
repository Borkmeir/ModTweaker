package modtweaker.util;

import java.util.List;

import minetweaker.IUndoableAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class BaseListRemoval implements IUndoableAction {
	protected final List list;
	protected final ItemStack stack;
	protected Object recipe;
	protected String description;
	
	public BaseListRemoval(String description, List list, ItemStack stack) {
		this(list, stack);
		this.description = description;
	}
	
	public BaseListRemoval(List list, ItemStack stack) {
		this.list = list;
		this.stack = stack;
	}

	@Override
	public boolean canUndo() {
		return list != null;
	}

	@Override
	public void undo() {
		list.add(recipe);
	}
	
	public String getRecipeInfo() {
		return "Unknown Item";
	}

	@Override
	public String describe() {
		if(recipe instanceof ItemStack) return "Adding " + description + " Recipe for :" + ((ItemStack)recipe).getDisplayName();
		else if (recipe instanceof FluidStack) return "Adding " + description + " Recipe for :" + ((FluidStack)recipe).getFluid().getLocalizedName();
		else return "Removing " + description + " Recipe for :" + getRecipeInfo();
	}

	@Override
	public String describeUndo() {
		if(recipe instanceof ItemStack) return "Removing " + description + " Recipe for :" + ((ItemStack)recipe).getDisplayName();
		else if (recipe instanceof FluidStack) return "Removing " + description + " Recipe for :" + ((FluidStack)recipe).getFluid().getLocalizedName();
		else return "Restoring " + description + " Recipe for :" + getRecipeInfo();
	}
}
