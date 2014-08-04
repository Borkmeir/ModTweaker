package naruto1310.extendedWorkbench.item;

import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseSwordDamage;
import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseSwordDurability;
import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseSwordMiningSpeed;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemExtendedSword extends ItemSword
{
	protected Item.ToolMaterial ewToolMaterial;
	protected float exWeaponDamage;
	
    public ItemExtendedSword(Item.ToolMaterial material)
    {
        super(material);
        this.ewToolMaterial = material;
        setMaxDamage((int)(getMaxDamage() * increaseSwordDurability));
        this.exWeaponDamage = 5 + material.getDamageVsEntity() * increaseSwordDamage;
    }
    
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers()
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", this.exWeaponDamage, 0));
        return multimap;
    }

    //TODO getStrVsBlock
    @Override
    public float func_150893_a(ItemStack par1ItemStack, Block par2Block)
    {
        return (super.func_150893_a(par1ItemStack, par2Block) * increaseSwordMiningSpeed);
    }
}