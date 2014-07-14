package mods.manarz.item.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPickaxe extends ModelBase
{
  //fields
    ModelRenderer side1;
    ModelRenderer head;
    ModelRenderer side2;
  
  public ModelPickaxe()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      head = new ModelRenderer(this, 14, 0);
      head.addBox(-1F, -6F, -1F, 2, 2, 2);
      head.setRotationPoint(0F, 0F, 0F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      side1 = new ModelRenderer(this, 0, 2);
      side1.addBox(-5.5F, -1F, -0.5F, 5, 1, 1);
      side1.setRotationPoint(0F, -5F, 0F);
      side1.setTextureSize(64, 32);
      side1.mirror = true;
      setRotation(side1, 0F, 0F, -0.1396263F);
      side2 = new ModelRenderer(this, 0, 0);
      side2.addBox(0.5F, -1F, -0.5F, 5, 1, 1);
      side2.setRotationPoint(0F, -5F, 0F);
      side2.setTextureSize(64, 32);
      side2.mirror = true;
      setRotation(side2, 0F, 0F, 0.1396263F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    side1.render(f5);
    head.render(f5);
    side2.render(f5);
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
