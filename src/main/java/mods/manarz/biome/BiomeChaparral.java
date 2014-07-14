package mods.manarz.biome;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeChaparral extends BiomeGenBase{

	public BiomeChaparral(int par1) {
		super(par1);
		this.setBiomeName("Chaparral");
		this.topBlock = Blocks.grass;
		this.heightVariation = 0.6F;
		this.rootHeight = 0.1F;
		this.theBiomeDecorator.treesPerChunk = 0;
}
	
}
