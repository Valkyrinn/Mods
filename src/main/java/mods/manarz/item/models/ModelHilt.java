package mods.manarz.item.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHilt extends ModelBase
{
  //fields
    ModelRenderer hiltR;
    ModelRenderer handle;
    ModelRenderer pommel;
    ModelRenderer hiltL;
  
  public ModelHilt()
  {
    textureWidth = 64;
    textureHeight = 32;
    
    hiltR = new ModelRenderer(this, 0, 18);
    hiltR.addBox(-5F, -0.4666667F, -1F, 10, 1, 2);
    hiltR.setRotationPoint(0F, 1F, 0F);
    hiltR.setTextureSize(64, 32);
    hiltR.mirror = true;
    setRotation(hiltR, 0F, 0F, 0.0698132F);
    handle = new ModelRenderer(this, 0, 24);
    handle.addBox(-0.5F, 1.5F, -0.5F, 1, 4, 1);
    handle.setRotationPoint(0F, 0F, 0F);
    handle.setTextureSize(64, 32);
    handle.mirror = true;
    setRotation(handle, 0F, 0F, 0F);
    pommel = new ModelRenderer(this, 4, 24);
    pommel.addBox(-1F, 5.5F, -1F, 2, 2, 2);
    pommel.setRotationPoint(0F, 0F, 0F);
    pommel.setTextureSize(64, 32);
    pommel.mirror = true;
    setRotation(pommel, 0F, 0F, 0F);
    hiltL = new ModelRenderer(this, 0, 21);
    hiltL.addBox(-5F, -0.5F, -1F, 10, 1, 2);
    hiltL.setRotationPoint(0F, 1F, 0F);
    hiltL.setTextureSize(64, 32);
    hiltL.mirror = true;
    setRotation(hiltL, 0F, 0F, -0.0698132F);
}

public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
{
  super.render(entity, f, f1, f2, f3, f4, f5);
  setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  hiltR.render(f5);
  handle.render(f5);
  pommel.render(f5);
  hiltL.render(f5);
}
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
