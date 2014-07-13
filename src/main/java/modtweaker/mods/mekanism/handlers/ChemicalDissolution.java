package modtweaker.mods.mekanism.handlers;

import static modtweaker.helpers.InputHelper.toFluid;
import static modtweaker.helpers.InputHelper.toStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import modtweaker.mods.mekanism.util.AddMekanismRecipe;
import modtweaker.mods.mekanism.util.RemoveMekanismRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.chemical.Dissolution")
public class ChemicalDissolution {
    @ZenMethod
    public static void addRecipe(IItemStack input, ILiquidStack output) {
        MineTweakerAPI.tweaker.apply(new AddMekanismRecipe("CHEMICAL_DISSOLUTION_CHAMBER", toStack(input), toFluid(output)));
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack output) {
        MineTweakerAPI.tweaker.apply(new RemoveMekanismRecipe("CHEMICAL_DISSOLUTION_CHAMBER", toFluid(output)));
    }
}
