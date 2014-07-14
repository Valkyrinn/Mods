package mods.horsehud.api;

public enum Tabs {
	/**
	 * This tab is handled in the GuiItemPanel and contains all the items in the game, sorted by mod and ID.
	 * You the mod displayed is determined by the MODS tab. Hovering over an item will give you a tooltip. 
	 * Left-clicking on an item will open a GuiRecipeDisplay screen containing all crafting recipes 
	 * that produce that item. Right-clicking on an item will open all recipes that use that item as an ingredient
	 * or as a machine. By holding shift, you can give yourself a stack of items by left-clicking or a single item by
	 * right-clicking on an item. You can also search for items in the searchField, or clear the field with a right-click. 
	 */
	ITEMS,
	/**
	 * This tab is handled in the GuiModPanel and displays a list of all mods registered with Forge 
	 * that add items to the game. By left-clicking on a mod name, you filter items in the ITEMS tab to only display 
	 * items from that mod. There is an ALL option, which lists all the items in the game, appending the modname to 
	 * the tooltip. You can search the list of mods the same way that you can search for items. Right-click clears 
	 * the searchField.
	 */
	MODS,
	/**
	 * TODO Options tab
	 */
	OPTIONS,
	/**
	 * TODO Tools tab
	 */
	TOOLS;
}
