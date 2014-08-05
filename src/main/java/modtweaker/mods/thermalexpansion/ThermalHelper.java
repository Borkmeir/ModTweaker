package modtweaker.mods.thermalexpansion;

import cofh.util.inventory.ComparableItemStackSafe;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import thermalexpansion.util.crafting.SmelterManager.RecipeSmelter;

import static modtweaker.helpers.ReflectionHelper.getStaticObject;

public class ThermalHelper {
    public static Map crucible;
    public static Map smelter;
    public static Set smelterValid;
    public static Constructor smelterRecipe;

    static {
        try {
            crucible = getStaticObject(Class.forName("thermalexpansion.util.crafting.CrucibleManager"), "recipeMap");

            smelter = getStaticObject(Class.forName("thermalexpansion.util.crafting.SmelterManager"), "recipeMap");
            smelterValid = getStaticObject(Class.forName("thermalexpansion.util.crafting.SmelterManager"), "validationSet");
            smelterRecipe = RecipeSmelter.class.getDeclaredConstructor(ItemStack.class, ItemStack.class, ItemStack.class, ItemStack.class, Integer.TYPE, Integer.TYPE);
            smelterRecipe.setAccessible(true);
        }
        catch(Exception e){}
    }

    public static boolean removeCrucibleRecipe(ItemStack input){
        return crucible.remove(new ComparableItemStackSafe(input)) != null;
    }

    public static boolean removeSmelterRecipe(ItemStack input, ItemStack input2){
        ComparableItemStackSafe inp1 = new ComparableItemStackSafe(input);
        ComparableItemStackSafe inp2 = new ComparableItemStackSafe(input2);

        /*for(Object ob : smelter.keySet()){
            if(ob != null && ob instanceof List){
                List list = (List)ob;
                if(list.size() >= 2){
                    if((((ComparableItemStackSafe)(list.get(0))).isItemEqual(inp1) && ((ComparableItemStackSafe)(list.get(1))).isItemEqual(inp2)) ||
                            (((ComparableItemStackSafe)(list.get(1))).isItemEqual(inp1) && ((ComparableItemStackSafe)(list.get(0))).isItemEqual(inp2))){
                        smelter.remove(ob);
                        smelterValid.remove(inp1);
                        smelterValid.remove(inp2);
                        return true;
                    }
                }
            }
        }*/

        /*for(Object ob : smelter.keySet()){
            if(smelter.get(ob) != null && smelter.get(ob) instanceof RecipeSmelter){
                RecipeSmelter recipe = (RecipeSmelter)smelter.get(ob);
                if((input.isItemEqual(recipe.getPrimaryInput()) && input2.isItemEqual(recipe.getSecondaryInput()))||
                        (input2.isItemEqual(recipe.getPrimaryInput()) && input.isItemEqual(recipe.getSecondaryInput()))){
                    smelter.remove(ob);
                    smelterValid.remove(inp1);
                    smelterValid.remove(inp2);
                    return true;
                }
            }
        }*/

        if(smelter.remove(Arrays.asList(new ComparableItemStackSafe[]{inp1, inp2})) != null){
            //smelterValid.remove(inp1);
            //smelterValid.remove(inp2);
            return true;
        }
        else if(smelter.remove(Arrays.asList(new ComparableItemStackSafe[]{inp2, inp1})) != null){
            //smelterValid.remove(inp1);
            //smelterValid.remove(inp2);
            return true;
        }
        else
            return false;
    }

    public static RecipeSmelter makeSmelterRecipe(ItemStack inp1, ItemStack inp2, ItemStack out1, ItemStack out2, int chance, int energy){
        try {
            return (RecipeSmelter)smelterRecipe.newInstance(inp1, inp2, out1, out2, chance, energy);
        }
        catch(Exception e){}
        return null;
    }
}
