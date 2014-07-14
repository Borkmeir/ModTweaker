package modtweaker.mods.mekanism.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.mekanism.util.AddMekanismRecipe;
import modtweaker.mods.mekanism.util.RemoveMekanismRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.Enrichment")
public class Enrichment {
    @ZenMethod
    public static void addRecipe(IItemStack input, IItemStack output) {
        MineTweakerAPI.tweaker.apply(new AddMekanismRecipe("ENRICHMENT_CHAMBER", toStack(input), toStack(output)));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack input) {
        MineTweakerAPI.tweaker.apply(new RemoveMekanismRecipe("ENRICHMENT_CHAMBER", toStack(input)));
    }
}