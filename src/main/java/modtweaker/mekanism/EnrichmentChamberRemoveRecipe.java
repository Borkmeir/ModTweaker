package modtweaker.mekanism;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import mekanism.common.recipe.RecipeHandler.Recipe;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class EnrichmentChamberRemoveRecipe extends TweakerBaseFunction {
	public static final EnrichmentChamberRemoveRecipe INSTANCE = new EnrichmentChamberRemoveRecipe();

	private EnrichmentChamberRemoveRecipe() {
		super("mekanism.enrichment.removeRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(1, args)) {
			ItemStack output = getItem();
			Tweaker.apply(new MekanismRemoveRecipeAction(Recipe.ENRICHMENT_CHAMBER, output));
		} else throwException(this, 1);
	}
}
