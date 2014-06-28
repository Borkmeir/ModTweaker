package modtweaker.mekanism;

import static modtweaker.util.TweakerHelper.canContinue;
import static modtweaker.util.TweakerHelper.getItem;
import static modtweaker.util.TweakerHelper.throwException;
import mekanism.common.recipe.RecipeHandler.Recipe;
import modtweaker.util.TweakerBaseFunction;
import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.Tweaker;
import stanhebben.minetweaker.api.value.TweakerValue;

public class EnrichmentChamberAddRecipe extends TweakerBaseFunction {
	public static final EnrichmentChamberAddRecipe INSTANCE = new EnrichmentChamberAddRecipe();

	private EnrichmentChamberAddRecipe() {
		super("mekanism.enrichment.addRecipe");
	}

	@Override
	public void perform(TweakerValue... args) {
		if (canContinue(2, args)) {
			ItemStack input = getItem();
			ItemStack output = getItem();
			Tweaker.apply(new MekanismAddRecipeAction(Recipe.ENRICHMENT_CHAMBER, input, output));
		} else throwException(this, 2);
	}
}
