package mods.manarz.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;

public class MItemBase extends Item {

	public MItemBase() {
		super();
	}
	
    @SideOnly(Side.CLIENT)
    public String getIconString()
    {
        return this.iconString == null ? "MISSING_ICON_ITEM_" + itemRegistry.getIDForObject(this) + "_" + this.getUnlocalizedName() : this.iconString;
    }
	
}
