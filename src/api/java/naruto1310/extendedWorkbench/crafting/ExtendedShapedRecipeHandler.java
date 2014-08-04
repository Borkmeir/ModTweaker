package naruto1310.extendedWorkbench.crafting;

import java.util.ArrayList;
import java.util.List;

import naruto1310.extendedWorkbench.block.GuiExtended;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.ShapedRecipeHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class ExtendedShapedRecipeHandler extends ShapedRecipeHandler
{
	
	public class CachedExtendedRecipe extends CachedRecipe
	{
		public CachedExtendedRecipe(ExtendedShapedRecipes recipe)
		{
			this.result = new PositionedStack(recipe.getRecipeOutput(), 119, 52);
			this.ingredients = new ArrayList<PositionedStack>();
			setIngredients(recipe);
		}
		
		public CachedExtendedRecipe(int width, int height, Object[] items, ItemStack out)
		{
			this.result = new PositionedStack(out, 119, 52);
			this.ingredients = new ArrayList<PositionedStack>();
			setIngredients(width, height, items);
		}
		
		public void setIngredients(int width, int height, Object[] items)
		{
			for(int x = 0; x < width; x++)
			{
				for(int y = 0; y < height; y++)
				{
					if(items[y * width + x] == null)
					{
						continue;
					}
					PositionedStack stack = new PositionedStack(items[y * width + x], 25 + x * 18, 6 + y * 18);
					stack.setMaxSize(1);
					this.ingredients.add(stack);
				}
			}
		}
		
		public void setIngredients(ExtendedShapedRecipes recipe)
		{
			int width;
			int height;
			ItemStack[] items;
			try
			{
				width = ((Integer)ObfuscationReflectionHelper.getPrivateValue(ExtendedShapedRecipes.class, recipe, 1)).intValue();
				height = ((Integer)ObfuscationReflectionHelper.getPrivateValue(ExtendedShapedRecipes.class, recipe, 2)).intValue();
				items = (ItemStack[]) ObfuscationReflectionHelper.getPrivateValue(ExtendedShapedRecipes.class, recipe, 3);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
			
			setIngredients(width, height, items);
		}
		
		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			return (ArrayList<PositionedStack>) getCycledIngredients(ExtendedShapedRecipeHandler.this.cycleticks / 20, this.ingredients);
		}
		
		@Override
		public PositionedStack getResult()
		{
			return this.result;
		}
		
		public ArrayList<PositionedStack> ingredients;
		public PositionedStack result;
	}

	@Override
	public int recipiesPerPage()
	{
		return 1;
	}
	
	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiExtended.class;
	}

	@Override
	public String getRecipeName()
	{
		return "Extended Crafting";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if(outputId.equals("extended") && getClass() == ExtendedShapedRecipeHandler.class)
		{
			List<IRecipe> allrecipes = ExtendedCraftingManager.getInstance().getRecipeList();
			for(IRecipe irecipe : allrecipes)
			{
				CachedExtendedRecipe recipe = null;
				if(irecipe instanceof ExtendedShapedRecipes)
					recipe = new CachedExtendedRecipe((ExtendedShapedRecipes)irecipe);

				if(recipe == null)
					continue;
				
				this.arecipes.add(recipe);
			}
		}
		else
		{
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result)
	{
		List<IRecipe> allrecipes = ExtendedCraftingManager.getInstance().getRecipeList();
		for(IRecipe irecipe : allrecipes)
		{
			if(NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result))
			{
				CachedExtendedRecipe recipe = null;
				if(irecipe instanceof ExtendedShapedRecipes)
					recipe = new CachedExtendedRecipe((ExtendedShapedRecipes)irecipe);
				
				if(recipe == null)
					continue;
				
				this.arecipes.add(recipe);
			}
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient)
	{
		List<IRecipe> allrecipes = ExtendedCraftingManager.getInstance().getRecipeList();
		for(IRecipe irecipe : allrecipes)
		{
			CachedExtendedRecipe recipe = null;
			if(irecipe instanceof ExtendedShapedRecipes)
				recipe = new CachedExtendedRecipe((ExtendedShapedRecipes)irecipe);

			if(recipe == null)
				continue;
			
			if(recipe.contains(recipe.ingredients, ingredient))
			{
				recipe.setIngredientPermutation(recipe.ingredients, ingredient);
				this.arecipes.add(recipe);
			}
		}
	}

	@Override
	public String getGuiTexture()
	{
		return "extendedworkbench:textures/gui/container/extended.png";
	}

	@Override
	public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
	{
		return RecipeInfo.hasDefaultOverlay(gui, "extended");
	}
	
	@Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 119);
    }
}
