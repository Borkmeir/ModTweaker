package modtweaker.mods.mekanism.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import mekanism.api.ChanceOutput;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.mekanism.util.AddMekanismRecipe;
import modtweaker.mods.mekanism.util.RemoveMekanismRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.Sawmill")
public class Sawmill {
    @ZenMethod
    public static void addRecipe(IItemStack input, IItemStack output1, IItemStack output2, double chance) {
        ChanceOutput chanceOutput = new ChanceOutput(toStack(output1), toStack(output2), chance);
        MineTweakerAPI.tweaker.apply(new AddMekanismRecipe("PRECISION_SAWMILL", toStack(input), chanceOutput));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output1, @Optional IItemStack output2) {
        ChanceOutput chanceOutput = new ChanceOutput(toStack(output1), toStack(output2), 100);
        MineTweakerAPI.tweaker.apply(new RemoveMekanismRecipe("PRECISION_SAWMILL", chanceOutput));
    }
}
