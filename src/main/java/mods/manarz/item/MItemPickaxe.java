package mods.manarz.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.manarz.Manarz;
import mods.manarz.ManarzEnums.Blade;
import mods.manarz.ManarzEnums.Hilt;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.IIcon;

public class MItemPickaxe extends ItemPickaxe {

	protected IIcon[] itemIconArray;
	protected Hilt hilt;
	protected Blade blade;
	
	public MItemPickaxe(ToolMaterial par1Enum, Hilt hil, Blade bla) {
		super(par1Enum);
		this.hilt = hil;
		this.blade = bla;
	}
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
		if (hilt != null && blade != null) {
			this.itemIcon = par1IconRegister.registerIcon(Manarz.modID + ":swords/headPickaxe" + blade.getName());
			this.itemIconArray = new IIcon[] { itemIcon, par1IconRegister.registerIcon(Manarz.modID + ":swords/handle" + hilt.getName()) };
		} else {
			this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
			this.itemIconArray = new IIcon[] { itemIcon };
		}
	}
    @SideOnly(Side.CLIENT)
    public IIcon[] getIcons() {
    	return itemIconArray;
    }

}
