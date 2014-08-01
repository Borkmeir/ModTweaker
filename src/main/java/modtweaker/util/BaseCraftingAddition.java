package modtweaker.util;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public abstract class BaseCraftingAddition extends BaseDescriptionAddition {
    protected final List<IRecipe> list;
    protected final boolean shapeless;
    protected final ItemStack output;
    protected final Object[] recipe;

    public BaseCraftingAddition(String name, boolean shapeless, List list, ItemStack output, Object... recipe) {
        super(name);
        this.shapeless = shapeless;
        this.output = output;
        this.recipe = recipe;
        this.list = list;
    }

    @Override
    public void apply() {
        if (shapeless) applyShapeless();
        else applyShaped();
    }

    public abstract void applyShaped();

    public abstract void applyShapeless();

    @Override
    public boolean canUndo() {
        return list != null;
    }

    public void undo() {
        IRecipe remove = null;
        for (IRecipe recipe : list) {
            if (recipe.getRecipeOutput() != null && recipe.getRecipeOutput().isItemEqual(output)) {
                remove = recipe;
                break;
            }
        }

        list.remove(remove);
    }
}
