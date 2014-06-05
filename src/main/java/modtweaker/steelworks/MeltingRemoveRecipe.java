package modtweaker.steelworks;

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
import tsteelworks.lib.crafting.AdvancedSmelting;

public class MeltingRemoveRecipe extends TweakerBaseFunction {
	public static final MeltingRemoveRecipe INSTANCE = new MeltingRemoveRecipe();
	private MeltingRemoveRecipe() {
		super("steelworks.removeMelting");
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
			fluid = SteelworksHacks.smeltingList.get(Arrays.asList(input.itemID, input.getItemDamage()));
			temp = SteelworksHacks.temperatureList.get(Arrays.asList(input.itemID, input.getItemDamage()));
			renderer = SteelworksHacks.renderIndex.get(Arrays.asList(input.itemID, input.getItemDamage()));
			SteelworksHacks.smeltingList.remove(Arrays.asList(input.itemID, input.getItemDamage()));
			SteelworksHacks.temperatureList.remove(Arrays.asList(input.itemID, input.getItemDamage()));
			SteelworksHacks.renderIndex.remove(Arrays.asList(input.itemID, input.getItemDamage()));
		}

		@Override
		public boolean canUndo() {
			return SteelworksHacks.smeltingList != null;
		}

		@Override
		public void undo() {
			AdvancedSmelting.addMelting(input, renderer.itemID, renderer.getItemDamage(), temp, fluid);
		}

		@Override
		public String describe() {
			return "Removing High Oven Recipe: " + input.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring High Oven Recipe: " + input.getDisplayName();
		}
	}
}
