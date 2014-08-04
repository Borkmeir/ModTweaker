package naruto1310.extendedWorkbench.item;

import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EWRecipes
{
	public static class mapUpdate implements IRecipe
	{
		@Override
		public boolean matches(InventoryCrafting inv, World world)
		{
			ItemStack map = null;
			boolean d = false;
			
			for(int i = 0; i < inv.getSizeInventory(); i++)
			{
				ItemStack stack = inv.getStackInSlot(i);
				if(stack == null)
					continue;
				if(stack.getItem() instanceof ItemExtendedMap)
				{
					if(map == null)
					{
						map = stack;
						continue;
					}
					return false;
				}
				
				if(stack.getItem() == Items.diamond)
				{
					if(!d)
					{
						d = true;
						continue;
					}
					return false;
				}
				return false;
			}
			
			if(map != null && d)
				return true;
			
			return false;
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv)
		{
			ItemStack map = null;
			boolean d = false;
			
			for(int i = 0; i < inv.getSizeInventory(); i++)
			{
				ItemStack stack = inv.getStackInSlot(i);
				if(stack == null)
					continue;
				if(stack.getItem() instanceof ItemExtendedMap)
				{
					if(map == null)
					{
						map = stack;
						continue;
					}
					return null;
				}
				
				if(stack.getItem() == Items.diamond)
				{
					if(!d)
					{
						d = true;
						continue;
					}
					return null;
				}
				return null;
			}
			
			if(map != null && d)
			{
				ItemStack stack = map.copy();
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setBoolean("autoUpdate", true);
				stack.setTagCompound(nbt);
				return stack;
			}
			
			return null;
		}

		@Override
		public int getRecipeSize()
		{
			return 2;
		}

		@Override
		public ItemStack getRecipeOutput()
		{
			/*
			ItemStack stack = new ItemStack(mod_ExtendedWorkbench.extendedMap, 1);
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setBoolean("autoUpdate", true);
			stack.setTagCompound(nbt);
			return stack;
			//	*/
			return null;
		}
		
	}

	public static class RecipeExtendedMapCloning implements IRecipe
	{
	    @Override
		public boolean matches(InventoryCrafting inv, World world)
	    {
	        int clones = 0;
	        ItemStack map = null;

	        for (int i = 0; i < inv.getSizeInventory(); ++i)
	        {
	            ItemStack stack = inv.getStackInSlot(i);

	            if(stack != null)
	            {
	                if(stack.getItem() == mod_ExtendedWorkbench.extendedMap)
	                {
	                    if(map != null)
	                        return false;

	                    map = stack;
	                }
	                else
	                {
	                	if(stack.getItem() != mod_ExtendedWorkbench.extendedMapEmpty)
	                        return false;
	                    clones++;
	                }
	            }
	        }

	        return map != null && clones > 0;
	    }

	    @Override
		public ItemStack getCraftingResult(InventoryCrafting inv)
	    {
	        int clones = 0;
	        ItemStack map = null;

	        for (int i = 0; i < inv.getSizeInventory(); ++i)
	        {
	            ItemStack stack = inv.getStackInSlot(i);

	            if(stack != null)
	            {
	                if(stack.getItem() == mod_ExtendedWorkbench.extendedMap)
	                {
	                    if(map != null)
	                        return null;

	                    map = stack;
	                }
	                else
	                {
	                	if(stack.getItem() != mod_ExtendedWorkbench.extendedMapEmpty)
	                        return null;
	                    clones++;
	                }
	            }
	        }

	        if(map != null && clones >= 1)
	        {
	            ItemStack newStack = new ItemStack(mod_ExtendedWorkbench.extendedMap, clones + 1, map.getItemDamage());

	            //if(map.hasDisplayName())
	                //newStack.func_151001_c(map.getDisplayName());
	            if(map.hasTagCompound())
	            	newStack.setTagCompound((NBTTagCompound)map.getTagCompound().copy());

	            return newStack;
	        }
	        
	        return null;
	    }

	    @Override
		public int getRecipeSize()
	    {
	        return 9;
	    }

	    @Override
		public ItemStack getRecipeOutput()
	    {
	        return null;
	    }
	}
}
