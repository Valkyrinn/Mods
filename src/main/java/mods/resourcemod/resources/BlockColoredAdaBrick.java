package mods.resourcemod.resources;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.resourcemod.common.ResourceMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockColoredAdaBrick extends BlockColored {
    private IIcon[] iconArray;
    public static final String[] colors = {"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "LightGreen", "Yellow", "LightBlue", "Magenta", "Orange", "White"};
    public static final String[] names = {"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "Silver", "Gray", "Pink", "Lime", "Yellow", "Light Blue", "Magenta", "Orange", "White"};

	public BlockColoredAdaBrick(int id, Material par2Material) {
		super(par2Material);
		//this.setBlockName("adamithril");
		this.setCreativeTab(ResourceMod.tabResourceBlocks);
	}

	/**
	     * Determines the damage on the item the block drops. Used in cloth and wood.
	     */
	public int damageDropped(int metadata)
	{
	     return metadata;
	}
	/**
	     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	     */
	public void getSubBlocks(Block blockID, CreativeTabs creativeTabs, List itemList)
	{
	     for (int j = 0; j < colors.length; ++j)
	     {
	         itemList.add(new ItemStack(blockID, 1, j));
	     }
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		iconArray = new IIcon[16];
		
	     for (int j = 0; j < this.iconArray.length; ++j)
	    	 iconArray[j] = par1IconRegister.registerIcon(ResourceMod.modid + ":" + "adamithril" + colors[j]);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2) {
		return iconArray[par2];
	}

}
