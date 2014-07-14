package mods.manarz.item;

import mods.manarz.Manarz;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
//import net.minecraftforge.common.IArmorTextureProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class MItemArmor extends ItemArmor {

    public MItemArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
        super(par2EnumArmorMaterial, par3, par4);
        this.maxStackSize = 1;
//        this.setCreativeTab(tab);
//        this.setUnlocalizedName(name);
    }

   /* @Override
    public String getArmorTextureFile(ItemStack par1ItemStack) {
            return "/mods/Manarz/textures/armor/" + this.getUnlocalizedName().substring(5) + ".png";
        }*/
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IconRegister iconRegister) {
//		this.itemIcon = iconRegister.registerIcon(Manarz.modID + ":" + this.getUnlocalizedName().substring(5));
//	}
}