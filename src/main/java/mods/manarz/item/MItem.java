package mods.manarz.item;


import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.manarz.Manarz;
import mods.manarz.ManarzEnums.Blade;
import mods.manarz.ManarzEnums.Hilt;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;

public class MItem {
	protected static int i = 0, t=2000;
	protected static Item[] itemList = new Item[t];
	protected static String[] itemNames = new String[t];

	public static Item item(String name, CreativeTabs tab) {
		return item(name, tab, new MItemBase(), "");
	}
	public static Item item(String name, CreativeTabs tab, String folder) {
		return item(name, tab, new Item(), folder);
	}
	
	public static Item item(String name, CreativeTabs tab, Item mItem) {
		return item(name, tab, mItem, "");
	}
	
	public static Item item(String name, CreativeTabs tab, Item mItem, String folder) {
		mItem.setUnlocalizedName(name);
		mItem.setCreativeTab(tab);
		if (mItem instanceof MItemSword) {
			if (((MItemSword)mItem).weapon != null) {
				mItem.setTextureName(Manarz.modID + ":weapons/" + ((MItemSword)mItem).weapon.getName());
			} else if (((MItemSword)mItem).blade != null) {
				mItem.setTextureName(Manarz.modID + ":swords/blade" + ((MItemSword)mItem).blade.getName());
				//mItem.setTextureName(Manarz.modID + ":swords/hilt" + ((ManarzItemSword)mItem).hilt.getName());
			} else
				mItem.setTextureName(Manarz.modID + ":" + name);
		} else
			mItem.setTextureName(Manarz.modID + ":" + folder + name);
		
		//System.out.println("Index: " + i + " - " + name);
		itemList[i] = mItem;
		itemNames[i] = name;
		i++;
		
//		GameRegistry.registerItem(mItem, name);
		return mItem;
	}
	
	public static void registerItems() {
		for (int j=0; j<i; j++)
			GameRegistry.registerItem(itemList[j], itemNames[j]);
	}

//	public static Item item(String name, CreativeTabs tab, ToolMaterial mat, Hilt hil, Blade bla, String type) {
//		Item mItem;
//		if (type == "Pickaxe")
//			mItem = new ManarzItemPickaxe(mat, hil, bla);
//		else if (type == "Shovel")
//			mItem = new ManarzItemSpade(mat, hil, bla);
//		else if (type == "Axe")
//			mItem = new ManarzItemAxe(mat, hil, bla);
//		else mItem = new Item();
//		mItem.setUnlocalizedName(name);
//		mItem.setCreativeTab(tab);
//		mItem.setTextureName(Manarz.modID + ":swords/head"+ type + name);
//		
//		GameRegistry.registerItem(mItem, name);
//		return new Item();
//	}

	/*
	public ManarzItem(String par2Name, CreativeTabs tab) {
		super();
		this.setUnlocalizedName(par2Name);
		this.setCreativeTab(tab);
	}
	//@SideOnly(Side.CLIENT)
	//public void registerIcons(IconRegister par1IconRegister) {
	//	this.itemIcon = par1IconRegister.registerIcon(Manarz.modID + ":" + this.getUnlocalizedName().substring(5));
	//}
	 */
}
