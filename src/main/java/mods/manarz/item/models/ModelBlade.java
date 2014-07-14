package mods.manarz.item.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBlade extends ModelBase
{
  //fields
    ModelRenderer bladeFL;
    ModelRenderer sideTL;
    ModelRenderer sideTR;
    ModelRenderer sideBL;
    ModelRenderer sideBR;
    ModelRenderer center;
    ModelRenderer edgeL;
    ModelRenderer bladeBL;
    ModelRenderer bladeFR;
    ModelRenderer bladeBR;
    ModelRenderer edgeR;
    ModelRenderer tipR;
    ModelRenderer tipL;
    ModelRenderer base;
    ModelRenderer hiltR;
    ModelRenderer handle;
    ModelRenderer pommel;
    ModelRenderer hiltL;
  
  public ModelBlade()
  {
    textureWidth = 64;
    textureHeight = 32;
    
    bladeFL = new ModelRenderer(this, 12, 0);
    bladeFL.addBox(-1.5F, -13F, -0.7F, 1, 11, 1);
    bladeFL.setRotationPoint(0F, 0F, 0F);
    bladeFL.setTextureSize(64, 32);
    bladeFL.mirror = true;
    setRotation(bladeFL, 0F, 0F, 0F);
    sideTL = new ModelRenderer(this, 20, 12);
    sideTL.addBox(-1.5F, -2.5F, -1.5F, 1, 3, 3);
    sideTL.setRotationPoint(-2.5F, 0F, 0F);
    sideTL.setTextureSize(64, 32);
    sideTL.mirror = true;
    setRotation(sideTL, 0F, 0F, 0.8028515F);
    sideTR = new ModelRenderer(this, 4, 12);
    sideTR.addBox(-1.5F, -0.5F, -1.5F, 1, 3, 3);
    sideTR.setRotationPoint(2.5F, -0.03333334F, 0F);
    sideTR.setTextureSize(64, 32);
    sideTR.mirror = true;
    setRotation(sideTR, 0F, 0F, 2.373648F);
    sideBL = new ModelRenderer(this, 28, 12);
    sideBL.addBox(-0.5F, -1.5F, -1.5F, 1, 3, 3);
    sideBL.setRotationPoint(-2.5F, 0F, 0F);
    sideBL.setTextureSize(64, 32);
    sideBL.mirror = true;
    setRotation(sideBL, 0F, 0F, -0.7853982F);
    sideBR = new ModelRenderer(this, 12, 12);
    sideBR.addBox(-0.5F, -1.5F, -1.5F, 1, 3, 3);
    sideBR.setRotationPoint(2.5F, 0F, 0F);
    sideBR.setTextureSize(64, 32);
    sideBR.mirror = true;
    setRotation(sideBR, 0F, 0F, -2.391101F);
    center = new ModelRenderer(this, 0, 0);
    center.addBox(-0.5F, -17.6F, -0.5F, 1, 17, 1);
    center.setRotationPoint(0F, 2F, 0F);
    center.setTextureSize(64, 32);
    center.mirror = true;
    setRotation(center, 0F, 0.7853982F, 0F);
    edgeL = new ModelRenderer(this, 20, 0);
    edgeL.addBox(-0.5F, -12F, -0.5F, 1, 11, 1);
    edgeL.setRotationPoint(-1.5F, -1F, 0F);
    edgeL.setTextureSize(64, 32);
    edgeL.mirror = true;
    setRotation(edgeL, 0F, 0.7853982F, 0F);
    bladeBL = new ModelRenderer(this, 16, 0);
    bladeBL.addBox(-1.5F, -13F, -0.3F, 1, 11, 1);
    bladeBL.setRotationPoint(0F, 0F, 0F);
    bladeBL.setTextureSize(64, 32);
    bladeBL.mirror = true;
    setRotation(bladeBL, 0F, 0F, 0F);
    bladeFR = new ModelRenderer(this, 4, 0);
    bladeFR.addBox(0.5F, -13F, -0.7F, 1, 11, 1);
    bladeFR.setRotationPoint(0F, 0F, 0F);
    bladeFR.setTextureSize(64, 32);
    bladeFR.mirror = true;
    setRotation(bladeFR, 0F, 0F, 0F);
    bladeBR = new ModelRenderer(this, 8, 0);
    bladeBR.addBox(0.5F, -13F, -0.3F, 1, 11, 1);
    bladeBR.setRotationPoint(0F, 0F, 0F);
    bladeBR.setTextureSize(64, 32);
    bladeBR.mirror = true;
    setRotation(bladeBR, 0F, 0F, 0F);
    edgeR = new ModelRenderer(this, 24, 0);
    edgeR.addBox(-0.5F, -12F, -0.5F, 1, 11, 1);
    edgeR.setRotationPoint(1.5F, -1F, 0F);
    edgeR.setTextureSize(64, 32);
    edgeR.mirror = true;
    setRotation(edgeR, 0F, 0.7853982F, 0F);
    tipR = new ModelRenderer(this, 28, 2);
    tipR.addBox(-0.5F, -0.5F, -0.5F, 3, 1, 1);
    tipR.setRotationPoint(0.3F, -14.8F, 0F);
    tipR.setTextureSize(64, 32);
    tipR.mirror = true;
    setRotation(tipR, 0.7853982F, 0F, 1.047198F);
    tipL = new ModelRenderer(this, 28, 0);
    tipL.addBox(-0.5F, -0.5F, -0.5F, 3, 1, 1);
    tipL.setRotationPoint(-0.3F, -14.8F, 0F);
    tipL.setTextureSize(64, 32);
    tipL.mirror = true;
    setRotation(tipL, 0.7853982F, 0F, 2.094395F);
    base = new ModelRenderer(this, 28, 6);
    base.addBox(-2.466667F, -3F, -1.5F, 5, 1, 3);
    base.setRotationPoint(0F, 0F, 0F);
    base.setTextureSize(64, 32);
    base.mirror = true;
    setRotation(base, 0F, 0F, 0F);
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
  bladeFL.render(f5);
  sideTL.render(f5);
  sideTR.render(f5);
  sideBL.render(f5);
  sideBR.render(f5);
  center.render(f5);
  edgeL.render(f5);
  bladeBL.render(f5);
  bladeFR.render(f5);
  bladeBR.render(f5);
  edgeR.render(f5);
  tipR.render(f5);
  tipL.render(f5);
  base.render(f5);
  //hiltR.render(f5);
  //handle.render(f5);
  //pommel.render(f5);
  //hiltL.render(f5);
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
