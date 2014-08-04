package naruto1310.extendedWorkbench.item;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderExtendedFishingHook extends Render
{
	private static final ResourceLocation field_110792_a = new ResourceLocation("textures/particle/particles.png");

	/**
     * Actually renders the fishing line and hook
     */
	public void doRenderFishHook(EntityExtendedFishHook fishHook, double par2, double par4, double par6, @SuppressWarnings("unused") float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        byte var10 = 1;
        byte var11 = 2;
        //this.loadTexture("/particles.png");
        Tessellator var12 = Tessellator.instance;
        float var13 = (var10 * 8 + 0) / 128.0F;
        float var14 = (var10 * 8 + 8) / 128.0F;
        float var15 = (var11 * 8 + 0) / 128.0F;
        float var16 = (var11 * 8 + 8) / 128.0F;
        float var17 = 1.0F;
        float var18 = 0.5F;
        float var19 = 0.5F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        var12.startDrawingQuads();
        var12.setNormal(0.0F, 1.0F, 0.0F);
        var12.addVertexWithUV((0.0F - var18), (0.0F - var19), 0.0D, var13, var16);
        var12.addVertexWithUV((var17 - var18), (0.0F - var19), 0.0D, var14, var16);
        var12.addVertexWithUV((var17 - var18), (1.0F - var19), 0.0D, var14, var15);
        var12.addVertexWithUV((0.0F - var18), (1.0F - var19), 0.0D, var13, var15);
        var12.draw();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();

        //TODO angler
        if (fishHook.field_146042_b != null)
        {
            float var20 = (fishHook.field_146042_b.prevRotationYaw + (fishHook.field_146042_b.rotationYaw - fishHook.field_146042_b.prevRotationYaw) * par9) * (float)Math.PI / 180.0F;
            double var21 = MathHelper.sin(var20);
            double var23 = MathHelper.cos(var20);
            float var25 = fishHook.field_146042_b.getSwingProgress(par9);
            float var26 = MathHelper.sin(MathHelper.sqrt_float(var25) * (float)Math.PI);
            Vec3 var27 = Vec3.createVectorHelper(-0.5D, 0.03D, 0.8D);
            var27.rotateAroundX(-(fishHook.field_146042_b.prevRotationPitch + (fishHook.field_146042_b.rotationPitch - fishHook.field_146042_b.prevRotationPitch) * par9) * (float)Math.PI / 180.0F);
            var27.rotateAroundY(-(fishHook.field_146042_b.prevRotationYaw + (fishHook.field_146042_b.rotationYaw - fishHook.field_146042_b.prevRotationYaw) * par9) * (float)Math.PI / 180.0F);
            var27.rotateAroundY(var26 * 0.5F);
            var27.rotateAroundX(-var26 * 0.7F);
            double var28 = fishHook.field_146042_b.prevPosX + (fishHook.field_146042_b.posX - fishHook.field_146042_b.prevPosX) * par9 + var27.xCoord;
            double var30 = fishHook.field_146042_b.prevPosY + (fishHook.field_146042_b.posY - fishHook.field_146042_b.prevPosY) * par9 + var27.yCoord;
            double var32 = fishHook.field_146042_b.prevPosZ + (fishHook.field_146042_b.posZ - fishHook.field_146042_b.prevPosZ) * par9 + var27.zCoord;

            if (this.renderManager.options.thirdPersonView > 0)
            {
                var20 = (fishHook.field_146042_b.prevRenderYawOffset + (fishHook.field_146042_b.renderYawOffset - fishHook.field_146042_b.prevRenderYawOffset) * par9) * (float)Math.PI / 180.0F;
                var21 = MathHelper.sin(var20);
                var23 = MathHelper.cos(var20);
                var28 = fishHook.field_146042_b.prevPosX + (fishHook.field_146042_b.posX - fishHook.field_146042_b.prevPosX) * par9 - var23 * 0.35D - var21 * 0.85D;
                var30 = fishHook.field_146042_b.prevPosY + (fishHook.field_146042_b.posY - fishHook.field_146042_b.prevPosY) * par9 - 0.45D;
                var32 = fishHook.field_146042_b.prevPosZ + (fishHook.field_146042_b.posZ - fishHook.field_146042_b.prevPosZ) * par9 - var21 * 0.35D + var23 * 0.85D;
            }

            double var34 = fishHook.prevPosX + (fishHook.posX - fishHook.prevPosX) * par9;
            double var36 = fishHook.prevPosY + (fishHook.posY - fishHook.prevPosY) * par9 + 0.25D;
            double var38 = fishHook.prevPosZ + (fishHook.posZ - fishHook.prevPosZ) * par9;
            double var40 = ((float)(var28 - var34));
            double var42 = ((float)(var30 - var36));
            double var44 = ((float)(var32 - var38));
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            var12.startDrawing(3);
            var12.setColorOpaque_I(0);
            byte var46 = 16;

            for (int var47 = 0; var47 <= var46; ++var47)
            {
                float var48 = (float)var47 / (float)var46;
                var12.addVertex(par2 + var40 * var48, par4 + var42 * (var48 * var48 + var48) * 0.5D + 0.25D, par6 + var44 * var48);
            }

            var12.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderFishHook((EntityExtendedFishHook)entity, par2, par4, par6, par8, par9);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return field_110792_a;
	}
}
