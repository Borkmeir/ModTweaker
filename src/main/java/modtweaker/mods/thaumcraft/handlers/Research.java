package modtweaker.mods.thaumcraft.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.thaumcraft.ThaumcraftHelper;
import modtweaker.mods.thaumcraft.research.AddPage;
import modtweaker.mods.thaumcraft.research.AddPrereq;
import modtweaker.mods.thaumcraft.research.AddResearch;
import modtweaker.mods.thaumcraft.research.AddSibling;
import modtweaker.mods.thaumcraft.research.AddTab;
import modtweaker.mods.thaumcraft.research.ClearPages;
import modtweaker.mods.thaumcraft.research.ClearPrereqs;
import modtweaker.mods.thaumcraft.research.ClearSiblings;
import modtweaker.mods.thaumcraft.research.Difficulty;
import modtweaker.mods.thaumcraft.research.MoveResearch;
import modtweaker.mods.thaumcraft.research.OrphanResearch;
import modtweaker.mods.thaumcraft.research.RefreshResearch;
import modtweaker.mods.thaumcraft.research.RemoveResearch;
import modtweaker.mods.thaumcraft.research.RemoveTab;
import modtweaker.mods.thaumcraft.research.SetAspects;
import modtweaker.mods.thaumcraft.research.SetResearch;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage.PageType;

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

    @ZenMethod
    public static void addResearch(String key, String tab, @Optional String aspects, int x, int y, int difficulty, String domain, String path) {
        MineTweakerAPI.tweaker.apply(new AddResearch(new ResearchItem(key, tab, ThaumcraftHelper.parseAspects(aspects), x, y, difficulty, new ResourceLocation(domain, path))));
    }

    @ZenMethod
    public static void addResearch(String key, String tab, @Optional String aspects, int x, int y, int difficulty, IItemStack item) {
        MineTweakerAPI.tweaker.apply(new AddResearch(new ResearchItem(key, tab, ThaumcraftHelper.parseAspects(aspects), x, y, difficulty, toStack(item))));
    }

    @ZenMethod
    public static void addPage(String key, String unlocalized) {
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.TEXT, unlocalized));
    }

    @ZenMethod
    public static void addCraftingPage(String key, IItemStack item) {
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.NORMAL_CRAFTING, toStack(item)));
    }

    @ZenMethod
    public static void addCruciblePage(String key, IItemStack item) {
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.CRUCIBLE_CRAFTING, toStack(item)));
    }

    @ZenMethod
    public static void addArcanePage(String key, IItemStack item) {
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.ARCANE_CRAFTING, toStack(item)));
    }

    @ZenMethod
    public static void addInfusionPage(String key, IItemStack item) {
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.INFUSION_CRAFTING, toStack(item)));
    }

    @ZenMethod
    public static void addEnchantmentPage(String key, int i) {
        MineTweakerAPI.tweaker.apply(new AddPage(key, PageType.INFUSION_ENCHANTMENT, Enchantment.enchantmentsList[i]));
    }

    @ZenMethod
    public static void clearPages(String key) {
        MineTweakerAPI.tweaker.apply(new ClearPages(key));
    }

    @ZenMethod
    public static void addPrereq(String key, String req, @Optional boolean hidden) {
        MineTweakerAPI.tweaker.apply(new AddPrereq(key, req, hidden));
    }

    @ZenMethod
    public static void clearPrereqs(String key) {
        MineTweakerAPI.tweaker.apply(new ClearPrereqs(key));
    }

    @ZenMethod
    public static void addSibling(String key, String sibling) {
        MineTweakerAPI.tweaker.apply(new AddSibling(key, sibling));
    }

    @ZenMethod
    public static void clearSiblings(String key) {
        MineTweakerAPI.tweaker.apply(new ClearSiblings(key));
    }

    @ZenMethod
    public static void setRound(String key, boolean flag) {
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.ROUND));
    }

    @ZenMethod
    public static void setSpikey(String key, boolean flag) {
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.SPIKE));
    }

    @ZenMethod
    public static void setStub(String key, boolean flag) {
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.STUB));
    }

    @ZenMethod
    public static void setSecondary(String key, boolean flag) {
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.SECONDARY));
    }

    @ZenMethod
    public static void setVirtual(String key, boolean flag) {
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.VIRTUAL));
    }

    @ZenMethod
    public static void setAutoUnlock(String key, boolean flag) {
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.AUTO));
    }

    @ZenMethod
    public static void setConcealed(String key, boolean flag) {
        MineTweakerAPI.tweaker.apply(new SetResearch(key, flag, SetType.CONCEAL));
    }

    @ZenMethod
    public static void setAspects(String key, String aspects) {
        MineTweakerAPI.tweaker.apply(new SetAspects(key, ThaumcraftHelper.parseAspects(aspects)));
    }

    @ZenMethod
    public static void setComplexity(String key, int complexity) {
        MineTweakerAPI.tweaker.apply(new Difficulty(key, complexity));
    }

    @ZenMethod
    public static void refreshResearchRecipe(String key) {
        MineTweakerAPI.tweaker.apply(new RefreshResearch(key));
    }

    @ZenMethod
    public static void moveResearch(String key, String destination, int x, int y) {
        MineTweakerAPI.tweaker.apply(new MoveResearch(key, destination, x, y));
    }

    public static enum SetType {
        AUTO, ROUND, SPIKE, SECONDARY, STUB, VIRTUAL, CONCEAL
    }
}
