package modtweaker.mods.vanilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ToolTipRegistry {
    public static HashMap<Item, ToolTipEffect> tooltips = new HashMap();

    public static class ToolTipEffect {
        int damage;
        NBTTagCompound nbt;
        ArrayList<String> tooltip;

        boolean useDamage;
        boolean useNBT;
        boolean displayAdvanced;
        boolean localized;

        public ToolTipEffect(ItemStack stack, String[] lines, boolean useDamage, boolean useNBT, boolean displayAdvanced, boolean localized) {
            if (useDamage) damage = stack.getItemDamage();
            if (useNBT) nbt = stack.getTagCompound();
            tooltip = new ArrayList();
            for (String s : lines) {
                tooltip.add(s);
            }
            
            this.displayAdvanced = displayAdvanced;
            this.localized = localized;
        }

        public void apply(int damage, NBTTagCompound nbt, List<String> tooltip, boolean advanced) {
            if (displayAdvanced == advanced) {
                if (!useDamage || (useDamage && damage == this.damage)) {
                    if (!useNBT || (useNBT && nbt != null && this.nbt != null && nbt.equals(this.nbt))) {
                        if(!localized) {
                            tooltip.addAll(this.tooltip);
                        } else {
                            for(String s: this.tooltip) {
                                String g = StatCollector.translateToLocal(s);
                                tooltip.add(g);
                            }
                        }
                    }
                }
            }
        }
    }
}
