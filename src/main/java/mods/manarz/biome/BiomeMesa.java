package mods.manarz.biome;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeMesa extends BiomeGenBase{

	public BiomeMesa(int par1) {
		super(par1);
		this.setBiomeName("Mesa");
		this.topBlock = Blocks.stone;
		this.fillerBlock = Blocks.stone; 
		this.heightVariation = 0.1F;
		this.rootHeight = 2.2F;
		this.theBiomeDecorator.treesPerChunk = -1;
		this.theBiomeDecorator.generateLakes = false;
		this.spawnableCreatureList.clear();
	
}
}
