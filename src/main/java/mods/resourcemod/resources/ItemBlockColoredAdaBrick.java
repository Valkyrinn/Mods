package mods.resourcemod.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockColoredAdaBrick extends ItemBlock
{
	public ItemBlockColoredAdaBrick(Block par1) {
		super(par1);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("iadabrick");
	}
		
	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int par1)
    {
        return this.field_150939_a.func_149735_b(2, BlockColored.func_150032_b(par1));
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
	@Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
    	return "adamithril" + BlockColoredAdaBrick.colors[itemStack.getItemDamage()];
    }
	
}

