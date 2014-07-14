package mods.manarz.item.tomes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class ItemRenderTome implements IItemRenderer {

	protected ModelBook bookModel;
	
	public ItemRenderTome()
	{
		bookModel = new ModelBook();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
			case EQUIPPED:
			case ENTITY:
			case EQUIPPED_FIRST_PERSON:
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
    	float bob = 0F, rot = 0F;
    	boolean shouldBob = false;
    	
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
			
			GL11.glRotatef(-145.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.8F * (shouldBob?0:1), -0.8F + (0.2F + bob) * (shouldBob?-1:0), 0F);
			GL11.glRotatef(90+rot, 0.0F, 1.0F, 0.0F);

			float scale = 2.0F;
			GL11.glScalef(scale, scale, scale);

			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("manarz", "textures/models/book.png"));
			
			bookModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			
			GL11.glPopMatrix();
			break;
		default:
			break;
	}
	}

}
