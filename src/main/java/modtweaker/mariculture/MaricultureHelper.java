package modtweaker.mariculture;

import static modtweaker.util.Helper.getPrivateFinalObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;

public class MaricultureHelper {
    public static Map fuels = null;
    public static HashMap<Rarity, ArrayList<Loot>> loot = null;

    static {
        try {
            fuels = getPrivateFinalObject(MaricultureHandlers.crucible, "fuels");
            loot = getPrivateFinalObject(Fishing.fishing, "fishing_loot");
        } catch (Exception e) {}
    }

    private MaricultureHelper() {}
}
