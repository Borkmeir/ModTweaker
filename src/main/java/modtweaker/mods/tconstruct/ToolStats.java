package modtweaker.mods.tconstruct;

import java.util.Map;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import modtweaker.helpers.ReflectionHelper;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;

@ZenClass("mods.tconstruct.ToolStats")
public class ToolStats {
    @ZenMethod
    public static void set(String material, @Optional String name, int level, int durability, int speed, int damage, float handle, int reinforced, float stonebound, String style, String ability) {
        if (name == null) name = material + " ";
        MineTweakerAPI.tweaker.apply(new SetVar(material, "", new ToolMaterial(material, name, level, durability, speed, damage, handle, reinforced, stonebound, style, ability)));
    }

    @ZenMethod
    public static void setDisplayName(String material, String name) {
        MineTweakerAPI.tweaker.apply(new SetVar(material, "displayName", name));
    }

    @ZenMethod
    public static void setHarvestLevel(String material, int value) {
        MineTweakerAPI.tweaker.apply(new SetVar(material, "harvestLevel", value));
    }

    @ZenMethod
    public static void setDurability(String material, int value) {
        MineTweakerAPI.tweaker.apply(new SetVar(material, "durability", value));
    }

    @ZenMethod
    public static void setSpeed(String material, int value) {
        MineTweakerAPI.tweaker.apply(new SetVar(material, "miningspeed", value));
    }

    @ZenMethod
    public static void setDamage(String material, int value) {
        MineTweakerAPI.tweaker.apply(new SetVar(material, "attack", value));
    }

    @ZenMethod
    public static void setHandleModifier(String material, double value) {
        MineTweakerAPI.tweaker.apply(new SetVar(material, "handle", (float) value));
    }

    @ZenMethod
    public static void setReinforcedLevel(String material, int value) {
        MineTweakerAPI.tweaker.apply(new SetVar(material, "reinforced", value));
    }

    @ZenMethod
    public static void setStoneboundLevel(String material, double value) {
        MineTweakerAPI.tweaker.apply(new SetVar(material, "stonebound", (float) value));
    }

    @ZenMethod
    public static void setStyle(String material, String name) {
        MineTweakerAPI.tweaker.apply(new SetVar(material, "tipStyle", name));
    }

    @ZenMethod
    public static void setAbility(String material, String name) {
        MineTweakerAPI.tweaker.apply(new SetVar(material, "ability", name));
    }

    //Sets various variables with reflection, making my life partially easier :D
    private static class SetVar implements IUndoableAction {
        private int id;
        private ToolMaterial old;
        private ToolMaterial fresh;
        private final String material;
        private final String field;
        private final Object value;

        public SetVar(String material, String field, Object value) {
            this.material = material;
            this.field = field;
            this.value = value;
        }

        private int getIDFromString(String material) {
            for (Map.Entry<Integer, ToolMaterial> entry : TConstructRegistry.toolMaterials.entrySet()) {
                if (entry.getValue().materialName.equals(material)) {
                    return entry.getKey();
                }
            }

            return -1;
        }

        @Override
        public void apply() {
            old = TConstructRegistry.toolMaterialStrings.get(material);
            id = getIDFromString(material);
            if (id != -1) {
                if (value instanceof ToolMaterial) {
                    fresh = (ToolMaterial) value;
                } else {
                    ReflectionHelper.setPrivateValue(ToolMaterial.class, fresh, field, value);
                    fresh = new ToolMaterial(old.materialName, old.displayName, old.harvestLevel, old.durability, old.miningspeed, old.attack, old.handleModifier, old.reinforced, old.stonebound, old.tipStyle, old.ability);
                }

                TConstructRegistry.toolMaterials.put(id, fresh);
                TConstructRegistry.toolMaterialStrings.put(material, fresh);
            }
        }

        @Override
        public boolean canUndo() {
            return TConstructRegistry.toolMaterialStrings != null && id != -1;
        }

        @Override
        public void undo() {
            TConstructRegistry.toolMaterials.put(id, old);
            TConstructRegistry.toolMaterialStrings.put(material, old);
        }

        @Override
        public String describe() {
            return "Changing material stats field : + " + field + " for " + material;
        }

        @Override
        public String describeUndo() {
            return "Undoing change of material stats field : + " + field + " for " + material;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
