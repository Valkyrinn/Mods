package mods.manarz.item.tomes;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import mods.manarz.entity.EntityGnats;
import mods.manarz.entity.EntityJellyfish;
import mods.manarz.entity.EntityProjectile;
import mods.manarz.item.tomes.summons.EntitySummonCreeper;
import mods.manarz.item.tomes.summons.EntitySummonSkeleton;
import mods.manarz.item.tomes.summons.EntitySummonZombie;

public class EntitySpell extends EntityProjectile {
	
	protected String spellType, modifier = "Lightning";
	protected Block conjuredBlock = Blocks.air;
//	public static final int NONE = 0, SUMMON = 1;

	public EntitySpell(World par1World) {
		super(par1World);
	}

	public EntitySpell(World par1World, EntityLivingBase par2EntityLivingBase, float par3, ItemStack par4ItemStack, String par5Name) {
		super(par1World, par2EntityLivingBase, par3, par4ItemStack, par5Name, 0);
	}

	@Override
    public void onUpdate()
    {
//        super.onUpdate();
//        if (!worldObj.isRemote) System.out.println("onUpdate: " + this.texName); else System.out.println("Remote");
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }

        Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);

        if (block.getMaterial() != Material.air) {
            block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

            if (axisalignedbb != null && axisalignedbb.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)))
                this.inGround = true;
        }

        if (this.inGround) {
        	// Perform the Spell
//        	System.out.println("Spell cast!");
//        	this.worldObj.setBlock(xTile, yTile, zTile, Blocks.glass);
//            this.playSound("random.explode", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        	int[] mod = {0, 0, 0};
//        	System.out.println("(" + posX + ", " + posY + ", " + posZ + ") - (" + xTile + ", " + yTile + ", " + zTile + ")");
//        	DecimalFormat df = new DecimalFormat("#.##");
//        	if (!worldObj.isRemote)
//        		System.out.println("(" + df.format(Math.abs(posX - xTile)) + ", " + df.format(Math.abs(posY-yTile)) + ", " + df.format(Math.abs(posZ-zTile)) + ")");
        	if (Math.abs(posY-yTile) > 1 || Math.abs(posY-yTile) < 0.05) { if ((int)posY > yTile) { /*System.out.println("Hit from the Top!");*/ mod[1]++; } else { /*System.out.println("Hit from the Bottom!");*/ mod[1]--; } }
        	else if (Math.abs(posZ-zTile) > 1 || Math.abs(posZ-zTile) < 0.05) { if ((int)posZ > zTile) { /*System.out.println("Hit from the South!");*/ mod[2]++; } else { /*System.out.println("Hit from the North!");*/ mod[2]--; } }
        	else if (Math.abs(posX-xTile) > 1 || Math.abs(posX-xTile) < 0.05) { if ((int)posX > xTile) { /*System.out.println("Hit from the East!");*/ mod[0]++; } else { /*System.out.println("Hit from the West!");*/ mod[0]--; } }
//        	mod = new int[]{0, 0, 0};
        	posX = xTile + mod[0];
        	posY = yTile + mod[1];
        	posZ = zTile + mod[2];
        	
//        	if (!worldObj.isRemote) System.out.println("(" + df.format(motionX) + ", " + df.format(motionY) + ", " + df.format(motionZ) + ")");
        	performSpell();
        	this.setDead();
//            int j = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

//            if (block == this.inTile && j == this.inData) {
//                ++this.ticksInGround;

//                if (this.ticksInGround == 1200)
//                    this.setDead();
//            } else {
//                this.inGround = false;
//                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
//                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
//                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
//                this.ticksInGround = 0;
//                this.ticksInAir = 0;
//            }
        }
        else
        {
            ++this.ticksInAir;
            Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
            vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
            vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (movingobjectposition != null)
                vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);

            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int i;
            float f1;

            for (i = 0; i < list.size(); ++i) {
                Entity entity1 = (Entity)list.get(i);

                if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double)f1, (double)f1, (double)f1);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

                    if (movingobjectposition1 != null) {
                        double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D) {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
                movingobjectposition = new MovingObjectPosition(entity);

            if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

                // If PvP is disabled, null the mop
                if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
                    movingobjectposition = null;
            }

            float f2;
            float f4;

            if (movingobjectposition != null)
            {
                if (movingobjectposition.entityHit != null)
                {
//                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
//                    int k = MathHelper.ceiling_double_int((double)f2 * this.damage);

//                    if (this.getIsCritical())
//                        k += this.rand.nextInt(k / 2 + 2);

//                    DamageSource damagesource = null;

//                    if (this.shootingEntity == null)
//                        damagesource = DamageSource.causeIndirectMagicDamage(this, this);
//                    else
//                        damagesource = DamageSource.causeIndirectMagicDamage(this, this.shootingEntity);

//                    if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
//                        movingobjectposition.entityHit.setFire(5);

//                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float)k)) {
                        if (movingobjectposition.entityHit instanceof EntityLivingBase) {
                            EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;
//                        	System.out.println("Spell cast at " + entitylivingbase.getCommandSenderName());

                        	// If pvp, send packet
                            if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                        }
                    	this.posX = (int)entity.posX-1;
                    	this.posY = (int)entity.posY;
                    	this.posZ = (int)entity.posZ;
                    	performSpell();
                        this.setDead();
                }
                else
                {
                    this.xTile = movingobjectposition.blockX;
                    this.yTile = movingobjectposition.blockY;
                    this.zTile = movingobjectposition.blockZ;
                    this.inTile = block;
                    this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = (double)((float)(movingobjectposition.hitVec.xCoord - this.posX));
                    this.motionY = (double)((float)(movingobjectposition.hitVec.yCoord - this.posY));
                    this.motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - this.posZ));
                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
                    this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
                    this.inGround = true;
                    this.setIsCritical(false);

                    if (this.inTile.getMaterial() != Material.air)
                        this.inTile.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
                this.prevRotationPitch += 360.0F;

            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
                this.prevRotationYaw -= 360.0F;

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
                this.prevRotationYaw += 360.0F;

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float f3 = 0.99F;
            f1 = 0.05F;

            if (this.isInWater()) {
                for (int l = 0; l < 4; ++l) {
                    f4 = 0.25F;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
                }

                f3 = 0.8F;
            }

//            if (this.isWet())
//                this.extinguish();

            this.motionX *= (double)f3;
            this.motionY *= (double)f3;
            this.motionZ *= (double)f3;
            this.motionY -= (double)f1;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.func_145775_I();
        }
    }
	
	public void setEffect(String par1) { spellType = par1; }
	public void setModifier(String par1) { modifier = par1; }
	public void setBlock(Block par1) { conjuredBlock = par1; }
	
	protected void performSpell() {
		if (!worldObj.isRemote)
		if (spellType == TomeManager.SUMMON) {
			Entity entity;
			if (modifier == "HundredFlames") {
				float separation = 0.4F;
			    for (int i = -13-1; i < 17+1; i++) {
			        for (int j = -13-1; j < 17+1; j++) {
			         entity = new EntityArrow(worldObj);
			         entity.setFire(15);
			         entity.setVelocity(0, -2.5, 0);
			         entity.setLocationAndAngles(this.posX + i*separation, this.posY+75, this.posZ + j*separation, this.rotationYaw, 0.0F);
			        this.worldObj.spawnEntityInWorld(entity);
			        }
			    }			
			} else if (modifier == "SummonZombie") {
				entity = new EntitySummonZombie(this.worldObj);
	    		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
	            this.worldObj.spawnEntityInWorld(entity);
			} else if (modifier == "ZombieArmy") {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						entity = new EntityZombie(worldObj);
    	        		entity.setLocationAndAngles(this.posX + i, this.posY+1, this.posZ + j, this.rotationYaw, 0.0F);
    	                this.worldObj.spawnEntityInWorld(entity);
					}
				}
			} else if (modifier == "Skeleton") {
				entity = new EntitySummonSkeleton(worldObj, 400);
        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
        		entity.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
                this.worldObj.spawnEntityInWorld(entity);
			} else if (modifier == "WitherSkeleton") {
				entity = new EntitySummonSkeleton(worldObj, 400);
				((EntitySummonSkeleton)entity).setSkeletonType(1);
				entity.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
                this.worldObj.spawnEntityInWorld(entity);
			} else if (modifier == "Creeper") {
				entity = new EntitySummonCreeper(worldObj, 400);
        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
                this.worldObj.spawnEntityInWorld(entity);
			} else if (modifier == "PoweredCreeper") {
				EntityLightningBolt bolt = new EntityLightningBolt(worldObj, posX, posY+1, posZ);
				entity = new EntityCreeper(worldObj);
				entity.onStruckByLightning(bolt);
				entity.extinguish();
				worldObj.setBlock((int)posX, (int)posY, (int)posZ, Blocks.air);
        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
                this.worldObj.spawnEntityInWorld(entity);
			} else if (modifier == "JellyFish") {
				entity = new EntityJellyfish(worldObj);
        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
                this.worldObj.spawnEntityInWorld(entity);
			} else if (modifier == "Gnat") {
				entity = new EntityGnats(worldObj); 
        		entity.setLocationAndAngles(this.posX, this.posY+1, this.posZ, this.rotationYaw, 0.0F);
                this.worldObj.spawnEntityInWorld(entity);
			} else if (modifier == "FogOfGnats") {
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 3; j++) {
						entity = new EntityGnats(worldObj);
    	        		entity.setLocationAndAngles(this.posX + i, this.posY+1, this.posZ + j, this.rotationYaw, 0.0F);
    	                this.worldObj.spawnEntityInWorld(entity);
					}
				}
			} else {
				entity = EntityList.createEntityByName(modifier, worldObj);
	    		try{entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);}catch(Exception e){}
	            this.worldObj.spawnEntityInWorld(entity);
			}
		} else if (spellType == TomeManager.CONJUREBLOCK) {
			if (modifier.startsWith("Dome")) {
//				worldObj.setBlock((int)posX, (int)posY, (int)posZ, conjuredBlock);
				int radius = 3;
				
				if (conjuredBlock == Blocks.ice) radius = 1;
				
				int r=radius;
				for (int n=0; n<radius+1; n++) {
					for (int m=-(r/2+1); m<=(r/2+1); m++) {
						int k = (int)Math.abs(Math.floor(m/2));
						worldObj.setBlock((int)(posX-r+k), (int)posY+n, (int)(posZ-m), conjuredBlock);
						worldObj.setBlock((int)(posX+r-k), (int)posY+n, (int)(posZ+m), conjuredBlock);
						worldObj.setBlock((int)(posX+m), (int)posY+n, (int)(posZ-r+k), conjuredBlock);
						worldObj.setBlock((int)(posX-m), (int)posY+n, (int)(posZ+r-k), conjuredBlock);
					}
					if (n > 0) r--;
				}
				worldObj.setBlock((int)posX, (int)posY+radius+1, (int)posZ, conjuredBlock);

	            this.playSound("step.cloth", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
				
			} else if (modifier.startsWith("Wall")) {
				int height = 4;
				int width = 9;

				int w1=0,w2=0;
				if (width%2==1) {w2=(width+1)/2; w1=1-w2;} else {w2=width/2;w1=-w2;}
				for (int y=0; y<height; y++) {
					for (int w=w1; w<w2; w++) {
						if (Math.abs(this.motionX) > Math.abs(this.motionZ))
							worldObj.setBlock((int)posX, (int)posY+y, (int)posZ+w, conjuredBlock);
						else
							worldObj.setBlock((int)posX+w, (int)posY+y, (int)posZ, conjuredBlock);
					}
				}
	            this.playSound("step.cloth", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			} else if (modifier == "Milk") {
	            AxisAlignedBB axisalignedbb = this.boundingBox.expand(4.0D, 2.0D, 4.0D);
	            List list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
	
	            if (list != null && !list.isEmpty()) {
	                Iterator iterator = list.iterator();
	
	                while (iterator.hasNext()) {
	                    EntityLivingBase entitylivingbase = (EntityLivingBase)iterator.next();
	                    entitylivingbase.clearActivePotions();
	                }
	                
	            }
	            this.playSound("liquid.splash", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			} else {
				worldObj.setBlock((int)posX, (int)posY, (int)posZ, conjuredBlock);
			}
		}
	}
}
