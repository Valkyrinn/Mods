package mods.manarz.item.tomes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySummonCreeper extends EntityThrowable implements IProjectile
{
    public EntitySummonCreeper(World par1World)
    {
        super(par1World);
    }

    public EntitySummonCreeper(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }

    public EntitySummonCreeper(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
    	if (!this.worldObj.isRemote)
        {
            byte b0 = 0;

            
            b0 = 0;
            
            	
                EntityCreeper entitychicken = new EntityCreeper(worldObj);//net.minecraft.entity.Entity.
                entitychicken.setLocationAndAngles(this.posX, this.posY+3, this.posZ, this.rotationYaw, 0.0F);
                this.worldObj.spawnEntityInWorld(entitychicken);

                this.setDead();
                
              
          
        }
    	
        
    }
}
