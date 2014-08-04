package naruto1310.extendedWorkbench.block;

import naruto1310.extendedWorkbench.crafting.ExtendedCraftingManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class ContainerExtended extends Container
{
    public final int	width = 3,
                		height = 6;

    public InventoryCrafting craftMatrix = new InventoryCrafting(this, this.width, this.height);
    public IInventory craftResult = new InventoryCraftResult();
    private World worldObj;
    private InventoryPlayer inventory;
    private int posX;
    private int posY;
    private int posZ;

    public ContainerExtended(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
    {
        this.worldObj = par2World;
        this.inventory = par1InventoryPlayer;
        this.posX = par3;
        this.posY = par4;
        this.posZ = par5;
        int var6;
        int var7;

        //crafting result
        this.addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 63));

        //crafting slots
        for(int i = 0; i < this.height; i++)
            for(int j = 0; j < this.width; j++)
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * this.width, 30 + j * 18, 17 + i * 18));

        //player inventory
        for(var6 = 0; var6 < 3; ++var6)
            for(var7 = 0; var7 < 9; ++var7)
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 138 + var6 * 18));

        //player inventory hotbar
        for(var6 = 0; var6 < 9; ++var6)
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 196));

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
    	//look for extended recipe
        this.craftResult.setInventorySlotContents(0, ExtendedCraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
        if(this.craftResult.getStackInSlot(0) != null)
            return;

        //look for normal recipe
        boolean[]lines = new boolean[6];
        for(int i = 0; i < lines.length; i++)
        	lines[i] = this.craftMatrix.getStackInSlot(i * 3) != null || this.craftMatrix.getStackInSlot(i * 3 + 1) != null || this.craftMatrix.getStackInSlot(i * 3 + 2) != null;
        int usedLines	= -1;
        int firstLine	= -1;
        int lastLine	= -1;

        for(int i = 0; i < lines.length; i++)
            if(lines[i] && firstLine == -1)
                firstLine = i;

        for(int i = lines.length - 1; i >= 0; i--)
            if(lines[i] && lastLine == -1)
                lastLine = i;


        if(firstLine == -1 || lastLine == -1)
            return;

        usedLines = lastLine - firstLine + 1;
        
        if(usedLines <= 3)
        {
            InventoryCrafting matrix = new InventoryCrafting(new ContainerWorkbench(this.inventory, this.worldObj, 0, 0, 0), 3, 3);
            
            if(lastLine <= 2)
            {
            	firstLine = 0;
            	lastLine = 2;
            }
            if(firstLine >= 3)
            {
            	firstLine = 3;
            	lastLine = 5;
            }
            
            for(int i = firstLine * 3; i < lastLine * 3 + 3; i++)
                matrix.setInventorySlotContents(i - firstLine * 3, this.craftMatrix.getStackInSlot(i));

            this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(matrix, this.worldObj));
        }

        if(this.craftResult.getStackInSlot(0) != null)
            return;

        //look for normal shapeless recipe, shattered all over the crafting grid
        ItemStack itemstack[] = new ItemStack[18];
        int j = 0;

        for(int i = 0; i < this.craftMatrix.getSizeInventory(); i++)
            if(this.craftMatrix.getStackInSlot(i) != null)
            {
                itemstack[j] = this.craftMatrix.getStackInSlot(i);
                j++;
            }

        int length = 0;

        for(int i = 0; i < itemstack.length; i++)
            if(itemstack[i] != null)
                length++;

        if(length < 9)
        {
            InventoryCrafting matrix = new InventoryCrafting(new ContainerWorkbench(this.inventory, this.worldObj, 0, 0, 0), 3, 3);

            for(int i = 0; i < length; i++)
                matrix.setInventorySlotContents(i, itemstack[i]);

            for(int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++)
                if(CraftingManager.getInstance().getRecipeList().get(i) instanceof ShapelessRecipes)
                    if(((ShapelessRecipes)CraftingManager.getInstance().getRecipeList().get(i)).matches(matrix, this.worldObj))
                        this.craftResult.setInventorySlotContents(0, ((ShapelessRecipes)CraftingManager.getInstance().getRecipeList().get(i)).getCraftingResult(matrix));
        }
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);

        if(!this.worldObj.isRemote)
        {
            for(int var2 = 0; var2 < this.craftMatrix.getSizeInventory(); ++var2)
            {
                ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);

                if(var3 != null)
                    par1EntityPlayer.dropPlayerItemWithRandomChoice(var3, false);
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
    	return this.worldObj.getBlock(this.posX, this.posY, this.posZ) != Blocks.crafting_table ? false : player.getDistanceSq(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D) <= 64.0D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if(var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if(par2 == 0)
            {
                if(!this.mergeItemStack(var5, 19, 55, true))
                    return null;

                var4.onSlotChange(var5, var3);
            }
            else if(par2 >= 19 && par2 < 46)
            {
                if(!this.mergeItemStack(var5, 46, 55, false))
                    return null;
            }
            else if(par2 >= 46 && par2 < 55)
            {
                if(!this.mergeItemStack(var5, 19, 46, false))
                    return null;
            }
            else if(!this.mergeItemStack(var5, 19, 55, false))
            	return null;

            if(var5.stackSize == 0)
                var4.putStack((ItemStack)null);
            else
                var4.onSlotChanged();

            if(var5.stackSize == var3.stackSize)
                return null;

            var4.onPickupFromSlot(player, var5);
        }

        return var3;
    }
}
