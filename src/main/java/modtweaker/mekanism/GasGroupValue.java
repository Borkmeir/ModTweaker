package modtweaker.mekanism;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.MineTweakerRegistry;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.value.TweakerLiquid;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class GasGroupValue extends TweakerGasBase {
	private final String name;
	
	public GasGroupValue() {
		this.name = null;
	}
	
	public GasGroupValue(String name) {
		this.name = name;
	}
	
	@Override
	public TweakerValue mul(TweakerValue value) throws TweakerExecuteException {
		Gas gas = GasRegistry.getGas(name);
		if (gas == null) return super.mul(value);
		return new TweakerGas(gas).mul(value);
	}
	
	@Override
	public TweakerGas asGas() {
		Gas gas = GasRegistry.getGas(name);
		if (gas == null) return null;
		return new TweakerGas(gas);
	}
	
	@Override
	public TweakerGasStack asGasStack() {
		Gas gas = GasRegistry.getGas(name);
		if (gas == null) return null;
		return new TweakerGasStack(new GasStack(gas, 1));
	}
	
	@Override
	public TweakerValue index(String index) throws TweakerExecuteException {
		if (GasRegistry.containsGas(index)) {
			return new GasGroupValue(index);
		} else if (GasRegistry.getGas(index) != null) {
			return new TweakerGas(GasRegistry.getGas(index));
		} else if (GasRegistry.getGas(index) != null) {
			return new TweakerGas(GasRegistry.getGas(name)).index(index);
		} else {
			throw new TweakerExecuteException("no such gas: " + index);
		}
	}
	
	@Override
	public void indexSet(TweakerValue index, TweakerValue value) throws TweakerExecuteException {
		Fluid fluid = FluidRegistry.getFluid(name);
		if (fluid == null) {
			super.indexSet(index, value);
		} else {
			new TweakerLiquid(fluid).indexSet(index, value);
		}
	}

	@Override
	public String toString() {
		return name == null ? "fluids" : name;
	}
}
