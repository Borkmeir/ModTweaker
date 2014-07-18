package modtweaker.vanilla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;
import modtweaker.helpers.ForgeHelper;
import modtweaker.helpers.ReflectionHelper;
import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class LootLogger implements ICommandFunction {
    public static final Comparator<WeightedRandomChestContent> COMPARATOR = new Compare();

    private static class Compare implements Comparator<WeightedRandomChestContent> {
        @Override
        public int compare(WeightedRandomChestContent o1, WeightedRandomChestContent o2) {
            String one = Item.itemRegistry.getNameForObject(o1.theItemId.getItem());
            String two = Item.itemRegistry.getNameForObject(o2.theItemId.getItem());
            return one.compareTo(two);
        }
    }

    @Override
    public void execute(String[] arguments, IPlayer player) {
        String chest = arguments != null && arguments.length == 1? arguments[0]: ChestGenHooks.DUNGEON_CHEST;
        ArrayList<WeightedRandomChestContent> loots =  ReflectionHelper.getObject(ForgeHelper.loot.get(chest), "contents");
        System.out.println("Loots for " + chest + ": " + loots.size());
        Collections.sort(loots, COMPARATOR);
        for (WeightedRandomChestContent loot : loots) {
            System.out.println("Loot " + Item.itemRegistry.getNameForObject(loot.theItemId.getItem()));
            MineTweakerAPI.logCommand("<" + Item.itemRegistry.getNameForObject(loot.theItemId.getItem()) + "> -- " + loot.theItemId.getDisplayName() 
                    + " -- (Min: " + loot.theMinimumChanceToGenerateItem + ", Max: " + loot.theMaximumChanceToGenerateItem + ", Weight: " + loot.itemWeight + ")");
        }

        if (player != null) {
            player.sendChat(MineTweakerImplementationAPI.platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
        }
    }
}
