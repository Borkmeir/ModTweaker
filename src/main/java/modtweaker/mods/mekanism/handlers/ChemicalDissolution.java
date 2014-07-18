package modtweaker.mods.mekanism.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import static modtweaker.mods.mekanism.MekanismHelper.toGas;
import mekanism.common.recipe.RecipeHandler.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.mekanism.gas.IGasStack;
import modtweaker.mods.mekanism.util.AddMekanismRecipe;
import modtweaker.mods.mekanism.util.RemoveMekanismRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.chemical.Dissolution")
public class ChemicalDissolution {
    @ZenMethod
    public static void addRecipe(IItemStack input, IGasStack output) {
        MineTweakerAPI.tweaker.apply(new AddMekanismRecipe("CHEMICAL_DISSOLUTION_CHAMBER", Recipe.CHEMICAL_DISSOLUTION_CHAMBER.get(), toStack(input), toGas(output)));
    }

    @ZenMethod
    public static void removeRecipe(IGasStack output) {
        MineTweakerAPI.tweaker.apply(new RemoveMekanismRecipe("CHEMICAL_DISSOLUTION_CHAMBER", Recipe.CHEMICAL_DISSOLUTION_CHAMBER.get(), toGas(output)));
    }
}
