package modtweaker.tconstruct;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;

import java.util.Arrays;

import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;
import tconstruct.library.crafting.Smeltery;

public class MeltingRemoveRecipe extends TweakerBaseFunction {
	public static final MeltingRemoveRecipe INSTANCE = new MeltingRemoveRecipe();
	private MeltingRemoveRecipe() {
		super("tconstruct.smeltery.removeMelting");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(1, args)) {
			ItemStack input = getItem();
			Tweaker.apply(new Action(input));
		} else throwException(this, 1);
	}
	
	private static class Action implements IUndoableAction {
		private final ItemStack input;
		private FluidStack fluid;
		private Integer temp;
		private ItemStack renderer;
		public Action(ItemStack input) {
			this.input = input;
		}

		@Override
		public void apply() {
			fluid = TConstructHacks.smeltingList.get(Arrays.asList(input.itemID, input.getItemDamage()));
			temp = TConstructHacks.temperatureList.get(Arrays.asList(input.itemID, input.getItemDamage()));
			renderer = TConstructHacks.renderIndex.get(Arrays.asList(input.itemID, input.getItemDamage()));
			TConstructHacks.smeltingList.remove(Arrays.asList(input.itemID, input.getItemDamage()));
			TConstructHacks.temperatureList.remove(Arrays.asList(input.itemID, input.getItemDamage()));
			TConstructHacks.renderIndex.remove(Arrays.asList(input.itemID, input.getItemDamage()));
		}

		@Override
		public boolean canUndo() {
			return TConstructHacks.smeltingList != null;
		}

		@Override
		public void undo() {
			Smeltery.addMelting(input, renderer.itemID, renderer.getItemDamage(), temp, fluid);
		}

		@Override
		public String describe() {
			return "Removing Melting Recipe: " + input.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring Melting Recipe: " + input.getDisplayName();
		}
	}
}
