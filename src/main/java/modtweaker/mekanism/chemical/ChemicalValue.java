package modtweaker.mekanism.chemical;

import modtweaker.mekanism.MekanismBaseValue;
import modtweaker.util.TweakerBaseValue;
import stanhebben.minetweaker.api.value.TweakerValue;

public class ChemicalValue extends TweakerBaseValue {
	public static final ChemicalValue INSTANCE = new ChemicalValue();
	private ChemicalValue() {
		super("mekanism.chemical");
	}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("infuser")) return InfuserValue.INSTANCE;
		if(index.equals("oxidizer")) return OxidizerValue.INSTANCE;
		if(index.equals("injection")) return InjectionValue.INSTANCE;
		if(index.equals("dissolution")) return DissolutionValue.INSTANCE;
		if(index.equals("washer")) return WasherValue.INSTANCE;
		if(index.equals("crystalizer")) return CrystalizerValue.INSTANCE;
		return super.index(index);
	}
	
	public static class InfuserValue extends MekanismBaseValue {
		public static final InfuserValue INSTANCE = new InfuserValue();
		private InfuserValue() {
			super("mekanism.chemical.infuser", Infuser.Add.INSTANCE, Infuser.Remove.INSTANCE);
		}
	}
	
	public static class OxidizerValue extends MekanismBaseValue {
		public static final OxidizerValue INSTANCE = new OxidizerValue();
		private OxidizerValue() {
			super("mekanism.chemical.oxidizer", Oxidizer.Add.INSTANCE, Oxidizer.Remove.INSTANCE);
		}
	}
	
	public static class InjectionValue extends MekanismBaseValue {
		public static final InjectionValue INSTANCE = new InjectionValue();
		private InjectionValue() {
			super("mekanism.chemical.injection", Injection.Add.INSTANCE, Injection.Remove.INSTANCE);
		}
	}
	
	public static class DissolutionValue extends MekanismBaseValue {
		public static final DissolutionValue INSTANCE = new DissolutionValue();
		private DissolutionValue() {
			super("mekanism.chemical.dissolution", Dissolution.Add.INSTANCE, Dissolution.Remove.INSTANCE);
		}
	}
	
	public static class WasherValue extends MekanismBaseValue {
		public static final WasherValue INSTANCE = new WasherValue();
		private WasherValue() {
			super("mekanism.chemical.washer", Washer.Add.INSTANCE, Washer.Remove.INSTANCE);
		}
	}
	
	public static class CrystalizerValue extends MekanismBaseValue {
		public static final CrystalizerValue INSTANCE = new CrystalizerValue();
		private CrystalizerValue() {
			super("mekanism.chemical.crystalizer", Crystalizer.Add.INSTANCE, Crystalizer.Remove.INSTANCE);
		}
	}
}
