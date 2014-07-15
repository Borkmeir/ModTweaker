package modtweaker.mods.thaumcraft.handlers;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.mc172.util.MineTweakerHacks;
import modtweaker.mods.thaumcraft.ThaumcraftHelper;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;

import javax.annotation.Resource;

import static modtweaker.helpers.InputHelper.*;

@ZenClass("mods.thaumcraft.Research")
public class Research {
    private static final ResourceLocation defaultBackground = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");

    @ZenMethod
    public static void addTab(String key, String iconDomain, String iconPath){
        addTab(key, iconDomain, iconPath, null, null);
    }

    @ZenMethod
    public static void addTab(String key, String iconDomain, String iconPath, String backDomain, String backPath) {
        ResourceLocation icon = new ResourceLocation(iconDomain, iconPath);
        ResourceLocation background;
        if(backPath == null)
            background = defaultBackground;
        else
            background = new ResourceLocation(backDomain, backPath);
        addTab(key, icon, background);
    }

    private static void addTab(String key, ResourceLocation icon, ResourceLocation background){
        MineTweakerAPI.tweaker.apply(new AddTab(key, icon, background));
    }

    private static class AddTab implements IUndoableAction {
        String tab;
        ResourceLocation icon;
        ResourceLocation background;

        public AddTab(String research, ResourceLocation pic, ResourceLocation back){
            icon = pic;
            background = back;
            tab = research;
        }

        @Override
        public void apply() {
            ResearchCategories.registerCategory(tab, icon, background);
        }

        @Override
        public String describe(){
            return "Registering Research Tab: " + tab;
        }

        @Override
        public boolean canUndo(){
            return true;
        }

        @Override
        public void undo(){
            ResearchCategories.researchCategories.remove(tab);
        }

        @Override
        public String describeUndo(){
            return "Removing Research Tab: " + tab;
        }

        @Override
        public String getOverrideKey(){
            return null;
        }

    }

    private static class Add extends BaseListAddition {
        public Add(IArcaneRecipe recipe) {
            super("Thaumcraft Arcane Worktable", ThaumcraftApi.getCraftingRecipes(), recipe);
        }

        @Override
        public String getRecipeInfo() {
            Object out = ((IArcaneRecipe) recipe).getRecipeOutput();
            if (out instanceof ItemStack) {
                return ((ItemStack) out).getDisplayName();
            } else return super.getRecipeInfo();
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
        public RemoveTab(String victim){
            tab = victim;
        }

        @Override
        public void apply() {
            list = ResearchCategories.getResearchList(tab);
            ResearchCategories.researchCategories.remove(tab);
        }

        @Override
        public String describe(){
            return "Removing Research Tab: " + tab;
        }

        @Override
        public boolean canUndo(){
            return list != null;
        }

        @Override
        public void undo(){
            ResearchCategories.researchCategories.put(tab, list);
        }

        @Override
        public String describeUndo(){
            return "Restoring Research Tab: " + tab;
        }

        @Override
        public String getOverrideKey(){
            return null;
        }

    }

    private static class RemoveResearch implements IUndoableAction {
        String research;
        String tab;
        ResearchItem removed;
        public RemoveResearch(String victim){
            research = victim;
        }

        @Override
        public void apply() {
            for(String key : ResearchCategories.researchCategories.keySet()){
                if(ResearchCategories.researchCategories.get(key).research.containsKey(research)) {
                    tab = key;
                    removed = ResearchCategories.researchCategories.get(key).research.get(research);
                }
            }
            if(tab != null)
                ResearchCategories.researchCategories.get(tab).research.remove(research);
        }

        @Override
        public String describe(){
            return "Removing Research: " + tab;
        }

        @Override
        public boolean canUndo(){
            return tab != null && removed != null;
        }

        @Override
        public void undo(){
            ResearchCategories.researchCategories.get(tab).research.put(tab, removed);
        }

        @Override
        public String describeUndo(){
            return "Restoring Research: " + tab;
        }

        @Override
        public String getOverrideKey(){
            return null;
        }

    }

}
