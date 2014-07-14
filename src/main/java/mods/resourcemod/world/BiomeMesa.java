package mods.resourcemod.world;

import java.util.Random;

import mods.resourcemod.common.ResourceMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenLakes;

public class BiomeMesa extends BiomeGenBase{
	
	public BiomeMesa(int par1) {
		super(par1);
		this.setBiomeName("Mesa");
		this.topBlock = ResourceMod.granite;
		this.fillerBlock = ResourceMod.granite; 
		this.heightVariation = 0.1F;
		this.rootHeight = 2.2F;
		this.theBiomeDecorator.treesPerChunk = 0;
		this.theBiomeDecorator.generateLakes = false;
		
		
}
	public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        super.decorate(par1World, par2Random, par3, par4);

        if (par2Random.nextInt(10) == 0)
        {
            int k = par3 + par2Random.nextInt(16) + 8;
            int l = par4 + par2Random.nextInt(16) + 8;
            WorldGenLakes worldgenlakes = new WorldGenLakes(Blocks.lava);
            worldgenlakes.generate(par1World, par2Random, k, par1World.getHeightValue(k, l) + 1, l);
        }
    }
}
