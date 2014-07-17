package modtweaker.mods.thaumcraft.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.thaumcraft.ThaumcraftHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.research.ResearchPage.PageType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

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

    @ZenMethod
    public static void orphanResearch(String research) {
        MineTweakerAPI.tweaker.apply(new OrphanResearch(research));
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
        String key;
        String tab;
        ResearchItem removed;

        public RemoveResearch(String victim) {
            key = victim;
            tab = ThaumcraftHelper.getResearchTab(key);
        }

        @Override
        public void apply() {
            if (tab != null) {
                removed = ResearchCategories.researchCategories.get(tab).research.get(key);
                ResearchCategories.researchCategories.get(tab).research.remove(key);
            }
        }

        @Override
        public String describe() {
            return "Removing Research: " + key;
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
            return "Restoring Research: " + key;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    private static class OrphanResearch implements IUndoableAction {
        String key;
        HashMap<String, String> children = new HashMap();
        HashMap<String, String> secretChildren = new HashMap();
        HashMap<String, String> siblings = new HashMap();

        public OrphanResearch(String victim) {
            key = victim;
        }

        @Override
        public void apply() {
            for(String tab : ResearchCategories.researchCategories.keySet()){
                for(String research : ResearchCategories.researchCategories.get(tab).research.keySet()){
                    String[] prereqs = ResearchCategories.researchCategories.get(tab).research.get(research).parents;
                    if(prereqs != null) {
                        for (int x = 0; x < prereqs.length; x++) {
                            if (prereqs[x] != null && prereqs[x].equals(key)) {
                                children.put(research, tab);
                                ArrayList<String> newReqs = new ArrayList();
                                for (int y = 0; y < prereqs.length; y++) {
                                    if (y != x)
                                        newReqs.add(prereqs[y]);
                                }
                                ResearchCategories.researchCategories.get(tab).research.get(research).setParents(newReqs.toArray(new String[prereqs.length - 1]));
                                break;
                            }
                        }
                    }
                    prereqs = ResearchCategories.researchCategories.get(tab).research.get(research).parentsHidden;
                    if(prereqs != null) {
                        for (int x = 0; x < prereqs.length; x++) {
                            if (prereqs[x] != null && prereqs[x].equals(key)) {
                                secretChildren.put(research, tab);
                                ArrayList<String> newReqs = new ArrayList();
                                for (int y = 0; y < prereqs.length; y++) {
                                    if (y != x)
                                        newReqs.add(prereqs[y]);
                                }
                                ResearchCategories.researchCategories.get(tab).research.get(research).setParentsHidden(newReqs.toArray(new String[prereqs.length - 1]));
                                break;
                            }
                        }
                    }
                    prereqs = ResearchCategories.researchCategories.get(tab).research.get(research).siblings;
                    if(prereqs != null) {
                        for (int x = 0; x < prereqs.length; x++) {
                            if (prereqs[x] != null && prereqs[x].equals(key)) {
                                siblings.put(research, tab);
                                ArrayList<String> newReqs = new ArrayList();
                                for (int y = 0; y < prereqs.length; y++) {
                                    if (y != x)
                                        newReqs.add(prereqs[y]);
                                }
                                ResearchCategories.researchCategories.get(tab).research.get(research).setSiblings(newReqs.toArray(new String[prereqs.length - 1]));
                                break;
                            }
                        }
                    }
                }
            }
        }

        @Override
        public String describe() {
            return "Orphaning Research: " + key;
        }

        @Override
        public boolean canUndo() {
            return children.size() > 0 || secretChildren.size() > 0 || siblings.size() > 0;
        }

        @Override
        public void undo() {
            if(children.size() > 0) {
                for (String research : children.keySet()) {
                    String[] oldPrereqs = ResearchCategories.researchCategories.get(children.get(research)).research.get(research).parents;
                    String[] newReqs = new String[oldPrereqs.length + 1];
                    for (int x = 0; x < oldPrereqs.length; x++) {
                        newReqs[x] = oldPrereqs[x];
                    }
                    newReqs[oldPrereqs.length] = key;
                    ResearchCategories.researchCategories.get(children.get(research)).research.get(research).setParents(newReqs);
                }
            }
            if(secretChildren.size() > 0){
                for(String research : secretChildren.keySet()){
                    String[] oldPrereqs = ResearchCategories.researchCategories.get(secretChildren.get(research)).research.get(research).parentsHidden;
                    String[] newReqs = new String[oldPrereqs.length + 1];
                    for(int x = 0;x < oldPrereqs.length;x++){
                        newReqs[x] = oldPrereqs[x];
                    }
                    newReqs[oldPrereqs.length] = key;
                    ResearchCategories.researchCategories.get(secretChildren.get(research)).research.get(research).setParentsHidden(newReqs);
                }
            }
            if(siblings.size() > 0){
                for(String research : siblings.keySet()){
                    String[] oldPrereqs = ResearchCategories.researchCategories.get(siblings.get(research)).research.get(research).siblings;
                    String[] newReqs = new String[oldPrereqs.length + 1];
                    for(int x = 0;x < oldPrereqs.length;x++){
                        newReqs[x] = oldPrereqs[x];
                    }
                    newReqs[oldPrereqs.length] = key;
                    ResearchCategories.researchCategories.get(siblings.get(research)).research.get(research).setSiblings(newReqs);
                }
            }
        }

        @Override
        public String describeUndo() {
            return "Reattaching Research: " + key;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void addResearch(String key, String tab, @Optional String aspects, int x, int y, int difficulty, String domain, String path){
        MineTweakerAPI.tweaker.apply(new AddResearch(new ResearchItem(key, tab, ThaumcraftHelper.parseAspects(aspects), x, y, difficulty, new ResourceLocation(domain, path))));
    }

    @ZenMethod
    public static void addResearch(String key, String tab, @Optional String aspects, int x, int y, int difficulty, IItemStack item){
        MineTweakerAPI.tweaker.apply(new AddResearch(new ResearchItem(key, tab, ThaumcraftHelper.parseAspects(aspects), x, y, difficulty, toStack(item))));
    }

    @ZenMethod
    public static void addPage(String key, String unlocalized){
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.TEXT, unlocalized));
    }

    @ZenMethod
    public static void addCraftingPage(String key, IItemStack item){
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.NORMAL_CRAFTING, toStack(item)));
    }

    @ZenMethod
    public static void addCruciblePage(String key, IItemStack item){
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.CRUCIBLE_CRAFTING, toStack(item)));
    }

    @ZenMethod
    public static void addArcanePage(String key, IItemStack item){
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.ARCANE_CRAFTING, toStack(item)));
    }

    @ZenMethod
    public static void addInfusionPage(String key, IItemStack item){
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.INFUSION_CRAFTING, toStack(item)));
    }

    @ZenMethod
    public static void addEnchantmentPage(String key, int i){
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.INFUSION_ENCHANTMENT, Enchantment.enchantmentsList[i]));
    }

    @ZenMethod
    public static void clearPages(String key){
        MineTweakerAPI.tweaker.apply(new ClearPages(key));
    }

    @ZenMethod
    public static void addPrereq(String key, String req, @Optional boolean hidden){
        MineTweakerAPI.tweaker.apply(new AddPrereq(key, req, hidden));
    }

    @ZenMethod
    public static void clearPrereqs(String key){
        MineTweakerAPI.tweaker.apply(new ClearPrereqs(key));
    }

    @ZenMethod
    public static void addSibling(String key, String sibling){
        MineTweakerAPI.tweaker.apply(new AddSibling(key, sibling));
    }

    @ZenMethod
    public static void clearSiblings(String key){
        MineTweakerAPI.tweaker.apply(new ClearSiblings(key));
    }

    @ZenMethod
    public static void setRound(String key, boolean flag){
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.ROUND));
    }

    @ZenMethod
    public static void setSpikey(String key, boolean flag){
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.SPIKE));
    }

    @ZenMethod
    public static void setStub(String key, boolean flag){
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.STUB));
    }

    @ZenMethod
    public static void setSecondary(String key, boolean flag){
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.SECONDARY));
    }

    @ZenMethod
    public static void setVirtual(String key, boolean flag){
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.VIRTUAL));
    }

    @ZenMethod
    public static void setAutoUnlock(String key, boolean flag){
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.AUTO));
    }

    @ZenMethod
    public static void setConcealed(String key, boolean flag){
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.CONCEAL));
    }

    @ZenMethod
    public static void refreshResearchRecipe(String key){
        MineTweakerAPI.tweaker.apply(new RefreshResearch(key));
    }

    @ZenMethod
    public static void moveResearch(String key, String destination, int x, int y){
        MineTweakerAPI.tweaker.apply(new MoveResearch(key, destination, x, y));
    }

    private static class AddResearch implements IUndoableAction {
        String key;
        String tab;
        ResearchItem research;

        public AddResearch(ResearchItem res) {
            research = res;
            tab = research.category;
            key = research.key;
        }

        @Override
        public void apply() {
            research.registerResearchItem();
        }

        @Override
        public String describe() {
            return "Registering Research: " + key;
        }

        @Override
        public boolean canUndo() {
            return tab != null && key != null;
        }

        @Override
        public void undo() {
            ResearchCategories.researchCategories.get(tab).research.remove(key);
        }

        @Override
        public String describeUndo() {
            return "Removing Research: " + key;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    private static class AddPage implements IUndoableAction {
        String key;
        String tab;
        ResearchPage page;
        ResearchPage[] oldPages;
        PageType type;
        ItemStack target;
        Enchantment enchant;

        public AddPage(String res, PageType a, Object b) {
            key = res;
            tab = ThaumcraftHelper.getResearchTab(key);
            type = a;
            if(type == PageType.TEXT)
                page = new ResearchPage((String)b);
            else if(type == PageType.INFUSION_ENCHANTMENT)
                enchant = (Enchantment)b;
            if(b instanceof ItemStack)
                target = (ItemStack)b;
        }

        @Override
        public void apply() {
            if(type == PageType.NORMAL_CRAFTING){
                for (Object craft : CraftingManager.getInstance().getRecipeList()) {
                    if(craft instanceof IRecipe){
                        IRecipe theCraft = (IRecipe)craft;
                        if(theCraft.getRecipeOutput() != null && theCraft.getRecipeOutput().isItemEqual(target)) {
                            page = new ResearchPage(theCraft);
                            break;
                        }
                    }
                }
            }
            else if(type == PageType.ARCANE_CRAFTING){
                for(Object craft : ThaumcraftApi.getCraftingRecipes()){
                    if(craft instanceof IArcaneRecipe){
                        IArcaneRecipe theCraft = (IArcaneRecipe)craft;
                        if(theCraft.getRecipeOutput() != null && theCraft.getRecipeOutput().isItemEqual(target)) {
                            page = new ResearchPage(theCraft);
                            break;
                        }
                    }
                }
            }
            else if(type == PageType.CRUCIBLE_CRAFTING){
                for(Object craft : ThaumcraftApi.getCraftingRecipes()){
                    if(craft instanceof CrucibleRecipe){
                        CrucibleRecipe theCraft = (CrucibleRecipe)craft;
                        if(theCraft.getRecipeOutput() != null && theCraft.getRecipeOutput().isItemEqual(target)) {
                            page = new ResearchPage(theCraft);
                            break;
                        }
                    }
                }
            }
            else if(type == PageType.INFUSION_CRAFTING){
                for (Object craft : ThaumcraftApi.getCraftingRecipes()) {
                    if (craft instanceof InfusionRecipe) {
                        InfusionRecipe theCraft = (InfusionRecipe) craft;
                        if (theCraft.getRecipeOutput() != null && theCraft.getRecipeOutput() instanceof ItemStack &&
                                ((ItemStack)(theCraft.getRecipeOutput())).isItemEqual(target)) {
                            page = new ResearchPage(theCraft);
                            break;
                        }
                    }
                }
            }
            else if(type == PageType.INFUSION_ENCHANTMENT){
                for (Object craft : ThaumcraftApi.getCraftingRecipes()) {
                    if (craft instanceof InfusionEnchantmentRecipe) {
                        InfusionEnchantmentRecipe theCraft = (InfusionEnchantmentRecipe) craft;
                        if (theCraft.getEnchantment() != null && theCraft.getEnchantment() == enchant) {
                            page = new ResearchPage(theCraft);
                            break;
                        }
                    }
                }
            }
            if(page == null)
                return;
            oldPages = ResearchCategories.researchCategories.get(tab).research.get(key).getPages();
            if(oldPages == null)
                oldPages = new ResearchPage[0];
            ResearchPage[] newPages = new ResearchPage[oldPages.length + 1];
            for(int x = 0;x < oldPages.length;x++){
                newPages[x] = oldPages[x];
            }
            newPages[oldPages.length] = page;
            ResearchCategories.researchCategories.get(tab).research.get(key).setPages(newPages);
        }

        @Override
        public String describe() {
            return "Adding Research Page to " + key;
        }

        @Override
        public boolean canUndo() {
            return oldPages != null;
        }

        @Override
        public void undo() {
            ResearchCategories.researchCategories.get(tab).research.get(key).setPages(oldPages);
        }

        @Override
        public String describeUndo() {
            return "Removing Page from " + key;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    private static class ClearPages implements IUndoableAction {
        String key;
        String tab;
        ResearchPage[] oldPages;

        public ClearPages(String res) {
            key = res;
            tab = ThaumcraftHelper.getResearchTab(key);
        }

        @Override
        public void apply() {
            oldPages = ResearchCategories.researchCategories.get(tab).research.get(key).getPages();
            ResearchCategories.researchCategories.get(tab).research.get(key).setPages(new ResearchPage[0]);
        }

        @Override
        public String describe() {
            return "Clearing Research Pages from " + key;
        }

        @Override
        public boolean canUndo() {
            return oldPages != null;
        }

        @Override
        public void undo() {
            ResearchCategories.researchCategories.get(tab).research.get(key).setPages(oldPages);
        }

        @Override
        public String describeUndo() {
            return "Restoring Pages to " + key;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

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

    private static class AddPrereq implements IUndoableAction {
        String key;
        String tab;
        String prereq;
        String[] oldPrereqs;
        boolean hidden;

        public AddPrereq(String res, String req, boolean secret) {
            key = res;
            tab = ThaumcraftHelper.getResearchTab(key);
            prereq = req;
            hidden = secret;
        }

        @Override
        public void apply() {
            if(!hidden) {
                oldPrereqs = ResearchCategories.researchCategories.get(tab).research.get(key).parents;
                if(oldPrereqs == null)
                    oldPrereqs = new String[0];
                String[] newPrereqs = new String[oldPrereqs.length + 1];
                for(int x = 0;x < oldPrereqs.length;x++){
                    newPrereqs[x] = oldPrereqs[x];
                }
                newPrereqs[oldPrereqs.length] = prereq;
                ResearchCategories.researchCategories.get(tab).research.get(key).setParents(prereq);
            }
            else {
                oldPrereqs = ResearchCategories.researchCategories.get(tab).research.get(key).parentsHidden;
                if(oldPrereqs == null)
                    oldPrereqs = new String[0];
                String[] newPrereqs = new String[oldPrereqs.length + 1];
                for(int x = 0;x < oldPrereqs.length;x++){
                    newPrereqs[x] = oldPrereqs[x];
                }
                newPrereqs[oldPrereqs.length] = prereq;
                ResearchCategories.researchCategories.get(tab).research.get(key).setParentsHidden(prereq);
            }
        }

        @Override
        public String describe() {
            return "Adding Prerequisites to " + key;
        }

        @Override
        public boolean canUndo() {
            return oldPrereqs != null;
        }

        @Override
        public void undo() {
            if(!hidden)
                ResearchCategories.researchCategories.get(tab).research.get(key).setParents(oldPrereqs);
            else
                ResearchCategories.researchCategories.get(tab).research.get(key).setParentsHidden(oldPrereqs);
        }

        @Override
        public String describeUndo() {
            return "Restoring Prerequisites for " + key;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    private static class AddSibling implements IUndoableAction {
        String key;
        String tab;
        String sibling;
        String[] oldSiblings;

        public AddSibling(String res, String sib) {
            key = res;
            tab = ThaumcraftHelper.getResearchTab(key);
            sibling = sib;
        }

        @Override
        public void apply() {
            oldSiblings = ResearchCategories.researchCategories.get(tab).research.get(key).siblings;
            if(oldSiblings == null)
                oldSiblings = new String[0];
            String[] newSiblings = new String[oldSiblings.length + 1];
            for(int x = 0;x < oldSiblings.length;x++){
                newSiblings[x] = oldSiblings[x];
            }
            newSiblings[oldSiblings.length] = sibling;
            ResearchCategories.researchCategories.get(tab).research.get(key).setSiblings(sibling);
        }

        @Override
        public String describe() {
            return "Adding Sibling to " + key;
        }

        @Override
        public boolean canUndo() {
            return oldSiblings != null;
        }

        @Override
        public void undo() {
            ResearchCategories.researchCategories.get(tab).research.get(key).setSiblings(oldSiblings);
        }

        @Override
        public String describeUndo() {
            return "Restoring Siblings for " + key;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    private static class ClearPrereqs implements IUndoableAction {
        String key;
        String tab;
        String[] prereqs;
        String[] secretPrereqs;

        public ClearPrereqs(String res) {
            key = res;
            tab = ThaumcraftHelper.getResearchTab(key);
        }

        @Override
        public void apply() {
            prereqs = ResearchCategories.researchCategories.get(tab).research.get(key).parents;
            secretPrereqs = ResearchCategories.researchCategories.get(tab).research.get(key).parentsHidden;
            ResearchCategories.researchCategories.get(tab).research.get(key).setParents(new String[0]);
            ResearchCategories.researchCategories.get(tab).research.get(key).setParentsHidden(new String[0]);
        }

        @Override
        public String describe() {
            return "Clearing Prerequisites for " + key;
        }

        @Override
        public boolean canUndo() {
            return prereqs != null || secretPrereqs != null;
        }

        @Override
        public void undo() {
            if(prereqs != null)
                ResearchCategories.researchCategories.get(tab).research.get(key).setParents(prereqs);
            if(secretPrereqs != null)
                ResearchCategories.researchCategories.get(tab).research.get(key).setParentsHidden(secretPrereqs);
        }

        @Override
        public String describeUndo() {
            return "Restoring Prerequisites for " + key;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    private static class ClearSiblings implements IUndoableAction {
        String key;
        String tab;
        String[] siblings;

        public ClearSiblings(String res) {
            key = res;
            tab = ThaumcraftHelper.getResearchTab(key);
        }

        @Override
        public void apply() {
            siblings = ResearchCategories.researchCategories.get(tab).research.get(key).siblings;
            ResearchCategories.researchCategories.get(tab).research.get(key).setSiblings(new String[0]);
        }

        @Override
        public String describe() {
            return "Clearing Siblings for " + key;
        }

        @Override
        public boolean canUndo() {
            return siblings != null;
        }

        @Override
        public void undo() {
            ResearchCategories.researchCategories.get(tab).research.get(key).setSiblings(siblings);
        }

        @Override
        public String describeUndo() {
            return "Restoring Siblings for " + key;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    private static enum SetType {
        AUTO, ROUND, SPIKE, SECONDARY, STUB, VIRTUAL, CONCEAL
    }

    private static class SetResearch implements IUndoableAction {
        String key;
        String tab;
        SetType type;
        boolean flag;
        boolean applied = false;

        public SetResearch(String res, boolean f, SetType typ) {
            key = res;
            tab = ThaumcraftHelper.getResearchTab(key);
            type = typ;
            flag = f;
        }

        @Override
        public void apply() {
            ResearchItem research = ResearchCategories.researchCategories.get(tab).research.get(key);
            if(flag) {
                if (type == SetType.AUTO)
                    research.setAutoUnlock();
                else if (type == SetType.ROUND)
                    research.setRound();
                else if (type == SetType.SPIKE)
                    research.setSpecial();
                else if (type == SetType.SECONDARY)
                    research.setSecondary();
                else if (type == SetType.STUB)
                    research.setStub();
                else if (type == SetType.VIRTUAL)
                    research.setVirtual();
                else if (type == SetType.CONCEAL)
                    research.setConcealed();
                applied = true;
            }
            else {
                try {
                    Field target = null;
                    if (type == SetType.AUTO)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isAutoUnlock");
                    else if (type == SetType.ROUND)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isRound");
                    else if (type == SetType.SPIKE)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isSpecial");
                    else if (type == SetType.SECONDARY)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isSecondary");
                    else if (type == SetType.STUB)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isStub");
                    else if (type == SetType.VIRTUAL)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isVirtual");
                    else if (type == SetType.CONCEAL)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isConcealed");

                    if(target != null){
                        target.setAccessible(true);
                        target.setBoolean(research, false);
                        applied = true;
                    }
                }
                catch(Exception e){ e.printStackTrace();}
            }
        }

        @Override
        public String describe() {
            return "Setting tag for " + key;
        }

        @Override
        public boolean canUndo() {
            return applied;
        }

        @Override
        public void undo() {
            ResearchItem research = ResearchCategories.researchCategories.get(tab).research.get(key);
            if(!flag) {
                if (type == SetType.AUTO)
                    research.setAutoUnlock();
                else if (type == SetType.ROUND)
                    research.setRound();
                else if (type == SetType.SPIKE)
                    research.setSpecial();
                else if (type == SetType.SECONDARY)
                    research.setSecondary();
                else if (type == SetType.STUB)
                    research.setStub();
                else if (type == SetType.VIRTUAL)
                    research.setVirtual();
                else if (type == SetType.CONCEAL)
                    research.setConcealed();
            }
            else {
                try {
                    Field target = null;
                    if (type == SetType.AUTO)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isAutoUnlock");
                    else if (type == SetType.ROUND)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isRound");
                    else if (type == SetType.SPIKE)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isSpecial");
                    else if (type == SetType.SECONDARY)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isSecondary");
                    else if (type == SetType.STUB)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isStub");
                    else if (type == SetType.VIRTUAL)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isVirtual");
                    else if (type == SetType.CONCEAL)
                        target = Class.forName("thaumcraft.api.research.ResearchItem").getDeclaredField("isConcealed");

                    if(target != null){
                        target.setAccessible(true);
                        target.setBoolean(research, false);
                    }
                }
                catch(Exception e){ e.printStackTrace();}
            }
        }

        @Override
        public String describeUndo() {
            return "Reversing tag for " + key;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

    private static class MoveResearch implements IUndoableAction {
        String key;
        String newTab;
        int x;
        int y;
        String oldTab;
        int oldX;
        int oldY;
        boolean moved = false;

        public MoveResearch(String research, String destination, int ex, int wy) {
            key = research;
            oldTab = ThaumcraftHelper.getResearchTab(key);
            newTab = destination;
            x = ex;
            y = wy;
        }

        @Override
        public void apply() {
            if (oldTab != null) {
                ResearchItem research = ResearchCategories.researchCategories.get(oldTab).research.get(key);
                oldX = research.displayColumn;
                oldY = research.displayRow;
                try {
                    Class res = Class.forName("thaumcraft.api.research.ResearchItem");
                    Field ex = res.getField("displayColumn");
                    ex.setAccessible(true);
                    ex.setInt(research, x);
                    Field wy = res.getField("displayRow");
                    wy.setAccessible(true);
                    wy.setInt(research, y);
                    Field cat = res.getField("category");
                    cat.setAccessible(true);
                    cat.set(research, newTab);
                    ResearchCategories.researchCategories.get(oldTab).research.remove(key);
                    research.registerResearchItem();
                    moved = true;

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        public String describe() {
            return "Moving Research " + key + " to " + newTab;
        }

        @Override
        public boolean canUndo() {
            return moved;
        }

        @Override
        public void undo() {
            ResearchItem research = ResearchCategories.researchCategories.get(oldTab).research.get(key);
            try {
                Class res = Class.forName("thaumcraft.api.research.ResearchItem");
                Field ex = res.getField("displayColumn");
                ex.setAccessible(true);
                ex.setInt(research, oldX);
                Field wy = res.getField("displayRow");
                wy.setAccessible(true);
                wy.setInt(research, oldY);
                Field cat = res.getField("category");
                cat.setAccessible(true);
                cat.set(research, oldTab);
                ResearchCategories.researchCategories.get(newTab).research.remove(key);
                research.registerResearchItem();

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public String describeUndo() {
            return "Moving Research " + key + " back to " + oldTab;
        }

        @Override
        public String getOverrideKey() {
            return null;
        }

    }

}
