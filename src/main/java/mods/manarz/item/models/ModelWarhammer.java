package mods.manarz.item.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWarhammer extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape3;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
  
  public ModelWarhammer()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(0F, 0F, 0F, 3, 22, 3);
      Shape1.setRotationPoint(0F, 0F, 0F);
      Shape1.setTextureSize(128, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 12, 0);
      Shape2.addBox(0F, 0F, 0F, 4, 4, 4);
      Shape2.setRotationPoint(-0.5F, -4F, -0.5F);
      Shape2.setTextureSize(128, 64);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 0, 25);
      Shape4.addBox(0F, 0F, 0F, 2, 3, 3);
      Shape4.setRotationPoint(3.5F, -3.5F, 0F);
      Shape4.setTextureSize(128, 64);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 0, 31);
      Shape5.addBox(0F, 0F, 0F, 3, 4, 4);
      Shape5.setRotationPoint(5.5F, -4F, -0.5F);
      Shape5.setTextureSize(128, 64);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 12, 8);
      Shape3.addBox(0F, 0F, 0F, 3, 3, 3);
      Shape3.setRotationPoint(-3.5F, -3.3F, 0F);
      Shape3.setTextureSize(128, 64);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 12, 14);
      Shape6.addBox(0F, 0F, 0F, 4, 2, 3);
      Shape6.setRotationPoint(-7.5F, -2.6F, 0F);
      Shape6.setTextureSize(128, 64);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 12, 19);
      Shape7.addBox(0F, 0F, 0F, 4, 1, 2);
      Shape7.setRotationPoint(-11.5F, -1.9F, 0.5F);
      Shape7.setTextureSize(128, 64);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 38, 0);
      Shape8.addBox(0F, 0F, 0F, 1, 2, 1);
      Shape8.setRotationPoint(1F, -7F, 1F);
      Shape8.setTextureSize(128, 64);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 0F);
      Shape9 = new ModelRenderer(this, 30, 0);
      Shape9.addBox(0F, 0F, 0F, 2, 1, 2);
      Shape9.setRotationPoint(0.5F, -5F, 0.5F);
      Shape9.setTextureSize(128, 64);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape3.render(f5);
    Shape6.render(f5);
    Shape7.render(f5);
    Shape8.render(f5);
    Shape9.render(f5);
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