package mods.resourcemod.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.resourcemod.common.ResourceMod;
import net.minecraft.item.Item;

public class ResourceItem extends Item {

	public ResourceItem(int id) {
		super();
	}

	/**
     * Returns the unlocalized name of this item.
     */
    public String getUnlocalizedName2()
    {
        return this.getUnlocalizedName().substring(5);
    }
	
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IconRegister par1IconRegister) {
//		this.itemIcon = par1IconRegister.registerIcon(ResourceMod.modid + ":" + this.getUnlocalizedName2());
//	}
}