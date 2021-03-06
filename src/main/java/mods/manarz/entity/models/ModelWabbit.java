/**Made with techne**/
package mods.manarz.entity.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWabbit extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape8;
    ModelRenderer Shape7;
    ModelRenderer Shape9;
  
  public ModelWabbit()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(0F, 0F, 0F, 5, 5, 8);
      Shape1.setRotationPoint(-2F, 18F, -4F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 26, 0);
      Shape2.addBox(0F, 0F, 0F, 3, 3, 5);
      Shape2.setRotationPoint(-1F, 18F, -7F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0.2094395F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 20, 13);
      Shape3.addBox(0F, 0F, 0F, 1, 2, 0);
      Shape3.setRotationPoint(0F, 20F, -6F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 14, 13);
      Shape4.addBox(-1F, 0F, 0F, 3, 4, 0);
      Shape4.setRotationPoint(0F, 14F, -3F);
      Shape4.setTextureSize(64, 32);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 26, 8);
      Shape5.addBox(0F, 0F, 0F, 1, 1, 1);
      Shape5.setRotationPoint(0F, 19F, 4F);
      Shape5.setTextureSize(64, 32);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 4, 13);
      Shape6.addBox(0F, 0F, 0F, 1, 2, 4);
      Shape6.setRotationPoint(3F, 22F, 0F);
      Shape6.setTextureSize(64, 32);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 4, 13);
      Shape8.addBox(0F, 0F, 0F, 1, 2, 4);
      Shape8.setRotationPoint(-3F, 22F, 0F);
      Shape8.setTextureSize(64, 32);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 0, 13);
      Shape7.addBox(0F, 0F, 0F, 1, 3, 1);
      Shape7.setRotationPoint(-3F, 21F, -3F);
      Shape7.setTextureSize(64, 32);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0F);
      Shape9 = new ModelRenderer(this, 0, 13);
      Shape9.addBox(0F, 0F, 0F, 1, 3, 1);
      Shape9.setRotationPoint(3F, 21F, -3F);
      Shape9.setTextureSize(64, 32);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape6.render(f5);
    Shape6.render(f5);
    Shape7.render(f5);
    Shape7.render(f5);
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
