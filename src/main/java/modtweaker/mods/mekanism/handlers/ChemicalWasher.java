package modtweaker.mods.mekanism.handlers;

import static modtweaker.mods.mekanism.MekanismHelper.toGas;
import minetweaker.MineTweakerAPI;
import modtweaker.mods.mekanism.gas.IGasStack;
import modtweaker.mods.mekanism.util.AddMekanismRecipe;
import modtweaker.mods.mekanism.util.RemoveMekanismRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.chemical.Washer")
public class ChemicalWasher {
    @ZenMethod
    public static void addRecipe(IGasStack input, IGasStack output) {
        MineTweakerAPI.tweaker.apply(new AddMekanismRecipe("CHEMICAL_WASHER", toGas(input), toGas(output)));
    }

    @ZenMethod
    public static void removeRecipe(IGasStack output) {
        MineTweakerAPI.tweaker.apply(new RemoveMekanismRecipe("CHEMICAL_WASHER", toGas(output)));
    }
}
