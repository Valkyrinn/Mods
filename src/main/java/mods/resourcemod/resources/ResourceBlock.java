package mods.resourcemod.resources;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.resourcemod.common.ResourceMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ResourceBlock extends Block {

	public ResourceBlock(int id, Material par2Material) {
		super(par2Material);
	}

	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return (Item) (this == ResourceMod.oreRediamond ? ResourceMod.rediamond : (this == ResourceMod.oreWyrdstone ? ResourceMod.wyrdstoneCrystal : Item.getItemFromBlock(this)));
	}

	public int quantityDropped(Random par1Random)
    {
        return 1;
    }
    public int quantityDroppedWithBonus(int par1, Random par2Random)
    {
        if (par1 > 0 && this != Block.getBlockFromItem(this.getItemDropped(0, par2Random, par1)))
        {
            int j = par2Random.nextInt(par1 + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return this.quantityDropped(par2Random) * (j + 1);
        }
        else
        {
            return this.quantityDropped(par2Random);
        }
    }
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);

        if (Block.getBlockFromItem(this.getItemDropped(par5, par1World.rand, par7)) != this)
        {
            int j1 = 0;

            if (this == ResourceMod.oreWyrdstone)
            {
                j1 = MathHelper.getRandomIntegerInRange(par1World.rand, 0, 2);
            }
            else if (this == ResourceMod.oreRediamond)
            {
                j1 = MathHelper.getRandomIntegerInRange(par1World.rand, 3, 7);
            }

            this.dropXpOnBlockBreak(par1World, par2, par3, par4, j1);
        }
    }
	
//    @Override
//    public boolean canDragonDestroy(World world, int x, int y, int z)
//    {
//        return blockID != ResourceMod.oreMindsilverEnd.blockID && blockID != ResourceMod.oreOriumEnd.blockID;
//    }
    
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IconRegister par1IconRegister) {
//		this.blockIcon = par1IconRegister.registerIcon(ResourceMod.modid + ":" + this.getUnlocalizedName().substring(5));
//	}
}
