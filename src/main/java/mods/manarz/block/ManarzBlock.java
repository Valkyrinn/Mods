package mods.manarz.block;

import cpw.mods.fml.common.registry.GameRegistry;
import mods.manarz.Manarz;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ManarzBlock {
	protected static int i = 0, t=9;
	public static Block[] blockList = new Block[t];
	protected static String[] blockNames = new String[t];
	
	public static Block block(String name, CreativeTabs tab, Material mat) {
		return block(name, tab, new ManarzBlockBase(mat));
	}
	
	public static Block block(String name, CreativeTabs tab, Block mBlock) {
		mBlock.setBlockName(name);
		mBlock.setCreativeTab(tab);
		mBlock.setBlockName(name);
		mBlock.setBlockTextureName(Manarz.modID + ":" + name);

		blockList[i] = mBlock;
		blockNames[i] = name;
		i++;
		
//		GameRegistry.registerBlock(mBlock, name);
		return mBlock;
	}
    public static void registerBlocks() {
    	for (int j=0; j<i; j++)
    		GameRegistry.registerBlock(blockList[j], blockNames[j]);
    }

//	public static void registerBlocks() {
////		GameRegistry.registerBlock(Manarz.airManaOre,       "airManaOre");
////		GameRegistry.registerBlock(Manarz.darkManaOre,      Manarz.darkManaOre.getUnlocalizedName());
////		GameRegistry.registerBlock(Manarz.earthManaOre,     Manarz.earthManaOre.getUnlocalizedName());
////		GameRegistry.registerBlock(Manarz.fireManaOre,      Manarz.fireManaOre.getUnlocalizedName());
////		GameRegistry.registerBlock(Manarz.lightManaOre,     Manarz.lightManaOre.getUnlocalizedName());
////		GameRegistry.registerBlock(Manarz.prismaticManaOre, Manarz.prismaticManaOre.getUnlocalizedName());
////		GameRegistry.registerBlock(Manarz.waterManaOre,     Manarz.waterManaOre.getUnlocalizedName());
////		GameRegistry.registerBlock(Manarz.wattleanddaub,    Manarz.wattleanddaub.getUnlocalizedName());
////		GameRegistry.registerBlock(Manarz.wattle2,          Manarz.wattle2.getUnlocalizedName());
//		for (int j=0; j<9; j++)
//    		GameRegistry.registerBlock(blocklist[j], blockNames[j]);
//	}
}
