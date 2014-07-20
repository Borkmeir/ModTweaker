package modtweaker.mods.mekanism.handlers;

import static modtweaker.helpers.InputHelper.toStack;

import java.util.Iterator;
import java.util.Map;

import mekanism.api.AdvancedInput;
import mekanism.api.gas.GasRegistry;
import mekanism.common.recipe.RecipeHandler.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.mekanism.util.AddMekanismRecipe;
import modtweaker.mods.mekanism.util.RemoveMekanismRecipe;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.Purification")
public class Purification {
    @ZenMethod
    public static void addRecipe(IItemStack input, IItemStack output) {
        AdvancedInput aInput = new AdvancedInput(toStack(input), GasRegistry.getGas("oxygen"));
        MineTweakerAPI.tweaker.apply(new AddMekanismRecipe("PURIFICATION_CHAMBER", Recipe.PURIFICATION_CHAMBER.get(), aInput, toStack(output)));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Remove("PURIFICATION_CHAMBER", Recipe.PURIFICATION_CHAMBER.get(), toStack(output)));
    }

    private static class Remove extends RemoveMekanismRecipe {
        public Remove(String string, Map map, Object key) {
            super(string, map, key);
        }

        @Override
        public void apply() {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                AdvancedInput key = (AdvancedInput) pairs.getKey();
                ItemStack value = (ItemStack) pairs.getValue();
                if (key != null) {
                    if (this.key instanceof ItemStack && value.isItemEqual((ItemStack) this.key)) {
                        this.key = key;
                        break;
                    }
                }

            }

            recipe = map.get(key);
            map.remove(key);
        }
    }
}
