package mods.manarz.item.tomes;

import mods.manarz.entity.EntityGnats;
import mods.manarz.entity.EntityJellyfish;
import mods.manarz.item.tomes.summons.EntitySummonCreeper;
import mods.manarz.item.tomes.summons.EntitySummonSkeleton;
import mods.manarz.item.tomes.summons.EntitySummonZombie;
import mods.resourcemod.common.ResourceMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySummon extends EntityThrowable implements IProjectile
{
	private int summon;
	public int color = 8388608, color_a = 128;

	public int[] colors = { Integer.parseInt("800000", 16), Integer.parseInt("008000", 16), Integer.parseInt("000080", 16), Integer.parseInt("000000", 16), Integer.parseInt("FFFFFF", 16)};
	public static final int RED = 0, GREEN = 1, BLUE = 2, BLACK = 3, WHITE = 4;
	
	public static final int LIGHTNING = 0, ZOMBIEARMY = 1, JELLYFISH = 4, GNAT = 5, FOGOFGNATS = 6, SUPERCHARGEDCREEPER = 7; // Add IDs here to reference custom monsters
	public static final int CREEPER = 50, SKELETON = 51, SPIDER = 52, GIANT_ZOMBIE = 53, ZOMBIE = 54, SLIME = 55, GHAST = 56, PIG_ZOMBIE = 57, ENDERMAN = 58,
	CAVE_SPIDER = 59, SILVERFISH = 60, BLAZE = 61, LAVA_SLIME = 62, BAT = 65, WITCH = 66, PIG = 90, SHEEP = 91, COW = 92, 
	CHICKEN = 93, SQUID = 94, WOLF = 95, MUSHROOM_COW = 96,OCELOT = 98, VILLAGER = 120, ENDERCRYSTAL = 121;

    public EntitySummon(World par1World, EntityPlayer par3EntityPlayer, int par3Summon)
    {
        super(par1World, par3EntityPlayer);
        this.summon = par3Summon;
    }

    public void setColor(int par1Index) {
    	this.color = this.colors[par1Index];
    }
    public void setColor(int par1Color, int par2ColorA) {
    	this.color = par1Color;
    	this.color_a = par2ColorA;
    }
    
    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (par1MovingObjectPosition.entityHit != null)
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);

        if (!this.worldObj.isRemote) {
			// Add new monster summoning here
			Entity entity;
			switch (this.summon)
			{
	    		case LIGHTNING:
	            	this.worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, posX, posY, posZ));
	    			break;
	    		case ZOMBIE:
					entity = new EntitySummonZombie(worldObj, 400);
	        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
	                this.worldObj.spawnEntityInWorld(entity);
	    			break;
				case ZOMBIEARMY:
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							entity = new EntityZombie(worldObj);
	    	        		entity.setLocationAndAngles(this.posX + i, this.posY+1, this.posZ + j, this.rotationYaw, 0.0F);
	    	                this.worldObj.spawnEntityInWorld(entity);
						}
					}
					break;
				case SKELETON:
					entity = new EntitySummonSkeleton(worldObj, 400);
	        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
	        		entity.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
	                this.worldObj.spawnEntityInWorld(entity);
					break;
				case CREEPER:
					entity = new EntitySummonCreeper(worldObj, 400);
	        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
	                this.worldObj.spawnEntityInWorld(entity);
					break;
				case JELLYFISH:
					entity = new EntityJellyfish(worldObj);
	        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
	                this.worldObj.spawnEntityInWorld(entity);
					break;
				case GNAT:
					entity = new EntityGnats(worldObj); 
	        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
	                this.worldObj.spawnEntityInWorld(entity);
					break;
				case FOGOFGNATS:
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 3; j++) {
							entity = new EntityGnats(worldObj);
	    	        		entity.setLocationAndAngles(this.posX + i, this.posY+1, this.posZ + j, this.rotationYaw, 0.0F);
	    	                this.worldObj.spawnEntityInWorld(entity);
						}
					}
				case SUPERCHARGEDCREEPER:
					entity = new EntityCreeper(worldObj); 
	        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
	                this.worldObj.spawnEntityInWorld(entity);
	                
					
				default:
	        		entity = EntityList.createEntityByID(summon, worldObj);
	        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
	                this.worldObj.spawnEntityInWorld(entity);
					break;
					
			}
        }
        
		// Kill the summoning spell
		this.setDead();
    }


}
