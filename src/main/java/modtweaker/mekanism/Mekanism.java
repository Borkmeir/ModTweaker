package modtweaker.mekanism;

import modtweaker.mekanism.chemical.ChemicalValue;
import modtweaker.util.TweakerBaseValue;
import stanhebben.minetweaker.api.MineTweakerInterface;
import stanhebben.minetweaker.api.value.TweakerValue;

public class Mekanism extends MineTweakerInterface {
	public static final Mekanism INSTANCE = new Mekanism();
	
	Mekanism() {
		super("mekanism", MekanismValue.INSTANCE);
	}
	
	/** Base TiCon Handler **/
	public static class MekanismValue extends TweakerBaseValue {
		public static final MekanismValue INSTANCE = new MekanismValue();
		private MekanismValue() {
			super("mekanism");
		}
		
		@Override
		public TweakerValue index(String index) {
			if(index.equals("enrichment")) return EnrichmentChamberValue.INSTANCE;
			if(index.equals("compressor"))   return OsmiumCompressorValue.INSTANCE;
			if(index.equals("combiner")) return CombinerValue.INSTANCE;
			if(index.equals("crusher")) return CrusherValue.INSTANCE;
			if(index.equals("purification")) return PurificationValue.INSTANCE;
			if(index.equals("infuser")) return InfuserValue.INSTANCE;
			if(index.equals("seperator")) return SeperatorValue.INSTANCE;
			if(index.equals("sawmill")) return SawmillValue.INSTANCE;
			if(index.startsWith("chemical")) return ChemicalValue.INSTANCE;
			return super.index(index);
		}
	}
	
	public static class EnrichmentChamberValue extends MekanismBaseValue {
		public static final EnrichmentChamberValue INSTANCE = new EnrichmentChamberValue();
		private EnrichmentChamberValue() {
			super("mekanism.enrichment", EnrichmentChamberAddRecipe.INSTANCE, EnrichmentChamberRemoveRecipe.INSTANCE);
		}
	}
	
	public static class OsmiumCompressorValue extends MekanismBaseValue {
		public static final OsmiumCompressorValue INSTANCE = new OsmiumCompressorValue();
		private OsmiumCompressorValue() {
			super("mekanism.compressor", OsmiumCompressor.Add.INSTANCE, OsmiumCompressor.Remove.INSTANCE);
		}
	}
	
	public static class CombinerValue extends MekanismBaseValue {
		public static final CombinerValue INSTANCE = new CombinerValue();
		private CombinerValue() {
			super("mekanism.combiner", Combiner.Add.INSTANCE, Combiner.Remove.INSTANCE);
		}
	}
	
	public static class CrusherValue extends MekanismBaseValue {
		public static final CrusherValue INSTANCE = new CrusherValue();
		private CrusherValue() {
			super("mekanism.crusher", Crusher.Add.INSTANCE, Crusher.Remove.INSTANCE);
		}
	}
	
	public static class PurificationValue extends MekanismBaseValue {
		public static final PurificationValue INSTANCE = new PurificationValue();
		private PurificationValue() {
			super("mekanism.purification", PurificationChamber.Add.INSTANCE, PurificationChamber.Remove.INSTANCE);
		}
	}
	
	public static class InfuserValue extends MekanismBaseValue {
		public static final InfuserValue INSTANCE = new InfuserValue();
		private InfuserValue() {
			super("mekanism.infuser", Infuser.Add.INSTANCE, Infuser.Remove.INSTANCE);
		}
	}
	
	public static class SeperatorValue extends MekanismBaseValue {
		public static final SeperatorValue INSTANCE = new SeperatorValue();
		private SeperatorValue() {
			super("mekanism.seperator", Seperator.Add.INSTANCE, Seperator.Remove.INSTANCE);
		}
	}
	
	public static class SawmillValue extends MekanismBaseValue {
		public static final SawmillValue INSTANCE = new SawmillValue();
		private SawmillValue() {
			super("mekanism.sawmill", Sawmill.Add.INSTANCE, Sawmill.Remove.INSTANCE);
		}
	}
}
