package mods.manarz.entity.renders;

import mods.manarz.entity.EntityGnats;
import mods.manarz.entity.models.ModelGnats;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class RenderGnats extends RenderLiving {
	public static ModelGnats mod;
	
	public RenderGnats(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);

	}
	
	public RenderGnats(EntityGnats par1EntityGnats, double par2, double par4, double par6, float par8, float par9) {
		super(mod, par9);
		//super.doRenderLiving(par1EntityGnats, par2, par4, par6, par8, par9);
	}
	
	public void doRenderLiving(EntityGnats par1EntityGnats, double par2, double par4, double par6, float par8, float par9) {
//		super.doRenderLiving(par1EntityGnats, par2, par4, par6, par8, par9);
		//this((EntityGnats)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("manarz", "textures/models/Gnats.png");
	}
	
	/*public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		this.RenderGnats((EntityGnats)par1Entity, par2, par4, par6, par8, par9);
	}*/
}
