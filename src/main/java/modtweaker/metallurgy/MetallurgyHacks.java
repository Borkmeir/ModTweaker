package modtweaker.metallurgy;

import static modtweaker.helpers.ReflectionHelper.getPrivateStaticObject;

import java.util.Map;

import net.minecraft.item.ItemStack;

import com.google.common.collect.Table;

public class MetallurgyHacks {
	public static Map<Integer, ItemStack> crusher = null;
	public static Table<Integer, Integer, ItemStack> crusherMeta = null;
	static {
		try {
			Class<?> registry = Class.forName("rebelkeithy.mods.metallurgy.machines.crusher.CrusherRecipes");
			crusher = getPrivateStaticObject(registry, "smeltingList");
			crusherMeta = getPrivateStaticObject(registry, "metaSmeltingList");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MetallurgyHacks() {}
}
