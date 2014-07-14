package mods.manarz.item.renders;

import org.lwjgl.opengl.GL11;

import mods.manarz.item.models.ModelWarhammer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererWarhammer implements IItemRenderer{
	protected ModelWarhammer model;
	
	public ItemRendererWarhammer(){
		model = new ModelWarhammer();
	}
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type){
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		boolean shouldBob = false;
		float bob = 0F, rot = 0F;
		
		switch(type) {
    	case ENTITY:
    		shouldBob = true;
			EntityItem entity = ((EntityItem)data[1]);
			float x = 0.0625F;
			bob = MathHelper.sin(((float)entity.age + x) / 10.0F + entity.hoverStart) * 0.1F + 0.1F;
			rot = -(((float)entity.age + x) / 20.0F + entity.hoverStart) * (180F / (float)Math.PI);
    	case EQUIPPED_FIRST_PERSON:
		case EQUIPPED:
			GL11.glPushMatrix();

			GL11.glRotatef(-145f, 0, 0, 1);
			GL11.glTranslatef(-.7f * (shouldBob?0:1), -.4f + (0.3F + bob) * (shouldBob?-1:0), 0);
			GL11.glRotatef(180f+rot, 0, 1, 0);
			float scale = 0.8f;
			GL11.glScalef(scale, scale, scale);
			
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("manarz", "textures/models/warhammer.png"));
			
			model.render((Entity)data[1], 0, 0, 0, 0, 0, 0.0625f);
			
			GL11.glPopMatrix();
		}
		
	}
	
}
