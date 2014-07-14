package mods.manarz.entity;

import mods.manarz.ManarzEnums;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityGleamingDagger extends EntityProjectile {

	public EntityGleamingDagger(World par1World) {
		super(par1World);
		this.setDamage(6D);
//		this.setTexture(ManarzEnums.LootWeapon.WEAPON_GLEAMINGDAGGER.getName());
//		this.setRenderType(EntityProjectile.EP_DAGGER);
	}

	public EntityGleamingDagger(World par1World, EntityPlayer par2EntityPlayer, float par3, ItemStack par4ItemStack, String par5Tex) {
		super(par1World, par2EntityPlayer, par3, par4ItemStack, par5Tex);
	}

}
