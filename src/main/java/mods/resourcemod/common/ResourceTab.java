package mods.resourcemod.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
	import cpw.mods.fml.relauncher.Side;
	import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
final class ResourceTab extends CreativeTabs {

	private int tabIcon;
	
    ResourceTab(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    @SideOnly(Side.CLIENT)

    /**
     * the itemID for the item to be displayed on the tab
     */
    public int getTabIconItemIndex()
    {
        return tabIcon;
    }
    
    public void setIcon(int par1) {
    	tabIcon = par1;
    }

    @SideOnly(Side.CLIENT)

    /**
     * only shows items which have tabToDisplayOn == this
     */
    public void displayAllReleventItems(List par1List)
    {
        super.displayAllReleventItems(par1List);
    }

	@Override
	public Item getTabIconItem() {
		return null;
	}

}
