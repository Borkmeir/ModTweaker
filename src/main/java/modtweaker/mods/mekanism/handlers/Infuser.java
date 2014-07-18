package modtweaker.mods.mekanism.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import mekanism.api.infuse.InfuseRegistry;
import mekanism.api.infuse.InfusionInput;
import mekanism.api.infuse.InfusionOutput;
import mekanism.common.recipe.RecipeHandler.Recipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.mekanism.util.AddMekanismRecipe;
import modtweaker.mods.mekanism.util.RemoveMekanismRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.Infuser")
public class Infuser {
    @ZenMethod
    public static void addRecipe(String type, int infuse, IItemStack input, IItemStack output) {
        InfusionInput infuseIn = new InfusionInput(InfuseRegistry.get(type), infuse, toStack(input));
        InfusionOutput infuseOut = new InfusionOutput(infuseIn, toStack(output));
        MineTweakerAPI.tweaker.apply(new AddMekanismRecipe("METALLURGIC_INFUSER", Recipe.METALLURGIC_INFUSER.get(), infuseIn, infuseOut));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MineTweakerAPI.tweaker.apply(new RemoveMekanismRecipe("METALLURGIC_INFUSER", Recipe.METALLURGIC_INFUSER.get(), toStack(output)));
    }
}
