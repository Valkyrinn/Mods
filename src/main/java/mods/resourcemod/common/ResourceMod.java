/**
 * Title: ResourceMod
 * Description: A mod for Minecraft that adds resources for use with other mods.
 * Authors: Fodimo, Trizmo, Valkyrinn
  **/

package mods.resourcemod.common;

import mods.resourcemod.common.CommonProxy;


import mods.resourcemod.resources.BlockColoredAdaBrick;
import mods.resourcemod.resources.ItemBlockColoredAdaBrick;
import mods.resourcemod.resources.ResourceBlock;
import mods.resourcemod.resources.ResourceItem;
import mods.resourcemod.world.BiomeChaparral;
import mods.resourcemod.world.BiomeMesa;
import mods.resourcemod.world.WorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid="resourcemod", name="ResourceMod", version="0.1.1")
//@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class ResourceMod {

        @Instance("resourcemod")
        public static ResourceMod instance;
        public static String modid = "resourcemod";

        //Add custom tab
        public static CreativeTabs tabResourceItems = new CreativeTabs("tabResourceItems") {
//        	public ItemStack getIconItemStack() {
//        		return new ItemStack(ingotBronze, 1, 0);
//        	}

			@Override
			public Item getTabIconItem() {
				return ingotBronze;
			}
        };public static CreativeTabs tabResourceBlocks = new CreativeTabs("tabResourceBlocks") {
//        	public ItemStack getIconItemStack() {
//        		return new ItemStack(adamithrilBrickI, 1, 0);
//        	}

			@Override
			public Item getTabIconItem() {
				return Item.getItemFromBlock(adamithrilBrickB);
			}
        };
        //public static ResourceTab tabResourceItems = new ResourceTab(16, "tabResourceItems");
    	//public static ResourceTab tabResourceBlocks = new ResourceTab(17, "tabResourceBlocks");

        //Biome
        public BiomeGenBase chaparral;
        public BiomeGenBase mesa;


        /* Starting Item Number n
         * Called in: newBlock(), newItem()
         * Purpose: The registry number for each item or block added is incremented off this variable
         */
        private static int n = 175, m = 425, b=0, i=0;
        private static ResourceBlock[] blockList = new ResourceBlock[65];
        private static String[] blockNames = new String[65];
        private static ResourceItem[] itemList = new ResourceItem[35];
        private static String[] itemNames = new String[35];

        // Instantiate new Blocks
        public final static Block 
	    	// Misc [5]
        	// Variable                      Display Name                      Material     Hardness        StepSound           Unlocalized Name    Harvest Tool, Level
        	granite              = newBlock("Granite",                        Material.rock,  10.0F, Block.soundTypeStone, "granite",              "pickaxe", 2),
	    	coralBrain           = newBlock("Coral Brain",                    Material.coral,  2.0F, Block.soundTypeSand,  "coralBrain",           "pickaxe", 1),
	    	coralAcanthastrea    = newBlock("Coral Acanthastrea",             Material.coral,  2.0F, Block.soundTypeSand,  "coralAcan",            "pickaxe", 1),
	    	adamantiteBrickB     = newBlock("Adamantite Brick",               Material.rock,  75.5F, Block.soundTypeStone, "adamantiteBrickB",     "pickaxe", 2),
	    	wyrdstone            = newBlock("Wyrdstone",                      Material.rock,   1.5F, Block.soundTypeStone, "wyrdstone",            "pickaxe", 1),

    		// Ores [18]
    		// Variable                      Display Name                      Material     Hardness        StepSound           Unlocalized Name   Harvest Tool, Level
        	oreAdamantite        = newBlock("Adamantite Ore",                 Material.rock,  12.0F, Block.soundTypeStone, "oreAdamantite",       "pickaxe", 2),
        	oreMithril           = newBlock("Mithril Ore",                    Material.rock,   4.5F, Block.soundTypeStone, "oreMithril",          "pickaxe", 2),
        	oreObdurium          = newBlock("Obdurium Ore",                   Material.rock,  15.0F, Block.soundTypeStone, "oreObdurium",         "pickaxe", 3),
        	oreOrichalcum        = newBlock("Orichalcum Ore",                 Material.rock,   5.0F, Block.soundTypeStone, "oreOrichalcum",       "pickaxe", 2),
        	oreOrium             = newBlock("Orium Ore",                      Material.rock,   8.0F, Block.soundTypeStone, "oreOrium",            "pickaxe", 2),
        	oreOriumEnd          = newBlock("Orium Ore",                      Material.rock,   8.0F, Block.soundTypeStone, "oreOriumEnd",         "pickaxe", 2),
        	orePewter            = newBlock("Pewter Ore",                     Material.rock,   2.0F, Block.soundTypeStone, "orePewter",           "pickaxe", 1),
        	oreRediamond         = newBlock("Rediamond Ore",                  Material.rock,   2.5F, Block.soundTypeStone, "oreRediamond",        "pickaxe", 2),
        	oreWyrdstone         = newBlock("Wyrdstone Ore",                  Material.rock,   5.0F, Block.soundTypeStone, "oreWyrdstone",        "pickaxe", 2),
        	oreMindsilver        = newBlock("Mindsilver Ore",                 Material.rock,  15.0F, Block.soundTypeStone, "oreMindsilver",       "pickaxe", 2),
        	oreMindsilverEnd     = newBlock("Mindsilver Ore",                 Material.rock,  15.0F, Block.soundTypeStone, "oreMindsilverEnd",    "pickaxe", 2),
        	oreQuicksilver       = newBlock("Quicksilver Ore",                Material.rock,   5.0F, Block.soundTypeStone, "oreQuicksilver",      "pickaxe", 2),
        	oreTruesilver        = newBlock("Truesilver Ore",                 Material.rock,   4.0F, Block.soundTypeStone, "oreTruesilver",       "pickaxe", 2),
        	orePotassium         = newBlock("Potassium Ore",                  Material.rock,   0.5F, Block.soundTypeStone, "orePotassium",        "pickaxe", 1),
        	oreSulfur            = newBlock("Sulfur Ore",                     Material.rock,   1.5F, Block.soundTypeStone, "oreSulfur",           "pickaxe", 1),
        	oreCopper            = newBlock("Copper Ore",                     Material.rock,   2.0F, Block.soundTypeStone, "oreCopper",           "pickaxe", 0),
        	oreTin               = newBlock("Tin Ore",                        Material.rock,   3.5F, Block.soundTypeStone, "oreTin",              "pickaxe", 0),
        	oreWonderflonium     = newBlock("Wonderflonium Ore",              Material.rock,   4.5F, Block.soundTypeStone, "oreWonderflonium",    "pickaxe", 2),

        	// Ingot Storage Blocks [23]
        	// Variable                      Display Name                      Material     Hardness        StepSound           Unlocalized Name   Harvest Tool, Level
        	storageAdamantite    = newBlock("Block of Adamantite",            Material.iron,  55.0F, Block.soundTypeMetal, "blockAdamantite",     "pickaxe", 3),
        	storageSteel         = newBlock("Block of Steel",                 Material.iron,  15.0F, Block.soundTypeMetal, "blockSteel",          "pickaxe", 1),
        	storageDarksteel     = newBlock("Block of Darksteel",             Material.iron,  20.0F, Block.soundTypeMetal, "blockDarksteel",      "pickaxe", 3),
        	storageMithril       = newBlock("Block of Mithril",               Material.iron,   6.0F, Block.soundTypeMetal, "blockMithril",        "pickaxe", 2),
        	storagePureMithril   = newBlock("Block of Pure Mithril",          Material.iron,   8.0F, Block.soundTypeMetal, "blockPureMithril",    "pickaxe", 1),
        	storageObdurium      = newBlock("Block of Obdurium",              Material.iron,  13.0F, Block.soundTypeMetal, "blockObdurium",       "pickaxe", 2),
        	storageOrichalcum    = newBlock("Block of Orichalcum",            Material.iron,   5.0F, Block.soundTypeMetal, "blockOrichalcum",     "pickaxe", 1),
        	storageOrium         = newBlock("Block of Orium",                 Material.iron,   8.0F, Block.soundTypeMetal, "blockOrium",          "pickaxe", 1),
        	storagePewter        = newBlock("Block of Pewter",                Material.iron,   5.0F, Block.soundTypeMetal, "blockPewter",         "pickaxe", 1),
        	storageRediamond     = newBlock("Block of Rediamond",             Material.iron,   1.5F, Block.soundTypeMetal, "blockRediamond",      "pickaxe", 1),
        	storageWyrd          = newBlock("Block of Wyrd",                  Material.iron,   5.0F, Block.soundTypeMetal, "blockWyrd",           "pickaxe", 2),
        	storageMindsilver    = newBlock("Block of Mindsilver",            Material.iron,   5.0F, Block.soundTypeMetal, "blockMindsilver",     "pickaxe", 1),
        	storageQuicksilver   = newBlock("Block of Quicksilver",           Material.iron,   5.0F, Block.soundTypeMetal, "blockQuicksilver",    "pickaxe", 1),
        	storageTruesilver    = newBlock("Block of Truesilver",            Material.iron,   5.0F, Block.soundTypeMetal, "blockTruesilver",     "pickaxe", 1),
        	storagePotassium     = newBlock("Block of Potassium",             Material.iron,   2.5F, Block.soundTypeMetal, "blockPotassium",      "pickaxe", 1),
        	storageSulfur        = newBlock("Block of Sulfur",                Material.iron,   2.5F, Block.soundTypeMetal, "blockSulfur",         "pickaxe", 0),
        	storageCopper        = newBlock("Block of Copper",                Material.iron,   2.0F, Block.soundTypeMetal, "blockCopper",         "pickaxe", 0),
        	storageTin           = newBlock("Block of Tin",                   Material.iron,   2.5F, Block.soundTypeMetal, "blockTin",            "pickaxe", 0),
        	storageBronze        = newBlock("Block of Bronze",                Material.iron,   4.0F, Block.soundTypeMetal, "blockBronze",         "pickaxe", 1),
        	storageOddmatter     = newBlock("Block of Oddmatter",             Material.iron,  12.5F, Block.soundTypeMetal, "blockOddmatter",      "pickaxe", 1),        	
        	storageVirtuesilver  = newBlock("Block of Virtuesilver",          Material.iron,   6.0F, Block.soundTypeMetal, "blockVirtuesilver",   "pickaxe", 1),        	
        	storageMCA           = newBlock("Block of Mana Conductive Alloy", Material.iron,   4.5F, Block.soundTypeMetal, "blockMCA",            "pickaxe", 1),        	
        	storageWonderflonium = newBlock("Block of Wonderflonium",         Material.iron,   5.5F, Block.soundTypeMetal, "blockWonderflonium",  "pickaxe", 2),        	

        	
        	// Adamithril Blocks [2]
        	// Variable                      Display Name                      Material     Hardness        StepSound           Unlocalized Name    Harvest Tool, Level
        	adamithrilBrickB     = newBlock("Adamithril Brick",               Material.iron,  75.0F, Block.soundTypeMetal, "adamithrilBrickB",      "pickaxe", 3),
        	coloredAdaBrick      = new BlockColoredAdaBrick(n++, adamithrilBrickB.getMaterial())
        ;
        
        public final static Item
        	// Misc [9]
        	// Variable        = newItem(Display name, Stack Size, Unlocalized Name)
        	rediamond          = newItem("Rediamond",           64, "rediamond"),
        	rediamondShard     = newItem("Rediamond Shard",     64, "rediamondShard"),
        	wyrdstoneCrystal   = newItem("Wyrdstone Crystal",   64, "wyrdstoneCrystal"),
        	dropQuicksilver    = newItem("Drop of Quicksilver", 64, "dropQuicksilver"),
        	threadVirtuesilver = newItem("Virtuesilver Thread", 64, "threadVirtuesilver"),
        	dustPotassium      = newItem("Potassium Dust",      64, "dustPotassium"),
        	sulfur             = newItem("Sulfur",              64, "sulfur"),
        	adamantiteBrickI   = newItem("Adamantite Brick",    64, "adamantiteBrickI"),
        	adamithrilBrickI   = newItem("Adamithril Brick",    64, "adamithrilBrickI"),
        	
        	// Ingots [19]
        	ingotAdamantite    = newItem("Adamantite Ingot",      64, "ingotAdamantite"),
        	ingotAdamithril    = newItem("Adamithril Ingot",      64, "ingotAdamithril"),
        	ingotSteel         = newItem("Steel Ingot",           64, "ingotSteel"),
        	ingotDarksteel     = newItem("Darksteel Ingot",       64, "ingotDarksteel"),
        	ingotMCA           = newItem("Mana Conductive Alloy", 64, "ingotMCA"),
        	ingotMithril       = newItem("Mithril Ingot",         64, "ingotMithril"),
        	ingotPureMithril   = newItem("Pure Mithril Ingot",    64, "ingotPureMithril"),
        	ingotObdurium      = newItem("Obdurium Ingot",        64, "ingotObdurium"),
        	ingotOddmatter     = newItem("Oddmatter Ingot",       64, "ingotOddmatter"),
        	ingotOrichalcum    = newItem("Orichalcum Ingot",      64, "ingotOrichalcum"),
        	ingotOrium         = newItem("Orium Ingot",           64, "ingotOrium"),
        	ingotPewter        = newItem("Pewter Ingot",          64, "ingotPewter"),
        	ingotMindsilver    = newItem("Mindsilver Ingot",      64, "ingotMindsilver"),
        	ingotTruesilver    = newItem("Truesilver Ingot",      64, "ingotTruesilver"),
        	ingotVirtuesilver  = newItem("Virtuesilver Ingot",    64, "ingotVirtuesilver"),
        	ingotCopper        = newItem("Copper Ingot",          64, "ingotCopper"),
        	ingotTin           = newItem("Tin Ingot",             64, "ingotTin"),
        	ingotBronze        = newItem("Bronze Ingot",          64, "ingotBronze"),
        	ingotWonderflonium = newItem("Wonderflonium Ingot",   64, "ingotWonderflonium"),

        	// Alloys [7]
        	alloyAdamithril    = newItem("Adamithril Alloy",   64, "alloyAdamithril"),
        	alloySteel         = newItem("Steel Alloy",        64, "alloySteel"),
        	alloyDarksteel     = newItem("Darksteel Alloy",    64, "alloyDarksteel"),
        	alloyOddmatter     = newItem("Oddmatter Alloy",    64, "alloyOddmatter"),
        	alloyPureMithril   = newItem("Pure Mithril Alloy", 64, "alloyPureMithril"),
        	alloyVirtueSilver  = newItem("Virtuesilver Alloy", 64, "alloyVirtuesilver"),
        	alloyBronze        = newItem("Bronze Alloy",       64, "alloyBronze")
        ;

        
        
        private static Block newBlock(String par1Name, Material par2Material, float par3Hardness, SoundType par4StepSound, String par5UnlocalizedName, String par6ToolClass, int par7HarvestLevel) {
        	ResourceBlock returnBlock = new ResourceBlock(n++, par2Material);
        	returnBlock.setHardness(par3Hardness);
        	returnBlock.setStepSound(par4StepSound);
        	returnBlock.setBlockName(par5UnlocalizedName);
        	returnBlock.setCreativeTab(tabResourceBlocks);
        	//returnBlock.func_111022_d("resourcemod:" + par5UnlocalizedName);
        	returnBlock.setBlockTextureName("resourcemod:" + par5UnlocalizedName);
        	returnBlock.setHarvestLevel(par6ToolClass, par7HarvestLevel);
        	
        	blockList[b] = returnBlock;
        	blockNames[b] = par1Name;
        	b++;
        	
        	return returnBlock;
        }
        
        private static void registerBlock(ResourceBlock par1Block, String par2Name) {
//        	LanguageRegistry.addName(par1Block, par2Name);
        	GameRegistry.registerBlock(par1Block, par1Block.getUnlocalizedName());
        }

        private static Item newItem(String par1Name, int par2MaxStackSize, String par3UnlocalizedName) {
        	ResourceItem returnItem = new ResourceItem(m++);
        	returnItem.setMaxStackSize(par2MaxStackSize);
        	returnItem.setUnlocalizedName(par3UnlocalizedName);
        	returnItem.setCreativeTab(tabResourceItems);
        	//returnItem.func_111206_d("resourcemod:" + par3UnlocalizedName);
        	returnItem.setTextureName("resourcemod:" + par3UnlocalizedName);
        	
        	itemList[i] = returnItem;
        	itemNames[i] = par1Name;
        	i++;
        	
        	return returnItem;
        }
        private static void registerItem(ResourceItem par1Item, String par2Name) {
//        	LanguageRegistry.addName(par1Item, par2Name);
        	GameRegistry.registerItem(par1Item, par1Item.getUnlocalizedName2());
        }
        
        @SidedProxy(serverSide = "mods.resourcemod.common.CommonProxy", clientSide = "mods.resourcemod.client.ClientProxy")
        public static CommonProxy proxy;
        
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {
        	
                proxy.registerRenderers();
              
                //Biome
                chaparral = new BiomeChaparral(40);
                BiomeManager.addSpawnBiome(chaparral);
//                GameRegistry.addBiome(chaparral);
                mesa = new BiomeMesa(80);
                BiomeManager.addSpawnBiome(mesa);
//                GameRegistry.addBiome(mesa);
                
                //Custom Tab
                //this.tabResourceBlocks.setIcon(adamantiteBrickB.blockID);
                //this.tabResourceItems.setIcon(ingotBronze.itemID);
//                LanguageRegistry.instance().addStringLocalization("itemGroup.tabResourceItems", "en_US", "ResourceMod Items");
//                LanguageRegistry.instance().addStringLocalization("itemGroup.tabResourceBlocks", "en_US", "ResourceMod Blocks");
                
                // Register Blocks and Items
                for(int j = 0; j < b; j++) { registerBlock(blockList[j], blockNames[j]); }
                for(int j = 0; j < i; j++) { registerItem(itemList[j], itemNames[j]); }

                // Register ItemBlocks with metadata
                GameRegistry.registerBlock(coloredAdaBrick, ItemBlockColoredAdaBrick.class, "adabrick");
//                for (int j=0;j<16;j++)
//            		LanguageRegistry.addName( new ItemStack(coloredAdaBrick, 1, j), BlockColoredAdaBrick.names[j] + " Adamithril Brick ");
                
                
                // Generate Ores                                      ore.blockId,    Rarity, Vein Size, Height[, Biome[, Dimension, Replaces]]
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreAdamantite,     2,  5,  20), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreCopper,        35,  6, 105), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.granite,           6, 25,  20), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreMindsilver,    20,  7,  90, "Mesa"), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreMindsilverEnd, 20,  7,  90, "", 1, Blocks.end_stone), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreMithril,        1,  5,  10), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreObdurium,       3,  5,  20), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreOrichalcum,     3,  3,  15), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreOrium,         11,  5,  39, "Chaparral"), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreOriumEnd,      11,  5,  39, "", 1, Blocks.end_stone), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.orePewter,        20,  7,  50), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.orePotassium,     20,  5,  25), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreQuicksilver,    2,  7,  40), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreRediamond,     10,  4, 139, "", -1, Blocks.netherrack), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreSulfur,        20,  9,  64), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreTin,           20,  7, 110), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreTruesilver,     5,  7,  55), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreWyrdstone,     20,  5, 139, "", -1, Blocks.netherrack), 2);
                GameRegistry.registerWorldGenerator(new WorldGen(ResourceMod.oreWonderflonium, 12,  2,  30), 2);

                
        }
        
        @EventHandler
        public void load(FMLInitializationEvent event) {
        
                
                //Recipes
                //ItemStack rSstack = new ItemStack(rediamondShard);
                //ItemStack sulfurStack = new ItemStack(sulfur);
                //ItemStack stackD = new ItemStack(adamithrilBrickB);
                
                GameRegistry.addShapelessRecipe(new ItemStack(rediamondShard, 9), rediamond);
                GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 8), new ItemStack(Items.coal, 1, 1), new ItemStack(sulfur), new ItemStack(dustPotassium));
                GameRegistry.addShapelessRecipe(new ItemStack(alloySteel, 1), Items.iron_ingot, new ItemStack(Items.coal, 1, 1));
                GameRegistry.addRecipe(new ItemStack(alloyOddmatter, 1), "olo", "rsd", "klk", 'o', ingotOrichalcum, 'l', ingotObdurium, 'k', Items.lava_bucket, 'r', rediamond, 'd', Items.diamond, 's', storageSteel);
                
                // Brick Blocks (Adamantite and Adamithril)
                GameRegistry.addShapelessRecipe(new ItemStack(adamantiteBrickI, 4), ingotAdamantite, ingotAdamantite);
                GameRegistry.addShapelessRecipe(new ItemStack(adamithrilBrickI, 8), ingotAdamithril, ingotAdamithril);
                GameRegistry.addRecipe(new ItemStack(adamantiteBrickB, 1), "xx", "xx", 'x', adamantiteBrickI);
                GameRegistry.addRecipe(new ItemStack(adamithrilBrickB, 1), "xx", "xx", 'x', adamithrilBrickI);
                
                // Adamithril Colored Bricks
                for (int j=0; j<16; j++)
                	GameRegistry.addShapelessRecipe(new ItemStack(coloredAdaBrick, 1, j), adamithrilBrickB, new ItemStack(Items.dye, 1, j));

                GameRegistry.addRecipe(new ItemStack(alloyPureMithril, 8),    "mmm", "mdm", "mmm", 'd', Items.diamond, 'm', ingotMithril);

                
                
                // Ingot Storage Blocks
                GameRegistry.addRecipe(new ItemStack(storageRediamond, 1),    "xxx", "xxx", "xxx", 'x', rediamond);
                GameRegistry.addRecipe(new ItemStack(storageDarksteel, 1),    "xxx", "xxx", "xxx", 'x', ingotDarksteel);
                GameRegistry.addRecipe(new ItemStack(storageOrichalcum, 1),   "xxx", "xxx", "xxx", 'x', ingotOrichalcum);
                GameRegistry.addRecipe(new ItemStack(storageSteel, 1),        "xxx", "xxx", "xxx", 'x', ingotSteel);
                GameRegistry.addRecipe(new ItemStack(storageOrium, 1),        "xxx", "xxx", "xxx", 'x', ingotOrium);
                GameRegistry.addRecipe(new ItemStack(storageBronze, 1),       "xxx", "xxx", "xxx", 'x', ingotBronze);
                GameRegistry.addRecipe(new ItemStack(storageMCA, 1),          "xxx", "xxx", "xxx", 'x', ingotMCA);
                GameRegistry.addRecipe(new ItemStack(storageCopper, 1),       "xxx", "xxx", "xxx", 'x', ingotCopper);
                GameRegistry.addRecipe(new ItemStack(storageTin, 1),          "xxx", "xxx", "xxx", 'x', ingotTin);
                GameRegistry.addRecipe(new ItemStack(storageSulfur, 1),       "xxx", "xxx", "xxx", 'x', sulfur);
                GameRegistry.addRecipe(new ItemStack(storagePotassium, 1),    "xxx", "xxx", "xxx", 'x', dustPotassium);
                GameRegistry.addRecipe(new ItemStack(storageOddmatter, 1),    "xxx", "xxx", "xxx", 'x', ingotOddmatter);
                GameRegistry.addRecipe(new ItemStack(storageObdurium, 1),     "xxx", "xxx", "xxx", 'x', ingotObdurium);
                GameRegistry.addRecipe(new ItemStack(storageVirtuesilver, 1), "xxx", "xxx", "xxx", 'x', ingotVirtuesilver);
                GameRegistry.addRecipe(new ItemStack(storageTruesilver, 1),   "xxx", "xxx", "xxx", 'x', ingotTruesilver);
                GameRegistry.addRecipe(new ItemStack(storagePewter, 1),       "xxx", "xxx", "xxx", 'x', ingotPewter);
                GameRegistry.addRecipe(new ItemStack(storageMindsilver, 1),   "xxx", "xxx", "xxx", 'x', ingotMindsilver);
                GameRegistry.addRecipe(new ItemStack(storageWyrd, 1),         "xxx", "xxx", "xxx", 'x', wyrdstoneCrystal);
                GameRegistry.addRecipe(new ItemStack(storagePureMithril, 1),  "xxx", "xxx", "xxx", 'x', ingotPureMithril);
                GameRegistry.addRecipe(new ItemStack(storageMithril, 1),      "xxx", "xxx", "xxx", 'x', ingotMithril);
                GameRegistry.addRecipe(new ItemStack(storageQuicksilver, 1),  "xxx", "xxx", "xxx", 'x', dropQuicksilver);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotDarksteel, 9),    storageDarksteel);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotOrichalcum, 9),   storageOrichalcum);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotSteel, 9),        storageSteel);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotOrium, 9),        storageOrium);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotBronze, 9),       storageBronze);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotMCA, 9),          storageMCA);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotCopper, 9),       storageCopper);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotTin, 9),          storageTin);
                GameRegistry.addShapelessRecipe(new ItemStack(sulfur, 9),            storageSulfur);
                GameRegistry.addShapelessRecipe(new ItemStack(dustPotassium, 9),     storagePotassium);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotOddmatter, 9),    storageOddmatter);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotObdurium, 9),     storageObdurium);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotVirtuesilver, 9), storageVirtuesilver);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotTruesilver, 9),   storageTruesilver);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotPewter, 9),       storagePewter);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotMindsilver, 9),   storageMindsilver);
                GameRegistry.addShapelessRecipe(new ItemStack(wyrdstoneCrystal, 9),  storageWyrd);
                GameRegistry.addShapelessRecipe(new ItemStack(ingotPureMithril, 9),  storagePureMithril);
                GameRegistry.addShapelessRecipe(new ItemStack(dropQuicksilver, 9),   storageQuicksilver);

                // Smelting                   Input                             Output                XP
                GameRegistry.addSmelting(oreMithril,       new ItemStack(ingotMithril),      1.0f);
                GameRegistry.addSmelting(oreAdamantite,    new ItemStack(ingotAdamantite),   0.9f);
                GameRegistry.addSmelting(oreSulfur,        new ItemStack(sulfur),            0.2f);
                GameRegistry.addSmelting(oreTruesilver,    new ItemStack(ingotTruesilver),   0.7f);
                GameRegistry.addSmelting(oreMindsilver,    new ItemStack(ingotMindsilver),   0.9f);
                GameRegistry.addSmelting(alloyDarksteel,    new ItemStack(ingotDarksteel),    1.0f);
                GameRegistry.addSmelting(alloySteel,        new ItemStack(ingotSteel),        0.3f);
                GameRegistry.addSmelting(alloyPureMithril,  new ItemStack(ingotPureMithril),  0.5F);
                GameRegistry.addSmelting(oreMindsilver,    new ItemStack(ingotMindsilver),   0.7f);
                GameRegistry.addSmelting(oreTruesilver,    new ItemStack(ingotTruesilver),   0.7f);
                GameRegistry.addSmelting(oreCopper,        new ItemStack(ingotCopper),       0.2f);
                GameRegistry.addSmelting(oreRediamond,     new ItemStack(rediamond),         1.0f);
                GameRegistry.addSmelting(oreWyrdstone,     new ItemStack(wyrdstoneCrystal),  0.6f);
                GameRegistry.addSmelting(oreTin,           new ItemStack(ingotTin),          0.2f);
                GameRegistry.addSmelting(orePotassium,     new ItemStack(dustPotassium),     0.3f);
                GameRegistry.addSmelting(oreOrium,         new ItemStack(ingotOrium),        1.0f);
                GameRegistry.addSmelting(oreObdurium,      new ItemStack(ingotObdurium),     0.7f);
                GameRegistry.addSmelting(oreOrichalcum,    new ItemStack(ingotOrichalcum),   0.7f);
                GameRegistry.addSmelting(oreQuicksilver,   new ItemStack(dropQuicksilver),   1.0f);
                GameRegistry.addSmelting(oreOrium,         new ItemStack(ingotOrium),        1.0f);
                GameRegistry.addSmelting(orePewter,        new ItemStack(ingotPewter),       0.6f);
                GameRegistry.addSmelting(alloyOddmatter,    new ItemStack(ingotOddmatter),    6.0f);
                GameRegistry.addSmelting(alloyBronze,       new ItemStack(ingotBronze),       1.0f);
                GameRegistry.addSmelting(alloyAdamithril,   new ItemStack(ingotAdamithril),   1.0f);
                GameRegistry.addSmelting(alloyVirtueSilver, new ItemStack(ingotVirtuesilver), 1.0f);
    
                
        }
}