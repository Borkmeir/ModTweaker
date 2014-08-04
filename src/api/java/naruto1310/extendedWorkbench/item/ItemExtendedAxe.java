package naruto1310.extendedWorkbench.item;

import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseToolAttackDamage;
import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseToolDurability;
import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseToolPower;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemExtendedAxe extends ItemAxe
{
    public ItemExtendedAxe(Item.ToolMaterial material)
    {
        super(material);
        setMaxDamage((int)(getMaxDamage() * increaseToolDurability));
        try
        {
        	Field f = ItemTool.class.getDeclaredFields()[2];
        	f.setAccessible(true);
        	if(f.getType() == float.class)
        		f.set(this, Math.round((Float)f.get(this) * increaseToolAttackDamage));
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        //this.damageVsEntity *= increaseToolAttackDamage;
    }

    //TODO getStrVsBlock
    @Override
    public float func_150893_a(ItemStack stack, Block block)
    {
        return super.func_150893_a(stack, block) * increaseToolPower;
    }
}
