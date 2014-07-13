package modtweaker.mods.mekanism;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import minetweaker.api.item.IIngredient;
import modtweaker.helpers.ReflectionHelper;
import modtweaker.mods.mekanism.gas.IGasStack;

public class MekanismHelper {
    public static Map ELECTROLYTIC_SEPARATOR = null;
    public static Map CHEMICAL_CRYSTALLIZER = null;
    public static Map CHEMICAL_DISSOLUTION_CHAMBER = null;
    public static Map CHEMICAL_INFUSER = null;
    public static Map CHEMICAL_INJECTION_CHAMBER = null;
    public static Map CHEMICAL_OXIDIZER = null;
    public static Map CHEMICAL_WASHER = null;
    public static Map COMBINER = null;
    public static Map OSMIUM_COMPRESSOR = null;
    public static Map CRUSHER = null;
    public static Map ENRICHMENT_CHAMBER = null;
    public static Map METALLURGIC_INFUSER = null;
    public static Map PURIFICATION_CHAMBER = null;
    public static Map PRESSURIZED_REACTION_CHAMBER = null;
    public static Map PRECISION_SAWMILL = null;

    static {
        try {
            Class recipeClass = Class.forName("mekanism.common.recipe.RecipeHandler.Recipe");
            setField(recipeClass, "ELECTROLYTIC_SEPARATOR");
            setField(recipeClass, "CHEMICAL_CRYSTALLIZER");
            setField(recipeClass, "CHEMICAL_DISSOLUTION_CHAMBER");
            setField(recipeClass, "CHEMICAL_INFUSER");
            setField(recipeClass, "CHEMICAL_INJECTION_CHAMBER");
            setField(recipeClass, "CHEMICAL_OXIDIZER");
            setField(recipeClass, "CHEMICAL_WASHER");
            setField(recipeClass, "COMBINER");
            setField(recipeClass, "OSMIUM_COMPRESSOR");
            setField(recipeClass, "CRUSHER");
            setField(recipeClass, "ENRICHMENT_CHAMBER");
            setField(recipeClass, "METALLURGIC_INFUSER");
            setField(recipeClass, "PURIFICATION_CHAMBER");
            setField(recipeClass, "PRESSURIZED_REACTION_CHAMBER");
            setField(recipeClass, "PRECISION_SAWMILL");
        } catch (Exception e) {}
    }

    private static void setField(Class clazz, String field) throws Exception {
        Field f = clazz.getDeclaredField(field);
        Object o = f.get(null);
        Method m = clazz.getMethod("get");
        ReflectionHelper.setPrivateValue(MekanismHelper.class, field, m.invoke(null));
    }

    public static Map get(String str) {
        try {
            return (Map) MekanismHelper.class.getDeclaredField(str).get(null);
        } catch (Exception e) {
            return null;
        }
    }

    private MekanismHelper() {}

    public static GasStack toGas(IGasStack iStack) {
        if (iStack == null) {
            return null;
        } else return new GasStack(GasRegistry.getGas(iStack.getName()), iStack.getAmount());
    }

    public static GasStack[] toGases(IIngredient[] input) {
        return toGases((IGasStack[]) input);
    }

    public static GasStack[] toGases(IGasStack[] iStack) {
        GasStack[] stack = new GasStack[iStack.length];
        for (int i = 0; i < stack.length; i++)
            stack[i] = toGas(iStack[i]);
        return stack;
    }
}
