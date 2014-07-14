package mods.manarz.item.tomes;

import mods.manarz.Manarz;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;

public class ItemSummonZombie extends Item{
	public ItemSummonZombie(int par1) {
		super();
	}
	@Override
	 public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer par3EntityPlayer) {
		
		if(par3EntityPlayer.capabilities.isCreativeMode||par3EntityPlayer.inventory.consumeInventoryItem(Manarz.darkManaStone)){
		
	      	 par2World.spawnEntityInWorld(new EntitySummonZombie(par2World));

	     //par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
	     	   }
		
		
		
		return par1ItemStack;
	}
	
//	public void registerIcons(IconRegister iconRegister) {
//		this.itemIcon = iconRegister.registerIcon(Manarz.modID + ":tomeZombie");
//	}
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }
}
