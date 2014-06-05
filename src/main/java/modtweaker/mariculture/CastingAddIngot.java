package modtweaker.mariculture;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getFluid;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting.RecipeIngotCasting;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.IUndoableAction;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class CastingAddIngot extends TweakerBaseFunction {
	public static final CastingAddIngot INSTANCE = new CastingAddIngot();
	private CastingAddIngot() {
		super("mariculture.casting.addIngotCasting");
	}
	
	@Override
	public void perform(TweakerValue... args) {
		if(canContinue(2,  args)) {
			FluidStack input = getFluid();
			ItemStack output = getItem();
			Tweaker.apply(new Action(new RecipeIngotCasting(input, output)));
		} else throwException(this, 2);
	}
	
	private static class Action implements IUndoableAction {
		private final RecipeIngotCasting casting;
		public Action(RecipeIngotCasting casting) {
			this.casting = casting;
		}

		@Override
		public void apply() {
			MaricultureHandlers.casting.addRecipe(casting);
		}

		@Override
		public boolean canUndo() {
			return MaricultureHacks.ingotCasting != null;
		}

		@Override
		public void undo() {
			MaricultureHacks.ingotCasting.remove(casting.fluid.getFluid().getName());
		}

		@Override
		public String describe() {
			return "Adding Ingot Casting Recipe: " + casting.output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Ingot Casting Recipe: " + casting.output.getDisplayName();
		}
	}
}
