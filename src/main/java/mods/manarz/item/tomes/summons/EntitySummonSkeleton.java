package mods.manarz.item.tomes.summons;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;

public class EntitySummonSkeleton extends EntitySkeleton {
	private int spellDuration;
	
	public EntitySummonSkeleton(World par1World) {
		this(par1World, 400);
	}

	public EntitySummonSkeleton(World par1World, int par2Duration) {
		super(par1World);
		this.spellDuration = par2Duration;
		
	}
	
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!this.worldObj.isRemote) {
			spellDuration--;
			if (spellDuration <= 0) this.kill();
			
		}
	}

}
