package modtweaker.vanilla;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;
import modtweaker.helpers.ForgeHelper;
import modtweaker.helpers.ReflectionHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;

public class SeedLogger implements ICommandFunction {
    public static final Comparator<WeightedRandom.Item> COMPARATOR = new Compare();

    private static class Compare implements Comparator<WeightedRandom.Item> {
        @Override
        public int compare(WeightedRandom.Item o1, WeightedRandom.Item o2) {
            String one = Item.itemRegistry.getNameForObject(((ItemStack) ReflectionHelper.getObject(o1, "seed")).getItem());
            String two = Item.itemRegistry.getNameForObject(((ItemStack) ReflectionHelper.getObject(o2, "seed")).getItem());
            return one.compareTo(two);
        }
    }

    @Override
    public void execute(String[] arguments, IPlayer player) {
        List<WeightedRandom.Item> seeds =  ForgeHelper.seeds;
        System.out.println("Seeds: " + seeds.size());
        Collections.sort(seeds, COMPARATOR);
        for (WeightedRandom.Item seed : seeds) {
            ItemStack stack = ((ItemStack) ReflectionHelper.getObject(seed, "seed"));
            System.out.println("Seed " + Item.itemRegistry.getNameForObject(stack.getItem()));
            MineTweakerAPI.logCommand("<" + Item.itemRegistry.getNameForObject(stack.getItem()) + "> -- " + stack.getDisplayName() 
                    + " -- (Weight: " + seed.itemWeight + ")");
        }

        if (player != null) {
            player.sendChat(MineTweakerImplementationAPI.platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
        }
    }
}
