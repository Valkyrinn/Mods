package mods.manarz.item.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelShovel extends ModelBase
{
  //fields
    ModelRenderer head;
    ModelRenderer back;
    ModelRenderer main;
    ModelRenderer right;
    ModelRenderer left;
  
  public ModelShovel()
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
      back.addBox(-1.5F, -1.5F, -0.5F, 3, 1, 1);
      back.setRotationPoint(0F, -4F, 0F);
      back.setTextureSize(64, 32);
      back.mirror = true;
      setRotation(back, 0F, 0F, 0F);
      main = new ModelRenderer(this, 0, 0);
      main.addBox(-1.5F, -4F, 0F, 3, 3, 1);
      main.setRotationPoint(0F, -4F, 0F);
      main.setTextureSize(64, 32);
      main.mirror = true;
      setRotation(main, 0F, 0F, 0F);
      right = new ModelRenderer(this, 8, 0);
      right.addBox(-0.5F, -3F, -0.5F, 1, 2, 1);
      right.setRotationPoint(1.5F, -4F, 0F);
      right.setTextureSize(64, 32);
      right.mirror = true;
      setRotation(right, 0F, 0.7853982F, 0F);
      left = new ModelRenderer(this, 8, 3);
      left.addBox(-0.5F, -3F, -0.5F, 1, 2, 1);
      left.setRotationPoint(-1.5F, -4F, 0F);
      left.setTextureSize(64, 32);
      left.mirror = true;
      setRotation(left, 0F, -0.7853982F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    //head.render(f5);
    back.render(f5);
    main.render(f5);
    right.render(f5);
    left.render(f5);
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
