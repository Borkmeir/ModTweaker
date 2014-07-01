package modtweaker.mariculture;

import static modtweaker.util.Helper.getPrivateFinalObject;

import java.util.Map;

import mariculture.api.core.MaricultureHandlers;

public class MaricultureHelper {
    public static Map fuels = null;

    static {
        try {
            fuels = getPrivateFinalObject(MaricultureHandlers.crucible, "fuels");
        } catch (Exception e) {}
    }

    private MaricultureHelper() {}
}
