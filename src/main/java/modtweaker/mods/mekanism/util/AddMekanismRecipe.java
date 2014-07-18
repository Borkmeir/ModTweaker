package modtweaker.mods.mekanism.util;

import java.util.Map;

import modtweaker.util.BaseMapAddition;

public class AddMekanismRecipe extends BaseMapAddition {
    public AddMekanismRecipe(String str, Map map, Object key, Object recipe) {
        super(str.toLowerCase(), map, key, recipe);
    }
}
