package mods.manarz.item.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMaul2 extends ModelBase
{
  //fields
    ModelRenderer handle;
    ModelRenderer head;
    ModelRenderer back;
    ModelRenderer front;
  
  public ModelMaul2()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      handle = new ModelRenderer(this, 0, 0);
      handle.addBox(-0.5F, -12F, -0.5333334F, 1, 12, 1);
      handle.setRotationPoint(0F, 12F, 0F);
      handle.setTextureSize(64, 32);
      handle.mirror = true;
      setRotation(handle, 0F, 0F, 0F);
      head = new ModelRenderer(this, 4, 0);
      head.addBox(-1F, -14F, -1.5F, 2, 3, 3);
      head.setRotationPoint(0F, 12F, 0F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      back = new ModelRenderer(this, 4, 12);
      back.addBox(-1.5F, -14.5F, 1.5F, 3, 4, 2);
      back.setRotationPoint(0F, 12F, 0F);
      back.setTextureSize(64, 32);
      back.mirror = true;
      setRotation(back, 0F, 0F, 0F);
      front = new ModelRenderer(this, 4, 6);
      front.addBox(-1.5F, -14.5F, -3.5F, 3, 4, 2);
      front.setRotationPoint(0F, 12F, 0F);
      front.setTextureSize(64, 32);
      front.mirror = true;
      setRotation(front, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    handle.render(f5);
    head.render(f5);
    back.render(f5);
    front.render(f5);
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
