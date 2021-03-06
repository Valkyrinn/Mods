package mods.manarz.entity.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelJelly extends ModelBase
{
	  //fields
	    ModelRenderer Jellybody;
	    ModelRenderer Arm1;
	    ModelRenderer Arm2;
	    ModelRenderer Arm3;
	    ModelRenderer Arm4;
	    ModelRenderer Arm5;
	    ModelRenderer Arm6;
	    ModelRenderer Arm7;
	    ModelRenderer Arm8;
	    ModelRenderer Overjelly;
	  
	  public ModelJelly()
	  {
	    textureWidth = 64;
	    textureHeight = 32;
	    
	      Jellybody = new ModelRenderer(this, 4, 0);
	      Jellybody.addBox(0F, 0F, 0F, 7, 5, 7);
	      Jellybody.setRotationPoint(0F, 0F, 0F);
	      Jellybody.setTextureSize(64, 32);
	      Jellybody.mirror = true;
	      setRotation(Jellybody, 0F, 0F, 0F);
	      Arm1 = new ModelRenderer(this, 0, 0);
	      Arm1.addBox(0F, 0F, 0F, 1, 8, 1);
	      Arm1.setRotationPoint(0F, 5F, 0F);
	      Arm1.setTextureSize(64, 32);
	      Arm1.mirror = true;
	      setRotation(Arm1, 0F, 0F, 0F);
	      Arm2 = new ModelRenderer(this, 0, 0);
	      Arm2.addBox(0F, 0F, 0F, 1, 8, 1);
	      Arm2.setRotationPoint(0F, 5F, 6F);
	      Arm2.setTextureSize(64, 32);
	      Arm2.mirror = true;
	      setRotation(Arm2, 0F, 0F, 0F);
	      Arm3 = new ModelRenderer(this, 0, 0);
	      Arm3.addBox(0F, 0F, 0F, 1, 8, 1);
	      Arm3.setRotationPoint(0F, 5F, 3F);
	      Arm3.setTextureSize(64, 32);
	      Arm3.mirror = true;
	      setRotation(Arm3, 0F, 0F, 0F);
	      Arm4 = new ModelRenderer(this, 0, 0);
	      Arm4.addBox(0F, 0F, 0F, 1, 8, 1);
	      Arm4.setRotationPoint(3F, 5F, 0F);
	      Arm4.setTextureSize(64, 32);
	      Arm4.mirror = true;
	      setRotation(Arm4, 0F, 0F, 0F);
	      Arm5 = new ModelRenderer(this, 0, 0);
	      Arm5.addBox(0F, 0F, 0F, 1, 8, 1);
	      Arm5.setRotationPoint(6F, 5F, 0F);
	      Arm5.setTextureSize(64, 32);
	      Arm5.mirror = true;
	      setRotation(Arm5, 0F, 0F, 0F);
	      Arm6 = new ModelRenderer(this, 0, 0);
	      Arm6.addBox(0F, 2F, 0F, 1, 8, 1);
	      Arm6.setRotationPoint(3F, 3F, 6F);
	      Arm6.setTextureSize(64, 32);
	      Arm6.mirror = true;
	      setRotation(Arm6, 0F, 0F, 0F);
	      Arm7 = new ModelRenderer(this, 0, 0);
	      Arm7.addBox(0F, 0F, 0F, 1, 8, 1);
	      Arm7.setRotationPoint(6F, 5F, 6F);
	      Arm7.setTextureSize(64, 32);
	      Arm7.mirror = true;
	      setRotation(Arm7, 0F, 0F, 0F);
	      Arm8 = new ModelRenderer(this, 0, 0);
	      Arm8.addBox(0F, 0F, 0F, 1, 8, 1);
	      Arm8.setRotationPoint(6F, 5F, 3F);
	      Arm8.setTextureSize(64, 32);
	      Arm8.mirror = true;
	      setRotation(Arm8, 0F, 0F, 0F);
	      Overjelly = new ModelRenderer(this, 0, 12);
	      Overjelly.addBox(0F, 0F, 0F, 8, 5, 8);
	      Overjelly.setRotationPoint(-0.5F, -0.5F, -0.5F);
	      Overjelly.setTextureSize(64, 32);
	      Overjelly.mirror = true;
	      setRotation(Overjelly, 0F, 0F, 0F);
	  }
	  
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5);
	    Jellybody.render(f5);
	    Arm1.render(f5);
	    Arm2.render(f5);
	    Arm3.render(f5);
	    Arm4.render(f5);
	    Arm5.render(f5);
	    Arm6.render(f5);
	    Arm7.render(f5);
	    Arm8.render(f5);
	    Overjelly.render(f5);
	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	  
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	  }

	}
