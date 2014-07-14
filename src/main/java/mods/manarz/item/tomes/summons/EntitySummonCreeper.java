package mods.manarz.item.tomes.summons;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.World;

public class EntitySummonCreeper extends EntityCreeper{

	private int spellDuration;

	public EntitySummonCreeper(World par1World) {
		this(par1World, 400);
	}
	
	public EntitySummonCreeper(World par1World, int par2Duration) {
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
