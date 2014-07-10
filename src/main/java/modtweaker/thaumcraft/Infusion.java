package modtweaker.thaumcraft;

import static modtweaker.util.Helper.toStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.InfusionRecipe;

@ZenClass("mods.thaumcraft.Infusion")
public class Infusion {
    @ZenMethod
    public static void addRecipe(String key, ItemStack input, ItemStack[] recipe, String aspects, IItemStack result, int instability) {
        MineTweakerAPI.tweaker.apply(new Add(new InfusionRecipe(key, toStack(result), instability, ThaumcraftHelper.parseAspects(aspects), input, recipe)));
    }

    private static class Add extends BaseListAddition {
        public Add(InfusionRecipe recipe) {
            super("Thaumcraft Infusion", ThaumcraftApi.getCraftingRecipes(), recipe);
        }

        @Override
        public String getRecipeInfo() {
            Object out = ((InfusionRecipe) recipe).getRecipeOutput();
            if (out instanceof ItemStack) {
                return ((ItemStack) out).getDisplayName();
            } else return super.getRecipeInfo();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Remove(toStack(output)));
    }

    private static class Remove extends BaseListRemoval {
        public Remove(ItemStack stack) {
            super("Thaumcraft Infusion", ThaumcraftApi.getCraftingRecipes(), stack);
        }

        @Override
        public void apply() {
            for (Object o : ThaumcraftApi.getCraftingRecipes()) {
                if (o instanceof InfusionRecipe) {
                    InfusionRecipe r = (InfusionRecipe) o;
                    if (r.getRecipeOutput() != null && r.getRecipeOutput() instanceof ItemStack && stack.isItemEqual((ItemStack) r.getRecipeOutput())) {
                        recipe = r;
                        break;
                    }
                }
            }

            ThaumcraftApi.getCraftingRecipes().remove(recipe);
        }

        @Override
        public String getRecipeInfo() {
            return stack.getDisplayName();
        }
    }
}
