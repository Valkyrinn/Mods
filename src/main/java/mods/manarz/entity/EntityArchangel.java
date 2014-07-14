package mods.manarz.entity;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIWander;

public class EntityArchangel extends EntityFlying implements IMob {
	
	public EntityArchangel(World par1World) {
		super(par1World);
		//this.texture = null;
		this.experienceValue = 6;
//        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(120);
	}

	/*
	public int getMaxHealth() {
		return 120;
	}*/
	
	public boolean isAIEnabled() {
		return true;
	}
}
