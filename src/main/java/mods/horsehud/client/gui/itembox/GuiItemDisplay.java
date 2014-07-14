package mods.horsehud.client.gui.itembox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mods.horsehud.HorseHUD;
import mods.horsehud.api.IDisplayRecipe;
import mods.horsehud.api.Keys;
import mods.horsehud.api.Tabs;
import mods.horsehud.client.AConfigOption;
import mods.horsehud.client.ConfigHandler;
import mods.horsehud.client.ConfigKeyHandler;
import mods.horsehud.client.Lang;
import mods.horsehud.client.gui.ConfigScreen;
import mods.horsehud.client.gui.recipes.GuiRecipeDisplay;
import mods.horsehud.client.gui.recipes.RecipeList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.GuiSnooper;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.GuiIngameModOptions;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This is the main ItemBox class.
 * This class contains a Forge hook {@link GuiOpenEvent}, which is registered with the event bus in the init().
 * When a screen is opened that is not in the S_BLACKLIST, an ItemBox is added to the right of the gui. <p>
 *
 * There are four {@link Tabs}: {@link Tabs#ITEMS}, {@link Tabs#MODS}, {@link Tabs#TOOLS}, and {@link Tabs#OPTIONS} <p>
 *
 * @author Valkyrinn
 * @see GuiItemPanel
 * @see GuiModPanel
 * @see Keys
 * @see Tabs
 */
@SideOnly(Side.CLIENT)
public class GuiItemDisplay extends GuiContainer {
	public static final String CATEGORY = "itemdisplay";

	/**
	 * Initialize the configurations and setting for the GuiItemDisplay
	 * The keys are stored in @link Keys
	 */
	public static void init() {
    	MinecraftForge.EVENT_BUS.register(new GuiItemDisplay(Minecraft.getMinecraft()));

    	// Build a list of screens that this does not pop up on
    	List<Class> blacklist = new ArrayList();
		blacklist.add(GuiDownloadTerrain.class);
		blacklist.add(GuiIngameMenu.class);
		blacklist.add(ConfigScreen.class);
		blacklist.add(GuiChat.class);
		blacklist.add(GuiOptions.class);
		blacklist.add(GuiScreenOptionsSounds.class);
		blacklist.add(GuiVideoSettings.class);
		blacklist.add(ScreenChatOptions.class);
		blacklist.add(GuiScreenResourcePacks.class);
		blacklist.add(GuiSnooper.class);
		blacklist.add(GuiControls.class);
		blacklist.add(GuiKeyBindingList.class);
		blacklist.add(GuiAchievement.class);
		blacklist.add(GuiAchievements.class);
		blacklist.add(GuiStats.class);
		blacklist.add(GuiGameOver.class);
		blacklist.add(GuiMemoryErrorScreen.class);
		blacklist.add(GuiLanguage.class);
		blacklist.add(GuiGameOver.class);
		blacklist.add(GuiDisconnected.class);
		blacklist.add(GuiIngameModOptions.class);
		blacklist.add(GuiShareToLan.class);

		// Build a map of all the mods that have items
		LinkedHashMap<String, String> modNames = new LinkedHashMap();
		modNames.put(ALL, ALL);
        for (Map.Entry<String, String> entry : ConfigHandler.instance().modList.entrySet())
//			if (ConfigHandler.instance().modItemsLists.get(entry.getKey()).size() > 0)
				modNames.put(entry.getKey(), entry.getValue());
//		for (int i=0; i<30; i++)
//			modNames.put("DummyValue" + i, "Dummy Value " + i);


		// In-game settings generated on load
		// Unlike configs, these do not persist through saves
		// but they do persist for the duration of the game session
		setSetting(Keys.S_BLACKLIST, blacklist);
		setSetting(Keys.S_MODNAMES, modNames);
		setSetting(Keys.S_RECIPES, new RecipeList());
		setSetting(Keys.S_TAB, Tabs.ITEMS);
		setSetting(Keys.S_PARENT, null);
		setSetting(Keys.S_CATEGORIES, new HashMap<String, Boolean>());
		setSetting(Keys.S_OPTIONS_BARY, 0.0D);

        // Config options to persist through saves
		ConfigHandler chi = ConfigHandler.instance();
		Configuration cfg = HorseHUD.instance.config;
		int max = Integer.MAX_VALUE;
		chi.setupConfig(CATEGORY, Keys.C_KEY.toString(),          "minecraft", 1, false, cfg);
		chi.setupConfig(CATEGORY, Keys.C_VISIBLE.toString(),      true,        2, false, cfg);
		chi.setupConfig(CATEGORY, Keys.C_ITEM_SEARCH.toString(),  "",          1, false, cfg);
		chi.setupConfig(CATEGORY, Keys.C_MODS_SEARCH.toString(),  "",          1, false, cfg);
		chi.setupConfig(CATEGORY, Keys.C_ITEM_BARY.toString(),    0.0D,      max, false, cfg);
		chi.setupConfig(CATEGORY, Keys.C_MODS_BARY.toString(),    0.0D,      max, false, cfg);
//		chi.loadConfigs();
	}
	public static void        setConfig(Keys key, Object  value) {        ConfigHandler.instance().setConfig(CATEGORY, key.toString(), value); }
//	public static void        setConfig(Keys key, String  value) {        ConfigHandler.instance().setConfig(CATEGORY, key.toString(), value); }
//	public static void        setConfig(Keys key, boolean value) {        ConfigHandler.instance().setConfig(CATEGORY, key.toString(), value); }
//	public static void        setConfig(Keys key, int     value) {        ConfigHandler.instance().setConfig(CATEGORY, key.toString(), value); }
	public static String   getConfigStr(Keys key               ) { return (String)  ConfigHandler.instance().getConfig(CATEGORY, key.toString()).value(); }
	public static boolean getConfigBool(Keys key               ) { return (Boolean) ConfigHandler.instance().getConfig(CATEGORY, key.toString()).value(); }
	public static int      getConfigInt(Keys key               ) { return (Integer) ConfigHandler.instance().getConfig(CATEGORY, key.toString()).value(); }
	public static double   getConfigDbl(Keys key               ) { return (Double)  ConfigHandler.instance().getConfig(CATEGORY, key.toString()).value(); }
	public static void       setSetting(Keys key, Object  value) {        ConfigHandler.instance().addSetting(key.toString(), value); }
	public static Object     getSetting(Keys key               ) { return ConfigHandler.instance().getSetting(key.toString()); }

	@SubscribeEvent
	public void onOpenInventory(GuiOpenEvent event) {
		if (event.gui == null) return;
		if (Minecraft.getMinecraft().theWorld == null) return;

		List<Class> blacklist = (List<Class>) getSetting(Keys.S_BLACKLIST);
		if (blacklist.contains(event.gui.getClass())) return;
//		System.out.println("\nGui Opened: " + event.gui.getClass().toString()+"\n");

		event.gui = new GuiItemDisplay(event.gui);
	}

	public static final String ALL = "All";
	protected GuiScreen parent;
	private GuiItemPanel itemPanel;
	private GuiModPanel modPanel;
	private GuiOptionsPanel optionsPanel;
	private GuiToolsPanel toolsPanel;
	private int xw;
	private boolean isTakingScreenshot = false;
	protected boolean isSearching = false;
	
	public GuiItemDisplay(GuiScreen parent) {
		this(Minecraft.getMinecraft());
		this.parent = parent;
		setSetting(Keys.S_PARENT, parent);
	}
	public GuiItemDisplay(Minecraft minecraft) {
    	super(new ItemContainer());
		this.mc = minecraft;
		this.fontRendererObj = this.mc.fontRenderer;
		this.allowUserInput = true;
	}
    public void initGui() {
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        this.width  = scaledresolution.getScaledWidth();
        this.height = scaledresolution.getScaledHeight();
        this.parent.setWorldAndResolution(mc, width, height);
        
        int xSizeParent = 175;
        if (parent.getClass() == GuiContainerCreative.class) xSizeParent = 195;

        this.xw = (width - xSizeParent)/2; //guiLeft

		itemPanel    = new GuiItemPanel(   width-xw+2, 16, xw - 4, height - 36, this);
		modPanel     = new GuiModPanel(    width-xw+2, 16, xw - 4, height - 36, this);
		optionsPanel = new GuiOptionsPanel(width-xw+2, 16, xw - 4, height - 36, this);
		toolsPanel   = new GuiToolsPanel(  width-xw+2, 16, xw - 4, height - 36, this);

    	modPanel.initGui();
    	itemPanel.initGui();
    	optionsPanel.initGui();
    	toolsPanel.initGui();

		this.xSize = this.width;
		this.ySize = this.height;
    }
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int j = mouseY * this.height / this.mc.displayHeight;
//        System.out.println("(" + mouseX + ", " + mouseY + ") - (" + i + ", " + j + ")");
    	if (mouseX > width-16     && mouseX < width-2      && mouseY > 3         && mouseY < 15)         setConfig(Keys.C_VISIBLE, !getConfigBool(Keys.C_VISIBLE));
        if (!getConfigBool(Keys.C_VISIBLE)) return;
        if (modPanel.isMouseOverPanel(mouseX, mouseY) && (Tabs)getSetting(Keys.S_TAB) == Tabs.MODS) {
        	modPanel.mouseClicked(mouseX, mouseY, button);
        	itemPanel.setSelectedMod();
        	setConfig(Keys.C_ITEM_BARY, 0.0D);
//        	setSetting(Keys.S_TAB, Tabs.ITEMS);
        }
        
        Tabs tab = (Tabs)getSetting(Keys.S_TAB);
        if (tab == Tabs.ITEMS)   itemPanel.mouseClicked(mouseX, mouseY, button);
    	if (tab == Tabs.OPTIONS) optionsPanel.mouseClicked(mouseX, mouseY, button);
    	if (tab == Tabs.TOOLS)   toolsPanel.mouseClicked(mouseX, mouseY, button);

    	int x1 = width-xw;
    	int w = (xw-15)/2;
        if (mouseX > x1+2         && mouseX < x1+w-1       && mouseY >= 3        && mouseY <= 16)        setSetting(Keys.S_TAB, Tabs.ITEMS);
        if (mouseX > x1+w+1       && mouseX < x1+w*2-2     && mouseY >= 3        && mouseY <= 16)        setSetting(Keys.S_TAB, Tabs.MODS);
        if (mouseX > width-xw+2   && mouseX < width-xw/2-1 && mouseY <= height-3 && mouseY >= height-19) setSetting(Keys.S_TAB, Tabs.TOOLS);
        if (mouseX > width-xw/2+1 && mouseX < width-2      && mouseY <= height-3 && mouseY >= height-19) setSetting(Keys.S_TAB, Tabs.OPTIONS);
    }
    
    public void handleKeyboardInput() {
    	super.handleKeyboardInput();
    	if (!isSearching)
    		parent.handleKeyboardInput();
    	
    	Tabs tab = (Tabs)getSetting(Keys.S_TAB);
    	if (tab == Tabs.ITEMS) itemPanel.handleKeyboardInput();
    	if (tab == Tabs.MODS)  modPanel.handleKeyboardInput();
    }
    public void handleMouseInput() {
        super.handleMouseInput();
    	int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
    	int mouseY = Mouse.getEventY() * this.width / this.mc.displayHeight + 1;
//        if (!getConfigBool(Keys.C_VISIBLE)) return;

    	if (mouseX < width - xw)
    		parent.handleMouseInput();
    	else if (getConfigBool(Keys.C_VISIBLE)){
        	Tabs tab = (Tabs)getSetting(Keys.S_TAB);
	    	if (tab == Tabs.ITEMS) itemPanel.handleMouseInput();
	    	if (tab == Tabs.MODS)  modPanel.handleMouseInput();
	    	if (tab == Tabs.TOOLS) toolsPanel.handleMouseInput();
	    	if (tab == Tabs.OPTIONS) optionsPanel.handleMouseInput();
    	}
    }
    
    /**
     * Handles all the keybinds for this screen
     * 
     * Current KeyBinds:
     * ConfigKeyHandler.keyList.get(CATEGORY + ".key.visible") : Toggle the visibility of the GuiItemDisplay
     * ConfigKeyHandler.keyList.get(CATEGORY + ".key.recipe")  : Look up recipe for item mouse is over
     * ConfigKeyHandler.keyList.get(CATEGORY + ".key.usage")   : Look up recipe usage for item mouse is over
     * mc.gameSettings.KeyBindScreenshot : Allows user to take a screenshot in any inventory in which this is enabled
     */
    protected void keyTyped(char par1, int par2) {
    	super.keyTyped(par1, par2);

    	// Hide/Show the GuiItemDisplay
       	if (par2 == ConfigKeyHandler.keyList.get(CATEGORY + ".key.visible").getKeyCode() && !isSearching) {
   			setConfig(Keys.C_VISIBLE, !getConfigBool(Keys.C_VISIBLE));
    	}

    	// Open Crafting Recipe screen
    	if (par2 == ConfigKeyHandler.keyList.get(CATEGORY + ".key.recipe").getKeyCode()) {
    		Slot slot = null;
    		try { slot = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)parent, "theSlot"); }
    		catch (Exception e) { System.out.println("Error: " + e); }

    		if (slot != null) {
    			if (slot.getHasStack()) {
    				openCraftingRecipe(mc, parent, slot.getStack());
    			}
    		}
    	}
    	
    	// Open Crafting Usage screen
    	if (par2 == ConfigKeyHandler.keyList.get(CATEGORY + ".key.usage").getKeyCode()) {
    		Slot slot = null;
    		try { slot = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)parent, "theSlot"); }
    		catch (Exception e) { System.out.println("Error: " + e); }

    		if (slot != null) {
    			if (slot.getHasStack()) {
    				openCraftingUsage(mc, parent, slot.getStack());
    			}
    		}
    	}

    	// Take Screenshot
        if (par2 == mc.gameSettings.keyBindScreenshot.getKeyCode()) {
            if (!this.isTakingScreenshot) {
                this.isTakingScreenshot = true;
                mc.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(mc.mcDataDir, mc.displayWidth, mc.displayHeight, mc.getFramebuffer()));
            }
        }// else
            this.isTakingScreenshot = false;
    }
    public void updateScreen() {
    	parent.updateScreen();
    }

    /**
     * Opens a screen displaying all recipes that create the given ItemStack
     * @param mc     - the Minecraft instance
     * @param parent - the screen to return to when the recipe screen is closed
     * @param is     - the item to look up recipes for
     */
    public static void openCraftingRecipe(Minecraft mc, GuiScreen parent, ItemStack is) {
		openCrafting(mc, parent, ((RecipeList)GuiItemDisplay.getSetting(Keys.S_RECIPES)).getRecipesWithOutput(is));
    }
    /**
     * Opens a screen displaying all recipes that use the given ItemStack. 
     * If the ItemStack is a machine with recipes, also opens all recipes for that machine 
     * @param mc     : the Minecraft instance
     * @param parent : the screen to return to when the recipe screen is closed
     * @param is     : the item to look up recipes for
     */
    public static void openCraftingUsage(Minecraft mc, GuiScreen parent, ItemStack is) {
		openCrafting(mc, parent, ((RecipeList)GuiItemDisplay.getSetting(Keys.S_RECIPES)).getRecipesWithInput(is));
    }
    /**
     * Opens a screen displaying all recipes for the given machine (eg. Crafting Table, Furnace) 
     * @param mc     : the Minecraft instance
     * @param parent : the screen to return to when the recipe screen is closed
     * @param is     : the item to look up recipes for
     */
    public static void openRecipesFor(Minecraft mc, GuiScreen parent, ItemStack is) {
    	openCrafting(mc, parent, ((RecipeList)GuiItemDisplay.getSetting(Keys.S_RECIPES)).getAllRecipesFor(is));
    }
    /**
     * Actually opens the crafting screen. 
     * Called from openCraftingRecipe, openCraftingUsage, and openRecipesFor
     * 
     * @param mc      : the Minecraft instance
     * @param parent  : the screen to return to when the recipe screen is closed
     * @param recipes : A list of the recipes to be displayed
     */
    private static void openCrafting(Minecraft mc, GuiScreen parent, List<IDisplayRecipe> recipes) {
		if (recipes.isEmpty()) {
//			System.out.println("No Results");
		} else {
			if (parent instanceof GuiRecipeDisplay) parent = ((GuiRecipeDisplay)parent).parent;
			mc.displayGuiScreen(new GuiRecipeDisplay(parent, recipes));
		}
    }
    /**
     * Called when the mouse is moved or a mouse button is released.
     * @param mouseX : x coordinate of the mouse
     * @param mouseY : y coordinate of the mouse
     * @param which  : -1 is mouseMove, 0 is leftClick, 1 is rightClick
     */
    protected void mouseMovedOrUp(int mouseX, int mouseY, int which) {
    	if ((Tabs)getSetting(Keys.S_TAB) == Tabs.ITEMS && getConfigBool(Keys.C_VISIBLE))
    		itemPanel.mouseMovedOrUp(mouseX, mouseY, which);
    }

    /**
     * Draws the tabs, but not the box, and invokes child methods for the
     * parent, itemPanel, modPanel
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	if (parent instanceof GuiRecipeDisplay)
    		this.drawDefaultBackground();
    	else
    		parent.drawScreen(mouseX, mouseY, partialTicks);

//		if (getConfigBool(Keys.C_VISIBLE))
		this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		setupGfx();
        this.fontRendererObj.drawString(" ", 0, 0, 0xFFFFFF);
		
		if (getConfigBool(Keys.C_VISIBLE)) {
        	Tabs tab = (Tabs)getSetting(Keys.S_TAB);
        	if (tab == Tabs.MODS)        modPanel.drawScreen(mouseX, mouseY, partialTicks);
        	if (tab == Tabs.ITEMS)      itemPanel.drawScreen(mouseX, mouseY, partialTicks);
        	if (tab == Tabs.OPTIONS) optionsPanel.drawScreen(mouseX, mouseY, partialTicks);
        	if (tab == Tabs.TOOLS)     toolsPanel.drawScreen(mouseX, mouseY, partialTicks);

        	// Draw Tabs
        	int x1 = width-xw;
        	int w = (xw-15)/4;
        	int w2 = xw/4;
//    		this.drawGradientRect(    x1    +2,           3,     x1+w*2-1,       16, 0x33DDDDDD, 0x33DDDDDD);
//    		this.drawGradientRect(    x1+w*2+1,           3,     x1+w*4-2,       16, 0x33DDDDDD, 0x33DDDDDD);
//    		this.drawGradientRect(width-xw  +2, height-3-16, width-w2*2-1, height-3, 0x44DDDDDD, 0x33DDDDDD);
//    		this.drawGradientRect(width-w2*2+1, height-3-16, width     -2, height-3, 0x44DDDDDD, 0x33DDDDDD);

            if (mouseX > width-xw+2   && mouseX < width-w*2-1-15 && mouseY >= 3 && mouseY <= 16) {
            	List<String> l = new ArrayList();
            	l.add(EnumChatFormatting.GOLD + "Tab: " + Lang.local(CATEGORY + ".tab.items"));
            	l.addAll(fontRendererObj.listFormattedStringToWidth(EnumChatFormatting.GRAY + Lang.local(CATEGORY + ".tab.items.desc"), 172));
            	this.drawGradientRect(x1-176, 3, x1,   6+10*l.size(), 0x66DDDDDD, 0x66DDDDDD);
            	this.drawGradientRect(x1-175, 4, x1-1, 5+10*l.size(), 0x88000000, 0x88000000);
            	int y = 0;
            	for (String s : l)
            		this.drawString(fontRendererObj, s, x1-173, 5+10*(y++), 0xFFFFFF);
            	if (tab != Tabs.ITEMS)
            		this.drawGradientRect(x1+3, 4, x1+w*2-2, 16, 0x88444444, 0x66444444);
            }
            if (mouseX > width-w*2+1-15 && mouseX < width-2-15 && mouseY >= 3 && mouseY <= 16) {
            	List<String> l = new ArrayList();
            	l.add(EnumChatFormatting.GOLD + "Tab: " + Lang.local(CATEGORY + ".tab.mods"));
            	l.addAll(fontRendererObj.listFormattedStringToWidth(EnumChatFormatting.GRAY + Lang.local(CATEGORY + ".tab.mods.desc"), 172));
            	this.drawGradientRect(x1-176, 3, x1,   6+10*l.size(), 0x66DDDDDD, 0x66DDDDDD);
            	this.drawGradientRect(x1-175, 4, x1-1, 5+10*l.size(), 0x88000000, 0x88000000);
            	int y = 0;
            	for (String s : l)
            		this.drawString(fontRendererObj, s, x1-173, 5+10*(y++), 0xFFFFFF);
            	if (tab != Tabs.MODS)
            		this.drawGradientRect(x1+w*2+2, 4, x1+w*4-3, 16, 0x88444444, 0x66444444);
            }
            if (mouseX > width-xw+2   && mouseX < width-w*2-1-15 && mouseY <= height-3 && mouseY >= height-19) {
            	List<String> l = new ArrayList();
            	l.add(EnumChatFormatting.GOLD + "Tab: " + Lang.local(CATEGORY + ".tab.tools"));
            	l.addAll(fontRendererObj.listFormattedStringToWidth(EnumChatFormatting.GRAY + Lang.local(CATEGORY + ".tab.tools.desc"), 172));
            	this.drawGradientRect(x1-176, height-7-10*l.size(), x1,   height-4, 0x66DDDDDD, 0x66DDDDDD);
            	this.drawGradientRect(x1-175, height-6-10*l.size(), x1-1, height-5, 0x88000000, 0x88000000);
            	int y = l.size();
            	for (String s : l)
            		this.drawString(fontRendererObj, s, x1-173, height-5-10*(y--), 0xFFFFFF);
            	if (tab != Tabs.TOOLS)
            		this.drawGradientRect(width-xw+3, height-19, width-w2*2-2, height-4, 0x88444444, 0x66444444);
            }
            if (mouseX > width-xw/2+1 && mouseX < width-2      && mouseY <= height-3 && mouseY >= height-19) {
            	List<String> l = new ArrayList();
            	l.add(EnumChatFormatting.GOLD + "Tab: " + Lang.local(CATEGORY + ".tab.options"));
            	l.addAll(fontRendererObj.listFormattedStringToWidth(EnumChatFormatting.GRAY + Lang.local(CATEGORY + ".tab.options.desc"), 172));
            	this.drawGradientRect(x1-176, height-7-10*l.size(), x1,   height-4, 0x66DDDDDD, 0x66DDDDDD);
            	this.drawGradientRect(x1-175, height-6-10*l.size(), x1-1, height-5, 0x88000000, 0x88000000);
            	int y = l.size();
            	for (String s : l)
            		this.drawString(fontRendererObj, s, x1-173, height-5-10*(y--), 0xFFFFFF);
            	if (tab != Tabs.OPTIONS)
            		this.drawGradientRect(width-w2*2+2, height-19, width-3, height-4, 0x88444444, 0x66444444);
            }

//    		if (tab == Tabs.MODS)    this.drawGradientRect(x1+w*2+2,     4,           x1+w*4-3, 17,       0x55000000, 0x55000000);
//    		if (tab == Tabs.ITEMS)   this.drawGradientRect(x1+3,         4,           x1+w*2-2, 17,       0x55000000, 0x55000000);
//    		if (tab == Tabs.OPTIONS) this.drawGradientRect(width-w2*2+2, height-4-16, width-3,      height-4, 0x88000000, 0x66000000);
//    		if (tab == Tabs.TOOLS)   this.drawGradientRect(x1+3,         height-4-16, width-w2*2-2, height-4, 0x88000000, 0x66000000);

    		this.drawCenteredString(mc.fontRenderer, Lang.local(CATEGORY + ".tab.mods"),    x1+w*3,    6, 0x888888);
			this.drawCenteredString(mc.fontRenderer, Lang.local(CATEGORY + ".tab.items"),   x1+w,  6, 0x888888);
    		this.drawCenteredString(mc.fontRenderer, Lang.local(CATEGORY + ".tab.options"), width-w2,   height-14, 0x888888);
			this.drawCenteredString(mc.fontRenderer, Lang.local(CATEGORY + ".tab.tools"),   width-w2*3, height-14, 0x888888);
			
        }

    	int x1 = width-xw;
    	int w = (xw-15)/4;
		this.drawGradientRect(width-16, 3, width-3, 15, 0x55DDDDDD, 0x44DDDDDD);
    	this.drawGradientRect(width-15,   4, width-4, 14, 0xCC333333, 0xCC333333);
//		this.drawGradientRect(x1+w*4-1, 3, width-3, 15, 0x55DDDDDD, 0x44DDDDDD);
//    	this.drawGradientRect(x1+w*4,   4, width-4, 14, 0xCC222222, 0xCC222222);
    	if (mouseX > width-16 && mouseX < width-2 && mouseY > 3 && mouseY < 15) {
    		String s = Lang.local("itemdisplay.close_" + getConfigBool(Keys.C_VISIBLE));
    		int w2 = fontRendererObj.getStringWidth(s)+4;
        	this.drawGradientRect(width-17-w2, 3, width-16, 15, 0x55DDDDDD, 0x44DDDDDD);
        	this.drawGradientRect(width-16-w2, 4, width-15, 14, 0xCC222222, 0xCC222222);
    		this.drawString(mc.fontRenderer, s,   width-14-w2, 5, 0x888888);
        	this.drawGradientRect(width-15,   4, width-4, 14, 0xCC222222, 0xCC222222);
//        	this.drawGradientRect(width-17-w2, 3, x1+w*4-1, 15, 0x55DDDDDD, 0x44DDDDDD);
//        	this.drawGradientRect(width-16-w2, 4, x1+w*4, 14, 0xCC222222, 0xCC222222);
//    		this.drawString(mc.fontRenderer, s,   x1+w*4+3-w2, 5, 0x888888);
    	}
		int w3 = (x1+w*4-width+4)/2+fontRendererObj.getStringWidth("X")/2;
//		this.drawCenteredString(mc.fontRenderer, "X",   width-10, 5, 0x888888);
		fontRendererObj.drawString("X", width-12, 5, 0x888888, false);

		if (parent instanceof GuiRecipeDisplay)
    		parent.drawScreen(mouseX, mouseY, partialTicks);

//    	GL11.glPopMatrix();
//        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        RenderHelper.enableStandardItemLighting();
    	closeGfx();
    }
    
    /**
     * Draws the main box, as well as the child methods (and possibly parent)
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        if (getConfigBool(Keys.C_VISIBLE)) {
            this.fontRendererObj.drawString(" ", 0, 0, 0xFFFFFF);
        	// Draw Tabs
        	int x1 = width-xw;
        	int w = (xw-15)/4;
        	int w2 = xw/4;
    		this.drawGradientRect(    x1    +2,           3,     x1+w*2-1,       16, 0x33DDDDDD, 0x33DDDDDD);
    		this.drawGradientRect(    x1+w*2+1,           3,     x1+w*4-2,       16, 0x33DDDDDD, 0x33DDDDDD);
    		this.drawGradientRect(width-xw  +2, height-3-16, width-w2*2-1, height-3, 0x33DDDDDD, 0x33DDDDDD);
    		this.drawGradientRect(width-w2*2+1, height-3-16, width     -2, height-3, 0x33DDDDDD, 0x33DDDDDD);

    		// Draw box body
    		this.drawGradientRect(width-xw+2, 16, width-2, height-19, 0x33DDDDDD, 0x33DDDDDD);
    		this.drawGradientRect(width-xw+3, 17, width-3, height-20, 0x50000000, 0x50000000);

        	Tabs tab = (Tabs)getSetting(Keys.S_TAB);
    		if (tab == Tabs.MODS)    this.drawGradientRect(x1+w*2+2,     4,           x1+w*4-3, 17,       0x55000000, 0x55000000);
    		if (tab == Tabs.ITEMS)   this.drawGradientRect(x1+3,         4,           x1+w*2-2, 17,       0x50000000, 0x50000000);
    		if (tab == Tabs.OPTIONS) this.drawGradientRect(width-w2*2+2, height-4-16, width-3,      height-4, 0x50000000, 0x50000000);
    		if (tab == Tabs.TOOLS)   this.drawGradientRect(x1+3,         height-4-16, width-w2*2-2, height-4, 0x50000000, 0x50000000);

    		// Draw Items tab, if selected
    		if ((Tabs)getSetting(Keys.S_TAB) == Tabs.ITEMS)
        		itemPanel.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        }
    	if (parent instanceof GuiRecipeDisplay)
    		((GuiRecipeDisplay) parent).drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}
    
    /**
     * Set the GL11 setting typically called at the start of a drawX() method
     */
    protected static void setupGfx() {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        short short1 = 240;
        short short2 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)short1 / 1.0F, (float)short2 / 1.0F);
    }
    /**
     * Set the GL11 settings typically called at the end of a drawX() method
     */
    protected static void closeGfx() {
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    /**
     * An empty container to be sent to superconstructors of container screens.
     * @author Valkyrinn
     */
    public static class ItemContainer extends Container {
    	public ItemContainer() {}

		@Override
		public boolean canInteractWith(EntityPlayer var1) {
			return true;
		}
    }
}
