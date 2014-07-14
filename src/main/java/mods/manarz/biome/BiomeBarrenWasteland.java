package mods.manarz.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeBarrenWasteland extends BiomeGenBase{

	public BiomeBarrenWasteland(int par1) {
		super(par1);
		this.setBiomeName("Wasteland");
		this.topBlock = Blocks.gravel;
		this.fillerBlock = Blocks.gravel; 
		this.heightVariation = 0.0F;
		this.rootHeight = 0.0F;
		this.theBiomeDecorator.treesPerChunk = -1;
		this.theBiomeDecorator.generateLakes = true;
		this.theBiomeDecorator.deadBushPerChunk = 2;
		this.theBiomeDecorator.flowersPerChunk = -1;
		this.spawnableCreatureList.clear();
		this.waterColorMultiplier = 14745518;
		this.theBiomeDecorator.grassPerChunk = -1;

		
// this doesn't work lulz
	}
   
	@SideOnly(Side.CLIENT)

    /**
     * Provides the basic grass color based on the biome temperature and rainfall
     */
	@Override
    public int getBiomeGrassColor(int par1, int par2, int par3)
    {
        double d0 = (double)this.getFloatTemperature(par1, par2, par3);
        double d1 = (double)this.getFloatRainfall();
        return ((ColorizerGrass.getGrassColor(d0, d1) & 16711422) + 5115470) / 2;
    }
    @SideOnly(Side.CLIENT)

    /**
     * Provides the basic foliage color based on the biome temperature and rainfall
     */
    @Override
    public int getBiomeFoliageColor(int par1, int par2, int par3)
    {
        double d0 = (double)this.getFloatTemperature(par1, par2, par3);
        double d1 = (double)this.getFloatRainfall();
        return ((ColorizerFoliage.getFoliageColor(d0, d1) & 16711422) + 5115470) / 2;
    }
}


