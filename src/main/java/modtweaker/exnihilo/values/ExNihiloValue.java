package modtweaker.exnihilo.values;

import stanhebben.minetweaker.api.value.TweakerValue;

public class ExNihiloValue extends TweakerValue {
	public static final ExNihiloValue INSTANCE = new ExNihiloValue();
	
	private ExNihiloValue() {}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("sieve")) return SieveValue.INSTANCE;
		if(index.equals("heat")) return HeatValue.INSTANCE;
		if(index.equals("compost")) return CompostValue.INSTANCE;
		if(index.equals("hammer")) return HammerValue.INSTANCE;
		if(index.equals("crucible")) return CrucibleValue.INSTANCE;
		return super.index(index);
	}

	@Override
	public String toString() {
		return "exnihilo";
	}
}
