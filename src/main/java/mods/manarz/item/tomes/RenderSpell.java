package mods.manarz.item.tomes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.manarz.Manarz;
import mods.manarz.entity.EntityProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderSpell extends Render
{
	
	protected ResourceLocation projectileTexture = new ResourceLocation("textures/entity/experience_orb.png");
    public RenderSpell()
    {
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    public void doRender(EntityProjectile par1EntitySpell, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        //int i = 2477;
        int i = 1;
        //this.loadTexture("/mods/Manarz/textures/models/spell.png");
//        this.bindTexture(getEntityTexture(par1EntitySpell));
        this.bindEntityTexture(par1EntitySpell);
        System.out.println(projectileTexture.toString());
        //this.func_110776_a(new ResourceLocation("manarz:textures/models/spell.png"));
        //Tessellator tessellator = Tessellator.instance;
        float f2 = (float)(i % 4 * 16 + 0) / 64.0F;
        float f3 = (float)(i % 4 * 16 + 16) / 64.0F;
        float f4 = (float)(i / 4 * 16 + 0) / 64.0F;
        float f5 = (float)(i / 4 * 16 + 16) / 64.0F;
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.25F;
        int j = 240; //par1EntityXPOrb.getBrightnessForRender(par9);
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)l / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        /*float f9 = 255.0F;
        float f10 = ((float)par1EntityXPOrb.xpColor + par9) / 2.0F;
        l = (int)((MathHelper.sin(f10 + 0.0F) + 1.0F) * 0.5F * f9);
        int i1 = (int)f9;
        int j1 = (int)((MathHelper.sin(f10 + 4.1887903F) + 1.0F) * 0.1F * f9);
        int k1 = l << 16 | i1 << 8 | j1;*/
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float f11 = 0.3F;
        GL11.glScalef(f11, f11, f11);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        
//        tessellator.setColorRGBA_I(par1EntitySpell.color, par1EntitySpell.color_a);
        tessellator.setColorRGBA_I(8388608, 128);
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV((double)(0.0F - f7), (double)(0.0F - f8), 0.0D, (double)f2, (double)f5);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(0.0F - f8), 0.0D, (double)f3, (double)f5);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(1.0F - f8), 0.0D, (double)f3, (double)f4);
        tessellator.addVertexWithUV((double)(0.0F - f7), (double)(1.0F - f8), 0.0D, (double)f2, (double)f4);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        
        /*float f2 = (float)(i % 4 * 16 + 0) / 64.0F;
        float f3 = (float)(i % 4 * 16 + 16) / 64.0F;
        float f4 = (float)(i / 4 * 16 + 0) / 64.0F;
        float f5 = (float)(i / 4 * 16 + 16) / 64.0F;
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.25F;
        int j = 240;
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)l / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float f11 = 0.3F;
        GL11.glScalef(f11, f11, f11);
        tessellator.startDrawingQuads();
        
        tessellator.setColorRGBA_I(par1EntitySpell.color, par1EntitySpell.color_a);
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV((double)(0.0F - f7), (double)(0.0F - f8), 0.0D, (double)f2, (double)f5);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(0.0F - f8), 0.0D, (double)f3, (double)f5);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(1.0F - f8), 0.0D, (double)f3, (double)f4);
        tessellator.addVertexWithUV((double)(0.0F - f7), (double)(1.0F - f8), 0.0D, (double)f2, (double)f4);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();*/
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    //@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float yaw, float partialTicks)
	{
		this.doRender((EntityProjectile)par1Entity, par2, par4, par6, yaw, partialTicks);
//		this.doRenderSpell(par1Entity, par2, par4, par6, yaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return projectileTexture;
	}
    protected ResourceLocation getEntityTexture(EntityProjectile par1EntityXPOrb)
    {
        return projectileTexture;
    }
}
