package mods.manarz.item;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import mods.manarz.Manarz;
import mods.manarz.ManarzEnums;
import mods.manarz.ManarzEnums.Addition;
import mods.manarz.ManarzEnums.Blade;
import mods.manarz.ManarzEnums.Hilt;
import mods.manarz.ManarzEnums.Legendary;
import mods.manarz.ManarzEnums.LootWeapon;
import mods.manarz.entity.EntityGleamingDagger;
import mods.manarz.entity.EntityProjectile;
import mods.manarz.entity.EntityRustyDagger;
import mods.manarz.entity.EntityShadowedDagger;
import mods.manarz.entity.EntitySilverDagger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MItemSword extends ItemSword {
	private float weaponDamage;
	private ToolMaterial toolMaterial;
    public float efficiencyOnProperMaterial = 4.0F;
	public String displayName = "Manarz Sword", unlocalizedName = "manarzSword";
	private boolean isThrowing = false;

	public Legendary legendary = null;
	// Custom Sword Variables
	public Hilt hilt = null;
	public Blade blade = null;

	// Loot Weapon Variables
	public Addition addition = null;
	public LootWeapon weapon = null;
//	protected int socketLevel = -1;
	protected Block heldBlock = null;
	
	// Custom/Loot Shared Variables
	public IIcon itemIcon;
	protected IIcon[] itemIconArray;

	// Uses info from SwordToolManager
	public MItemSword(ToolMaterial par2Enum, Hilt par4Hilt, Blade par5Blade) {
		super(par2Enum);
		hilt = par4Hilt;
		blade = par5Blade;
	}
	
    public MItemSword(ToolMaterial par2Enum, Legendary par3Legendary) {
    	super(par2Enum);
    	legendary = par3Legendary;
    }
    public MItemSword(ToolMaterial par2Enum, LootWeapon par3Weapon, Addition par4Addition) {
		super(par2Enum);
		
		weapon = par3Weapon;
        addition = par4Addition;
        this.setMaxStackSize(64);
    }

	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
    	if (hilt != null && blade != null) {
	    	par3List.add(hilt.getName() + " Hilt");
	    	par3List.add(blade.getName() + " Blade");
    	} else if (addition != null) 
    		par3List.add("Socket: " + LanguageRegistry.instance().getStringLocalization("item." + addition.getName() + ".name"));
    	else if (weapon != null && addition == null)
    		par3List.add("Socket: No Gem");
//        if (socketLevel > -1)
//        	par3List.add("Socket: Level " + socketLevel);
    }

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase target, EntityLivingBase player){
		par1ItemStack.damageItem(1, player);

		if (blade != null && hilt != null) {
        	applyEffects(target, player, hilt.attack());
        	applyEffects(target, player, blade.attack());
		} else if (weapon != null) {
			if (addition != null) applyEffects(target, player, addition.attack());
        	applyEffects(target, player, weapon.attack());
		} else if (legendary != null)
			applyEffects(target, player, legendary.attack());
		
    	return true;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) 
	{
    	String tex = "_notexture";
		if (!par2World.isRemote)
		if (hilt != null && blade != null) {
        	if (applyEffects(null, par3EntityPlayer, hilt.rightClick())) par1ItemStack.damageItem(1, par3EntityPlayer);
        	if (applyEffects(null, par3EntityPlayer, blade.rightClick())) par1ItemStack.damageItem(1, par3EntityPlayer);
		} else if (weapon != null) {
	        if (addition != null)
	        	if (applyEffects(null, par3EntityPlayer, addition.rightClick())) par1ItemStack.damageItem(1, par3EntityPlayer);
	        applyEffects(null, par3EntityPlayer, weapon.rightClick());
	        tex = weapon.getName();
		} else if (legendary != null) {
			applyEffects(null, par3EntityPlayer, legendary.rightClick());
			tex = legendary.getName();
		}

        if (isThrowing) {
//        	EntityProjectile entityprojectile = new EntityProjectile(par2World, par3EntityPlayer, 1.0F, par1ItemStack, tex);
//            EntityArrow entityprojectile = new EntityArrow(par2World, par3EntityPlayer, 1.0F);
//            entityprojectile.setDamage(16.0D);
            if (!par2World.isRemote) {
            	if (weapon == LootWeapon.WEAPON_GLEAMINGDAGGER) par2World.spawnEntityInWorld(new EntityGleamingDagger(par2World, par3EntityPlayer, 1.0F, par1ItemStack, tex));
            	if (weapon == LootWeapon.WEAPON_RUSTYDAGGER)    par2World.spawnEntityInWorld(new EntityRustyDagger(par2World, par3EntityPlayer, 1.0F, par1ItemStack, tex));
            	if (weapon == LootWeapon.WEAPON_SHADOWEDDAGGER) par2World.spawnEntityInWorld(new EntityShadowedDagger(par2World, par3EntityPlayer, 1.0F, par1ItemStack, tex));
            	if (weapon == LootWeapon.WEAPON_SILVERDAGGER)   par2World.spawnEntityInWorld(new EntitySilverDagger(par2World, par3EntityPlayer, 1.0F, par1ItemStack, tex));
            	
//                par2World.spawnEntityInWorld(entityprojectile);
            }

            if(!par3EntityPlayer.capabilities.isCreativeMode)
            	--par1ItemStack.stackSize;
            
            isThrowing = false;
        } else

   		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	int[][] effect = new int[2][6];
    	boolean ret = false;

    	
    	if (weapon != null) effect[0] = weapon.itemUse();
    	if (addition != null) effect[1] = addition.itemUse();
    	if (blade != null) effect[0] = blade.itemUse();
    	if (hilt != null) effect[1] = hilt.itemUse();
    	if (legendary != null) {
    		effect[0] = legendary.itemUse();
    		effect[1] = null;
    	}

    	for (int i=0; i<2; i++) {
    		if (effect[i] != null) if (effect[i][0] != ManarzEnums.none) {
    			if (effect[i][0] == ManarzEnums.setFire) {
	                if (par7 == 0) --par5;
	                if (par7 == 1) ++par5;
	                if (par7 == 2) --par6;
	                if (par7 == 3) ++par6;
	                if (par7 == 4) --par4;
	                if (par7 == 5) ++par4;
	                if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
	                    if (par3World.isAirBlock(par4, par5, par6)) {
	                        par3World.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
	                        par3World.setBlock(par4, par5, par6, Blocks.fire);
		                    ret = true;
	                    }
	                }
    			} else if (effect[i][0] == ManarzEnums.extinguish) {
	                for (int x=-1; x<2; x++)
	                	for (int y=0; y<3; y++)
	                		for (int z=-1; z<2; z++)
				                if (par2EntityPlayer.canPlayerEdit(par4+x, par5+y, par6+z, par7, par1ItemStack)) {
				                    if (par3World.getBlock(par4+x, par5+y, par6+z) == Blocks.fire) {
				                        par3World.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "random.fizz", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				                        par3World.setBlock(par4+x, par5+y, par6+z, Blocks.air);
					                    ret = true;
				                    }
				                }
    			} else if (effect[i][0] == ManarzEnums.pickUp) {
    				Block b = Block.getBlockById(effect[i][1]);
    				if (!par3World.isRemote)
    				if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
    					if (this.heldBlock != null && par3World.getBlock(par4, par5+1, par6) == Blocks.air) {
	                        par3World.setBlock(par4, par5+1, par6, heldBlock);
	                        par3World.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "dig.grass", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
	                        this.heldBlock = null;
    					} else if (par3World.getBlock(par4, par5, par6) == b) {
	                        par3World.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "dig.grass", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
	                        this.heldBlock = b;
	                        par3World.setBlock(par4, par5, par6, Blocks.air);
    					}
    				}
    			} else if (effect[i][0] == ManarzEnums.bolt) {
	                if (par7 == 0) --par5;
	                if (par7 == 1) ++par5;
	                if (par7 == 2) --par6;
	                if (par7 == 3) ++par6;
	                if (par7 == 4) --par4;
	                if (par7 == 5) ++par4;
	                if (!par3World.isRemote)
	                	par3World.addWeatherEffect(new EntityLightningBolt(par3World, par4, par5, par6));
    				ret = true;
    			} else if (effect[i][0] == ManarzEnums.harvest) {
    				int rad = (effect[i][1] - 1)/2;
    				if (!par3World.isRemote)
	                for (int x=-rad; x<=rad; x++) for (int y=-rad; y<=rad; y++) for (int z=-rad; z<=rad; z++)
		                if (par2EntityPlayer.canPlayerEdit(par4+x, par5+y, par6+z, par7, par1ItemStack)) {
		                    if (par3World.getBlock(par4+x, par5+y, par6+z) == Blocks.wheat && par3World.getBlockMetadata(par4+x, par5+y, par6+z) == 7) {
		                    	ArrayList a = Blocks.wheat.getDrops(par3World, par4+x, par5+y, par6+z, 7, 0);
		                    	Iterator it = a.iterator();
		                    	while(it.hasNext())
		                    		par3World.spawnEntityInWorld(new EntityItem(par3World, par4+x, par5+y, par6+z, (ItemStack)it.next()));
		                        par3World.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "dig.grass", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
		                        par3World.setBlock(par4+x, par5+y, par6+z, Blocks.wheat, 2, 3);
			                    ret = true;
		                    }
		                }
    			}
    		}
    	}
    	
   		par2EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        par1ItemStack.damageItem(ret?1:0, par2EntityPlayer);
        return ret;
    }

	protected boolean randomChance(int par1) {
    	return (par1==0 || itemRand.nextInt(par1) == 0);
    }
	private void applyEffect(EntityLivingBase par1Entity, PotionEffect par2Effect) {
        if (par1Entity.isPotionApplicable(par2Effect)) par1Entity.addPotionEffect(par2Effect);  
	}
	private boolean applyEffects(EntityLivingBase target, EntityLivingBase player, int[][] effects) {
		boolean damage = false;
		EntityLivingBase[] entity = { target, player };
		if (effects != null) if (effects[0] != null) {
			for (int i = 0; i<effects.length; i++) {
				if (randomChance(effects[i][0])) {
					if (effects[i][2] == ManarzEnums.setFire)
						entity[effects[i][1]].setFire(effects[i][3]);
					else if (effects[i][2] == ManarzEnums.extinguish)
						entity[effects[i][1]].extinguish();
					else if (effects[i][2] == ManarzEnums.heal) 
						entity[effects[i][1]].heal(effects[i][3]);
		    		else if (effects[i][2] == ManarzEnums.harm)
		    			entity[effects[i][1]].attackEntityFrom(DamageSource.magic, 6 << effects[i][4]);
		    		else if (effects[i][2] == ManarzEnums.thrown) {
		    			// This is handled in onItemRightClick
		    			isThrowing = true;
//						System.out.println("Thrown Weapons not yet implemented!");
					} else if (effects[i][2] == ManarzEnums.randomPotion) {
						if (!(blade == Blade.BLADE_WYRDSTONE && hilt == Hilt.HILT_WYRDSTONE)) entity[effects[i][1]].clearActivePotions();
						PotionEffect randomPotion = new PotionEffect(itemRand.nextInt(22)+1, (itemRand.nextInt(5) + 1) * 100, itemRand.nextInt(1));
						System.out.println(randomPotion.toString());
					    if (randomPotion.getPotionID() == Potion.heal.id) entity[effects[i][1]].heal(6);
				    	else if (randomPotion.getPotionID() == Potion.harm.id) entity[effects[i][1]].attackEntityFrom(DamageSource.magic, 6);
				    	else applyEffect(entity[effects[i][1]], randomPotion);
					} else // if PotionEffect
						applyEffect(entity[effects[i][1]], new PotionEffect(effects[i][2], effects[i][3], effects[i][4]));
					
					damage = true;
				} // end if randomChance
			} // end for
		} // end if effects
		return damage;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		if (hilt != null && blade != null) {
			this.itemIcon = par1IconRegister.registerIcon(Manarz.modID + ":swords/blade" + blade.getName());
			this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
			this.itemIconArray = new IIcon[] { itemIcon, par1IconRegister.registerIcon(Manarz.modID + ":swords/hilt" + hilt.getName()) };
		} else if (weapon != null) {
			this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
			this.itemIconArray = new IIcon[] { itemIcon };
		} else if (legendary != null) {
			this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
		} else {
			this.itemIcon = par1IconRegister.registerIcon(Manarz.modID + ":swords/_notexture");
			this.itemIconArray = new IIcon[] { itemIcon };
		}
	}
    @SideOnly(Side.CLIENT)
    public IIcon[] getIcons() {
    	return itemIconArray;
    }
}
