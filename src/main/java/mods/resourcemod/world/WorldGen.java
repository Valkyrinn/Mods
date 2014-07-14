package mods.resourcemod.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator
{
	private Block blockID = Blocks.stone;
	private int dimension = 0;
	private int rarity = 20;
	private int veinsize = 4;
	private int minHeight = 0;
	private int maxHeight = 64;
	private Block replaces = Blocks.stone;
	private String biome = "";

	public WorldGen(Block par1BlockID, int par2Rarity, int par3VeinSize, int par5MaxHeight) {
		this(par1BlockID, par2Rarity, par3VeinSize, 0, par5MaxHeight, "", 0, Blocks.stone);
	}

	public WorldGen(Block par1BlockID, int par2Rarity, int par3VeinSize, int par4MaxHeight, String par5Biome) {
		this(par1BlockID, par2Rarity, par3VeinSize, 0, par4MaxHeight, par5Biome, 0, Blocks.stone);
	}
  
	public WorldGen(Block par1BlockID, int par2Rarity, int par3VeinSize, int par4MaxHeight, String par5Biome, int par6Dim, Block par7Replaces) {
		this(par1BlockID, par2Rarity, par3VeinSize, 0, par4MaxHeight, par5Biome, par6Dim, par7Replaces);
	}
	
	public WorldGen(Block par1BlockID, int par2Rarity, int par3VeinSize, int par4MinHeight, int par5MaxHeight, String par6Biome, int par7Dim, Block par8Replaces) {
		this.blockID = par1BlockID;
		this.rarity = par2Rarity;
		this.veinsize = par3VeinSize;
		this.minHeight = par4MinHeight;
		this.maxHeight = par5MaxHeight;
		this.biome = par6Biome;
		this.dimension = par7Dim;
		this.replaces = par8Replaces;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		//if (this.dimension == world.provider.dimensionId) 
			generateSurface(world, random, chunkX*16, chunkZ*16);
	}


	public void generateSurface(World par1World, Random par2Random, int chunkX, int chunkZ)
	{
		String s = par1World.getBiomeGenForCoords(chunkX + 8, chunkZ + 8).biomeName;
		if (s.startsWith(this.biome) || this.biome == "")
			for (int i = 0; i < this.rarity; i++)
			{
				int posX = chunkX + par2Random.nextInt(16);
				int posY = par2Random.nextInt(maxHeight); //par2Random.nextInt(this.maxHeight - this.minHeight) + this.minHeight;
				int posZ = chunkZ + par2Random.nextInt(16);
				//System.out.println(this.blockID + " : (" + posX + ", " + posY + ", " + posZ + ")");
				(new WorldGenMinable(this.blockID, this.veinsize, this.replaces)).generate(par1World, par2Random, posX, posY, posZ);
			}
	}
}
