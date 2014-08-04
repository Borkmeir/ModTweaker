package naruto1310.extendedWorkbench.item;

import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.chanceToCatchCookedFish;
import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseFishHookThrowSpeed;
import static naruto1310.extendedWorkbench.mod_ExtendedWorkbench.extendedValues.increaseFishingSpeed;

import java.util.Arrays;
import java.util.List;

import naruto1310.extendedWorkbench.mod_ExtendedWorkbench;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityExtendedFishHook extends EntityFishHook
{
	//all these fields need to be copied, just to make onUpdate() work
	/*
    private int xTile;
    private int yTile;
    private int zTile;
    private boolean inGround;
    private int ticksInGround;
    private int ticksInAir;
    private int ticksCatchable;
    private int fishPosRotationIncrements;
    private double fishX;
    private double fishY;
    private double fishZ;
    private double fishYaw;
    private double fishPitch;
    //	*/
    private int field_146037_g;
    private int field_146048_h;
    private int field_146050_i;
    private Block field_146046_j;
    private boolean field_146051_au;
    private int field_146049_av;
    private int field_146047_aw;
    private int field_146045_ax;
    private int field_146040_ay;
    private int field_146038_az;
    private float field_146054_aA;
    private int field_146055_aB;
    private double field_146056_aC;
    private double field_146057_aD;
    private double field_146058_aE;
    private double field_146059_aF;
    private double field_146060_aG;
    @SideOnly(Side.CLIENT)
    private double field_146061_aH;
    @SideOnly(Side.CLIENT)
    private double field_146052_aI;
    @SideOnly(Side.CLIENT)
    private double field_146053_aJ;
    
    private static final List<WeightedRandomFishable> junk = Arrays.asList(new WeightedRandomFishable[] {(new WeightedRandomFishable(new ItemStack(Items.leather_boots), 10)).func_150709_a(0.9F), new WeightedRandomFishable(new ItemStack(Items.leather), 10), new WeightedRandomFishable(new ItemStack(Items.bone), 10), new WeightedRandomFishable(new ItemStack(Items.potionitem), 10), new WeightedRandomFishable(new ItemStack(Items.string), 5), (new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 2)).func_150709_a(0.9F), new WeightedRandomFishable(new ItemStack(Items.bowl), 10), new WeightedRandomFishable(new ItemStack(Items.stick), 5), new WeightedRandomFishable(new ItemStack(Items.dye, 10, 0), 1), new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 10), new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10)});
    private static final List<WeightedRandomFishable> treasure = Arrays.asList(new WeightedRandomFishable[] {new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1), new WeightedRandomFishable(new ItemStack(Items.name_tag), 1), new WeightedRandomFishable(new ItemStack(Items.saddle), 1), (new WeightedRandomFishable(new ItemStack(Items.bow), 1)).func_150709_a(0.25F).func_150707_a(), (new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 1)).func_150709_a(0.25F).func_150707_a(), (new WeightedRandomFishable(new ItemStack(Items.book), 1)).func_150707_a()});
    private static final List<WeightedRandomFishable> fish = Arrays.asList(new WeightedRandomFishable[] {new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.func_150976_a()), 60), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.func_150976_a()), 25), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()), 2), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.func_150976_a()), 13)});
    private static final List<WeightedRandomFishable> cookedFish = Arrays.asList(new WeightedRandomFishable[] {new WeightedRandomFishable(new ItemStack(Items.cooked_fished, 1, ItemFishFood.FishType.COD.func_150976_a()), 60), new WeightedRandomFishable(new ItemStack(Items.cooked_fished, 1, ItemFishFood.FishType.SALMON.func_150976_a()), 25), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()), 2), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.func_150976_a()), 13)});

	
	public EntityExtendedFishHook(World world, EntityPlayer player)
	{
		super(world);
		
		//TODO angler
		this.field_146042_b = player;
        this.field_146042_b.fishEntity = this;
        this.setLocationAndAngles(player.posX, player.posY + 1.62D - player.yOffset, player.posZ, player.rotationYaw, player.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F *(float)Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F *(float)Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
		float var3 = 0.4F;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F *(float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F *(float)Math.PI) * var3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F *(float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F *(float)Math.PI) * var3;
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F *(float)Math.PI) * var3;
        //TODO calculateVelocity
        this.func_146035_c(this.motionX, this.motionY, this.motionZ, 1.5F * increaseFishHookThrowSpeed, 1.0F);
	}
	
	
	// I need to copy this whole method, just to change 1 line
	@Override
    public void onUpdate()
    {
		this.onEntityUpdate();

		if(this.field_146055_aB > 0)
        {
            double d7 = this.posX + (this.field_146056_aC - this.posX) / this.field_146055_aB;
            double d8 = this.posY + (this.field_146057_aD - this.posY) / this.field_146055_aB;
            double d9 = this.posZ + (this.field_146058_aE - this.posZ) / this.field_146055_aB;
            double d1 = MathHelper.wrapAngleTo180_double(this.field_146059_aF - this.rotationYaw);
            this.rotationYaw = (float)(this.rotationYaw + d1 / this.field_146055_aB);
            this.rotationPitch = (float)(this.rotationPitch + (this.field_146060_aG - this.rotationPitch) / this.field_146055_aB);
            --this.field_146055_aB;
            this.setPosition(d7, d8, d9);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        else
        {
            if(!this.worldObj.isRemote)
            {
                ItemStack itemstack = this.field_146042_b.getCurrentEquippedItem();

                //changed this line
                if(this.field_146042_b.isDead || !this.field_146042_b.isEntityAlive() || itemstack == null || itemstack.getItem() != mod_ExtendedWorkbench.extendedFishingRod || this.getDistanceSqToEntity(this.field_146042_b) > 1024.0D)
                {
                    this.setDead();
                    this.field_146042_b.fishEntity = null;
                    return;
                }

                if(this.field_146043_c != null)
                {
                    if(!this.field_146043_c.isDead)
                    {
                        this.posX = this.field_146043_c.posX;
                        this.posY = this.field_146043_c.boundingBox.minY + this.field_146043_c.height * 0.8D;
                        this.posZ = this.field_146043_c.posZ;
                        return;
                    }

                    this.field_146043_c = null;
                }
            }

            if(this.field_146044_a > 0)
            {
                this.field_146044_a--;
            }

            if(this.field_146051_au)
            {
                if(this.worldObj.getBlock(this.field_146037_g, this.field_146048_h, this.field_146050_i) == this.field_146046_j)
                {
                    this.field_146049_av++;
                    
                    if(this.field_146049_av == 1200)
                        this.setDead();

                    return;
                }

                this.field_146051_au = false;
                this.motionX *= this.rand.nextFloat() * 0.2F;
                this.motionY *= this.rand.nextFloat() * 0.2F;
                this.motionZ *= this.rand.nextFloat() * 0.2F;
                this.field_146049_av = 0;
                this.field_146047_aw = 0;
            }
            else
            {
                this.field_146047_aw++;
            }

            Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3);
            vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if(movingobjectposition != null)
                vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);

            Entity entity = null;
            @SuppressWarnings("rawtypes")
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            double d2;

            for(int i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);

                if(entity1.canBeCollidedWith() && (entity1 != this.field_146042_b || this.field_146047_aw >= 5))
                {
                    float f = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f, f, f);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec31, vec3);

                    if(movingobjectposition1 != null)
                    {
                        d2 = vec31.distanceTo(movingobjectposition1.hitVec);

                        if(d2 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d2;
                        }
                    }
                }
            }

            if(entity != null)
                movingobjectposition = new MovingObjectPosition(entity);

            if(movingobjectposition != null)
            {
                if(movingobjectposition.entityHit != null)
                {
                    if(movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.field_146042_b), 0.0F))
                        this.field_146043_c = movingobjectposition.entityHit;
                }
                else
                {
                    this.field_146051_au = true;
                }
            }

            if(!this.field_146051_au)
            {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                float f5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

                for(this.rotationPitch = (float)(Math.atan2(this.motionY, f5) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);

                while(this.rotationPitch - this.prevRotationPitch >= 180.0F)
                    this.prevRotationPitch += 360.0F;

                while(this.rotationYaw - this.prevRotationYaw < -180.0F)
                    this.prevRotationYaw -= 360.0F;

                while(this.rotationYaw - this.prevRotationYaw >= 180.0F)
                    this.prevRotationYaw += 360.0F;

                this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
                this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
                float f6 = 0.92F;

                if(this.onGround || this.isCollidedHorizontally)
                    f6 = 0.5F;

                byte b0 = 5;
                double d10 = 0.0D;

                for(int j = 0; j < b0; ++j)
                {
                    double d3 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (j + 0) / b0 - 0.125D + 0.125D;
                    double d4 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (j + 1) / b0 - 0.125D + 0.125D;
                    AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, d3, this.boundingBox.minZ, this.boundingBox.maxX, d4, this.boundingBox.maxZ);

                    if(this.worldObj.isAABBInMaterial(axisalignedbb1, Material.water))
                        d10 += 1.0D / b0;
                }

                if(!this.worldObj.isRemote && d10 > 0.0D)
                {
                    WorldServer worldserver = (WorldServer)this.worldObj;
                    int k = 1;

                    if(this.rand.nextFloat() < 0.25F && this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ)))
                        k = 2;

                    if(this.rand.nextFloat() < 0.5F && !this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ)))
                    	k--;

                    if(this.field_146045_ax > 0)
                    {
                        this.field_146045_ax--;

                        if(this.field_146045_ax <= 0)
                        {
                            this.field_146040_ay = 0;
                            this.field_146038_az = 0;
                        }
                    }
                    else
                    {
                        float f1;
                        float f2;
                        double d5;
                        double d6;
                        double d11;
                        float f7;

                        if(this.field_146038_az > 0)
                        {
                            this.field_146038_az -= k;

                            if(this.field_146038_az <= 0)
                            {
                                this.motionY -= 0.20000000298023224D;
                                this.playSound("random.splash", 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
                                f1 = MathHelper.floor_double(this.boundingBox.minY);
                                worldserver.func_147487_a("bubble", this.posX, f1 + 1.0F, this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D);
                                worldserver.func_147487_a("wake", this.posX, f1 + 1.0F, this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D);
                                this.field_146045_ax = MathHelper.getRandomIntegerInRange(this.rand, 10, 30);
                            }
                            else
                            {
                                this.field_146054_aA = (float)(this.field_146054_aA + this.rand.nextGaussian() * 4.0D);
                                f1 = this.field_146054_aA * 0.017453292F;
                                f7 = MathHelper.sin(f1);
                                f2 = MathHelper.cos(f1);
                                d11 = this.posX + f7 * this.field_146038_az * 0.1F;
                                d5 = MathHelper.floor_double(this.boundingBox.minY) + 1.0F;
                                d6 = this.posZ + f2 * this.field_146038_az * 0.1F;

                                if(this.rand.nextFloat() < 0.15F)
                                    worldserver.func_147487_a("bubble", d11, d5 - 0.10000000149011612D, d6, 1, f7, 0.1D, f2, 0.0D);

                                float f3 = f7 * 0.04F;
                                float f4 = f2 * 0.04F;
                                worldserver.func_147487_a("wake", d11, d5, d6, 0, f4, 0.01D, (-f3), 1.0D);
                                worldserver.func_147487_a("wake", d11, d5, d6, 0, (-f4), 0.01D, f3, 1.0D);
                            }
                        }
                        else if(this.field_146040_ay > 0)
                        {
                            this.field_146040_ay -= k;
                            f1 = 0.15F;

                            if(this.field_146040_ay < 20)
                            {
                                f1 = (float)(f1 + (20 - this.field_146040_ay) * 0.05D);
                            }
                            else if(this.field_146040_ay < 40)
                            {
                                f1 = (float)(f1 + (40 - this.field_146040_ay) * 0.02D);
                            }
                            else if(this.field_146040_ay < 60)
                            {
                                f1 = (float)(f1 + (60 - this.field_146040_ay) * 0.01D);
                            }

                            if(this.rand.nextFloat() < f1)
                            {
                                f7 = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F) * 0.017453292F;
                                f2 = MathHelper.randomFloatClamp(this.rand, 25.0F, 60.0F);
                                d11 = this.posX + MathHelper.sin(f7) * f2 * 0.1F;
                                d5 = MathHelper.floor_double(this.boundingBox.minY) + 1.0F;
                                d6 = this.posZ + MathHelper.cos(f7) * f2 * 0.1F;
                                worldserver.func_147487_a("splash", d11, d5, d6, 2 + this.rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D);
                            }

                            if(this.field_146040_ay <= 0)
                            {
                                this.field_146054_aA = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F);
                                this.field_146038_az = MathHelper.getRandomIntegerInRange(this.rand, 20, 80);
                            }
                        }
                        else
                        {
                            this.field_146040_ay = (int)(MathHelper.getRandomIntegerInRange(this.rand, 10, 90) * increaseFishingSpeed);
                            this.field_146040_ay -= EnchantmentHelper.func_151387_h(this.field_146042_b) * 20 * 5;
                        }
                    }

                    if(this.field_146045_ax > 0)
                    {
                        this.motionY -= this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat() * 0.2D;
                    }
                }

                d2 = d10 * 2.0D - 1.0D;
                this.motionY += 0.03999999910593033D * d2;

                if(d10 > 0.0D)
                {
                    f6 = (float)(f6 * 0.9D);
                    this.motionY *= 0.8D;
                }

                this.motionX *= f6;
                this.motionY *= f6;
                this.motionZ *= f6;
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
    }

	//TODO catchFish
	@Override
	public int func_146034_e()
    {
        if(this.worldObj.isRemote)
            return 0;
        
        byte var1 = 0;

        if(this.field_146043_c != null)
        {
            double var2 = this.field_146042_b.posX - this.posX;
            double var4 = this.field_146042_b.posY - this.posY;
            double var6 = this.field_146042_b.posZ - this.posZ;
            double var8 = MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
            double var10 = 0.1D;
            this.field_146043_c.motionX += var2 * var10;
            this.field_146043_c.motionY += var4 * var10 + MathHelper.sqrt_double(var8) * 0.08D;
            this.field_146043_c.motionZ += var6 * var10;
            var1 = 3;
        }
        //TODO ticksCatchable
        else if(this.field_146045_ax > 0)
        {
        	ItemStack stack;
	        float f = this.worldObj.rand.nextFloat();
	        int i = EnchantmentHelper.func_151386_g(this.field_146042_b);
	        int j = EnchantmentHelper.func_151387_h(this.field_146042_b);
	        float f1 = 0.1F - i * 0.025F - j * 0.01F;
	        float f2 = 0.05F + i * 0.01F - j * 0.01F;
	        f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
	        f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);

	        if(f < f1)
	        {
	            this.field_146042_b.addStat(StatList.field_151183_A, 1);
	            stack = ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, junk)).func_150708_a(this.rand);
	        }
	        else
	        {
	            if(f - f1 < f2)
	            {
	                this.field_146042_b.addStat(StatList.field_151184_B, 1);
	                stack = ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, treasure)).func_150708_a(this.rand);
	            }
	            else
	            {
	                this.field_146042_b.addStat(StatList.fishCaughtStat, 1);
	                if(this.rand.nextFloat() < chanceToCatchCookedFish)
	                	stack = ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, cookedFish)).func_150708_a(this.rand);
	                else
	                	stack = ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, fish)).func_150708_a(this.rand);
	            }
	        }
        	
            EntityItem var13 = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, stack);
            double var3 = this.field_146042_b.posX - this.posX;
            double var5 = this.field_146042_b.posY - this.posY;
            double var7 = this.field_146042_b.posZ - this.posZ;
            double var9 = MathHelper.sqrt_double(var3 * var3 + var5 * var5 + var7 * var7);
            double var11 = 0.1D;
            var13.motionX = var3 * var11;
            var13.motionY = var5 * var11 + MathHelper.sqrt_double(var9) * 0.08D;
            var13.motionZ = var7 * var11;
            this.worldObj.spawnEntityInWorld(var13);
            this.field_146042_b.addStat(StatList.fishCaughtStat, 1);
            var1 = 1;
        }

        //TODO inGround
        if(this.field_146051_au)
            var1 = 2;

        this.setDead();
        this.field_146042_b.fishEntity = null;
        return var1;
    }
}
