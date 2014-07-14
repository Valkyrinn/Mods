package mods.manarz.item.tomes;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySummonZombieArmy extends EntityThrowable
{
    public EntitySummonZombieArmy(World par1World)
    {
        super(par1World);
    }

    public EntitySummonZombieArmy(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }

    public EntitySummonZombieArmy(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
    	if (!this.worldObj.isRemote && this.rand.nextInt(1) == 0)
        {
            byte b0 = 0;

            if (this.rand.nextInt(32) == 0)
            {
                b0 = 0;
            }
            b0 = 1;
            for (int i = 0; i <= b0; ++i)
            {
                EntityZombie entitychicken = new EntityZombie(this.worldObj);//net.minecraft.entity.Entity.
                entitychicken.setLocationAndAngles(this.posX, this.posY+3, this.posZ, this.rotationYaw, 0.0F);
                this.worldObj.spawnEntityInWorld(entitychicken);
                if (!this.worldObj.isRemote)
                {
                    this.setDead();
                }
            }
        }
        
    }
}
