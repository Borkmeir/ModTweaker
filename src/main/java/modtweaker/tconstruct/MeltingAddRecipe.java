package modtweaker.tconstruct;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.getInt;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;

import java.util.Arrays;

import modtweaker.util.TweakerBaseFunction;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.value.TweakerValue;
import tconstruct.library.crafting.Smeltery;

public class MeltingAddRecipe extends TweakerBaseFunction {
	public static final MeltingAddRecipe INSTANCE = new MeltingAddRecipe();
	private MeltingAddRecipe() {
		super("tconstruct.smeltery.addMelting");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(new int[] { 3, 4 },  args)) {
			ItemStack input = getItem();
			FluidStack output = getFluid();
			int temp = getInt();
			ItemStack block = input.copy();
			if(args.length == 4) {
				block = getItem();
			}
			
			if(Block.blocksList[block.itemID] == null) {
				throw new TweakerExecuteException("Item must be a block, or you must specify a block to render as");
			} else {
				Tweaker.apply(new Action(input, block.itemID, block.getItemDamage(), temp, output));
			}
		} else throwException(this, new int[] { 3, 4 });
	}
	
	private static class Action implements IUndoableAction {
		private final ItemStack input;
		private final int blockID;
		private final int metadata;
		private final int temperature;
		private final FluidStack liquid;
		public Action(ItemStack input, int blockID, int metadata, int temperature, FluidStack liquid) {
			this.input = input;
			this.blockID = blockID;
			this.metadata = metadata;
			this.temperature = temperature;
			this.liquid = liquid;
		}

		@Override
		public void apply() {
			Smeltery.addMelting(input, blockID, metadata, temperature, liquid);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			TConstructHacks.smeltingList.remove(Arrays.asList(input.itemID, input.getItemDamage()));
			TConstructHacks.temperatureList.remove(Arrays.asList(input.itemID, input.getItemDamage()));
			TConstructHacks.renderIndex.remove(Arrays.asList(input.itemID, input.getItemDamage()));
		}

		@Override
		public String describe() {
			return "Adding Smeltery Recipe: " + input.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Smeltery Recipe: " + input.getDisplayName();
		}
	}
}
