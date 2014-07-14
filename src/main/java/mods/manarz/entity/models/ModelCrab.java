/**Made With Techne**/
package mods.manarz.entity.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrab extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape3a;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape5a;
    ModelRenderer Shape5b;
    ModelRenderer Shape5c;
    ModelRenderer Shape5d;
    ModelRenderer Shape5e;
    ModelRenderer Shape6;
    ModelRenderer Shape6a;
  
  public ModelCrab()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(0F, 0F, 0F, 8, 1, 7);
      Shape1.setRotationPoint(-4F, 21F, -3F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 0, 8);
      Shape2.addBox(0F, 0F, 0F, 3, 0, 2);
      Shape2.setRotationPoint(-1.5F, 22F, -5F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0.122173F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 30, 0);
      Shape3.addBox(0F, 0F, 0F, 1, 1, 5);
      Shape3.setRotationPoint(5F, 22F, -7F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, -0.296706F, 3.141593F);
      Shape3a = new ModelRenderer(this, 30, 0);
      Shape3a.addBox(0F, 0F, 0F, 1, 1, 5);
      Shape3a.setRotationPoint(-5F, 21F, -7F);
      Shape3a.setTextureSize(64, 32);
      Shape3a.mirror = true;
      setRotation(Shape3a, 0F, 0.296706F, 0F);
      Shape4 = new ModelRenderer(this, 10, 8);
      Shape4.addBox(0F, 0F, 0F, 2, 1, 1);
      Shape4.setRotationPoint(-1F, 20F, -3F);
      Shape4.setTextureSize(64, 32);
      Shape4.mirror = true;
      setRotation(Shape4, -0.4363323F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 0, 10);
      Shape5.addBox(0F, 0F, 0F, 4, 1, 1);
      Shape5.setRotationPoint(3F, 21F, -2F);
      Shape5.setTextureSize(64, 32);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0.2617994F);
      Shape5a = new ModelRenderer(this, 0, 10);
      Shape5a.addBox(0F, 0F, 0F, 4, 1, 1);
      Shape5a.setRotationPoint(-3F, 21F, -1F);
      Shape5a.setTextureSize(64, 32);
      Shape5a.mirror = true;
      setRotation(Shape5a, 0F, 3.141593F, 0.2617994F);
      Shape5b = new ModelRenderer(this, 0, 10);
      Shape5b.addBox(0F, 0F, 0F, 4, 1, 1);
      Shape5b.setRotationPoint(3F, 21F, 0F);
      Shape5b.setTextureSize(64, 32);
      Shape5b.mirror = true;
      setRotation(Shape5b, 0F, 0F, 0.2617994F);
      Shape5c = new ModelRenderer(this, 0, 10);
      Shape5c.addBox(0F, 0F, 0F, 4, 1, 1);
      Shape5c.setRotationPoint(-3F, 21F, 1F);
      Shape5c.setTextureSize(64, 32);
      Shape5c.mirror = true;
      setRotation(Shape5c, 0F, 3.141593F, 0.2617994F);
      Shape5d = new ModelRenderer(this, 0, 10);
      Shape5d.addBox(0F, 0F, 0F, 4, 1, 1);
      Shape5d.setRotationPoint(3F, 21F, 2F);
      Shape5d.setTextureSize(64, 32);
      Shape5d.mirror = true;
      setRotation(Shape5d, 0F, -0.3490659F, 0.2617994F);
      Shape5e = new ModelRenderer(this, 0, 10);
      Shape5e.addBox(0F, 0F, 0F, 4, 1, 1);
      Shape5e.setRotationPoint(-3F, 21F, 3F);
      Shape5e.setTextureSize(64, 32);
      Shape5e.mirror = true;
      setRotation(Shape5e, 0F, -2.792527F, 0.2617994F);
      Shape6 = new ModelRenderer(this, 0, 12);
      Shape6.addBox(0F, 0F, 0F, 1, 1, 4);
      Shape6.setRotationPoint(1F, 21F, 3F);
      Shape6.setTextureSize(64, 32);
      Shape6.mirror = true;
      setRotation(Shape6, -0.0872665F, 0.4886922F, 0F);
      Shape6a = new ModelRenderer(this, 0, 12);
      Shape6a.addBox(0F, 0F, 0F, 1, 1, 4);
      Shape6a.setRotationPoint(-2F, 21F, 2F);
      Shape6a.setTextureSize(64, 32);
      Shape6a.mirror = true;
      setRotation(Shape6a, -0.0872665F, -0.4886922F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape3a.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape5a.render(f5);
    Shape5b.render(f5);
    Shape5c.render(f5);
    Shape5d.render(f5);
    Shape5e.render(f5);
    Shape6.render(f5);
    Shape6a.render(f5);
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
