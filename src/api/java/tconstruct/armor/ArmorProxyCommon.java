package tconstruct.armor;

import tconstruct.armor.inventory.ArmorExtendedContainer;
import tconstruct.armor.inventory.KnapsackContainer;
import tconstruct.common.TProxyCommon;
import tconstruct.util.player.TPlayerStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class ArmorProxyCommon implements IGuiHandler
{
    public static final int inventoryGui = 100;
    public static final int armorGuiID = 101;
    public static final int knapsackGuiID = 102;
    public ArmorProxyCommon()
    {
        registerGuiHandler();
    }
    
    protected void registerGuiHandler()
    {
        TProxyCommon.registerServerGuiHandler(inventoryGui, this);
        TProxyCommon.registerServerGuiHandler(armorGuiID, this);
        TProxyCommon.registerServerGuiHandler(knapsackGuiID, this);
    }
    
    @Override
    public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == ArmorProxyCommon.inventoryGui)
        {
            // GuiInventory inv = new GuiInventory(player);
            return player.inventoryContainer;
        }
        if (ID == ArmorProxyCommon.armorGuiID)
        {
            TPlayerStats stats = TPlayerStats.get(player);
            return new ArmorExtendedContainer(player.inventory, stats.armor);
        }
        if (ID == ArmorProxyCommon.knapsackGuiID)
        {
            TPlayerStats stats = TPlayerStats.get(player);
            return new KnapsackContainer(player.inventory, stats.knapsack);
        }
        
        return null;
    }
    @Override
    public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void registerTickHandler ()
    {
        
    }

    public void registerKeys ()
    {
        
    }
}
