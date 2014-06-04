package modtweaker.bigreactors;

import static modtweaker.helpers.ReflectionHelper.getPrivateStaticObject;

import java.util.Map;

import erogenousbeef.bigreactors.api.IReactorFuel;
import erogenousbeef.bigreactors.common.multiblock.helpers.CoilPartData;
import erogenousbeef.bigreactors.common.multiblock.helpers.ReactorInteriorData;

public class BigReactorsHacks {
	public static Map<String, IReactorFuel> fuels = null;
	public static Map<String, CoilPartData> coils = null;;
	public static Map<String, ReactorInteriorData> blocks = null;;
	public static Map<String, ReactorInteriorData> fluids = null;;

	static {
		try {
			Class<?> registry = Class.forName("erogenousbeef.bigreactors.common.BRRegistry");
			fuels = getPrivateStaticObject(registry, "_reactorFluids");
			coils = getPrivateStaticObject(registry, "_turbineCoilParts");
			blocks = getPrivateStaticObject(registry, "_reactorModeratorBlocks");
			fluids = getPrivateStaticObject(registry, "_reactorModeratorFluids");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BigReactorsHacks() {}
}
