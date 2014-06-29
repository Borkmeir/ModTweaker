package modtweaker.util;

import cpw.mods.fml.common.Loader;

public class TweakerPlugin {	
	public static void register(String mod, Class clazz) {
		if(Loader.isModLoaded(mod)) {
			try {
				clazz.newInstance();
			} catch (Exception e) {}
		}
	}
}
