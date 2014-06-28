package modtweaker.mekanism;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.value.TweakerField;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerString;
import stanhebben.minetweaker.api.value.TweakerValue;
import stanhebben.minetweaker.base.actions.SetLocalizedStringAction;

/**
 * Represents a MineTweaker liquid. Compatible with all minecraft versions.
 * 
 * @author Stan Hebben
 */
public final class TweakerGas extends TweakerGasBase {
	private final Gas gas;
	
	public TweakerGas(Gas gas) {
		this.gas = gas;
	}
	
	public Gas get() {
		return gas;
	}
	
	public GasStack make(int amount) {
		return new GasStack(gas, amount);
	}
	
	public boolean equalsGas(Gas gas) {
		if (gas == null) return false;
		return gas.getName().equals(this.gas.getName());
	}
	
	public String getName() {
		return gas.getName();
	}
	
	public String getDisplayName() {
		return gas.getLocalizedName();
	}
	
	@Override
	public TweakerGas asGas() {
		return this;
	}
	
	@Override
	public TweakerGasStack asGasStack() {
		return new TweakerGasStack(new GasStack(gas, 1));
	}
	
	@Override
	public TweakerValue mul(TweakerValue value) {
		int amount = TweakerValue.notNull(value, "cannot multiply a gas by null").toInt("can only multiply a gas by an int value").get();
		return new TweakerGasStack(new GasStack(gas, amount));
	}
	
	@Override
	public TweakerValue index(String index) {
		switch (TweakerField.get(index)) {
			case NAME:
				return new TweakerString(gas.getUnlocalizedName());
			case DISPLAYNAME:
				return new TweakerString(gas.getLocalizedName());
			default:
				throw new TweakerExecuteException("no such field in gas: " + index);
		}
	}
	
	@Override
	public void indexSet(String index, TweakerValue value) {
		switch (TweakerField.get(index)) {
			case DISPLAYNAME:
				Tweaker.apply(new SetLocalizedStringAction(gas.getUnlocalizedName(), "en_US", value.toBasicString()));
				return;
			default:
				throw new TweakerExecuteException("no such settable field in gas: " + index);
		}
	}
	
	@Override
	public boolean equals(TweakerValue other) {
		if(!(other instanceof TweakerGas)) return false;
		TweakerGas asGas = ((TweakerGas)other).asGas();
		if (asGas == null) return false;
		return asGas.gas.getName().equals(gas.getName());
	}

	@Override
	public String toString() {
		return "Gas:" + gas.getUnlocalizedName();
	}
}
