package modtweaker.mods.botania.handlers;


import static modtweaker.helpers.InputHelper.toStack;
import static modtweaker.helpers.InputHelper.toStacks;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.thaumcraft.ThaumcraftHelper;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import vazkii.botania.api.BotaniaAPI;

@ZenClass("mods.botania.Orechid")
public class Orechid {
    @ZenMethod
    public static void addOre(String oreDict, int weight) {
        MineTweakerAPI.tweaker.apply(new Add(oreDict, weight));
    }

    private static class Add implements IUndoableAction {
        String oreDict;
        int weight;

        public Add(String ore, int prop){
            oreDict = ore;
            weight = prop;
        }

        @Override
        public void apply() {

            BotaniaAPI.addOreWeight(oreDict, weight);
        }

        @Override
        public String describe(){
            return "Adding Orechid Ore Weight: " + oreDict + ":" + weight;
        }

        @Override
        public boolean canUndo(){
            return oreDict != null;
        }

        @Override
        public void undo(){
            BotaniaAPI.oreWeights.remove(oreDict);
        }

        @Override
        public String describeUndo(){
            return "Removing Orechid Ore: " + oreDict;
        }

        @Override
        public String getOverrideKey(){
            return null;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeOre(String oreDict) {
        MineTweakerAPI.tweaker.apply(new Remove(oreDict));
    }

    private static class Remove implements IUndoableAction {
        String oreDict;
        int weight;

        public Remove(String ore){
            oreDict = ore;
        }

        @Override
        public void apply() {
            weight = BotaniaAPI.getOreWeight(oreDict);
            BotaniaAPI.oreWeights.remove(oreDict);
        }

        @Override
        public String describe(){
            return "Removing Orechid Ore: " + oreDict;
        }

        @Override
        public boolean canUndo(){
            return weight > 0;
        }

        @Override
        public void undo(){
            BotaniaAPI.addOreWeight(oreDict, weight);
        }

        @Override
        public String describeUndo(){
            return "Restoring Orechid Ore Weight: " + oreDict + ":" + weight;
        }

        @Override
        public String getOverrideKey(){
            return null;
        }
    }
}
