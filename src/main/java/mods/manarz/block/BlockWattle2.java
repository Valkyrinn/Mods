package mods.manarz.block;

import mods.manarz.Manarz;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockWattle2 extends ManarzBlockBase {
	
	public BlockWattle2(Material material) {
		super(Material.wood);
		this.setHardness(4.0F);
		this.setStepSound(Block.soundTypeWood);
//		Block.blockFireSpreadSpeed[id] = 30;
//		Block.blockFlammability[id] = 4; 
		//this.setBurnProperties(id, 5, 5);
	}
	
//	public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
//		return true;
//	}
//	
//	public void registerIcons(IconRegister iconRegister) {
//		this.blockIcon = iconRegister.registerIcon(Manarz.modID + ":wattleDaubVer");
//	}
}
