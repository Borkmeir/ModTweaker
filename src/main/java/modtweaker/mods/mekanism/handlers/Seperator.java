package modtweaker.mods.mekanism.handlers;

import static modtweaker.helpers.InputHelper.toFluid;
import static modtweaker.mods.mekanism.MekanismHelper.toGas;
import mekanism.api.ChemicalPair;
import mekanism.common.recipe.RecipeHandler.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.liquid.ILiquidStack;
import modtweaker.mods.mekanism.gas.IGasStack;
import modtweaker.mods.mekanism.util.AddMekanismRecipe;
import modtweaker.mods.mekanism.util.RemoveMekanismRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.Seperator")
public class Seperator {
    @ZenMethod
    public static void addRecipe(ILiquidStack input, IGasStack gas1, IGasStack gas2) {
        ChemicalPair pair = new ChemicalPair(toGas(gas1), toGas(gas2));
        MineTweakerAPI.tweaker.apply(new AddMekanismRecipe("ELECTROLYTIC_SEPARATOR", Recipe.ELECTROLYTIC_SEPARATOR.get(), toFluid(input), pair));
    }

    @ZenMethod
    public static void removeRecipe(IGasStack gas1, IGasStack gas2) {
        ChemicalPair pair = new ChemicalPair(toGas(gas1), toGas(gas2));
        MineTweakerAPI.tweaker.apply(new RemoveMekanismRecipe("ELECTROLYTIC_SEPARATOR", Recipe.ELECTROLYTIC_SEPARATOR.get(), pair));
    }
}
