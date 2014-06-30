package modtweaker.tconstruct;

import static modtweaker.util.Helper.FluidStack;
import static modtweaker.util.Helper.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import modtweaker.util.BaseDescriptionAddition;
import modtweaker.util.BaseDescriptionRemoval;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.library.crafting.AlloyMix;

@ZenClass("mods.tconstruct.Smeltery")
public class Smeltery {
										  /*****************************************/
	/********************************************** TConstruct Alloy Recipes **********************************************/
										  /*****************************************/
	
	//Adding a TConstruct Alloy recipe
	@ZenMethod
	public static void addAlloy(@NotNull ILiquidStack output, @NotNull ILiquidStack[] input) {
		MineTweakerAPI.tweaker.apply(new AddAlloy(new AlloyMix(FluidStack(output), new ArrayList<FluidStack>(Arrays.asList(FluidStack(input))))));
	}
	
	//Passes the list to the base list implementation, and adds the recipe
	private static class AddAlloy extends BaseListAddition {
		public AddAlloy(AlloyMix recipe) {
			super("Smeltery - Alloy", TConstructHacks.alloys, recipe);
		}

		@Override
		public String getRecipeInfo() {
			return ((AlloyMix)recipe).result.getFluid().getName();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Removing a TConstruct Alloy recipe
	@ZenMethod
	public static void removeAlloy(@NotNull ILiquidStack output) {
		MineTweakerAPI.tweaker.apply(new RemoveAlloy((FluidStack(output))));
	}
	
	//Removes a recipe, apply is never the same for anything, so will always need to override it
	private static class RemoveAlloy extends BaseListRemoval {
		public RemoveAlloy(FluidStack output) {
			super("Smeltery - Alloy", TConstructHacks.alloys, output);
		}

		//Loops through the registry, to find the item that matches, saves that recipe then removes it
		@Override
		public void apply() {
			for(AlloyMix r: TConstructHacks.alloys) {
				if(r.result != null && r.result.isFluidStackIdentical(fluid)) {
					recipe = r; break;
				}
			}
			
			TConstructHacks.alloys.remove(recipe);
		}
		
		@Override
		public String getRecipeInfo() {
			return fluid.getFluid().getName();
		}
	}
											/*****************************************/
	/********************************************** TConstruct Melting Recipes **********************************************/
											/*****************************************/
	
	//Adding a TConstruct Melting recipe
	@ZenMethod
	public static void addMelting(@NotNull IItemStack input, @NotNull ILiquidStack output, @NotNull int temp, @Optional IItemStack block) {
		if(block == null) block = input;
		if(!(ItemStack(block).getItem() instanceof ItemBlock)) MineTweakerAPI.logger.logError("Item must be a block, or you must specify a block to render as when adding a TConstruct Melting recipe");
		else {
			Block theBlock = Block.getBlockFromItem(ItemStack(block).getItem());
			int theMeta = ItemStack(block).getItemDamage();
			MineTweakerAPI.tweaker.apply(new AddMelting(ItemStack(input), theBlock, theMeta, temp, FluidStack(output)));
		}
	}
		
	//Takes all the variables and saves them in place
	private static class AddMelting extends BaseDescriptionAddition {
		private final ItemStack input;
		private final Block block;
		private final int meta;
		private final int temp;
		private final FluidStack output;
		
		public AddMelting(ItemStack input, Block block, int meta, int temp, FluidStack output) {
			super("Smeltery - Melting");
			this.input = input;
			this.block = block;
			this.meta = meta;
			this.temp = temp;
			this.output = output;
		}
		
		//Adds the Melting recipe
		@Override
		public void apply() {
			tconstruct.library.crafting.Smeltery.instance.addMelting(input, block, meta, temp, output);
		}

		//Removes the Melting recipe from the hashmaps
		@Override
		public void undo() {
			TConstructHacks.smeltingList.remove(Arrays.asList(input.getItem().hashCode(), input.getItemDamage()));
			TConstructHacks.temperatureList.remove(Arrays.asList(input.getItem().hashCode(), input.getItemDamage()));
			TConstructHacks.renderIndex.remove(Arrays.asList(input.getItem().hashCode(), input.getItemDamage()));
		}

		@Override
		public String getRecipeInfo() {
			return input.getDisplayName();
		}
	}
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	//Removing a TConstruct Melting recipe
	@ZenMethod
	public static void removeMelting(@NotNull IItemStack input) {
		MineTweakerAPI.tweaker.apply(new RemoveMelting((ItemStack(input))));
	}
		
	//Removes a recipe, apply is never the same for anything, so will always need to override it
	private static class RemoveMelting extends BaseDescriptionRemoval {
		private final ItemStack input;
		private FluidStack fluid;
		private Integer temp;
		private ItemStack renderer;
		
		public RemoveMelting(ItemStack input) {
			super("Smeltery - Melting");
			this.input = input;
		}

		//Gets the current values, and saves, them removes them from the hashmaps
		@Override
		public void apply() {
			fluid = TConstructHacks.smeltingList.get(Arrays.asList(input.getItem().hashCode(), input.getItemDamage()));
			temp = TConstructHacks.temperatureList.get(Arrays.asList(input.getItem().hashCode(), input.getItemDamage()));
			renderer = TConstructHacks.renderIndex.get(Arrays.asList(input.getItem().hashCode(), input.getItemDamage()));
			TConstructHacks.smeltingList.remove(Arrays.asList(input.getItem().hashCode(), input.getItemDamage()));
			TConstructHacks.temperatureList.remove(Arrays.asList(input.getItem().hashCode(), input.getItemDamage()));
			TConstructHacks.renderIndex.remove(Arrays.asList(input.getItem().hashCode(), input.getItemDamage()));
		}
		
		//Readds the Melting recipe
		@Override
		public void undo() {
			tconstruct.library.crafting.Smeltery.instance.addMelting(input, Block.getBlockFromItem(renderer.getItem()), renderer.getItemDamage(), temp, fluid);
		}
			
		@Override
		public String getRecipeInfo() {
			return input.getDisplayName();
		}
	}
}

