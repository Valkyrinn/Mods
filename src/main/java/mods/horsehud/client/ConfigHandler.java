package mods.horsehud.client;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mods.horsehud.HorseHUD;
import mods.horsehud.api.IConfigHandler;
import mods.horsehud.api.IConfigOption;
import mods.horsehud.api.IDataProvider;
import mods.horsehud.api.Keys;
import mods.horsehud.api.Type;
import mods.horsehud.client.gui.ConfigScreen;
import mods.horsehud.client.gui.itembox.GuiItemDisplay;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConfigHandler implements IConfigHandler{
	
	private static ConfigHandler instance = null;
	private ConfigHandler() { instance = this; buildMap(); }
	public static ConfigHandler instance() { 
		if (ConfigHandler.instance == null) 
			ConfigHandler.instance = new ConfigHandler(); 

		return ConfigHandler.instance;
	}
	
	private Map<Class, IDataProvider> exceptionList = new HashMap();
	public void register(Class c, IDataProvider i) { exceptionList.put(c, i); }
	public IDataProvider getException(Class c) { 
		if (exceptionList.containsKey(c)) return exceptionList.get(c);
		return null;
	}
	
	public Map<String, Map<String, IConfigOption>> activeConfigs = new HashMap();
	public void setupConfig(String category, Object key, Object defaultValue, int count, boolean isConfigurable, Configuration cfg) {
		AConfigOption configOption = new AConfigOption(category, key.toString(), defaultValue, count, isConfigurable, cfg) { @Override public void configure() {} };
		Configuration configFile = HorseHUD.instance.config;

		if (cfg != null) {
			cfg.load();
			if (configOption.type() == Type.STRING)  configOption.set(configFile.get(category, key.toString(), (String)  configOption.defaultValue()).getString());
			if (configOption.type() == Type.BOOLEAN) configOption.set(configFile.get(category, key.toString(), (Boolean) configOption.defaultValue()).getBoolean((Boolean) configOption.defaultValue()));
			if (configOption.type() == Type.INTEGER) configOption.set(configFile.get(category, key.toString(), (Integer) configOption.defaultValue()).getInt());
			if (configOption.type() == Type.DOUBLE)  configOption.set(configFile.get(category, key.toString(), (Double) configOption.defaultValue()).getDouble((Double) configOption.defaultValue()));
			if (configOption.type() == Type.POINT) {
				int[] dxy = {((Point)configOption.defaultValue()).x, ((Point)configOption.defaultValue()).y};
				int[] xy = configFile.get(category, key.toString(), dxy).getIntList();
				int x = (xy.length > 0 ? xy[0] : dxy[0]);
				int y = (xy.length > 1 ? xy[1] : dxy[1]);
//				int x = configFile.get(category, key.toString()+"X", ((Point)configOption.defaultValue()).x).getInt();
//				int y = configFile.get(category, key.toString()+"Y", ((Point)configOption.defaultValue()).y).getInt();
				Point point = new Point(x, y);
				configOption.set(point);
			}
			addConfig(category, key, configOption);
		}
	}

	public void addConfig(String category, Object key, IConfigOption configOption) {

		if (!activeConfigs.containsKey(category)) activeConfigs.put(category, new LinkedHashMap());
		activeConfigs.get(category).put(key.toString(), configOption);
	}
	public void     setConfig(String category, Object key, Object value) {
		Configuration configFile = HorseHUD.instance.config;
		IConfigOption configOption = activeConfigs.get(category).get(key.toString());
		
		configFile.load();
		configOption.set(value);
		configFile.save();
	}
//	public String cycleConfig(String category, String key)               { String s = activeConfigs.get(category).get(key).cycle(); saveConfigs(); return s; }
	public IConfigOption   getConfig(String category, Object key)               { return activeConfigs.get(category).get(key.toString()); }
//	public void setupConfig(String category, String key, String defaultValue, boolean isConfigurable) {
//		if (!activeConfigs.containsKey(category)) activeConfigs.put(category, new HashMap());
//		activeConfigs.get(category).put(key, new Config(category, key, defaultValue, 1, isConfigurable));
//	}
//	public void setupConfig(String category, String key, boolean defaultValue, boolean isConfigurable) {
//		if (!activeConfigs.containsKey(category)) activeConfigs.put(category, new HashMap());
//		activeConfigs.get(category).put(key, new Config(category, key, defaultValue, 2, isConfigurable));
//	}
//	public void setupConfig(String category, String key, int defaultValue, int count, boolean isConfigurable) {
//		if (!activeConfigs.containsKey(category)) activeConfigs.put(category, new HashMap());
//		activeConfigs.get(category).put(key, new Config(category, key, defaultValue, count, isConfigurable));
//	}
//	public void               setConfig(String category, String key, String value)  { activeConfigs.get(category).get(key).strValue  = value; saveConfigs(); }
//	public void               setConfig(String category, String key, boolean value) { activeConfigs.get(category).get(key).boolValue = value; saveConfigs(); }
//	public void               setConfig(String category, String key, int value)     { activeConfigs.get(category).get(key).intValue  = value; saveConfigs(); }
//	public String          getConfigStr(String category, String key)                { return activeConfigs.get(category).get(key).strValue; }
//	public boolean        getConfigBool(String category, String key)                { return activeConfigs.get(category).get(key).boolValue; }
//	public int             getConfigInt(String category, String key)                { return activeConfigs.get(category).get(key).intValue; }
//	public boolean isConfigConfigurable(String category, String key)                { return activeConfigs.get(category).get(key).isConfigurable; }

//	public void loadConfigs() {
//		Configuration cfg = HorseHUD.instance.config;
//		for (Entry<String, Map<String, Config>> entry : activeConfigs.entrySet()) {
//			String category = entry.getKey();
//			for (Entry<String, Config> entry2 : entry.getValue().entrySet()) {
//				Config config = entry2.getValue();
//				String key = entry2.getKey();
//				if (config.type() == Type.STRING)  activeConfigs.get(category).get(key).value = cfg.get(category, key, (String)  config.defaultValue).getString();
//				if (config.type() == Type.BOOLEAN) activeConfigs.get(category).get(key).value = cfg.get(category, key, (Boolean) config.defaultValue).getBoolean((Boolean) config.defaultValue);
//				if (config.type() == Type.INTEGER) activeConfigs.get(category).get(key).value = cfg.get(category, key, (Integer) config.defaultValue).getInt();
//			}
//		}
////		cfg.save();
//	}
//	private void saveConfigs() {
//		Configuration cfg = HorseHUD.instance.config;
//		cfg.load();
//		for (Entry<String, Map<String, ConfigOption>> entry : activeConfigs.entrySet()) {
////			String category = entry.getKey();
//			for (Entry<String, ConfigOption> entry2 : entry.getValue().entrySet()) {
//				ConfigOption config = entry2.getValue();
////				String key = entry2.getKey();
//				config.set(config.value(), cfg);
////				if (config.type == Type.STRING)  cfg.get(category, key, config.strDefault).set(config.strValue);
////				if (config.type == Type.BOOLEAN) cfg.get(category, key, config.boolDefault).set(config.boolValue);
////				if (config.type == Type.INTEGER) cfg.get(category, key, config.intDefault).set(config.intValue);
//			}
//		}
//		cfg.save();
//	}

	private Map<String, Object> activeSettings = new HashMap();
	public void addSetting(Object key, Object value) { activeSettings.put(key.toString(), value); }
	public Object getSetting(Object key) { return activeSettings.get(key.toString()); }

	public Map<String, String> itemList = new HashMap();
	public Map<String, String> modList = new HashMap();
	public Map<String, List<ItemStack>> modItemsLists = new HashMap();
	public void buildMap() {
		
		// Create a local HashMap that mirrors the public modItemsLists
		// Instead of a list, the value is a map so that duplicate items
		// in the return value of GameData.buildItemDataList() are
		// overwritten
		Map<String, Map<Integer, ItemStack>> listBuild = new HashMap();

		// Minecraft is not included in the Mod List, so they are added manually
		modList.put("minecraft", "Minecraft");
		modItemsLists.put("minecraft", new ArrayList());
		listBuild.put("minecraft", new HashMap());

		// Add each mod to modList and create a new collection in
		// the item list for each mod. This will allow us to add()
        List<ModContainer> mods = Loader.instance().getModList();
        for (ModContainer mod : mods) {
        	modList.put(mod.getModId(), mod.getName());
        	modItemsLists.put(mod.getModId(), new ArrayList());
        	listBuild.put(mod.getModId(), new HashMap());
//	        	System.out.println(mod.getModId() + " = " + mod.getName());
        }

        // Build a HashMap<Integer, ItemStack> based on GameData where Integer is the item ID
        // This is a map so that duplicate items which seem to be common in the list are overwritten
		Map<String, Integer> map = GameData.buildItemDataList();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
   			String[] split = entry.getKey().split(":");
   			String mod = split[0].substring(1);
   			itemList.put(split[1], mod);

   			Block b = Block.getBlockFromName(entry.getKey());
			Item i = Item.getItemById(entry.getValue());
			ItemStack is = null;
			if (i != null) is = new ItemStack(i);
			if (b != null) is = new ItemStack(b);
			
			if (is != null) listBuild.get(mod).put(entry.getValue(), is);
		}
		
		// Sort the map we just made for each mod into a List by item ID
		// Then put that sorted list into modItemsLists
		for (Map.Entry<String, Map<Integer, ItemStack>> entry : listBuild.entrySet()) {
			
			// Convert the map into a list
			List<ItemStack> lb = new ArrayList();
			for (ItemStack is : entry.getValue().values()) {
				Item item = is.getItem();
				if (item instanceof ItemEnchantedBook) {
					for (int i=0; i<Enchantment.enchantmentsList.length; i++) {
						Enchantment ench = Enchantment.enchantmentsList[i];
						if (ench != null) {
							for (int j=1; j<=ench.getMaxLevel(); j++)
								lb.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(ench, j)));
						}
					}
				} if (is.getHasSubtypes()) {
					is.getItem().getSubItems(is.getItem(), null, lb);
				} else {
					lb.add(is);
				}
			}

			// Sort the list by item ID
			List<ItemStack> itemStackList = lb;
			Collections.sort(itemStackList, new Comparator<ItemStack>(){
				public int compare(ItemStack is1, ItemStack is2) {
					return Item.getIdFromItem(is1.getItem()) - Item.getIdFromItem(is2.getItem());
				}
			});
			modItemsLists.put(entry.getKey(), itemStackList);
//			Iterator<ItemStack> it = itemStackList.iterator();
//			
//			// Add blocks with metadata to the sortedList
//			List<ItemStack> sortedListWithMeta = new ArrayList<ItemStack>();
//			while (it.hasNext()) {
//				ItemStack is = it.next();
//				Item item = is.getItem();
//				item.getSubItems(item, null, sortedListWithMeta);
//			}
//			modItemsLists.put(entry.getKey(), sortedListWithMeta);
		}
	}
	/*
	 * Returns the mod name for a given ItemStack
	 */
    public String getModName(ItemStack is) {
    	String name = "", retString = "<unknown>";
    	
    	try {
    		name = is.getItem().getUnlocalizedName();
//    		retString = name.substring(5);
    		retString = modList.get(itemList.get(name.substring(5)));
    		if (retString == null) retString = modList.get(itemList.get(name));
    	} catch(Exception e) { }

    	if (retString == null) return "Minecraft";
    	else return retString;
    }
	/*
	 * Returns the mod name for a given Entity
	 */
    public String getModName(Entity en) {
    	EntityRegistration er = EntityRegistry.instance().lookupModSpawn(en.getClass(), true);
	   	if (er != null) return modList.get(er.getContainer().getModId());
	   	return "Minecraft";
    }
    
    /**
     * A class designed to simplify the handling of Strings, Integers, and Booleans that are stored 
     * in {@link ConfigHandler#activeConfigs} and are saved via {@link HorseHUD#config}.
     * This makes use of several overloaded constructors to determine the stored data {@link Type}<p>
     * 
     * Configurations are referenced by {@link #category} and {@link #key}<p>
     * 
     * The boolean {@link #isConfigurable} determines whether it can be cycled from {@link ConfigScreen}.<p>
     * 
     * The constructors define the default values and are called by: <br>
     *   {@link #strDefault} : {@link ConfigHandler#setupConfig(String, String, String, boolean)}<br>
     *   {@link #boolDefault} : {@link ConfigHandler#setupConfig(String, String, boolean, boolean)}<br>
     *   {@link #intDefault} : {@link ConfigHandler#setupConfig(String, String, int, boolean)} <br>
     *   NOTE: These methods are typically called during a class' init()<p>
     *   
     * The values are called from: <br>
     *   {@link #strValue} : {@link ConfigHandler#getConfigStr(String, String)}<br>
     *   {@link #boolValue} : {@link ConfigHandler#getConfigBool(String, String)}<br>
     *   {@link #intValue} : {@link ConfigHandler#getConfigInt(String, String)}<p>
     *   
     * The values are changed by: <br>
     *   {@link #strValue} : {@link ConfigHandler#setConfig(String, String, String)} <br>
     *   {@link #boolValue} : {@link ConfigHandler#setConfig(String, String, boolean)} <br>
     *   {@link #intValue} : {@link ConfigHandler#setConfig(String, String, int)} <p>
     * 
     * @author Valkyrinn
     */
	public class BasicConfigOption extends AConfigOption {
		private BasicConfigOption(String category, String key, Object defaultValue, int count, boolean isConfigurable, Configuration cfg) {
			super(category, key, defaultValue, count, isConfigurable, cfg);
		}
		@Override public void configure() { }
		
//		private String category, key;//, strValue, strDefault;
////		private boolean boolValue, boolDefault;
////		private int intValue, intDefault, 
//		private int count;
//		private Object value, defaultValue;
//		private Type type;
//		private boolean isConfigurable;
//
//		private Config(String category, String key, Object defaultValue, int count, boolean isConfigurable) {
//			this.category = category;
//			this.key = key;
//			this.value = defaultValue;
//			this.defaultValue = defaultValue;
//			this.count = count;
//			this.isConfigurable = isConfigurable;
//
//			if (defaultValue instanceof String) type = Type.STRING;
//			else if (defaultValue instanceof Boolean) type = Type.BOOLEAN;
//			else if (defaultValue instanceof Integer) type = Type.INTEGER;
//			else if (defaultValue instanceof Point)   type = Type.POINT;
//			else type = Type.OTHER;
//			
//		}
//		
//		public String        category() { return this.category; }
//		public String             key() { return this.key; }
//		public Object           value() { return this.value; }
//		public String  formattedValue() { return category + "." + key + "_" + value.toString(); }
//		public Object    defaultValue() { return this.defaultValue; }
//		public int              count() { return this.count; }
//		public Type              type() { return this.type; }
//		public boolean isConfigurable() { return this.isConfigurable; }
//
//		public boolean   set(Object value, Configuration cfg) { 
//			this.value = value;
//			
//			if (cfg == null) return false;
//			
//			switch (type) {
//				case STRING:  cfg.get(category, key, (String)  defaultValue).set((String)  value); break;
//				case BOOLEAN: cfg.get(category, key, (Boolean) defaultValue).set((Boolean) value); break;
//				case INTEGER: cfg.get(category, key, (Integer) defaultValue).set((Integer) value); break;
//				case POINT:
//					cfg.get(category, key+"X", ((Point)defaultValue).x).set(((Point)value).x);
//					cfg.get(category, key+"Y", ((Point)defaultValue).y).set(((Point)value).y);
//				default: return false;
//			}
//			
//			return true;
//		}
//		
//		public abstract void configure();
//		
//		private Config(String category, String key, String defaultValue, boolean isConfigurable) {
//			this.category = category;
//			this.key = key;
//			this.strValue = defaultValue;
//			this.strDefault = defaultValue;
//			this.isConfigurable = isConfigurable;
//			this.type = Type.STRING;
//		}
//		private Config(String category, String key, boolean defaultValue, boolean isConfigurable) {
//			this.category = category;
//			this.key = key;
//			this.boolValue = defaultValue;
//			this.boolDefault = defaultValue;
//			this.count = 2;
//			this.isConfigurable = isConfigurable;
//			this.type = Type.BOOLEAN;
//		}
//		private Config(String category, String key, int defaultValue, int count, boolean isConfigurable) {
//			this.category = category;
//			this.key = key;
//			this.intValue = defaultValue;
//			this.intDefault = defaultValue;
//			this.count = count;
//			this.isConfigurable = isConfigurable;
//			this.type = Type.INT;
//		}
		/**
		 * Toggles {@link Type#BOOLEAN} and cycles {@link Type#INT}s incrementally through their allowed values.
		 * Attempting to cycle a {@link Type#STRING} has no effect.
		 * 
		 * @return A String version of the new value
		 */
//		private String cycle() {
//			if (type == Type.STRING)
//				return strValue;
//			if (type == Type.BOOLEAN) {
//				boolValue = !boolValue;
//				return boolValue+"";
//			} else if (type == Type.INTEGER) {
//				intValue = (intValue + 1) % count;
//				return intValue+"";
//			}
//			return "";
//		}
	}
	/**
	 * Enumeration used to define the {@link Config#type} that refer to
	 * Strings ({@link #STRING}), Booleans ({@link #STRING}), and Integers ({@link #INT})
	 * 
	 * @author Valkyrinn
	 * @see Config
	 */
//	public static enum Type {
//		STRING,
//		BOOLEAN,
//		INTEGER,
//		POINT,
//		OTHER;
//	}
}
