/**
 * Title: Manarz
 * Description: A mod for Minecraft.
 * Authors: Trizmo515, Fodimo
  **/

package mods.manarz;

import java.util.HashMap;

import mods.manarz.ManarzEnums.Addition;
import mods.manarz.ManarzEnums.Blade;
import mods.manarz.ManarzEnums.Hilt;
import mods.manarz.ManarzEnums.Legendary;
import mods.manarz.ManarzEnums.LootWeapon;
import mods.manarz.biome.BiomeBarrenWasteland;
import mods.manarz.biome.BiomeFieryWasteland;
import mods.manarz.block.BlockManaOre;
import mods.manarz.block.BlockWattle;
import mods.manarz.block.BlockWattle2;
import mods.manarz.block.ManarzBlock;
import mods.manarz.block.ManarzBlockBase;
import mods.manarz.entity.EntityCrab;
import mods.manarz.entity.EntityGleamingDagger;
import mods.manarz.entity.EntityGnats;
import mods.manarz.entity.EntityJellyfish;
import mods.manarz.entity.EntityProjectile;
import mods.manarz.entity.EntityRabbit;
import mods.manarz.entity.EntityRustyDagger;
import mods.manarz.entity.EntityShadowedDagger;
import mods.manarz.entity.EntitySilverDagger;
import mods.manarz.entity.models.ModelCrab;
import mods.manarz.entity.models.ModelJellyfish;
import mods.manarz.entity.models.ModelMan;
import mods.manarz.entity.models.ModelWabbit;
import mods.manarz.entity.models.ModelWoman;
import mods.manarz.entity.renders.RenderCrab;
import mods.manarz.entity.renders.RenderJellyfish;
import mods.manarz.entity.renders.RenderRebbit;
import mods.manarz.item.ItemFodimoBow;
import mods.manarz.item.ItemTrizmoBow;
import mods.manarz.item.MItem;
import mods.manarz.item.MItemArmor;
import mods.manarz.item.MItemBase;
import mods.manarz.item.tomes.EntitySpell;
import mods.manarz.item.tomes.EntitySummonCreeper;
import mods.manarz.item.tomes.EntitySummonLightning;
import mods.manarz.item.tomes.EntitySummonSkeleton;
import mods.manarz.item.tomes.TomeManager;
import mods.manarz.common.CommonProxy;
import mods.resourcemod.common.ResourceMod;
import mods.resourcemod.resources.ResourceBlock;
import mods.resourcemod.world.WorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid="manarz", name="Manarz", version="0.1.05")
//@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Manarz {

        @Instance("manarz")
        public static Manarz instance;
        public static final String modID = "manarz";

        @SidedProxy(serverSide = "mods.manarz.common.CommonProxy", clientSide = "mods.manarz.client.ClientProxy")
        public static CommonProxy proxy = new CommonProxy();
        
        //Add custom tab
        public static CreativeTabs 
        	tabMagic     = new CreativeTabs("tabMagic")     { @Override public Item getTabIconItem() { return blankManaStone; } },
        	tabTomes     = new CreativeTabs("tabTomes")     { @Override public Item getTabIconItem() { return tome;	} },
        	tabLegendary = new CreativeTabs("tabLegendary") { @Override public Item getTabIconItem() { return legendaries[Legendary.TRIZMO_SWORD.index()]; } },
        	tabToolMat   = new CreativeTabs("tabToolMat")   { @Override public Item getTabIconItem() { return heads[I_SWORD][Blade.BLADE_STEEL.index()]; } },
        	tabLoot      = new CreativeTabs("tabLoot")      { @Override public Item getTabIconItem() { return goldPiece; } },
        	tabSwords    = new CreativeTabs("tabSwords")    { @Override public Item getTabIconItem() { return tools[I_SWORD][Hilt.HILT_FANCY.index()][Blade.BLADE_DIVINE.index()]; } },
        	tabTools     = new CreativeTabs("tabTools")     { @Override public Item getTabIconItem() { return tools[I_PICKAXE][Hilt.HILT_FANCY.index()][Blade.BLADE_IRON.index()]; } };
        
        
        //Biome
        public BiomeGenBase barrenWasteland;
        public BiomeGenBase fieryWasteland;
        
      //Material
        //public static EnumToolMaterial ADA = EnumHelper.addToolMaterial("ADA", 4, -1, 6.0F, 2, 20);
        //public static EnumToolMaterial MITHRIL = EnumHelper.addToolMaterial("MITHRIL", 4, 2222, 9.0F, 5, 30);
      
        // Armor
        // TODO Add Armor Recipes
        // TODO Add textures for Mithril Armor and Steel Armor
        public final static Item
        	mithrilHelmet           = MItem.item("mithrilHelmet",           CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("MithrilArmor"), 0)),
	    	mithrilBreastplate      = MItem.item("mithrilBreastplate",      CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("MithrilArmor"), 1)),
	    	mithrilLeggings         = MItem.item("mithrilLeggings",         CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("MithrilArmor"), 2)),
	    	mithrilBoots            = MItem.item("mithrilBoots",            CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("MithrilArmor"), 3)),
        	mithrilChainHelmet      = MItem.item("mithrilChainHelmet",      CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("MithrilChainArmor"), 0)),
	    	mithrilChainBreastplate = MItem.item("mithrilChainBreastplate", CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("MithrilChainArmor"), 1)),
	    	mithrilChainLeggings    = MItem.item("mithrilChainLeggings",    CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("MithrilChainArmor"), 2)),
	    	mithrilChainBoots       = MItem.item("mithrilChainBoots",       CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("MithrilChainArmor"), 3)),
        	steelHelmet             = MItem.item("steelHelmet",             CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("SteelArmor"), 0)),
	    	steelBreastplate        = MItem.item("steelBreastplate",        CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("SteelArmor"), 1)),
	    	steelLeggings           = MItem.item("steelLeggings",           CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("SteelArmor"), 2)),
	    	steelBoots              = MItem.item("steelBoots",              CreativeTabs.tabCombat, new MItemArmor(ManarzEnums.enumMithrilArmor, proxy.addArmor("SteelArmor"), 3));
        
        // Mana Stones
        public final static Item 
        	prismaticManaStone 	= MItem.item("manaStonePrismatic", tabMagic).setMaxStackSize(16),
        	fireManaStone 		= MItem.item("manaStoneFire", tabMagic).setMaxStackSize(16),
        	airManaStone 		= MItem.item("manaStoneAir", tabMagic).setMaxStackSize(16),
        	darkManaStone 		= MItem.item("manaStoneDark", tabMagic).setMaxStackSize(16),
        	earthManaStone 		= MItem.item("manaStoneEarth", tabMagic).setMaxStackSize(16),
        	lightManaStone 		= MItem.item("manaStoneLight", tabMagic).setMaxStackSize(16),
        	waterManaStone 		= MItem.item("manaStoneWater", tabMagic).setMaxStackSize(16),
        	colorlessManaStone 	= MItem.item("manaStoneColorless", tabMagic).setMaxStackSize(16),
        	blankManaStone 		= MItem.item("manaStoneBlank", tabMagic),
        	pureFireManaStone   = MItem.item("manaStonePureFire",tabMagic),
        	pureAirManaStone    = MItem.item("manaStonePureAir",tabMagic);
        // Mana Dusts
        public final static Item 
	    	fireManaDust 	  = MItem.item("manaDustFire", tabMagic),
	    	airManaDust 	  = MItem.item("manaDustAir", tabMagic),
	    	darkManaDust 	  = MItem.item("manaDustDark", tabMagic),
	    	earthManaDust 	  = MItem.item("manaDustEarth", tabMagic),
	    	lightManaDust 	  = MItem.item("manaDustLight", tabMagic),
	    	waterManaDust 	  = MItem.item("manaDustWater", tabMagic),
	    	colorlessManaDust = MItem.item("manaDustColorless", tabMagic),
    		prismaticManaDust = MItem.item("manaDustPrismatic", tabMagic);

        //Legendary Weapons
        public final static Item
        	// TODO: Make these bows functional
	    	trizmoBow    = MItem.item("TrizmoBow",    tabLegendary, new ItemTrizmoBow()),
	    	fodimoBow    = MItem.item("FodimoBow",    tabLegendary, new ItemFodimoBow());
        public final static Item[] legendaries = ManarzEnums.getLegendaries();

        // Other Items
        public final static Item 
        	hammer          = MItem.item("hammer", tabToolMat),
        	goldPiece       = MItem.item("goldPiece", tabLoot),
        	fur             = MItem.item("fur", tabLoot),/**Tanning rack**/
        	mindsilverPaper = MItem.item("mindsilverPaper", tabToolMat),
        	tome            = MItem.item("tome", tabTomes),
        	tomeCrude       = MItem.item("tomeCrude", tabTomes),
        	tomeLegendary   = MItem.item("tomeLegendary", tabTomes),
        	tomeQuality     = MItem.item("tomeQuality", tabTomes);
        
        // Sword parts
        public final static int I_SWORD = 0, I_HILT = 0, I_PICKAXE = 1, I_HANDLE = 1, I_SPADE = 2, I_AXE = 3;
        public final static Item[]
            additions = ManarzEnums.getAdditions();
        public final static Item[][]
            handles = ManarzEnums.getHandles(),
            heads   = ManarzEnums.getHeads(),
            augWeapons = ManarzEnums.getAugmentedWeapons();
        public final static Item[][][] tools = ManarzEnums.getTools();
        
        
        // Blocks
        public final static Block 
        	wattleanddaub = ManarzBlock.block("wattleanddaub", CreativeTabs.tabBlock, new BlockWattle(Material.wood)),
        	wattle2       = ManarzBlock.block("wattle2",       CreativeTabs.tabBlock, new BlockWattle2(Material.wood));

		public final static Block
			darkManaOre      = ManarzBlock.block("darkManaOre",      tabMagic, new BlockManaOre(darkManaDust)),
			lightManaOre     = ManarzBlock.block("lightManaOre",     tabMagic, new BlockManaOre(lightManaDust)),
			earthManaOre     = ManarzBlock.block("earthManaOre",     tabMagic, new BlockManaOre(earthManaDust)),
			fireManaOre      = ManarzBlock.block("fireManaOre",      tabMagic, new BlockManaOre(fireManaDust)),
			waterManaOre     = ManarzBlock.block("waterManaOre",     tabMagic, new BlockManaOre(waterManaDust)),
			airManaOre       = ManarzBlock.block("airManaOre",       tabMagic, new BlockManaOre(airManaDust)),
			prismaticManaOre = ManarzBlock.block("prismaticManaOre", tabMagic, new BlockManaOre(prismaticManaDust));
        
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {
        	MItem.registerItems();
        	ManarzBlock.registerBlocks();
        	TomeManager.registerTomes();
        	
//        	for (Blade bla : Blade.values())
//        		for (Hilt hil : Hilt.values()) {
//        			System.out.println(tools[I_SWORD][hil.index()][bla.index()].getUnlocalizedName() + ".name=" + hil.getName() + "-Hilted " + bla.getName() + " Sword");
//        			System.out.println(tools[I_PICKAXE][hil.index()][bla.index()].getUnlocalizedName() + ".name=" + hil.getName() + "-Handled " + bla.getName() + " Pickaxe");
//        			System.out.println(tools[I_SPADE][hil.index()][bla.index()].getUnlocalizedName() + ".name=" + hil.getName() + "-Handled " + bla.getName() + " Shovel");
//        			System.out.println(tools[I_AXE][hil.index()][bla.index()].getUnlocalizedName() + ".name=" + hil.getName() + "-Handled " + bla.getName() + " Axe");
//        		}

//        	for (Blade bla : Blade.values()) {
//        		System.out.println(heads[I_SWORD][bla.index()].getUnlocalizedName() + ".name=" + bla.getName() + " Sword Blade");
//        		System.out.println(heads[I_PICKAXE][bla.index()].getUnlocalizedName() + ".name=" + bla.getName() + " Pickaxe Head");
//        		System.out.println(heads[I_SPADE][bla.index()].getUnlocalizedName() + ".name=" + bla.getName() + " Shovel Head");
//        		System.out.println(heads[I_AXE][bla.index()].getUnlocalizedName() + ".name=" + bla.getName() + " Axe Head");
//        	}
//        	System.out.println("\n");
//        	for (Hilt hil : Hilt.values()) {
//        		System.out.println(handles[I_HILT][hil.index()].getUnlocalizedName() + ".name=" + hil.getName() + " Sword Hilt");
//        		System.out.println(handles[I_HANDLE][hil.index()].getUnlocalizedName() + ".name=" + hil.getName() + " Tool Handle");
//        	}
        	
//        	for( LootWeapon wea : LootWeapon.values() )
//        		for ( Addition add : Addition.values() )
//        			System.out.println(augWeapons[wea.index()][add.index()].getUnlocalizedName() + ".name=" + LanguageRegistry.instance().getStringLocalization("item." + wea.getName() + ".name") + " of " + add.getBaseName());
//    		for ( Addition add : Addition.values() )
//    			System.out.println("item." + add.getName() + ".name=" + add.getName());
        }
        
        @EventHandler
        public void load(FMLInitializationEvent event) {

            proxy.registerRenderers();

            //Biome
            barrenWasteland = new BiomeBarrenWasteland(60);
            	BiomeManager.addSpawnBiome(barrenWasteland);

            fieryWasteland = new BiomeFieryWasteland(50);
            	BiomeManager.addSpawnBiome(fieryWasteland);
            
            // Generate Ores                                      ore.blockId,    Rarity, Vein Size, Height[, Biome[, Dimension, Replaces]]

            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.prismaticManaOre,         16,  7,  90, "Jungle"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.prismaticManaOre,         16,  7,  90, "JungleHills"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.darkManaOre,               8,  7,  90, "Volcanic Wasteland"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.fireManaOre,               8,  7,  90, "Volcanic Wasteland"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.lightManaOre,             16,  7,  90, "Plains"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.airManaOre,                8,  7,  90, "Extreme Hills"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.darkManaOre,               8,  7,  90, "Swampland"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.earthManaOre,              8,  7,  90, "Swampland"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.fireManaOre,              16,  7,  90, "Desert"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.fireManaOre,              16,  7,  90, "DesertHills"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.waterManaOre,             16,  7,  90, "Ocean"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.waterManaOre,              5,  7,  90, "River"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.waterManaOre,              9,  7,  90, "Beach"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.waterManaOre,              8,  7,  90, "FrozenRiver"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.waterManaOre,              8,  7,  90, "FrozenOcean"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.waterManaOre,              8,  7,  90, "Ice Plains"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.lightManaOre,              8,  7,  90, "Ice Plains"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.earthManaOre,             16,  7,  90, "Forest"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.lightManaOre,              8,  7,  90, "Mesa"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.airManaOre,                8,  7,  90, "Mesa"), 2);
            GameRegistry.registerWorldGenerator(new WorldGen(Manarz.darkManaOre,              16,  7,  90, "Wasteland"), 2);


            //GameRegistry.registerWorldGenerator(new WorldGenEMD());

            
            //Recipes
            //GameRegistry.addRecipe(new ItemStack(adamantiteSword), " a ", " a ", " s ", 'a', ResourceMod.ingotAdamantite, 's', net.minecraft.item.Item.stick );
            //GameRegistry.addRecipe(new ItemStack(mithrilSword), " m ", " m ", " s ", 'm', ResourceMod.ingotMithril, 's', net.minecraft.item.Item.stick);

            // Mana Dusts
            GameRegistry.addRecipe(new ItemStack(fireManaDust, 4), " s ", "glg", " s ", 's', ResourceMod.sulfur, 'g', Items.gunpowder, 'l', Items.lava_bucket);
            GameRegistry.addRecipe(new ItemStack(airManaDust, 4), " g ", "fef", " g ", 'f', Items.feather, 'e', Blocks.end_stone, 'g', Blocks.glass);
            GameRegistry.addRecipe(new ItemStack(darkManaDust, 4), " b ", "sks", " b ", 'b', Items.bone, 's', Blocks.soul_sand, 'k', new ItemStack(Items.skull, 1, 0));
            GameRegistry.addRecipe(new ItemStack(earthManaDust, 4), " w ", "sfs", " w ", 'w', Blocks.planks, 's', Items.wheat_seeds, 'f', fur);
            GameRegistry.addRecipe(new ItemStack(lightManaDust, 4), " g ", "lql", " g ", 'g', Blocks.glass, 'l', Items.glowstone_dust, 'q', Blocks.glowstone);
            GameRegistry.addRecipe(new ItemStack(waterManaDust, 4), " s ", "cwc", " s ", 's', Blocks.sand, 'c', ResourceMod.coralBrain, 'w', Items.water_bucket);
            GameRegistry.addRecipe(new ItemStack(waterManaDust, 4), " s ", "cwc", " s ", 's', Blocks.sand, 'c', ResourceMod.coralAcanthastrea, 'w', Items.water_bucket);
            
            // Mana Stones
            GameRegistry.addRecipe(new ItemStack(blankManaStone, 16), " i ", "rsl", " i ", 'l', new ItemStack(Items.dye, 1, 4), 'i', Items.iron_ingot, 's', Blocks.stone, 'r', ResourceMod.rediamondShard);
            GameRegistry.addShapelessRecipe(new ItemStack( fireManaStone, 1),  fireManaDust, blankManaStone, ResourceMod.rediamondShard);
            GameRegistry.addShapelessRecipe(new ItemStack(  airManaStone, 1),   airManaDust, blankManaStone, ResourceMod.rediamondShard);
            GameRegistry.addShapelessRecipe(new ItemStack( darkManaStone, 1),  darkManaDust, blankManaStone, ResourceMod.rediamondShard);
            GameRegistry.addShapelessRecipe(new ItemStack(earthManaStone, 1), earthManaDust, blankManaStone, ResourceMod.rediamondShard);
            GameRegistry.addShapelessRecipe(new ItemStack(lightManaStone, 1), lightManaDust, blankManaStone, ResourceMod.rediamondShard);
            GameRegistry.addShapelessRecipe(new ItemStack(waterManaStone, 1), waterManaDust, blankManaStone, ResourceMod.rediamondShard);

            // Wattle and Daub
            GameRegistry.addRecipe(new ItemStack(wattleanddaub, 4), "sss", "dcd", "sss", 's', Items.stick, 'd', Blocks.dirt, 'c', Items.clay_ball);
            GameRegistry.addRecipe(new ItemStack(wattleanddaub, 4), "sss", "dcd", "sss", 's', Items.stick, 'd', Blocks.sand, 'c', Items.clay_ball);
            GameRegistry.addShapelessRecipe(new ItemStack(wattleanddaub, 1), new ItemStack(wattle2));
            GameRegistry.addShapelessRecipe(new ItemStack(wattle2, 1), new ItemStack(wattleanddaub));
            
            // Tools and Swords
            for (int i=0; i<tools.length; i++)               // Tool type
            	for (int j=0; j<tools[i].length; j++)        // Tool handle
            		for (int k=0; k<tools[i][j].length; k++) // Tool head
            			GameRegistry.addShapelessRecipe(new ItemStack(tools[i][j][k], 1), handles[i>0?1:0][j], heads[i][k]);
            
            //Smelting
//             GameRegistry.addSmelting(ResourceMod.alloyVirtueSilver, new ItemStack(ResourceMod.ingotVirtuesilver), 1.0f);
            /*ModLoader.registerEntityID(EntityJelly.class, "Jellyfish", ModLoader.getUniqueEntityId());
            ModLoader.addSpawn(EntityJelly.class, 10, -5, 90, EnumCreatureType.waterCreature);*/
            /*ModLoader.registerEntityID(EntityCrab.class, "Crab", ModLoader.getUniqueEntityId());
            ModLoader.addSpawn(EntityCrab.class, 20, 3, 5, EnumCreatureType.waterCreature, 
            		BiomeGenBase.beach,
            		BiomeGenBase.ocean);*/
            //ModLoader.registerEntityID(EntityGnats.class, "fogOfGnats", ModLoader.getUniqueEntityId());
            //ModLoader.addSpawn(EntityGnats.class, 8, 3, 10, EnumCreatureType.ambient, 
            //		BiomeGenBase.swampland);
           
            //EntityRegistry.registerGlobalEntityID(EntitySummonZombie.class, "ZombSummon", ModLoader.getUniqueEntityId());
//            EntityRegistry.registerGlobalEntityID(EntitySummonSkeleton.class, "SkeleSummon", ModLoader.getUniqueEntityId());
//            EntityRegistry.registerGlobalEntityID(EntitySummonCreeper.class, "CreepSummon(he follows you =D)", ModLoader.getUniqueEntityId());
            int id = 0;
            
            // Jellyfish
            EntityRegistry.registerGlobalEntityID(EntityJellyfish.class, "Jellyfish", EntityRegistry.findGlobalUniqueEntityId(), 51555, 4565);
            	RenderingRegistry.registerEntityRenderingHandler(EntityJellyfish.class, new RenderJellyfish(new ModelJellyfish(), 1.0F));  

            // Crab
            EntityRegistry.registerGlobalEntityID(EntityCrab.class, "Crab", EntityRegistry.findGlobalUniqueEntityId(), 515, 651);
            	RenderingRegistry.registerEntityRenderingHandler(EntityCrab.class, new RenderCrab(new ModelCrab(), 1.0F));
            	
            // Rabbit
            EntityRegistry.registerGlobalEntityID(EntityRabbit.class, "Rabbit", EntityRegistry.findGlobalUniqueEntityId(), 51556, 4566);
	            RenderingRegistry.registerEntityRenderingHandler(EntityRabbit.class, new RenderRebbit(new ModelWabbit(), 1.0F));
	        
	        // Gnat Swarm
            EntityRegistry.registerGlobalEntityID(EntityGnats.class, "Gnat Swarm",
                    EntityRegistry.findGlobalUniqueEntityId(), 451651, 5416);
            EntityRegistry.registerModEntity(EntityGnats.class, "Gnat Swarm", id++, this, 128, 1, true);
                    
            // Man
//            EntityRegistry.registerGlobalEntityID(EntityMan.class, "Man", EntityRegistry.findGlobalUniqueEntityId(), 51654, 8135);
//	            RenderingRegistry.registerEntityRenderingHandler(EntityMan.class, new RenderMan(new ModelMan(), 1.0F));
            
	        // Woman	
//	        EntityRegistry.registerGlobalEntityID(EntityWoman.class, "Woman", EntityRegistry.findGlobalUniqueEntityId(), 5354, 5574);
//	            RenderingRegistry.registerEntityRenderingHandler(EntityWoman.class, new RenderWoman(new ModelWoman(), 1.0F));

//            EntityRegistry.registerGlobalEntityID(EntityProjectile.class,     "projectile",     EntityRegistry.findGlobalUniqueEntityId());
//                 EntityRegistry.registerModEntity(EntityProjectile.class,     "projectile",     id++, this, 128, 1, true);
            EntityRegistry.registerGlobalEntityID(EntityGleamingDagger.class, "gleamingDagger", EntityRegistry.findGlobalUniqueEntityId());
                 EntityRegistry.registerModEntity(EntityGleamingDagger.class, "gleamingDagger", id++, this, 128, 1, true);
            EntityRegistry.registerGlobalEntityID(EntityRustyDagger.class,    "rustyDagger",    EntityRegistry.findGlobalUniqueEntityId());
                 EntityRegistry.registerModEntity(EntityRustyDagger.class,    "rustyDagger",    id++, this, 128, 1, true);
            EntityRegistry.registerGlobalEntityID(EntityShadowedDagger.class, "shadowedDagger", EntityRegistry.findGlobalUniqueEntityId());
                 EntityRegistry.registerModEntity(EntityShadowedDagger.class, "shadowedDagger", id++, this, 128, 1, true);
            EntityRegistry.registerGlobalEntityID(EntitySilverDagger.class,   "silverDagger",   EntityRegistry.findGlobalUniqueEntityId());
                 EntityRegistry.registerModEntity(EntitySilverDagger.class,   "silverDagger",   id++, this, 128, 1, true);
            EntityRegistry.registerGlobalEntityID(EntitySpell.class,          "spell",     EntityRegistry.findGlobalUniqueEntityId());
                 EntityRegistry.registerModEntity(EntitySpell.class,          "spell",     id++, this, 128, 1, true);
            EntityRegistry.registerGlobalEntityID(EntitySummonLightning.class,"lspell",    EntityRegistry.findGlobalUniqueEntityId());
                 EntityRegistry.registerModEntity(EntitySummonLightning.class,"lspell",    id++, this, 128, 1, true);
        }
}