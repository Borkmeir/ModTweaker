package naruto1310.extendedWorkbench.item;

import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseArmorDurability;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemExtendedArmor extends ItemArmor implements ISpecialArmor
{
    /** Holds the 'base' maxDamage that each armorType have. */
    private static final int[] EWmaxDamageArray = new int[] {11, 16, 15, 13};

    /** Holds the amount of damage that the armor reduces at full durability. */
    public final int EWdamageReduceAmount;

    /** The EnumArmorMaterial used for this ItemArmor */
    private final ItemArmor.ArmorMaterial material;

    public ItemExtendedArmor(ItemArmor.ArmorMaterial material, int renderIndex, int armorType)
    {
        super(material, renderIndex, armorType);
        this.material = material;
        this.EWdamageReduceAmount = material.getDamageReductionAmount(armorType);
        this.setMaxDamage(material.getDurability(armorType));
        this.maxStackSize = 1;
        this.setMaxDamage((int)(getMaxDamage() * increaseArmorDurability));
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
	public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    /**
     * Returns the 'max damage' factor array for the armor, each piece of armor have a durability factor (that gets
     * multiplied by armor material factor)
     */
    static int[] getMaxDamageArray()
    {
        return EWmaxDamageArray;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
    {
    	if(source.isUnblockable())
    		return nullCopy;	//take that Forge, I tricked you
        return new ArmorProperties(0, (this.EWdamageReduceAmount + 0.5D) / 25D , armor.getMaxDamage() + 1 - armor.getItemDamage());
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
    {
        return this.EWdamageReduceAmount;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
    {
        stack.damageItem(damage, entity);
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
    	return false;
    }
    
    @Override
    public int getColor(ItemStack stack)
    {
        if (this.material != ItemArmor.ArmorMaterial.CLOTH)
            return -1;
        
        NBTTagCompound var2 = stack.getTagCompound();

        if (var2 == null)
    		return 0x632E1B;
        
        NBTTagCompound var3 = var2.getCompoundTag("display");
        return var3 == null ? 0x632E1B : (var3.hasKey("color") ? var3.getInteger("color") : 0x632E1B);
    }

    private static ArmorProperties nullCopy = new ArmorProperties(0, 0, 0)
    {
    	@Override
    	public ArmorProperties copy()
    	{
    		return null;
    	}
    };
}
