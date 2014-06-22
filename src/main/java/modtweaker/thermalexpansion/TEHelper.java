package modtweaker.thermalexpansion;

import java.lang.reflect.Constructor;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.minetweaker.api.TweakerExecuteException;
import thermalexpansion.util.crafting.CrucibleManager.RecipeCrucible;
import thermalexpansion.util.crafting.FurnaceManager.RecipeFurnace;
import thermalexpansion.util.crafting.PulverizerManager.RecipePulverizer;
import thermalexpansion.util.crafting.SawmillManager.RecipeSawmill;
import thermalexpansion.util.crafting.SmelterManager.RecipeSmelter;
import thermalexpansion.util.crafting.TransposerManager.RecipeTransposer;

public class TEHelper {
	public static RecipeCrucible getCrucibleRecipe(ItemStack input, FluidStack output, int energy) {
		try {
			Constructor constructor = RecipeCrucible.class.getDeclaredConstructor(ItemStack.class, FluidStack.class, int.class);
			constructor.setAccessible(true);
			return (RecipeCrucible) constructor.newInstance(input, output, energy);
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new TweakerExecuteException("Failed to instantiate RecipeCrucible");
		}
	}

	public static RecipeFurnace getFurnaceRecipe(ItemStack input, ItemStack output, int energy) {
		try {
			Constructor constructor = RecipeFurnace.class.getDeclaredConstructor(ItemStack.class, ItemStack.class, int.class);
			constructor.setAccessible(true);
			return (RecipeFurnace) constructor.newInstance(input, output, energy);
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new TweakerExecuteException("Failed to instantiate RecipeFurnace");
		}
	}

	public static RecipePulverizer getPulverizerRecipe(ItemStack input, ItemStack output, int energy, ItemStack secondary, int chance) {
		try {
			Constructor constructor = RecipePulverizer.class.getDeclaredConstructor(ItemStack.class, ItemStack.class, ItemStack.class, int.class, int.class);
			constructor.setAccessible(true);
			return (RecipePulverizer) constructor.newInstance(input, output, secondary, chance, energy);
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new TweakerExecuteException("Failed to instantiate RecipePulverizer");
		}
	}

	public static RecipeSawmill getSawmillRecipe(ItemStack input, ItemStack output, int energy, ItemStack secondary, int chance) {
		try {
			Constructor constructor = RecipeSawmill.class.getDeclaredConstructor(ItemStack.class, ItemStack.class, ItemStack.class, int.class, int.class);
			constructor.setAccessible(true);
			return (RecipeSawmill) constructor.newInstance(input, output, secondary, chance, energy);
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new TweakerExecuteException("Failed to instantiate RecipeSawmill");
		}
	}

	public static RecipeSmelter getSmelterRecipe(ItemStack input, ItemStack input2, ItemStack output, int energy, ItemStack secondary, int chance) {
		try {
			Constructor constructor = RecipeSmelter.class.getDeclaredConstructor(ItemStack.class, ItemStack.class, ItemStack.class, ItemStack.class, int.class, int.class);
			constructor.setAccessible(true);
			return (RecipeSmelter) constructor.newInstance(input, input2, output, secondary, chance, energy);
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new TweakerExecuteException("Failed to instantiate RecipeSmelter");
		}
	}
	
	public static RecipeTransposer getTransposerRecipe(ItemStack input, ItemStack output, FluidStack fluid, int energy, int chance) {
		try {
			Constructor constructor = RecipeTransposer.class.getDeclaredConstructor(ItemStack.class, ItemStack.class, FluidStack.class, int.class, int.class);
			constructor.setAccessible(true);
			return (RecipeTransposer) constructor.newInstance(input, output, fluid, energy, chance);
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new TweakerExecuteException("Failed to instantiate RecipeTransposer");
		}
	}
}
