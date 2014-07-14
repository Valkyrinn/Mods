package mods.manarz.item.tomes;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySummonLightning extends EntitySpell
{
    public EntitySummonLightning(World par1World)
    {
        super(par1World);
        this.spellType = TomeManager.SUMMON;
        this.modifier = "Lightning";
    }
	public EntitySummonLightning(World par1World, EntityLivingBase par2EntityLivingBase, float par3, ItemStack par4ItemStack, String par5Name) {
		super(par1World, par2EntityLivingBase, par3, par4ItemStack, par5Name);
	}
	
	public void onUpdate() { super.onUpdate(); }
	protected void performSpell() { 
		EntityLightningBolt bolt = new EntityLightningBolt(this.worldObj, this.posX, this.posY, this.posZ);
		this.worldObj.spawnEntityInWorld(bolt);
        this.worldObj.addWeatherEffect(bolt);
	}
}
