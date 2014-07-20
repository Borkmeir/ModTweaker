package modtweaker.vanilla;

import static modtweaker.helpers.LogHelper.logPrinted;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import minetweaker.MineTweakerAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;
import modtweaker.helpers.ForgeHelper;
import modtweaker.helpers.ReflectionHelper;
import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class LootLogger implements ICommandFunction {
    public static ArrayList<String> list = null;
    public static final Comparator<WeightedRandomChestContent> COMPARATOR = new Compare();

    private static class Compare implements Comparator<WeightedRandomChestContent> {
        @Override
        public int compare(WeightedRandomChestContent o1, WeightedRandomChestContent o2) {
            String one = Item.itemRegistry.getNameForObject(o1.theItemId.getItem());
            String two = Item.itemRegistry.getNameForObject(o2.theItemId.getItem());
            return one.compareTo(two);
        }
    }

    static {
        list = new ArrayList();
        for (Map.Entry<String, ChestGenHooks> entry : ForgeHelper.loot.entrySet()) {
            list.add(entry.getKey());
        }

        Collections.sort(list);
    }

    @Override
    public void execute(String[] arguments, IPlayer player) {
        if (arguments != null && arguments.length == 1) {
            if (arguments[0].equals("names")) {
                printChests(player);
            } else {
                printChest(arguments[0], player);
                logPrinted(player);
            }
        } else {
            for (String str : list) {
                printChest(str, player);
            }

            logPrinted(player);
        }
    }

    private void printChests(IPlayer player) {
        System.out.println("List of Chest Names + : " + list.size());
        for (String str : list) {
            System.out.println("Chest " + str);
            MineTweakerAPI.logCommand("<" + str + ">");
        }

        logPrinted(player);
    }

    private void printChest(String chest, IPlayer player) {
        ArrayList<WeightedRandomChestContent> loots = ReflectionHelper.getObject(ForgeHelper.loot.get(chest), "contents");
        System.out.println("Loots for " + chest + ": " + loots.size());
        Collections.sort(loots, COMPARATOR);
        for (WeightedRandomChestContent loot : loots) {
            System.out.println("Loot " + Item.itemRegistry.getNameForObject(loot.theItemId.getItem()));
            MineTweakerAPI.logCommand("<" + chest + "> -- <" + Item.itemRegistry.getNameForObject(loot.theItemId.getItem()) + "> -- " + loot.theItemId.getDisplayName() + " -- (Min: " + loot.theMinimumChanceToGenerateItem + ", Max: " + loot.theMaximumChanceToGenerateItem + ", Weight: " + loot.itemWeight + ")");
        }
    }
}
