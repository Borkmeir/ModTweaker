package modtweaker.util;

import java.util.Map;

import minetweaker.IUndoableAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class BaseMapRemoveKey implements IUndoableAction {
    protected ItemStack stack;
    protected String description;
    protected final Map map;
    protected final Object key;
    protected Object recipe;

    public BaseMapRemoveKey(ItemStack stack, Map map, Object key) {
        this(null, map, key, stack);
    }

    public BaseMapRemoveKey(String description, Map map, Object key, ItemStack stack) {
        this.stack = stack;
        this.map = map;
        this.key = key;
        this.description = description;
    }

    @Override
    public void apply() {
        recipe = map.get(key);
        map.remove(key);
    }

    @Override
    public boolean canUndo() {
        return map != null;
    }

    @Override
    public void undo() {
        map.put(key, recipe);
    }

    public String getRecipeInfo() {
        return "Unknown Item";
    }

    @Override
    public String describe() {
        if (recipe instanceof ItemStack) return "Adding " + description + " Recipe for :" + ((ItemStack) recipe).getDisplayName();
        else if (recipe instanceof FluidStack) return "Adding " + description + " Recipe for :" + ((FluidStack) recipe).getFluid().getLocalizedName();
        else return "Adding " + description + " Recipe for :" + getRecipeInfo();
    }

    @Override
    public String describeUndo() {
        if (recipe instanceof ItemStack) return "Removing " + description + " Recipe for :" + ((ItemStack) recipe).getDisplayName();
        else if (recipe instanceof FluidStack) return "Removing " + description + " Recipe for :" + ((FluidStack) recipe).getFluid().getLocalizedName();
        else return "Removing " + description + " Recipe for :" + getRecipeInfo();
    }
}
