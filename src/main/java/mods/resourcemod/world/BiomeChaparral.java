package mods.resourcemod.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeChaparral extends BiomeGenBase{

 public BiomeChaparral(int par1) {
  super(par1);
  this.setBiomeName("Chaparral");
  this.topBlock = Blocks.grass;
  this.heightVariation = 0.6F;
  this.rootHeight = 0.1F;
  this.theBiomeDecorator.treesPerChunk = (int) .5;
}
 
}