package modtweaker.mods.factorization;

import java.util.List;

import factorization.oreprocessing.TileEntityCrystallizer;
import factorization.oreprocessing.TileEntityGrinder;
import factorization.oreprocessing.TileEntitySlagFurnace.SlagRecipes;

public class FactorizationHelper {
    public static List lacerator = null;
    public static List slag = null;
    public static List crystallizer = null;

    static {
        try {
            lacerator = TileEntityGrinder.recipes;
            slag = SlagRecipes.smeltingResults;
            crystallizer = TileEntityCrystallizer.recipes;
        } catch (Exception e) {}
    }

    private FactorizationHelper() {}
}
