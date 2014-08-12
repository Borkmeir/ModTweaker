package modtweaker.mods.thermalexpansion.handlers;

import cofh.util.inventory.ComparableItemStackSafe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.thermalexpansion.ThermalHelper;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thermalexpansion.util.crafting.SmelterManager;
import thermalexpansion.util.crafting.SmelterManager.RecipeSmelter;

import java.util.Arrays;
import java.util.List;

import static modtweaker.helpers.InputHelper.toStack;
import static modtweaker.mods.thermalexpansion.ThermalHelper.removeSmelterRecipe;
import static modtweaker.mods.thermalexpansion.ThermalHelper.makeSmelterRecipe;

@ZenClass("mods.thermalexpansion.Smelter")
public class Smelter {
    @ZenMethod
    public static void addRecipe(int energy, IItemStack input, IItemStack input2, IItemStack output) {
        MineTweakerAPI.tweaker.apply(new Add(energy, toStack(input), toStack(input2), toStack(output), null, 0));
    }

    @ZenMethod
    public static void addRecipe(int energy, IItemStack input, IItemStack input2, IItemStack output, IItemStack output2, int chance) {
        MineTweakerAPI.tweaker.apply(new Add(energy, toStack(input), toStack(input2), toStack(output), toStack(output2), chance));
    }

    private static class Add implements IUndoableAction {
        ItemStack input;
        ItemStack input2;
        ItemStack output;
        ItemStack secondary;
        int secondaryChance;
        int energy;
        boolean applied = false;

        public Add(int rf, ItemStack inp, ItemStack inp2, ItemStack out, ItemStack out2, int chance) {
            energy = rf;
            input = inp;
            input2 = inp2;
            output = out;
            secondary = out2;
            secondaryChance = chance;
        }

        public void apply(){
            applied = SmelterManager.addRecipe(energy, input, input2, output, secondary, secondaryChance);
        }

        public boolean canUndo () {
            //return input != null && input2 != null && applied;
            return false;
        }

        public String describe () {
            return "Adding Induction Smelter Recipe using " + input.getDisplayName() + " and " + input2.getDisplayName();
        }

        public void undo(){
            removeSmelterRecipe(input, input2);
        }

        public String describeUndo () {
            return "Removing Induction Smelter Recipe using " + input.getDisplayName() + " and " + input2.getDisplayName();
        }

        public Object getOverrideKey(){
            return null;
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*@ZenMethod
    public static void removeRecipe(IItemStack input, IItemStack input2) {
        MineTweakerAPI.tweaker.apply(new Remove(toStack(input), toStack(input2)));
    }

    private static class Remove implements IUndoableAction {
        ItemStack input;
        ItemStack input2;
        RecipeSmelter removed;
        boolean actuallyRemoved;

        public Remove(ItemStack inp, ItemStack inp2) {
            input = inp;
            input2 = inp2;
        }

        public void apply(){
            removed = SmelterManager.getRecipe(input, input2);
            actuallyRemoved = removeSmelterRecipe(input, input2);
        }

        public boolean canUndo () {
            //return removed != null && actuallyRemoved;
            return false;
        }

        public String describe () {
            return "Removing Induction Smelter Recipe using " + input.getDisplayName();
        }

        public void undo(){
            SmelterManager.addRecipe(removed.getEnergy(), removed.getPrimaryInput(), removed.getSecondaryInput(), removed.getPrimaryOutput(), removed.getSecondaryOutput(), removed.getSecondaryOutputChance());
        }

        public String describeUndo () {
            return "Restoring Induction Smelter Recipe using " + input.getDisplayName();
        }

        public Object getOverrideKey(){
            return null;
        }

    }*/

}
