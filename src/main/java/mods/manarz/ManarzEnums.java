package mods.manarz;

import mods.manarz.item.MItem;
import mods.manarz.item.MItemAxe;
import mods.manarz.item.MItemPickaxe;
import mods.manarz.item.MItemSpade;
import mods.manarz.item.MItemSword;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.block.Block;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;

public class ManarzEnums {
	// Armor Enums
	public static ArmorMaterial
		enumMithrilArmor = EnumHelper.addArmorMaterial("mithril", 35, new int[]{4,8,6,3}, 35),
		enumMithrilChainArmor = EnumHelper.addArmorMaterial("Mithril Chain Armor", 26, new int[]{2,6,4,1}, 25),
		enumSteelArmor = EnumHelper.addArmorMaterial("SteelArmor", 20, new int[]{2, 6, 5, 2}, 14);

    // Legendary Weapons Enums
    public final static ToolMaterial
		enumTrizmoSword  = EnumHelper.addToolMaterial("TrizmoSword",  4, -1, 8.0F, 20, 0),
		enumFodimoDagger = EnumHelper.addToolMaterial("FodimoDagger", 4, -1, 6.0F, 18, 0),
		enumMidScythe    = EnumHelper.addToolMaterial("midScythe",    4, -1, 2.0F, 25, 0),
		enumMidTomahawk  = EnumHelper.addToolMaterial("midTomahawk",  4, -1, 5.0F, 17, 0);

	public static int a = 1, w = 0, l = 0, x = 0;
	public static int 
		target = 0, player = 1, always = 0,
		none = x--, thrown = x--, pickUp = x--, bolt = x--, harvest = x--, randomPotion = x--, setFire = x--, extinguish = x--, heal = Potion.heal.id, harm = Potion.harm.id;
    public enum Legendary {
    	TRIZMO_SWORD ( "TrizmoSword",       enumTrizmoSword,
						/*Attack*/     new int[][] { null },
					    /*RightClick*/ new int[][] { null },
						/*ItemUse*/    new int[]   { none }),
		FODIMO_DAGGER ( "FodimoDagger",     enumFodimoDagger,
						/*Attack*/     new int[][] { null },
					    /*RightClick*/ new int[][] { null },
						/*ItemUse*/    new int[]   { none }),
		MIDNIGHT_SCYTHE ( "MidScythe",      enumMidScythe,
						/*Attack*/     new int[][] { null },
					    /*RightClick*/ new int[][] { null },
						/*ItemUse*/    new int[]   { harvest, 3 }),
		MIDNIGHT_TOMAHAWK ( "MidTomahawk",  enumMidTomahawk,
						/*Attack*/     new int[][] { null },
					    /*RightClick*/ new int[][] { { always, target, thrown } },
						/*ItemUse*/    new int[]   { none });

//		private final int index, harvestLevel, durability, enchantability;
//	    private final float efficiency, damage;
    	private final int index;
    	private final ToolMaterial toolmat;
	    private final String name;
	    private final int[][] attack, rightclick;
	    private final int[] itemuse;
	    
	    Legendary ( String par1, ToolMaterial par2) {
	    	this(par1, par2, new int[][] { null }, new int[][] { null }, new int[] { none });
	    }
	    Legendary ( String par1, ToolMaterial par2, int[][] par7, int[][] par8, int[] par9) {
	    	this.index = l++;
            this.name = par1;
            this.toolmat = par2;
//	        this.harvestLevel = par2;
//	        this.durability = par3;
//	        this.efficiency = par4;
//	        this.damage = par5;
//	        this.enchantability = par6;
	        this.attack = par7;
	        this.rightclick = par8;
	        this.itemuse = par9;
	    }

	    public int index()                { return this.index; }
	    public ToolMaterial toolMaterial(){ return this.toolmat; }
//	    public int harvestLevel()         { return this.harvestLevel; }
//        public int durabilityBonus()      { return this.durability; }
//        public float efficiency()         { return this.efficiency; }
//        public float damage()             { return this.damage; }
//        public int enchantability()       { return this.enchantability; }
        public String getName()           { return this.name; }
        public int[][] attack()           { return this.attack; }
        public int[][] rightClick()       { return this.rightclick; }
        public int[] itemUse()            { return this.itemuse; }
    }
	/*
	 * Loot Weapon Enums
	 */
	public enum Addition {
	    // Harvest Level, Durability Bonus, Durability Multiplier, Efficiency, Damage, Enchantability, Name
	    ADDITION_POISON            ( 0,  0,  1,   0.0F,  0, 0, "Poison",            
	    							/*Attack*/     new int[][] { { always, target, Potion.poison.id, 200, 1 } },
	                                /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_FLAME             ( 0,  0,  1,   0.0F,  0, 4, "Flame",             
                    	            /*Attack*/     new int[][] { { always, target, setFire, 4 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { setFire }),
	    ADDITION_WITHER            ( 0,  0,  1,   0.0F,  0, 0, "Wither",            
                    	            /*Attack*/     new int[][] { { always, target, Potion.wither.id, 200, 0 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_THERESILIENT      ( 0, 15,  1.2, 0.0F,  0, 0, "The Resiliant"),
	    ADDITION_DURABILITY        ( 0, 50,  1,   0.0F,  0, 0, "Durability"),
	    ADDITION_HOLYLIGHT         ( 0,  0,  1,   0.0F,  1, 5, "Holy Light",        
                    	            /*Attack*/     new int[][] { null },
                                    /*RightClick*/ new int[][] { { always, player, Potion.nightVision.id, 300, 0 } },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_FROST             ( 0,  0,  1,   0.0F,  1, 5, "Frost",             
                    	            /*Attack*/     new int[][] { { always, target, Potion.digSlowdown.id, 100, 2 },
                    	                                            { always, target, Potion.moveSlowdown.id, 200, 0 } },
                    	            /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_STRONGWINDS       ( 0,  0,  1,   0.0F,  1, 5, "Strong Winds"),
	    ADDITION_THEBREEZE         ( 0,  0,  1,   0.0F,  1, 5, "The Breeze"),
	    ADDITION_LIGHTNING         ( 0,  0,  1,   0.0F,  1, 5, "Lightning",         
                                    /*Attack*/     new int[][] { null },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { bolt }),
	    ADDITION_DRAGONBREATH      ( 0,  0,  1,   0.0F,  1, 5, "Dragon Breath",     
	                                /*Attack*/     new int[][] { { always, target, setFire, 10 } },
	                                /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_DOOM              ( 0,  0,  1,   0.0F,  1, 5, "Doom",              
                    	            /*Attack*/     new int[][] { { always, target, Potion.blindness.id, 100, 2 },
                    	                                            { always, target, Potion.wither.id, 200, 0 },
                    	                                            { always, player, Potion.digSlowdown.id, 150, 0 } },
                    	            /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_MOISTURE          ( 0,  0,  1,   0.0F,  1, 5, "Moisture",          
                    	            /*Attack*/     new int[][] { { always, target, extinguish } },
                                    /*RightClick*/ new int[][] { { always, player, extinguish } },
	    							/*ItemUse*/    new int[]   { extinguish }),
	    ADDITION_VENOM             ( 0,  0,  1,   0.0F,  1, 5, "Venom"),
	    ADDITION_PAINFULHEALING    ( 0,  0,  1,   0.0F,  1, 5, "Painful Healing",   
                                    /*Attack*/     new int[][] { { always, target, Potion.regeneration.id, 100, 1 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_THEINDESTRUCTIBLE ( 0,  0,  0,   0.0F,  1, 5, "The Indestructible"),
	    ADDITION_SLOWNESS          ( 0,  0,  1,   0.0F,  1, 5, "Slowness"),
	    ADDITION_THESWIFT          ( 0,  0,  1,   0.0F,  1, 5, "The Swift",         
                                    /*Attack*/     new int[][] { { always, player, Potion.moveSpeed.id, 250, 0 } },
                                    /*RightClick*/ new int[][] { { always, player, Potion.digSpeed.id, 250, 0 } },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_THEHASTY          ( 0,  0,  1,   0.0F,  1, 5, "The Hasty",         
                                    /*Attack*/     new int[][] { { always, player, Potion.digSpeed.id, 250, 0 } },
                                    /*RightClick*/ new int[][] { { always, player, Potion.moveSpeed.id, 250, 0 } },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_SLEEP             ( 0,  0,  1,   0.0F,  1, 5, "Sleep",             
                                    /*Attack*/     new int[][] { { always, target, Potion.blindness.id, 100, 0 },
                                                                 { always, target, Potion.confusion.id, 100, 1 },
                                                                 { always, target, Potion.moveSlowdown.id, 100, 0 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_WEAKNESS          ( 0,  0,  1,   0.0F,  1, 5, "Weakness",          
                                    /*Attack*/     new int[][] { { always, target, Potion.weakness.id, 250, 0 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_THESLOWSWING      ( 0,  0,  1,   0.0F,  1, 5, "The Slow Swing",    
                                    /*Attack*/     new int[][] { { always, target, Potion.digSlowdown.id, 250, 0 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_DARKNESS          ( 0,  0,  1,   0.0F,  1, 5, "Darkness",          
                                    /*Attack*/     new int[][] { { always, target, Potion.blindness.id, 150, 0 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_VAMPIRISM         ( 0,  0,  1,   0.0F,  1, 5, "Vampirism",         
                                    /*Attack*/     new int[][] { { always, player, heal, 3 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_THEFISH           ( 0,  0,  1,   0.0F,  1, 5, "The Fish",          
                                    /*Attack*/     new int[][] { { always, target, Potion.waterBreathing.id, 100, 1 } },
                                    /*RightClick*/ new int[][] { { always, player, Potion.waterBreathing.id, 100, 1 } },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_FAMINE            ( 0,  0,  1,   0.0F,  1, 5, "Famine",            
                                    /*Attack*/     new int[][] { { always, target, Potion.hunger.id, 125, 0 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_DEATH             ( 0,  0,  1,   0.0F,  5, 0, "Death",             
                                    /*Attack*/     new int[][] { { always, target, Potion.blindness.id, 200, 0 },
                                                                 { always, target, Potion.confusion.id, 150, 0 },
                                                                 { always, target, Potion.moveSlowdown.id, 250, 3 },
                                                                 { 4, target, Potion.wither.id, 150, 1 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_WAR               ( 0,  0,  1,   0.0F,  1, 0, "War",               // Strength to wielder and blocker, and perhaps will deal damage to all nearby mobs
                                    /*Attack*/     new int[][] { { always, target, Potion.damageBoost.id, 150, 3},
	    														 { always, player, Potion.damageBoost.id, 150, 3} },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_POWER             ( 0, 10,  1,   0.0F,  2, 0, "Power"),
	    ADDITION_GREED             ( 0,  0,  1,   0.0F,  0, 5, "Greed"),
	    ADDITION_LUCK              ( 0,  0,  1,   0.0F, -1, 5, "Luck",              
                                    /*Attack*/     new int[][] { { 9, player, Potion.damageBoost.id, 150, 1 },
                                                                 { 9, player, Potion.digSpeed.id, 150, 1 },
                                                                 { 9, player, Potion.regeneration.id, 50, 1 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_THECURSED         ( 0,  0,  1,   0.0F,  2, 6, "The Cursed"),
	    ADDITION_THEVOID           ( 0,  0,  1,   0.0F,  2, 6, "The Void",          
                                    /*Attack*/     new int[][] { { always, target, Potion.blindness.id, 100, 0 },
	                                                             { always, target, Potion.wither.id, 90, 0 },
	                                                             { always, target, Potion.moveSlowdown.id, 90, 1 },
	                                                             { 4, target, Potion.wither.id, 150, 1 } },
                                    /*RightClick*/ new int[][] { null },
	    							/*ItemUse*/    new int[]   { none }),
	    ADDITION_OBLIVION          ( 0,  0,  1,   0.0F,  2, 6, "Oblivion");
	    
	    private final int index, harvestLevel, durabilityBonus, enchantability;
	    private final double durabilityMultiplier;
	    private final float efficiency, damage;
	    private final String name;
	    private final int[][] attack, rightclick;
	    private final int[] itemuse;
	    
	    Addition ( int par1, int par2, double par3, float par4, int par5, int par6, String par7) {
	    	this(par1, par2, par3, par4, par5, par6, par7, new int[][] { null }, new int[][] { null }, new int[] { none });
	    }
	    Addition ( int par1, int par2, double par3, float par4, float par5, int par6, String par7, int[][] par8, int[][] par9, int[] par10) {
            this.index = a++;
	        this.harvestLevel = par1;
	        this.durabilityBonus = par2;
	        this.durabilityMultiplier = par3;
	        this.efficiency = par4;
	        this.damage = par5;
	        this.enchantability = par6;
	        this.name = par7;
	        this.attack = par8;
	        this.rightclick = par9;
	        this.itemuse = par10;
	    }
	    
        public int index()                { return this.index; }
	    public int harvestLevel()         { return this.harvestLevel; }
        public int durabilityBonus()      { return this.durabilityBonus; }
        public double durabilityMultiplier() { return this.durabilityMultiplier; }
        public float efficiency()         { return this.efficiency; }
        public float damage()               { return this.damage; }
        public int enchantability()       { return this.enchantability; }
        public String getName()           { return "Gem Of " + this.name; }
        public String getBaseName()       { return this.name; }
        public int[][] attack()           { return this.attack; }
        public int[][] rightClick()       { return this.rightclick; }
        public int[] itemUse()            { return this.itemuse; }
	}
	
	public enum LootWeapon {
	    // Harvest Level, Durability, Efficiency, Damage, Enchantability, Name, Socket Level
        WEAPON_SIMPLESPEAR       ( 0,  2000,  6.0F, 0, 8,  "simpleSpear"        , 1),
        WEAPON_MORNINGSTAR       ( 0,  4000,  7.0F, 2, 12, "morningstar"        , 1),
        WEAPON_RUSTYDAGGER       ( 0,  4000,  7.0F, 0, 12, "rustyDagger"        , 1,
                				 /*Attack*/     new int[][] { null },
                				 /*RightClick*/ new int[][] { { always, target, thrown } },
                				 /*ItemUse*/    new int[]   { none }), // POISON, THROW
        WEAPON_GLEAMINGDAGGER    ( 0,  4000,  7.0F, 2, 12, "gleamingDagger"     , 1,
                                 /*Attack*/     new int[][] { null },
                                 /*RightClick*/ new int[][] { { always, target, thrown } },
                                 /*ItemUse*/    new int[]   { none }), // THROW
        WEAPON_WICKEDSWORD       ( 0,  4000,  7.0F, 2, 12, "wickedSword"        , 1),
        WEAPON_FALCHION          ( 0,  4000,  7.0F, 1, 12, "falchion"           , 1),
        WEAPON_BASTARDSWORD      ( 0,  4000,  7.0F, 2, 12, "bastardSword"       , 1),
        WEAPON_CLEAVER           ( 0,  4000,  7.0F, 1, 12, "cleaver"            , 1),
        WEAPON_MAUL              ( 0,  4000,  7.0F, 2, 12, "maul"               , 1),
        WEAPON_WARHAMMER         ( 0,  4000,  7.0F, 2, 12, "warhammer"          , 1),
        WEAPON_SABER             ( 0,  4000,  7.0F, 2, 12, "saber"              , 1),
        WEAPON_SICKLE            ( 0,  4000,  7.0F, 0, 12, "sickle"             , 1,
                                 /*Attack*/     new int[][] { null },
                                 /*RightClick*/ new int[][] { { always, target, thrown } },
                                 /*ItemUse*/    new int[]   { harvest, 1 }),
        WEAPON_WARSCYTHE         ( 0,  4000,  7.0F, 1, 12, "warScythe"          , 1),
        WEAPON_HALBERD           ( 0,  4000,  7.0F, 1, 12, "halberd"            , 1),
        WEAPON_PIKE              ( 0,  4000,  7.0F, 1, 12, "pike"               , 1),
        WEAPON_SHADOWEDDAGGER    ( 0,  4000,  7.0F, 1, 12, "shadowedDagger"     , 1,
                                 /*Attack*/     new int[][] { null },
                                 /*RightClick*/ new int[][] { { always, target, thrown } }, // THROW, INVISIBLE for 3 secs after you hit something
                                 /*ItemUse*/    new int[]   { none }),
        WEAPON_CRACKEDSHORTSWORD ( 0,  4000,  7.0F, 0, 12, "crackedShortsword"  , 1),
        WEAPON_SHININGLONGSWORD  ( 0,  4000,  7.0F, 2, 12, "shiningLongsword"   , 1),
        WEAPON_SHORTSWORD        ( 0,  4000,  7.0F, 2, 12, "shortsword"         , 1),
        WEAPON_PITCHFORK         ( 0,  4000,  7.0F, 1, 12, "pitchfork"          , 1,
                                 /*Attack*/     new int[][] { null },
                                 /*RightClick*/ new int[][] { null },
                                 /*ItemUse*/    new int[]   { pickUp, Block.getIdFromBlock(Blocks.hay_block) }),
        WEAPON_TORCH             ( 0,  4000,  7.0F, 0, 12, "torch"              , 1,
                                 /*Attack*/     new int[][] { {always, target, setFire, 4 } },
                                 /*RightClick*/ new int[][] { null },
                                 /*ItemUse*/    new int[]   { setFire }), // BURRRRRRNNNNN
//        WEAPON_WHIP              ( 0,  4000,  7.0F, 2, 12, "whip"               , 1),
//        WEAPON_CHAINWHIP         ( 0,  4000,  7.0F, 2, 12, "chainWhip"          , 1),
        WEAPON_KATANA            ( 0,  4000,  7.0F, 1, 12, "katana"             , 1),
        WEAPON_FINESPEAR         ( 0,  4000,  7.0F, 1, 12, "fineSpear"          , 1),
        WEAPON_SILVERSWORD       ( 0,  4000,  7.0F, 1, 12, "silverSword"        , 1),
        WEAPON_SILVERDAGGER      ( 0,  4000,  7.0F, 0, 12, "silverDagger"       , 1,
						         /*Attack*/     new int[][] { null },
						         /*RightClick*/ new int[][] { { always, target, thrown } },
						         /*ItemUse*/    new int[]   { none }), // THROW
        WEAPON_HOLYLONGSWORD     ( 0,  4000,  7.0F, 0, 12, "holyLongsword"      , 1),
        WEAPON_TRIBALSPEAR       ( 0,  1750,  6.0F, 0, 18, "tribalSpear"        , 1),
        WEAPON_FLAMINGSPEAR      ( 0,  1750,  6.0F, 0, 18, "flamingSpear"       , 1,
                                 /*Attack*/     new int[][] { { 9, target, setFire, 4 } },
                                 /*RightClick*/ new int[][] { null },
                                 /*ItemUse*/    new int[]   { none }),
        WEAPON_CURSEDSHORTSWORD  ( 0,  3750,  6.0F, 0, 18, "cursedShortsword"   , 1),   
        WEAPON_SHINYSHORTSWORD   ( 0,  3750,  6.0F, 0, 18, "shinyShortsword"    , 1),    
        WEAPON_ICELONGSWORD      ( 0,  3750,  6.0F, 1, 18, "iceLongsword"       , 1,
                                 /*Attack*/     new int[][] { { 4, target, Potion.moveSlowdown.id, 100, 1},
        									 	              { 4, target, Potion.digSlowdown.id, 100, 1 } },
        						 /*RightClick*/ new int[][] { null },
        						 /*ItemUse*/    new int[]   { none }),   
        WEAPON_POINTEDSTICK      ( 0,  3750,  6.0F, 0, 18, "pointedStick"       , 1);   

        private final int index, harvestLevel, durability, damage, enchantability, socketLevel;
        private final float efficiency;
        private final String name;
	    private final int[][] attack, rightclick;
	    private final int[] itemuse;
        
        LootWeapon ( int par1, int par2, float par3, int par4, int par5, String par6, int par7) {
	    	this(par1, par2, par3, par4, par5, par6, par7, new int[][] { null }, new int[][] { null }, new int[] { none });
        }
        LootWeapon ( int par1, int par2, float par3, int par4, int par5, String par6, int par7, int[][] par8, int[][] par9, int[] par10) {
            this.index = w++;
            this.harvestLevel = par1;
            this.durability = par2;
            this.efficiency = par3;
            this.damage = par4;
            this.enchantability = par5;
            this.name = par6;
            this.socketLevel = par7;
            this.attack = par8;
            this.rightclick = par9;
            this.itemuse = par10;
        }
        
        public int index()          { return this.index; }
        public int harvestLevel()   { return this.harvestLevel; }
        public int durability()     { return this.durability; }
        public float efficiency()   { return this.efficiency; }
        public int damage()         { return this.damage; }
        public int enchantability() { return this.enchantability; }
        public String getName()     { return this.name; }
        public int socket()         { return this.socketLevel; }
        public int[][] attack()     { return this.attack; }
        public int[][] rightClick() { return this.rightclick; }
        public int[] itemUse()      { return this.itemuse; }
	}

	/*
	 * Custom Tool/Weapon Enums
	 */
	public static int h = 0, b = 0;//, m = 0;
    public static final int TYPE_SWORD = 0, TYPE_PICKAXE = 1, TYPE_AXE = 2, TYPE_SHOVEL = 3;
	public enum Hilt {
	    // Harvest Level, Durability Bonus, Durability Multiplier, Efficiency, Damage, Enchantability, Name
        HILT_IRON      (  0,    0, 1    , 0.0F,  0,   0, "Iron"      ),
        HILT_CRUDE     ( -1,    0, 0.625, 0.0F,  0,   0, "Crude"     ),
        HILT_STEEL     (  0,  +50, 1    , 0.0F,  0,  -3, "Steel"     ),
        HILT_ORIUM     (  0,  +50, 1    , 0.0F,  0, +20, "Orium"     ),
        HILT_FANCY     (  0,  -25, 1    , 0.0F,  0, +10, "Fancy"     ),
        HILT_WYRDSTONE (  0,  +50, 1    , 0.0F,  0,  +2, "Wyrdstone" ,
                       /*Attack*/     new int[][] { { 4, target, randomPotion } },
                       /*RightClick*/ new int[][] { { always, player, randomPotion } },
                       /*ItemUse*/    new int[]   { none }),
        HILT_STRONG    (  0, +100, 1    , 0.0F, +1,  +5, "Strong"    ),
        HILT_EMERALD   (  0,  +50, 1.1  , 0.0F,  0, +10, "Emerald"   );
        
        private final int index, harvestLevel, durabilityBonus, enchantability;
        private final double durabilityMultiplier;
        private final float efficiency, damage;
        private final String name;
        private final int[][] attack, rightclick;
        private final int[] itemuse;
        
        Hilt ( int par1, int par2, double par3, float par4, float par5, int par6, String par7, int[][] par8, int[][] par9, int[] par10) {
            this.index = h++;
            this.harvestLevel = par1;
            this.durabilityBonus = par2;
            this.durabilityMultiplier = par3;
            this.efficiency = par4;
            this.damage = par5;
            this.enchantability = par6;
            this.name = par7;
        	this.attack = par8;
        	this.rightclick = par9;
        	this.itemuse = par10;
        }
        
        Hilt ( int par1, int par2, double par3, float par4, int par5, int par6, String par7) {
        	this(par1, par2, par3, par4, par5, par6, par7, new int[][]{ null }, new int[][]{ null }, new int[]{ none });
        }
        
        public int index()                { return this.index; }
        public int harvestLevel()         { return this.harvestLevel; }
        public int durabilityBonus()      { return this.durabilityBonus; }
        public double durabilityMultiplier() { return this.durabilityMultiplier; }
        public float efficiency()         { return this.efficiency; }
        public float damage()               { return this.damage; }
        public int enchantability()       { return this.enchantability; }
        public String getName()           { return this.name; }
        public int[][] attack()           { return this.attack; }
        public int[][] rightClick()       { return this.rightclick; }
        public int[] itemUse()            { return this.itemuse; }
    }

	public enum Blade {
        BLADE_ORICHALCUM    ( 3,  400,   6.0F, 2, 18, "Orichalcum"    ),
        BLADE_DIVINE        ( 3,  400,   7.0F, 2, 23, "Divine"        ),
        BLADE_ORIUM         ( 2,  300,   4.0F, 2, 40, "Orium"         ),
        BLADE_WYRDSTONE     ( 2,  300,   4.0F, 2, 19, "Wyrdstone"     ,
                            /*Attack*/     new int[][] { { 4, target, randomPotion } },
                            /*RightClick*/ new int[][] { { always, player, randomPotion } },
                            /*ItemUse*/    new int[]   { none }),
        BLADE_MINDSILVER    ( 2,  300,   4.0F, 2, 19, "Mindsilver"    ),
        BLADE_IRON          ( 2,  250,   6.0F, 2, 14, "Iron"          ),
        BLADE_STEEL         ( 2,  300,   6.1F, 2, 12, "Steel"         ),
        BLADE_DIAMOND       ( 3, 1561,   8.0F, 3, 10, "Diamond"       ),
        BLADE_GOLD          ( 0,   32,  12.0F, 0, 22, "Gold"          ),
        BLADE_MITHRIL       ( 3, 2222,   9.0F, 5, 30, "Mithril"       ),
        BLADE_ADAMANTITE    ( 3, 2000,   6.5F, 3, 16, "Adamantite"    ),
        BLADE_ADAMITHRIL    ( 3, 1600,   6.5F, 3, 20, "Adamithril"    ),
        BLADE_DARKSTEEL     ( 3, 2000,   8.0F, 3, 16, "Darksteel"     ),
        BLADE_OBDURIUM      ( 3, 2000,   5.5F, 3,  2, "Obdurium"      ),
        BLADE_ODDMATTER     ( 3, 2000,   8.5F, 5, 16, "Oddmatter"     ),
        BLADE_BRONZE        ( 2, 2450,   6.0F, 1, 12, "Bronze"        ),
        BLADE_TIN           ( 1,  500,   4.0F, 0, 10, "Tin"           ),
        BLADE_VIRTUESILVER  ( 3, 18000,  8.0F, 4, 18, "Virtuesilver"  ),
        BLADE_EMERALD       ( 3, 18500, 10.0F, 2, 18, "Emerald"       ),
        BLADE_OBSIDIAN      ( 3, 19000,  7.0F, 2, 10, "Obsidian"      ),
        BLADE_WONDERFLONIUM ( 3, 17000,  9.1F, 2, 12, "Wonderflonium" );

        private final int index, harvestLevel, durability, enchantability;
        private final float efficiency, damage;
        private final String name;
        private final int[][] attack, rightclick;
        private final int[] itemuse;
        
        Blade ( int par1, int par2, float par3, float par4, int par5, String par6, int[][] par7, int[][] par8, int[] par9 ) {
            this.index = b++;
            this.harvestLevel = par1;
            this.durability = par2;
            this.efficiency = par3;
            this.damage = par4;
            this.enchantability = par5;
            this.name = par6;
            this.attack = par7;
            this.rightclick = par8;
            this.itemuse = par9;
        }
        Blade ( int par1, int par2, float par3, int par4, int par5, String par6 ) {
        	this(par1, par2, par3, par4, par5, par6, new int[][]{ null }, new int[][]{ null }, new int[] { none });
        }
        
        public int index()          { return this.index; }
        public int harvestLevel()   { return this.harvestLevel; }
        public int durability()     { return this.durability; }
        public float efficiency()   { return this.efficiency; }
        public float damage()       { return this.damage; }
        public int enchantability() { return this.enchantability; }
        public String getName()     { return this.name; }
        public int[][] attack()     { return this.attack; }
        public int[][] rightClick() { return this.rightclick; }
        public int[] itemUse()      { return this.itemuse; }
}

	/*
	 * Item Returning Methods based on Enums
	 */
	public static Item[] getLegendaries() {
		Item[] retItem = new Item[Legendary.values().length];
		
		for (Legendary leg : Legendary.values())
			retItem[leg.index()] = MItem.item(leg.getName(), Manarz.tabLegendary, new MItemSword(leg.toolMaterial(), leg));
		
		return retItem;
	}
	
	
	public static Item[][] getHandles() {
		Item[][] retItem = new Item[2][Hilt.values().length];
		
        for (Hilt han : Hilt.values()) {
        	retItem[0][han.index()] = MItem.item("hilt"   + han.getName(), Manarz.tabToolMat, "swords/");
        	retItem[1][han.index()] = MItem.item("handle" + han.getName(), Manarz.tabToolMat, "swords/");
        }
		
		return retItem;
	}
	public static Item[][] getHeads() {
		Item[][] retItem = new Item[4][Blade.values().length];
		String[] headType = { "blade", "headPickaxe", "headShovel", "headAxe" };

		for (int i=0; i<4; i++)
	        for (Blade hea : Blade.values())
	        	retItem[i][hea.index()] = MItem.item(headType[i] + hea.getName(), Manarz.tabToolMat, "swords/");
		
		return retItem;
	}
	
	public static Item[][][] getTools() {
		Item[][][] retItem = new Item[4][Hilt.values().length][Blade.values().length];
		ToolMaterial t;
		
		for (Hilt hil : Hilt.values())
			for (Blade bla : Blade.values()) {
				t = buildTool(hil, bla);
				retItem[Manarz.I_SWORD][hil.index()][bla.index()]   = MItem.item("sword"   + hil.getName() + bla.getName(), Manarz.tabSwords, new MItemSword(t, hil, bla), "swords/");
				retItem[Manarz.I_PICKAXE][hil.index()][bla.index()] = MItem.item("Pickaxe" + hil.getName() + bla.getName(), Manarz.tabTools,  new MItemPickaxe(t, hil, bla), "swords/");
				retItem[Manarz.I_SPADE][hil.index()][bla.index()]   = MItem.item("Shovel"  + hil.getName() + bla.getName(), Manarz.tabTools,  new MItemSpade(t, hil, bla), "swords/");
				retItem[Manarz.I_AXE][hil.index()][bla.index()]     = MItem.item("Axe"     + hil.getName() + bla.getName(), Manarz.tabTools,  new MItemAxe(t, hil, bla), "swords/");
			}
		
		return retItem;
	}
	private static ToolMaterial buildTool (Hilt hil, Blade bla) {
		
        // Set the EnumToolMaterial variables
        int harvestLevel   = bla.harvestLevel() + hil.harvestLevel();
        int durability     = (int) (bla.durability() * hil.durabilityMultiplier()) + hil.durabilityBonus();
        float efficiency   = bla.efficiency() + hil.efficiency();
        float damage         = bla.damage() + hil.damage();
        int enchantability = bla.enchantability() + hil.enchantability();
        String unlocName   = hil.getName().replaceAll("\\s", "").toLowerCase() + bla.getName().replaceAll("\\s", "").toLowerCase();
        
        // return the EnumToolMaterial for the constructor
        return EnumHelper.addToolMaterial(unlocName, harvestLevel, durability, efficiency, damage, enchantability);
	}
	
	public static Item[] getAdditions() {
		Item[] retItem = new Item[Addition.values().length+1];
		
		for (Addition add : Addition.values())
			retItem[add.index()] = MItem.item(add.getName(), Manarz.tabLoot, "weapons/");
		
		return retItem;
	}
	public static Item[] getLootWeapons() {
		Item[] retItem = new Item[LootWeapon.values().length];
		
		for (LootWeapon wea : LootWeapon.values())
			retItem[wea.index()] = MItem.item(wea.getName(), Manarz.tabLoot, new MItemSword(buildWeapon(wea), wea, null), "weapons/");
		
		return retItem;
	}
	public static Item[][] getAugmentedWeapons() {
		Item[][] retItem = new Item[LootWeapon.values().length][Addition.values().length+1];
		ToolMaterial w;
		
		for (LootWeapon wea : LootWeapon.values()) {
			retItem[wea.index()][0] = MItem.item(wea.getName(), Manarz.tabLoot, new MItemSword(buildWeapon(wea), wea, null), "weapons/");
			for (Addition add : Addition.values()) {
				retItem[wea.index()][add.index()] = MItem.item(
						(wea.getName().replaceAll("\\s", "").toLowerCase() + "of" + add.getName().replaceAll("\\s", "").toLowerCase()), 
						Manarz.tabLoot, new MItemSword(buildWeapon(wea,add), wea, add));
			}
		}
		
		return retItem;
	}
   private static ToolMaterial buildWeapon (LootWeapon wea) {
        
        // Set the EnumToolMaterial variables
        int harvestLevel   = wea.harvestLevel();
        int durability     = wea.durability();
        float efficiency   = wea.efficiency();
        int damage         = wea.damage();
        int enchantability = wea.enchantability();
        String unlocName   = wea.getName().replaceAll("\\s", "").toLowerCase();
        
        // return the EnumToolMaterial for the constructor
        return EnumHelper.addToolMaterial(unlocName, harvestLevel, durability, efficiency, damage, enchantability);
    }
	private static ToolMaterial buildWeapon (LootWeapon wea, Addition add) {
		
		// Set the EnumToolMaterial variables
		int harvestLevel   = wea.harvestLevel() + add.harvestLevel();
		int durability     = (int) (wea.durability() * add.durabilityMultiplier()) + add.durabilityBonus();
		float efficiency   = wea.efficiency() + add.efficiency();
		float damage         = wea.damage() + add.damage();
		int enchantability = wea.enchantability() + add.enchantability();
		String unlocName   = wea.getName().replaceAll("\\s", "").toLowerCase() + "of" + add.getName().replaceAll("\\s", "").toLowerCase();
		
		// return the EnumToolMaterial for the constructor
		return EnumHelper.addToolMaterial(unlocName, harvestLevel, durability, efficiency, damage, enchantability);
	}

}
