package modtweaker.thaumcraft;

import static modtweaker.util.Helper.getPrivateStaticObject;

import java.util.ArrayList;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ThaumcraftHelper {
    public static ArrayList recipes = null;

    static {
        try {
            recipes = getPrivateStaticObject(ThaumcraftApi.class, "recipes");
        } catch (Exception e) {}
    }

    private ThaumcraftHelper() {}

    public static AspectList parseAspects(String aspects) {
        return parseAspects(new AspectList(), aspects);
    }

    public static AspectList parseAspects(AspectList list, String str) {
        if (list == null) list = new AspectList();
        String[] aspects = str.split(",");
        for (String aspect : aspects) {
            if(aspect.startsWith(" ")) aspect = aspect.replaceFirst(" ", "");
            String[] aspct = aspect.split("\\s+");
            if (aspct.length == 2) list.add(Aspect.aspects.get(aspct[0]), Integer.parseInt(aspct[1]));
        }

        return list;
    }

    public static AspectList removeAspects(AspectList list, String str) {
        String[] aspects = str.split(",");
        for (String aspect : aspects) {
            if(aspect.startsWith(" ")) aspect = aspect.replaceFirst(" ", "");
            String[] aspct = aspect.split("\\s+");
            if (aspct.length == 2) {
                list.remove(Aspect.aspects.get(aspct[0]), Integer.parseInt(aspct[1]));
            }
        }
        
        return list;
    }
}
