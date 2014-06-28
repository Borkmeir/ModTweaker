package modtweaker.mekanism;

import mekanism.api.gas.GasStack;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.value.TweakerField;
import stanhebben.minetweaker.api.value.TweakerInt;
import stanhebben.minetweaker.api.value.TweakerString;
import stanhebben.minetweaker.api.value.TweakerValue;

public final class TweakerGasStack extends TweakerGasBase {
	private final GasStack value;
	
	public TweakerGasStack(GasStack value) {
		this.value = value;
	}
	
	public GasStack get() {
		return value;
	}
	
	public String getName() {
		return value.getGas().getName();
	}
	
	public String getDisplayName() {
		return value.getGas().getLocalizedName();
	}
	
	@Override
	public TweakerValue index(String index) {
		if(index.equals("gas")) return new TweakerGas(value.getGas());
		switch (TweakerField.get(index)) {
			case NAME:
				return new TweakerString(value.getGas().getUnlocalizedName());
			case DISPLAYNAME:
				return new TweakerString(value.getGas().getLocalizedName());
			case AMOUNT:
				return new TweakerInt(value.amount);
			default:
				throw new TweakerExecuteException("no such field in gas: " + index);
		}
	}
	
	@Override
	public TweakerGasStack asGasStack() {
		return this;
	}

	@Override
	public String toString() {
		return "GasStack:" + value.getGas().getUnlocalizedName() + " * " + value.amount;
	}
}
