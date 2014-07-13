package modtweaker.mods.mekanism.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import static modtweaker.mods.mekanism.MekanismHelper.toGas;
import mekanism.api.AdvancedInput;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.mekanism.gas.IGasStack;
import modtweaker.mods.mekanism.util.AddMekanismRecipe;
import modtweaker.mods.mekanism.util.RemoveMekanismRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.chemical.Injection")
public class ChemicalInjection {
    @ZenMethod
    public static void addRecipe(IItemStack input, IGasStack gas, IItemStack output) {
        AdvancedInput aInput = new AdvancedInput(toStack(input), toGas(gas).getGas());
        MineTweakerAPI.tweaker.apply(new AddMekanismRecipe("CHEMICAL_INJECTION_CHAMBER", aInput, toStack(output)));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MineTweakerAPI.tweaker.apply(new RemoveMekanismRecipe("CHEMICAL_INJECTION_CHAMBER", toStack(output)));
    }
}
