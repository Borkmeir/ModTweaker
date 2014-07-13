package modtweaker.mods.mekanism.util;

import modtweaker.mods.mekanism.MekanismHelper;
import modtweaker.util.BaseMapAddition;

public class AddMekanismRecipe extends BaseMapAddition {
    public AddMekanismRecipe(String str, Object key, Object recipe) {
        super(str.toLowerCase(), MekanismHelper.get(str), key, recipe);
    }
}
