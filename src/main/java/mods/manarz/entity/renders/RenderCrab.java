package mods.manarz.entity.renders;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.manarz.entity.models.ModelCrab;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySpider;
import org.lwjgl.opengl.GL11;


@SideOnly(Side.CLIENT)
public class RenderCrab extends RenderLiving
{
    public RenderCrab(ModelBase par1ModelBase, float par2)
    {
        super(par1ModelBase, par2);
        this.setRenderPassModel(new ModelCrab());
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("manarz", "textures/models/Crab.png");
	}


    

    /**
     * Sets the spider's glowing eyes
     */
    

   

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
   
   
    /**
     * Queries whether should render the specified pass or not.
     */
   
}
