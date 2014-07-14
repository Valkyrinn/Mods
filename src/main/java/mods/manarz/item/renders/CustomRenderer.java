package mods.manarz.item.renders;

import org.lwjgl.opengl.GL11;

import mods.manarz.Manarz;
import mods.manarz.ManarzEnums;
import mods.manarz.ManarzEnums.Blade;
import mods.manarz.ManarzEnums.Hilt;
import mods.manarz.item.MItemAxe;
import mods.manarz.item.MItemPickaxe;
import mods.manarz.item.MItemSpade;
import mods.manarz.item.MItemSword;
import mods.manarz.item.models.ModelAxe;
import mods.manarz.item.models.ModelBlade;
import mods.manarz.item.models.ModelBladeStandard;
import mods.manarz.item.models.ModelHandle;
import mods.manarz.item.models.ModelHilt;
import mods.manarz.item.models.ModelPickaxe;
import mods.manarz.item.models.ModelShovel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.ItemRenderer;
//import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class CustomRenderer implements IItemRenderer {
    private static RenderItem renderItem = new RenderItem();
	protected ModelBase bladeModel = null;
	protected ModelBase hiltModel = null;

	private Hilt hilt;
	private Blade blade;
	private int type;
	private String domain = Manarz.modID;
	private ResourceLocation bladeTexture, hiltTexture;
	private float scale = 1.0F;

	public CustomRenderer(int par1Type, Hilt par2Handle, Blade par3Head) {
		type = par1Type;
		blade = par3Head;
		hilt = par2Handle;
		
		switch(type) {
		case ManarzEnums.TYPE_SWORD:
			if (blade == Blade.BLADE_DIVINE) bladeModel = new ModelBlade();
			bladeTexture = new ResourceLocation(domain, "textures/models/blade" + blade.getName() + ".png");
			
			if (hilt == Hilt.HILT_IRON) hiltModel = new ModelHilt();
			hiltTexture = new ResourceLocation(domain, "textures/models/hilt" + hilt.getName() + ".png");
			break;
		case ManarzEnums.TYPE_PICKAXE:
//			bladeModel   = new ModelPickaxe();
//			hiltModel    = new ModelHandle();
//			bladeTexture = new ResourceLocation(domain, "textures/models/pickaxeBase.png");
//			hiltTexture  = new ResourceLocation(domain, "textures/models/handleBase.png");
			scale = 2.0F;
			break;
		case ManarzEnums.TYPE_AXE:
//			bladeModel   = new ModelAxe();
//			hiltModel    = new ModelHandle();
//			bladeTexture = new ResourceLocation(domain, "textures/models/axeBase.png");
//			hiltTexture  = new ResourceLocation(domain, "textures/models/handleBase.png");
			scale = 2.0F;
			break;
		case ManarzEnums.TYPE_SHOVEL:
//			bladeModel   = new ModelShovel();
//			hiltModel    = new ModelHandle();
//			bladeTexture = new ResourceLocation(domain, "textures/models/shovelBase.png");
//			hiltTexture  = new ResourceLocation(domain, "textures/models/handleBase.png");
			scale = 2.0F;
			break;
		default:
			break;
		}
	}
    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType type) {
    	switch (type) {
	    	case INVENTORY:
	    	case EQUIPPED:
	    	case EQUIPPED_FIRST_PERSON:
	    	case ENTITY:
	    		return true;
    		default:
    			return false;
    	}
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
            return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
    	boolean shouldBob = false;
    	float bob = 0F, rot = 0F;
    	IIcon[] icons = new IIcon[2];
    	
    	Item it = itemStack.getItem();
    	switch (this.type) {
	    	case ManarzEnums.TYPE_SWORD:   if (itemStack.getItem() instanceof MItemSword)   { icons = ((MItemSword)  it).getIcons();   } else System.out.println("Can't cast to Sword");   break;
	    	case ManarzEnums.TYPE_PICKAXE: if (itemStack.getItem() instanceof MItemPickaxe) { icons = ((MItemPickaxe)  it).getIcons(); } else System.out.println("Can't cast to Pickaxe"); break;
	    	case ManarzEnums.TYPE_SHOVEL:  if (itemStack.getItem() instanceof MItemSpade)   { icons = ((MItemSpade)  it).getIcons();   } else System.out.println("Can't cast to Shovel");  break;
	    	case ManarzEnums.TYPE_AXE:     if (itemStack.getItem() instanceof MItemAxe)     { icons = ((MItemAxe)  it).getIcons();     } else System.out.println("Can't cast to Axe");     break;
    	}

    	switch (type) {
    	case INVENTORY:
    		for (int i=icons.length; i>0; i--)
    			renderItem.renderIcon(0, 0, icons[i-1], 16, 16);
    		break;
    	case ENTITY:
    		shouldBob = true;
			EntityItem entity = ((EntityItem)data[1]);
			float x = 0.0625F;
			bob = MathHelper.sin(((float)entity.age + x) / 10.0F + entity.hoverStart) * 0.1F + 0.1F;
			rot = -(((float)entity.age + x) / 20.0F + entity.hoverStart) * (180F / (float)Math.PI);
    	case EQUIPPED:
    		if (this.type == ManarzEnums.TYPE_SHOVEL && rot == 0F) rot = 180F;
    	case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();

			TextureManager re = Minecraft.getMinecraft().renderEngine;
            Tessellator tessellator = Tessellator.instance;

            if (RenderItem.renderInFrame) {
            	scale = 1.15F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(-0.5F, 0.8F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            } else if (shouldBob) {
				GL11.glTranslatef(0.0F, 0.1F + bob, 0F);
				GL11.glRotatef(-rot, 0.0F, 1.0F, 0.0F);
	            GL11.glTranslatef(-0.5F, -0.25F, -((0.0625F + 0.021875F) / 2.0F));
			}
            if (hiltModel == null) ItemRenderer.renderItemIn2D(tessellator, icons[1].getMaxU(), icons[1].getMinV(), icons[1].getMinU(), icons[1].getMaxV(), icons[1].getIconWidth(), icons[1].getIconHeight(), 0.0625F);
			if (bladeModel == null)ItemRenderer.renderItemIn2D(tessellator, icons[0].getMaxU(), icons[0].getMinV(), icons[0].getMinU(), icons[0].getMaxV(), icons[0].getIconWidth(), icons[1].getIconHeight(), 0.0625F);

            if (RenderItem.renderInFrame) {
            	scale = 1.0F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.6F, 0.4F, -0.03F);
                GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
            } else {
				GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.7F, 0.1F, -0.03F);
				GL11.glScalef(scale, scale, scale);
            }
            
            if (bladeModel != null) {
	            re.bindTexture(bladeTexture);
				bladeModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            }
            if (hiltModel != null) {
	            re.bindTexture(hiltTexture);
				hiltModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            }

           /*if (itemStack.hasEffect())
            {
	            GL11.glDepthFunc(GL11.GL_EQUAL);
	            GL11.glDisable(GL11.GL_LIGHTING);
	            re.func_110581_b("%blur%/misc/glint.png");
	            GL11.glEnable(GL11.GL_BLEND);
	            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
	            float f7 = 0.76F;
	            GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
	            GL11.glMatrixMode(GL11.GL_TEXTURE);
	            GL11.glPushMatrix();
	            float f8 = 0.125F;
	            GL11.glScalef(f8, f8, f8);
	            float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
	            GL11.glTranslatef(f9, 0.0F, 0.0F);
	            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
	            if (flag)
	            	bladeModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	            else
		            for (int i=0; i<icons.length; i++)
		                ItemRenderer.renderItemIn2D(tessellator, icons[i].getMaxU(), icons[i].getMinV(), icons[i].getMinU(), icons[i].getMaxV(), icons[i].getSheetWidth(), icons[i].getSheetHeight(), 0.0625F);
	            GL11.glPopMatrix();
	            GL11.glPushMatrix();
	            GL11.glScalef(f8, f8, f8);
	            f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
	            GL11.glTranslatef(-f9, 0.0F, 0.0F);
	            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
	            if (flag)
	            	bladeModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	            else
		            for (int i=0; i<icons.length; i++)
		                ItemRenderer.renderItemIn2D(tessellator, icons[i].getMaxU(), icons[i].getMinV(), icons[i].getMinU(), icons[i].getMaxV(), icons[i].getSheetWidth(), icons[i].getSheetHeight(), 0.0625F);
	            GL11.glPopMatrix();
	            GL11.glMatrixMode(GL11.GL_MODELVIEW);
	            GL11.glDisable(GL11.GL_BLEND);
	            GL11.glEnable(GL11.GL_LIGHTING);
	            GL11.glDepthFunc(GL11.GL_LEQUAL);
            }*/

			GL11.glPopMatrix();
    		break;
    	default:
    		break;
    	}
    }
}