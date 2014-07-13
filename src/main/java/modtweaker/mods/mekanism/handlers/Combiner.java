package modtweaker.mods.mekanism.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import mekanism.api.AdvancedInput;
import mekanism.api.gas.GasRegistry;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.mekanism.util.AddMekanismRecipe;
import modtweaker.mods.mekanism.util.RemoveMekanismRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.Combiner")
public class Combiner {
    @ZenMethod
    public static void addRecipe(IItemStack input, IItemStack output) {
        AdvancedInput aInput = new AdvancedInput(toStack(input), GasRegistry.getGas("liquidStone"));
        MineTweakerAPI.tweaker.apply(new AddMekanismRecipe("COMBINER", aInput, toStack(output)));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MineTweakerAPI.tweaker.apply(new RemoveMekanismRecipe("COMBINER", toStack(output)));
    }
}
