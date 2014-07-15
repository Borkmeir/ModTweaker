package modtweaker.helpers;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.ForgeHooks;

public class ForgeHelper {
    public static List seeds = null;
    public static HashMap<String, ChestGenHooks> loot = null;

    static {
        try {
            seeds = ReflectionHelper.getStaticObject(ForgeHooks.class, "seedList");
            loot = ReflectionHelper.getStaticObject(ChestGenHooks.class, "chestInfo");
        } catch (Exception e) {}
    }

    private ForgeHelper() {}

    public static Object getSeedEntry(ItemStack stack, int weight) {
        try {
            Class clazz = Class.forName("net.minecraftforge.common.SeedEntry");
            Constructor constructor = clazz.getDeclaredConstructor(ItemStack.class, int.class);
            constructor.setAccessible(true);
            return constructor.newInstance(stack, weight);
        } catch (Exception e) {
            throw new NullPointerException("Failed to instantiate SeedEntry");
        }
    }
}
