package modtweaker.mods.hee;

import static modtweaker.helpers.ReflectionHelper.getStaticObject;

import java.lang.reflect.Constructor;
import java.util.List;

import net.minecraft.item.ItemStack;

public class HEEHelper {
    public static List altar = null;
    static {
        try {
            altar = getStaticObject(Class.forName("chylex.hee.mechanics.essence.handler.DragonEssenceHandler"), "recipes");
        } catch (Exception e) {}
    }

    public static Object getAltarRecipe(ItemStack source, ItemStack result, int cost) {
        try {
            Class clazz = Class.forName("chylex.hee.mechanics.essence.handler.dragon.AltarItemRecipe");
            Constructor constructor = clazz.getDeclaredConstructor(ItemStack.class, ItemStack.class, int.class);
            constructor.setAccessible(true);
            return constructor.newInstance(source, result, cost);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("Failed to instantiate AltarItemRecipe");
        }
    }
}
