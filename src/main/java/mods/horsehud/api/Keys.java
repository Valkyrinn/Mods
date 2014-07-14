package mods.horsehud.api;

public enum Keys {
	// InfoBar keys
	C_POSITION,
	C_SCALE, 
	C_VISIBILITY, 
	C_FLUID, 
	C_META, 
	C_MINE, 
	C_MODVISIBLE,

	// ItemDisplay keys
	S_PARENT,
	/**
	 * Setting | ArrayList< Class > : A list of GUIs the GuiItemDisplay is not allowed to appear on.
	 */
	S_BLACKLIST,
	/**
	 * Setting | LinkedHashMap<String, String> : A mapping of mods that have items by <modID, displayName>
	 */
	S_MODNAMES,
	/**
	 * Setting | RecipeList : Contains a reference to a @link RecipeList
	 */
	S_RECIPES,
	/**
	 * Setting | Tabs : Currently selected tab. Contains one of @link Tabs
	 */
	S_TAB,
	/**
	 * Setting | HashMap<String, Boolean> : A mapping of isCollapsed for each category in GuiOptionsPanel 
	 */
	S_CATEGORIES,
	/**
	 * Setting | Integer : The y value of the GuiScrollBar in GuiOptionsPanel
	 */
	S_OPTIONS_BARY,

	/**
	 * Config | String: The currently selected mod
	 */
	C_KEY,
	/**
	 * Config | Boolean: Whether the GuiItemDisplay is visible
	 */
	C_VISIBLE,
	/**
	 * Config | String: The contents of the itemPanel's searchField
	 */
	C_ITEM_SEARCH,
	/**
	 * Config | String: The contents of the modPanel's searchField
	 */
	C_MODS_SEARCH,
	/**
	 * Config | Integer: The y location of the itemPanel's GuiScrollBar
	 */
	C_ITEM_BARY,
	/**
	 * Config | Integer: the y location of the modPanel's GuiScrollBar
	 */
	C_MODS_BARY;

}
