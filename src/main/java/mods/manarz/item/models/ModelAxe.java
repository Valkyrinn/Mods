package mods.manarz.item.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAxe extends ModelBase
{
  //fields
    ModelRenderer head;
    ModelRenderer back;
    ModelRenderer left;
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
  
  public ModelAxe()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      head = new ModelRenderer(this, 14, 0);
      head.addBox(-1F, -7F, -1F, 2, 1, 2);
      head.setRotationPoint(0F, 0F, 0F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      back = new ModelRenderer(this, 0, 4);
      back.addBox(-1.466667F, -2.5F, -0.5F, 3, 2, 1);
      back.setRotationPoint(0F, -4F, 0F);
      back.setTextureSize(64, 32);
      back.mirror = true;
      setRotation(back, 0F, 0F, -0.2094395F);
      left = new ModelRenderer(this, 8, 3);
      left.addBox(-0.5F, -3F, -0.5F, 1, 3, 1);
      left.setRotationPoint(-2.533333F, -4F, 0F);
      left.setTextureSize(64, 32);
      left.mirror = true;
      setRotation(left, 0F, -0.7853982F, 0F);
      Shape1 = new ModelRenderer(this, 12, 3);
      Shape1.addBox(-2.533333F, -3F, -0.7F, 1, 3, 1);
      Shape1.setRotationPoint(0F, -4F, 0F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 16, 3);
      Shape2.addBox(-2.5F, -3F, -0.3F, 1, 3, 1);
      Shape2.setRotationPoint(0F, -4F, 0F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 0, 0);
      Shape3.addBox(-2.666667F, -2.5F, -0.5F, 3, 2, 1);
      Shape3.setRotationPoint(0F, -4F, 0F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0.1919862F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    //head.render(f5);
    back.render(f5);
    left.render(f5);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
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
