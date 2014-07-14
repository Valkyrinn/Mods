package mods.manarz.block;


import java.util.Random;


import mods.manarz.Manarz;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockManaOre extends ManarzBlockBase {
	
	protected Item drop;
	
	public BlockManaOre(Item par2Drop) {
		super(Material.rock);
		this.setHardness(4.0F);
		this.setStepSound(Block.soundTypeStone);
		drop = par2Drop;
	}
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return drop;
	}
	public int quantityDroppedWithBonus(int par1, Random par2Random)
    {
        return this.quantityDropped(par2Random) + par2Random.nextInt(par1 + 1);
    }
    public int quantityDropped(Random par1Random)
    {
        return 6 + par1Random.nextInt(4);
    }
//	public void registerIcons(IconRegister iconRegister) {
//	this.blockIcon = iconRegister.registerIcon(Manarz.modID + ":airManaOre");
//	}
}
