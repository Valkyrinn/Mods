package mods.manarz.block;

import mods.manarz.Manarz;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;

public class BlockWattle extends ManarzBlockBase {
	
	public BlockWattle(Material material) {
		super(Material.wood);
		this.setHardness(4.0F);
		this.setStepSound(Block.soundTypeWood);
		//Block.blockFireSpreadSpeed[id] = 30;
		//Block.blockFlammability[id] = 4; 
	}
	
//	public void registerIcons(IconRegister iconRegister) {
//	this.blockIcon = iconRegister.registerIcon(Manarz.modID + ":wattleDaub");
//	}
}
// TODO Set everything on fire. =D