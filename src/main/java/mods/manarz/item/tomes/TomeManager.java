package mods.manarz.item.tomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mods.manarz.Manarz;
import mods.manarz.entity.*;
import mods.manarz.item.MItem;
import mods.manarz.item.tomes.summons.EntitySummonCreeper;
import mods.manarz.item.tomes.summons.EntitySummonSkeleton;
import mods.manarz.item.tomes.summons.EntitySummonZombie;
import mods.resourcemod.common.ResourceMod;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TomeManager {
//	private static int t = 0;
//    public final static int TOME_LIGHTNING = t++, TOME_ZOMBIE = t++, TOME_ZOMBIEARMY = t++, TOME_SKELETONBOW = t++, TOME_CREEPER = t++, TOME_JELLYFISH = t++, TOME_UNARMEDSKELETON = t++, TOME_SHOCK = t++, TOME_PIG = t++, TOME_GNAT = t++, TOME_FOGOFGNATS = t++, TOME_COW = t++, TOME_SQUID = t++, TOME_ENDERMAN = t++, TOME_GHAST = t++,
//    	TOME_BLAZE = t++, TOME_SHEEP = t++, TOME_CAVESPIDER = t++, TOME_MAGMACUBE = t++, TOME_SILVERFISH = t++, TOME_SLIME = t++, TOME_WITCH = t++, TOME_SUPERCHARGEDCREEPER = t++, TOME_WITHERSKELETON = t++, TOME_ENDERCRYSTAL = t++, TOME_BAT = t++, TOME_CHICKEN = t++, TOME_MOOSHROOM = t++, TOME_OCELOT = t++;
//	public static final int LIGHTNING = 0, ZOMBIEARMY = 1, JELLYFISH = 4, GNAT = 5, FOGOFGNATS = 6, SUPERCHARGEDCREEPER = 7; // Add IDs here to reference custom monsters
//	public static final int CREEPER = 50, SKELETON = 51, SPIDER = 52, GIANT_ZOMBIE = 53, ZOMBIE = 54, SLIME = 55, GHAST = 56, PIG_ZOMBIE = 57, ENDERMAN = 58,
//	CAVE_SPIDER = 59, SILVERFISH = 60, BLAZE = 61, LAVA_SLIME = 62, BAT = 65, WITCH = 66, PIG = 90, SHEEP = 91, COW = 92, 
//	CHICKEN = 93, SQUID = 94, WOLF = 95, MUSHROOM_COW = 96,OCELOT = 98, VILLAGER = 120, ENDERCRYSTAL = 121;
//    public static Item tome;
//    public static Item[] tomeList = new Item[t];

//    public static final Map<String, Class> summons = new HashMap();
	public static final Map<String, int[]> draws = new HashMap();
	public static final Map<String, ItemStack[]> costs = new HashMap();
	public static final Map<String, String> types = new HashMap();
	public static final Map<String, Block> blocks = new HashMap();
	public static final Map<String, Integer> consumptionChance = new HashMap();
	public static final List<String> tomesList = new ArrayList();
	public static final String NONE = "None", SUMMON = "Summon", CONJUREBLOCK = "ConjureBlock";

    // Actually creates the tome and registers it
//    private static ItemTome newTome(String par2Unloc, String par3Display, int par4Summon, int par5Draw, int par6Color, Item[] par7Cost, int[] par8Num) {
//    	ItemTome returnTome = new ItemTome(
//	    			par4Summon, 
//	    			new String[] {par2Unloc, par2Unloc + "_2", par2Unloc + "_3"},  
//	    			new int[] { par5Draw, par5Draw*2 }, 
//	    			par6Color, 
//	    			par7Cost, 
//	    			par8Num
//    			);
//    	returnTome.setCreativeTab(Manarz.tabTomes);
//    	returnTome.setUnlocalizedName(par2Unloc);
//
//    	GameRegistry.registerItem(returnTome,  par2Unloc);
////    	LanguageRegistry.addName( returnTome,  par3Display);
//    	
//    	return returnTome;
//    }
    
    private static void addMaps(String name, String type, int draw, int chance, ItemStack[] cost) {
    	addMaps( name, type, null, draw, chance, cost);
    }
    private static void addMaps(String name, String type, Block b, int draw, int chance, ItemStack[] cost) {
    	tomesList.add(name);
    	blocks.put(name, b);
    	types.put(name, type);
//    	summons.put(name, clazz);
    	draws.put(name, new int[] {draw, draw*2});
    	costs.put(name, cost);
    	consumptionChance.put(name, chance);
    }
    
    private static Item newTome(String name, String type, int[] draw, ItemStack[] cost, int chance) {
    	ItemTome returnTome = new ItemTome( name, type, null, draw, cost, chance );
    	GameRegistry.registerItem(returnTome, name);
    	return returnTome;
    }
    private static Item newTome(String name, String type, Block b, int[] draw, ItemStack[] cost, int chance) {
    	ItemTome returnTome = new ItemTome( name, type, b, draw, cost, chance );
    	GameRegistry.registerItem(returnTome, name);
    	return returnTome;
    }
    
    // TODO Add textures for tome pullback animation
	public static int registerTomes() {
		addMaps("Lightning",      SUMMON, 50, 100, new ItemStack[]{new ItemStack(Manarz.airManaStone,   4), new ItemStack(Manarz.fireManaStone, 4)});
		addMaps("HundredFlames",  SUMMON, 15, 100, new ItemStack[]{new ItemStack(Manarz.fireManaStone, 3)});
		addMaps("SummonZombie",   SUMMON, 25, 100, new ItemStack[]{new ItemStack(Manarz.darkManaStone,  4)});
		addMaps("ZombieArmy",     SUMMON, 50, 100, new ItemStack[]{new ItemStack(Manarz.darkManaStone,  4)});
		addMaps("Skeleton",       SUMMON, 25, 100, new ItemStack[]{new ItemStack(Manarz.darkManaStone,  3)});
		addMaps("WitherSkeleton", SUMMON, 25, 100, new ItemStack[]{new ItemStack(Manarz.darkManaStone,  3)});
		addMaps("Creeper",        SUMMON, 25, 100, new ItemStack[]{new ItemStack(Manarz.darkManaStone,  3)});
		addMaps("PoweredCreeper", SUMMON, 25, 100, new ItemStack[]{new ItemStack(Manarz.darkManaStone,  3)});
		addMaps("Jellyfish",      SUMMON, 10, 100, new ItemStack[]{new ItemStack(Manarz.waterManaStone, 4)});
		addMaps("Gnat",           SUMMON, 15, 100, new ItemStack[]{new ItemStack(Manarz.darkManaStone,  2)});
		addMaps("FogOfGnats",     SUMMON, 50, 100, new ItemStack[]{new ItemStack(Manarz.darkManaStone, 12)});
		addMaps("CaveSpider",     SUMMON, 15, 100, new ItemStack[]{new ItemStack(Manarz.earthManaStone, 3), new ItemStack(Manarz.darkManaStone, 3)});
		addMaps("Silverfish",     SUMMON, 15, 100, new ItemStack[]{new ItemStack(Manarz.earthManaStone, 3), new ItemStack(Manarz.darkManaStone, 3)});
		addMaps("Blaze",          SUMMON, 25, 100, new ItemStack[]{new ItemStack(Manarz.fireManaStone,  6), new ItemStack(Manarz.airManaStone,  2)});
		addMaps("LavaSlime",      SUMMON, 15, 100, new ItemStack[]{new ItemStack(Manarz.airManaStone,   3)});
		addMaps("Bat",            SUMMON, 15, 100, new ItemStack[]{new ItemStack(Manarz.airManaStone,   1)});
		addMaps("Witch",          SUMMON, 15, 100, new ItemStack[]{new ItemStack(Manarz.darkManaStone,  3)});
		addMaps("Pig",            SUMMON,  6, 100, new ItemStack[]{new ItemStack(Manarz.earthManaStone, 1)});
		addMaps("Sheep",          SUMMON, 10, 100, new ItemStack[]{new ItemStack(Manarz.earthManaStone, 1)});
		addMaps("Cow",            SUMMON, 10, 100, new ItemStack[]{new ItemStack(Manarz.earthManaStone, 1)});
		addMaps("Chicken",        SUMMON, 15, 100, new ItemStack[]{new ItemStack(Manarz.earthManaStone, 3)});
		addMaps("Squid",          SUMMON, 10, 100, new ItemStack[]{new ItemStack(Manarz.waterManaStone, 1)});
		addMaps("Wolf",           SUMMON, 20, 100, new ItemStack[]{new ItemStack(Manarz.earthManaStone, 3)});
		addMaps("MushroomCow",    SUMMON, 15, 100, new ItemStack[]{new ItemStack(Manarz.earthManaStone, 3), new ItemStack(Manarz.lightManaStone, 2)});
		addMaps("Ozelot",         SUMMON, 15, 100, new ItemStack[]{new ItemStack(Manarz.earthManaStone, 3)});
		addMaps("Villager",       SUMMON, 50, 100, new ItemStack[]{new ItemStack(Manarz.lightManaStone, 6)});
		addMaps("EnderCrystal",   SUMMON, 15, 100, new ItemStack[]{new ItemStack(Manarz.airManaStone,   3), new ItemStack(Manarz.fireManaStone,  3)});
		addMaps("Fire",          CONJUREBLOCK, Blocks.fire, 15, 100, new ItemStack[]{new ItemStack(Manarz.fireManaStone, 3)});
		addMaps("Water",         CONJUREBLOCK, Blocks.flowing_water, 15, 100, new ItemStack[]{new ItemStack(Manarz.waterManaStone, 3)});
		addMaps("Wall",          CONJUREBLOCK, ResourceMod.adamantiteBrickB, 15, 100, new ItemStack[]{new ItemStack(Manarz.waterManaStone, 3)});
		addMaps("Glass",         CONJUREBLOCK, Blocks.glass, 15, 100, new ItemStack[]{new ItemStack(Manarz.waterManaStone, 3)});
		addMaps("DomeIce",       CONJUREBLOCK, Blocks.ice, 15, 100, new ItemStack[]{new ItemStack(Manarz.waterManaStone, 3)});
		addMaps("Milk",          CONJUREBLOCK, Blocks.air, 15, 100, new ItemStack[]{new ItemStack(Manarz.waterManaStone, 3)});
		
		Item[] retTomes = new Item[tomesList.size()];
		for (int i=0; i<tomesList.size(); i++) {
			String n = tomesList.get(i);
			retTomes[i] = newTome(n, types.get(n), blocks.get(n), draws.get(n), costs.get(n), consumptionChance.get(n));
		}

        	// Register Tome Recipes
        	GameRegistry.addShapelessRecipe(new ItemStack(Manarz.tome, 1, 0), Manarz.blankManaStone, Items.book);
        	
        	// Register Summoned Entities
        	EntityRegistry.registerGlobalEntityID(EntitySummonZombie.class,   "SummonedZombie",   EntityRegistry.findGlobalUniqueEntityId());
        	     EntityRegistry.registerModEntity(EntitySummonZombie.class,   "SummonedZombie",   6, Manarz.instance, 128, 1, true);
        	EntityRegistry.registerGlobalEntityID(EntitySummonSkeleton.class, "SummonedSkeleton", EntityRegistry.findGlobalUniqueEntityId());
        	     EntityRegistry.registerModEntity(EntitySummonZombie.class,   "SummonedSkeleton", 7, Manarz.instance, 128, 1, true);
        	EntityRegistry.registerGlobalEntityID(EntitySummonCreeper.class,  "SummonedCreeper",  EntityRegistry.findGlobalUniqueEntityId());
   	            EntityRegistry.registerModEntity(EntitySummonZombie.class,    "SummonedCreeper",  8, Manarz.instance, 128, 1, true);

        	return 0;
	}
}
