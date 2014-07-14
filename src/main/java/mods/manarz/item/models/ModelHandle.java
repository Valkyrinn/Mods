package mods.manarz.item.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHandle extends ModelBase {
	  //fields
    ModelRenderer handle;
  
  public ModelHandle()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      handle = new ModelRenderer(this, 0, 15);
      handle.addBox(-0.5F, -6.5F, -0.5F, 1, 11, 1);
      handle.setRotationPoint(0F, 0F, 0F);
      handle.setTextureSize(64, 32);
      handle.mirror = true;
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    handle.render(f5);
  }
  
  /*private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }*/
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
