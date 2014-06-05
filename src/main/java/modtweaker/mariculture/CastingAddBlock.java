package modtweaker.mariculture;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting.RecipeBlockCasting;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CastingAddBlock extends TweakerBaseFunction {
	public static final CastingAddBlock INSTANCE = new CastingAddBlock();
	private CastingAddBlock() {
		super("mariculture.casting.addBlockCasting");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(2,  args)) {
			FluidStack input = getFluid();
			ItemStack output = getItem();
			Tweaker.apply(new Action(new RecipeBlockCasting(input, output)));
		} else throwException(this, 2);
	}
	
	private static class Action implements IUndoableAction {
		private final RecipeBlockCasting casting;
		public Action(RecipeBlockCasting casting) {
			this.casting = casting;
		}

		@Override
		public void apply() {
			MaricultureHandlers.casting.addRecipe(casting);
		}

		@Override
		public boolean canUndo() {
			return MaricultureHacks.blockCasting != null;
		}

		@Override
		public void undo() {
			MaricultureHacks.blockCasting.remove(casting.fluid.getFluid().getName());
		}

		@Override
		public String describe() {
			return "Adding Block Casting Recipe: " + casting.output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Block Casting Recipe: " + casting.output.getDisplayName();
		}
	}
}
