package modtweaker.mods.thaumcraft.handlers;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import modtweaker.mods.thaumcraft.ThaumcraftHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

@ZenClass("mods.thaumcraft.Research")
public class Research {
    private static final ResourceLocation defaultBackground = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");

    @ZenMethod
    public static void addTab(String key, String iconDomain, String iconPath) {
        addTab(key, iconDomain, iconPath, null, null);
    }

    @ZenMethod
    public static void addTab(String key, String iconDomain, String iconPath, String backDomain, String backPath) {
        ResourceLocation icon = new ResourceLocation(iconDomain, iconPath);
        ResourceLocation background;
        if (backPath == null) background = defaultBackground;
        else background = new ResourceLocation(backDomain, backPath);
        addTab(key, icon, background);
    }

    private static void addTab(String key, ResourceLocation icon, ResourceLocation background) {
        MineTweakerAPI.tweaker.apply(new AddTab(key, icon, background));
    }

    private static class AddTab implements IUndoableAction {
        String tab;
        ResourceLocation icon;
        ResourceLocation background;

        public AddTab(String research, ResourceLocation pic, ResourceLocation back) {
            icon = pic;
            background = back;
            tab = research;
        }

        @Override
        public void apply() {
            ResearchCategories.registerCategory(tab, icon, background);
        }

        @Override
        public String describe() {
            return "Registering Research Tab: " + tab;
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            ResearchCategories.researchCategories.remove(tab);
        }

        @Override
        public String describeUndo() {
            return "Removing Research Tab: " + tab;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeTab(String tab) {
        MineTweakerAPI.tweaker.apply(new RemoveTab(tab));
    }

    @ZenMethod
    public static void removeResearch(String research) {
        MineTweakerAPI.tweaker.apply(new RemoveResearch(research));
    }

    private static class RemoveTab implements IUndoableAction {
        String tab;
        ResearchCategoryList list;

        public RemoveTab(String victim) {
            tab = victim;
        }

        @Override
        public void apply() {
            list = ResearchCategories.getResearchList(tab);
            ResearchCategories.researchCategories.remove(tab);
        }

        @Override
        public String describe() {
            return "Removing Research Tab: " + tab;
        }

        @Override
        public boolean canUndo() {
            return list != null;
        }

        @Override
        public void undo() {
            ResearchCategories.researchCategories.put(tab, list);
        }

        @Override
        public String describeUndo() {
            return "Restoring Research Tab: " + tab;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    private static class RemoveResearch implements IUndoableAction {
        String research;
        String tab;
        ResearchItem removed;

        public RemoveResearch(String victim) {
            research = victim;
            tab = ThaumcraftHelper.getResearchTab(research);
        }

        @Override
        public void apply() {
            if (tab != null) {
                removed = ResearchCategories.researchCategories.get(tab).research.get(research);
                ResearchCategories.researchCategories.get(tab).research.remove(research);
            }
        }

        @Override
        public String describe() {
            return "Removing Research: " + tab;
        }

        @Override
        public boolean canUndo() {
            return tab != null && removed != null;
        }

        @Override
        public void undo() {
            ResearchCategories.researchCategories.get(tab).research.put(tab, removed);
        }

        @Override
        public String describeUndo() {
            return "Restoring Research: " + tab;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void refreshResearchRecipe(String key){
        MineTweakerAPI.tweaker.apply(new RefreshResearch(key));
    }

    private static class RefreshResearch implements IUndoableAction {
        String research;
        String tab;

        public RefreshResearch(String target) {
            research = target;
            tab = ThaumcraftHelper.getResearchTab(research);
        }

        @Override
        public void apply() {
            if (tab != null) {
                ResearchItem target = ResearchCategories.researchCategories.get(tab).research.get(research);
                ResearchPage[] pages = target.getPages();
                for(int x = 0;x < pages.length;x++){
                    if(pages[x].recipe != null){
                        if(pages[x].recipe instanceof IRecipe) {
                            IRecipe recipe = (IRecipe)pages[x].recipe;
                            for (Object craft : CraftingManager.getInstance().getRecipeList()) {
                                if(craft instanceof IRecipe){
                                    IRecipe theCraft = (IRecipe)craft;
                                    if(theCraft.getRecipeOutput() != null && theCraft.getRecipeOutput().isItemEqual(recipe.getRecipeOutput())) {
                                        pages[x] = new ResearchPage(theCraft);
                                        break;
                                    }
                                }
                            }
                        }
                        else if(pages[x].recipe instanceof IArcaneRecipe){
                            IArcaneRecipe recipe = (IArcaneRecipe)pages[x].recipe;
                            for(Object craft : ThaumcraftApi.getCraftingRecipes()){
                                if(craft instanceof IArcaneRecipe){
                                    IArcaneRecipe theCraft = (IArcaneRecipe)craft;
                                    if(theCraft.getRecipeOutput() != null && theCraft.getRecipeOutput().isItemEqual(recipe.getRecipeOutput())) {
                                        pages[x] = new ResearchPage(theCraft);
                                        break;
                                    }
                                }
                            }
                        }
                        else if(pages[x].recipe instanceof CrucibleRecipe){
                            CrucibleRecipe recipe = (CrucibleRecipe)pages[x].recipe;
                            for(Object craft : ThaumcraftApi.getCraftingRecipes()){
                                if(craft instanceof CrucibleRecipe){
                                    CrucibleRecipe theCraft = (CrucibleRecipe)craft;
                                    if(theCraft.getRecipeOutput() != null && theCraft.getRecipeOutput().isItemEqual(recipe.getRecipeOutput())) {
                                        pages[x] = new ResearchPage(theCraft);
                                        break;
                                    }
                                }
                            }
                        }
                        else if(pages[x].recipe instanceof InfusionRecipe){
                            InfusionRecipe recipe = (InfusionRecipe)pages[x].recipe;
                            if(recipe.getRecipeOutput() instanceof ItemStack) {
                                for (Object craft : ThaumcraftApi.getCraftingRecipes()) {
                                    if (craft instanceof InfusionRecipe) {
                                        InfusionRecipe theCraft = (InfusionRecipe) craft;
                                        if (theCraft.getRecipeOutput() != null && theCraft.getRecipeOutput() instanceof ItemStack &&
                                                ((ItemStack) theCraft.getRecipeOutput()).isItemEqual((ItemStack) recipe.getRecipeOutput())) {
                                            pages[x] = new ResearchPage(theCraft);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public String describe() {
            return "Refreshing Research: " + research;
        }

        @Override
        public boolean canUndo() {
            return tab != null;
        }

        @Override
        public void undo() {
            apply();
        }

        @Override
        public String describeUndo() {
            return "Refreshing Research Again?: " + research;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

}
