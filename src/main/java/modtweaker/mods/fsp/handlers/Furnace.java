package modtweaker.mods.fsp.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.util.BaseMapAddition;
import modtweaker.util.BaseMapRemoval;

import org.apache.commons.lang3.tuple.MutablePair;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import flaxbeard.steamcraft.api.SteamcraftRegistry;

@ZenClass("mods.fsp.Furnace")
public class Furnace {
    @ZenMethod
    public static void addSteamFood(IItemStack input, IItemStack output) {
        MineTweakerAPI.apply(new AddSteamFood(MutablePair.of(toStack(input).getItem(), toStack(input).getItemDamage()), MutablePair.of(toStack(output).getItem(), toStack(output).getItemDamage())));
    }

    private static class AddSteamFood extends BaseMapAddition {
        public AddSteamFood(MutablePair key, MutablePair recipe) {
            super("FSP Furnace - Steam Food", SteamcraftRegistry.steamedFoods, key, recipe);
        }
    }

    @ZenMethod
    public static void removeSteamFood(IItemStack input) {
        MineTweakerAPI.apply(new RemoveSteamFood(MutablePair.of(toStack(input).getItem(), toStack(input).getItemDamage())));
    }

    private static class RemoveSteamFood extends BaseMapRemoval {
        public RemoveSteamFood(MutablePair key) {
            super("FSP Furnace - Steam Food", SteamcraftRegistry.steamedFoods, key);
        }
    }
}
