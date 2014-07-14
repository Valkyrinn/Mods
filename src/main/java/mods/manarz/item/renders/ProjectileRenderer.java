package mods.manarz.item.renders;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import mods.manarz.Manarz;
import mods.manarz.ManarzEnums;
import mods.manarz.ManarzEnums.Legendary;
import mods.manarz.entity.EntityProjectile;
import mods.manarz.item.MItemSword;
import mods.manarz.item.tomes.EntitySummon;

public class ProjectileRenderer extends Render {

//	protected static final ResourceLocation projectileTexture = new ResourceLocation(Manarz.modID, "textures/models/gleamingDagger.png");
//	protected static final ResourceLocation projectileTexture = new ResourceLocation("textures/entity/arrow.png");
	protected int type;
	protected String texName;
	private int c = 0;

	public ProjectileRenderer(int t, String n) {
		type = t;
		texName = n;
		
	}

	@Override
	public void doRender(Entity par1, double par2, double par4, double par6, float par8, float par9) {
		if (type == EntityProjectile.EP_SPELL)
			doRenderSpell((EntityProjectile)par1, par2, par4, par6, par8, par9);
		else //if (type == EntityProjectile.EP_DAGGER)
			doRenderDagger((EntityProjectile)par1, par2, par4, par6, par8, par9);
	}
	public void doRenderDagger(EntityProjectile par1EntityProjectile, double par2, double par4, double par6, float par8, float par9) {
        this.bindEntityTexture(par1EntityProjectile);
//		this.bindTexture(projectileTexture);
//		System.out.println(this.getEntityTexture(par1EntityProjectile));
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4+0F, (float)par6);
        GL11.glRotatef(par1EntityProjectile.prevRotationYaw + (par1EntityProjectile.rotationYaw - par1EntityProjectile.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(par1EntityProjectile.prevRotationPitch + (par1EntityProjectile.rotationPitch - par1EntityProjectile.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        byte b0 = 0;
        float f2 = (float)(5 + b0 * 10) / 32.0F; // blade length start
        float f3 = (float)(14 + b0 * 10) / 32.0F; // blade length tip
        float f4l = (float)(0 + b0 * 10) / 32.0F; // blade width start
        float f5l = (float)(3 + b0 * 10) / 32.0F; // blade width end
        float f4r = (float)(2 + b0 * 10) / 32.0F; // blade width start
        float f5r = (float)(5 + b0 * 10) / 32.0F; // blade width end
        float f4b = (float)(0 + b0 * 10) / 32.0F; // blade width start
        float f5b = (float)(1 + b0 * 10) / 32.0F; // blade width end

        float f2h = (float)(0 + b0 * 10) / 32.0F; // handle length start
        float f3h = (float)(4 + b0 * 10) / 32.0F; // handle length tip
        float f4h = (float)(2 + b0 * 10) / 32.0F; // handle width start
        float f5h = (float)(3 + b0 * 10) / 32.0F; // handle width end
        float f6 = 0.0F;
        float f7 = (float)(9 + b0 * 10) / 32.0F;
        float f8 = (float)(5 + b0 * 10) / 32.0F;
        float f9 = (float)(12 + b0 * 10) / 32.0F;
        float f10 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float f11 = (float)par1EntityProjectile.arrowShake - par9;

        if (f11 > 0.0F)
        {
            float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
            GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
        }

        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4.0F, 0.0F, 0.0F);

        // top of hilt
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-5.5D, -1.0D, -2.0D, (double)f6, (double)f8); // top edge R
        tessellator.addVertexWithUV(-5.5D, -1.0D,  2.0D, (double)f7, (double)f8); // top back R
        tessellator.addVertexWithUV(-5.5D,  1.0D,  2.0D, (double)f7, (double)f9); // top back L
        tessellator.addVertexWithUV(-5.5D,  1.0D, -2.0D, (double)f6, (double)f9); // top edge L
        tessellator.draw();
        // bottom of hilt
        GL11.glNormal3f(f10, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-5.0D,  1.0D, -2.0D, (double)f6, (double)f8); // bottom edge L
        tessellator.addVertexWithUV(-5.0D,  1.0D,  2.0D, (double)f7, (double)f8); // bottom back L
        tessellator.addVertexWithUV(-5.0D, -1.0D,  2.0D, (double)f7, (double)f9); // bottom back R
        tessellator.addVertexWithUV(-5.0D, -1.0D, -2.0D, (double)f6, (double)f9); // bottom edge R
        tessellator.draw();
        // top of hilt
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-8.0D, -0.5D, -0.5D, (double)f6, (double)f8); // top edge R
        tessellator.addVertexWithUV(-8.0D, -0.5D,  0.5D, (double)f7, (double)f8); // top back R
        tessellator.addVertexWithUV(-8.0D,  0.5D,  0.5D, (double)f7, (double)f9); // top back L
        tessellator.addVertexWithUV(-8.0D,  0.5D, -0.5D, (double)f6, (double)f9); // top edge L
        tessellator.draw();
        
        int n;
        for (int i = 0; i < 4; ++i)
        {
        	// handle
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-8.0D, -0.5D, 0.5D, (double)f2h, (double)f4h);
            tessellator.addVertexWithUV(-5.0D, -0.5D, 0.5D, (double)f3h, (double)f4h);
            tessellator.addVertexWithUV(-5.0D, 0.5D, 0.5D, (double)f3h, (double)f5h);
            tessellator.addVertexWithUV(-8.0D, 0.5D, 0.5D, (double)f2h, (double)f5h);
            tessellator.draw();
            
            // hilt edge
            n = (i+1)%2;
			GL11.glNormal3f(f10, 0.0F, 0.0F);
			tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(-5.5D, -1.0D-n, -2.0D+n, (double)f6, (double)f8); // top edge R
	        tessellator.addVertexWithUV(-5.5D,  1.0D+n, -2.0D+n, (double)f6, (double)f9); // top edge L
	        tessellator.addVertexWithUV(-5.0D,  1.0D+n, -2.0D+n, (double)f6, (double)f8); // bottom edge L
	        tessellator.addVertexWithUV(-5.0D, -1.0D-n, -2.0D+n, (double)f6, (double)f9); // bottom edge R
			tessellator.draw();
        }

    	// Back
        GL11.glNormal3f(0.0F, 0.0F, f10);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-5.0D, -.53D, 1.0D, (double)f2, (double)f4b);
        tessellator.addVertexWithUV(8.0D, -0.01D, 1.0D, (double)f3, (double)f4b);
        tessellator.addVertexWithUV(8.0D, 0.01D, 1.0D, (double)f3, (double)f5b);
        tessellator.addVertexWithUV(-5.0D, .53D, 1.0D, (double)f2, (double)f5b);
        tessellator.draw();

        // right blade side
        GL11.glRotatef(100.0F, 1.0F, 0.0F, 0.0F);
        GL11.glNormal3f(0.0F, 0.0F, f10);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-5.0D, -1.D, 0.2D, (double)f2, (double)f4r); // handle blade-side
        tessellator.addVertexWithUV(4.0D, -1.0D, 0.2D, (double)f3, (double)f4r);  // blade
        tessellator.addVertexWithUV(8.0D, 1.1D, -0.15D, (double)f3, (double)f5r);  // point
        tessellator.addVertexWithUV(-5.0D, 1.1D, 0.35D, (double)f2, (double)f5r); // handle point-side
        tessellator.draw();

        // left blade side
        GL11.glRotatef(-210.0F, 1.0F, 0.0F, 0.0F);
        GL11.glNormal3f(0.0F, 0.0F, f10);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-5.0D, -1.1D, 0.15D, (double)f2, (double)f4l); // handle point-side
        tessellator.addVertexWithUV(8.0D, -1.1D, -0.35D, (double)f3, (double)f4l); // point
        tessellator.addVertexWithUV(4.0D, 1.0D, 0.35D, (double)f3, (double)f5l);  // blade
        tessellator.addVertexWithUV(-5.0D, 1.0D, 0.35D, (double)f2, (double)f5l); // handle blade-side
        tessellator.draw();
//        System.out.println("f3: " + f3 + ", f5: " + f5);



        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
	}
	
	public void doRenderSpell(EntityProjectile par1EntitySummon, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        this.bindEntityTexture(par1EntitySummon);
        int i = 10; //par1EntitySummon.getTextureByXP();
        float f2 = (float)(i % 4 * 16 + 0) / 64.0F;
        float f3 = (float)(i % 4 * 16 + 16) / 64.0F;
        float f4 = (float)(i / 4 * 16 + 0) / 64.0F;
        float f5 = (float)(i / 4 * 16 + 16) / 64.0F;
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.25F;
        int j = par1EntitySummon.getBrightnessForRender(par9);
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)l / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f11 = 255.0F;
        c = (c+1)%100;
        float f10 = ((float)c/9 + par9) / 2.0F;
        l = (int)((MathHelper.sin(f10 + 0.0F) + 1.0F) * 0.5F * f11);
        int i1 = (int)f11;
        int j1 = (int)((MathHelper.sin(f10 + 4.1887903F) + 1.0F) * 0.1F * f11);
        int k1 = l << 16 | i1 << 8 | j1;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float f9 = 0.3F;
        GL11.glScalef(f9, f9, f9);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(k1, 128);
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV((double)(0.0F - f7), (double)(0.0F - f8), 0.0D, (double)f2, (double)f5);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(0.0F - f8), 0.0D, (double)f3, (double)f5);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(1.0F - f8), 0.0D, (double)f3, (double)f4);
        tessellator.addVertexWithUV((double)(0.0F - f7), (double)(1.0F - f8), 0.0D, (double)f2, (double)f4);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		return getEntityTexture((EntityProjectile)var1);
	}
	protected ResourceLocation getEntityTexture(EntityProjectile var1) {
//		System.out.println(var1.getName());
//		return new ResourceLocation(Manarz.modID, "textures/models/" + var1.getName() + ".png");
		return new ResourceLocation(Manarz.modID, "textures/models/" + texName + ".png");
	}

}
