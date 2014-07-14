package mods.manarz.item.tomes;

import mods.manarz.Manarz;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemSummonCreeper extends Item{
	public int par51;
	
	public ItemSummonCreeper(int par1) {
		super();
	}
	
//	public void registerIcons(IconRegister iconRegister) {
//		this.itemIcon = iconRegister.registerIcon(Manarz.modID + ":tomeCreeper");
//	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }
	
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}
	
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer, par1ItemStack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return event.result;
        }

        if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Manarz.darkManaStone))
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
         
        }
        return par1ItemStack;
    }
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4, int a)
    {
		par51 = 0;
        int i = this.getMaxItemUseDuration(par1ItemStack) - par4;
        int j = i / 3;
        ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        boolean flag = par3EntityPlayer.capabilities.isCreativeMode;
        if (event.charge == 0) {
        	System.out.println(event.charge);
        }else{
        	System.out.println(event.charge);
        }
        if (flag || par3EntityPlayer.inventory.hasItem(Items.arrow))
        {
        	EntitySummonCreeper entity = new EntitySummonCreeper(par2World);
        	 par2World.spawnEntityInWorld(new EntitySummonCreeper(par2World));
        }
    }
	

}
