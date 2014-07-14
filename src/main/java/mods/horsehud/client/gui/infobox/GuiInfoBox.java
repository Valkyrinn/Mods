package mods.horsehud.client.gui.infobox;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import mods.horsehud.HorseHUD;
import mods.horsehud.api.IDataProvider;
import mods.horsehud.api.Keys;
import mods.horsehud.client.AConfigOption;
import mods.horsehud.client.ConfigHandler;
import mods.horsehud.client.gui.ConfigScreen;
import mods.horsehud.client.gui.infobox.addons.VanillaMC;
import mods.horsehud.client.gui.itembox.GuiItemDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiInfoBox {

    private Minecraft mc;
	private String title, subTitle, modName;
	private int health, maxHealth, armor;
	private List infoList;
//	private boolean hasIcon;
	private ItemStack is;
	public void clearAll() {
		infoList = new ArrayList(); 
		title = "";
		subTitle = "";
		modName = "";
		health = 0;
		armor = 0;
		maxHealth = 0;
		is = null;
//		hasIcon = false;
	}
    
    public GuiInfoBox(Minecraft mc) {
      this.mc = mc;
    }
    
	// Visibility indexes for reference
	public static final int V_NONE = 0, V_ALL = 1, V_MOBS = 2;
    public static final String CATEGORY = "infobox";
//    public static final String C_POSX = "posX", C_POSY = "posY", C_SCALE = "scale", C_VISIBILITY = "0tooltipVisibility", C_FLUID = "isFluidVisible", C_META = "isBlockIDMetaVisible", C_MINE = "isHarvestInfoVisible", C_MODVISIBLE = "isModVisible";
	public static void setConfig(Object key, Object  value) { ConfigHandler.instance().setConfig(CATEGORY, key, value); }
//	public static void setConfig(String key, String  value) { ConfigHandler.instance().setConfig(CATEGORY, key, value); }
//	public static void setConfig(String key, boolean value) { ConfigHandler.instance().setConfig(CATEGORY, key, value); }
//	public static void setConfig(String key, int     value) { ConfigHandler.instance().setConfig(CATEGORY, key, value); }
	public static String  getConfigStr(  Object key) { return (String)  ConfigHandler.instance().getConfig(CATEGORY, key).value(); }
	public static boolean getConfigBool( Object key) { return (Boolean) ConfigHandler.instance().getConfig(CATEGORY, key).value(); }
	public static int     getConfigInt(  Object key) { return (Integer) ConfigHandler.instance().getConfig(CATEGORY, key).value(); }
	public static Point   getConfigPoint(Object key) { return (Point)   ConfigHandler.instance().getConfig(CATEGORY, key).value(); }

    public static void init() {
    	MinecraftForge.EVENT_BUS.register(new GuiInfoBox(Minecraft.getMinecraft()));

    	ConfigHandler chi = ConfigHandler.instance();
    	Configuration cfg = HorseHUD.instance.config;
    	int max = Integer.MAX_VALUE;
//    	chi.setupConfig(CATEGORY, C_POSX,          2, max, false);
//    	chi.setupConfig(CATEGORY, C_POSY,          2, max, false);
//    	chi.setupConfig(CATEGORY, Keys.C_SCALE,               100, max, false);
    	chi.setupConfig(CATEGORY, Keys.C_VISIBILITY,            1,   3,  true, cfg);
//    	chi.setupConfig(CATEGORY, Keys.C_POSITION, new Point(2,2),   1,  true);
		chi.addConfig(CATEGORY, Keys.C_POSITION, new AConfigOption(CATEGORY, Keys.C_POSITION.toString(), new Point(2,2), 1, true, cfg ) {
			@Override
			public void configure() {
				Minecraft mc = Minecraft.getMinecraft();
				mc.displayGuiScreen(new ConfigScreen((GuiScreen) GuiItemDisplay.getSetting(Keys.S_PARENT)));
			}
		});
    	chi.setupConfig(CATEGORY, Keys.C_FLUID,              true,   2,  true, cfg);
    	chi.setupConfig(CATEGORY, Keys.C_META,              false,   2,  true, cfg);
    	chi.setupConfig(CATEGORY, Keys.C_MINE,                  1,   3,  true, cfg);
    	chi.setupConfig(CATEGORY, Keys.C_MODVISIBLE,         true,   2,  true, cfg);
//    	chi.loadConfigs();
        
        IDataProvider ip = new VanillaMC();
        ip.register(chi);
//		ConfigHandler.instance().register(EntityHorse.class, ip);
    }
    
    // This event is called by GuiIngameForge during each frame by
    // GuiIngameForge.pre() and GuiIngameForce.post().
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void drawTooltip(RenderGameOverlayEvent event) {
    	if(event.isCancelable() || event.type != ElementType.EXPERIENCE) { return; }
    	if (getConfigInt(Keys.C_VISIBILITY) == 0) { return; }

    	ConfigHandler chi = ConfigHandler.instance();
    	DataAccessor acc = DataAccessor.instance;
		double d0 = (double)this.mc.playerController.getBlockReachDistance();
		boolean flag = true;

		if (!acc.set(mc.theWorld, mc.objectMouseOver, mc.renderViewEntity))   // Looking at entity
			if (!(acc.set(mc.theWorld, mc.thePlayer, d0, mc.renderViewEntity) && getConfigInt(Keys.C_VISIBILITY) == 1)) // Looking at block
				flag = false;
		if (flag) if (!acc.isEntityLiving() && getConfigInt(Keys.C_VISIBILITY) == 2) flag = false;
		if (!flag) clearAll();
    	if (event.partialTicks > 0.3) flag = false; else clearAll();
    	

		if (flag) {
			DataProvider provider = new DataProvider();
    	    IDataProvider iprovider = null;
			if (chi.getException(acc.getBlockClass()) != null )
				iprovider = chi.getException(acc.getBlockClass());
			else if (chi.getException(acc.getEntityClass()) != null)
				iprovider = chi.getException(acc.getEntityClass());
			boolean flag2 = (iprovider == null);

    	    is = provider.getItemStack(  is, acc, chi);// : iprovider.getItemStack(acc, chi);
    	    if (!flag2) is = iprovider.getItemStack(is, acc, chi);

    	    title =     provider.getTitle(    is, acc, chi, title);
    	    subTitle =  provider.getSubTitle( is, acc, chi, subTitle);
    	    health =    provider.getHealth(       acc, chi, health);
    	    maxHealth = provider.getMaxHealth(    acc, chi, maxHealth);
    	    armor =     provider.getArmor(        acc, chi, armor);
    	    infoList =  provider.getInfo(     is, acc, chi, infoList);
    	    modName =   provider.getModName(  is, acc, chi, modName);
//    	    if (getConfigBool(C_MODVISIBLE))
//	    	    modName = (acc.getEntity() != null) ? chi.getModName(acc.getEntity()) : chi.getModName(is);
//        	chi.addInfo("partialTicks: " + (int)(event.partialTicks*100));


			if (!flag2) {
	    	    title =     iprovider.getTitle(    is, acc, chi, title);
	    	    subTitle =  iprovider.getSubTitle( is, acc, chi, subTitle);
//	    	    is =        iprovider.getItemStack(acc, chi, is);
	    	    health =    iprovider.getHealth(       acc, chi, health);
	    	    maxHealth = iprovider.getMaxHealth(    acc, chi, maxHealth);
	    	    armor =     iprovider.getArmor(        acc, chi, armor);
	    	    infoList =  iprovider.getInfo(     is, acc, chi, infoList);
	    	    modName =   iprovider.getModName(  is, acc, chi, modName);
			}
//			infoList.add(acc.getBlock().getClass().toString());
			// List nearby dropped items
    	    List list = acc.getNearbyEntities(d0);
    	    for (int i = 0; i < list.size(); ++i)
    	    {
    	    	Entity e = (Entity)list.get(i);
    	    	if (e instanceof EntityItem) {
    				ItemStack its = ((EntityItem)e).getEntityItem();
    				int k = its.stackSize;
    	    		String s = its.getDisplayName();
    	    		if (k > 1) s = k + "x " + s;
    	    		infoList.add(s);
	    	    } else if (!(e instanceof EntityLiving)) {
//	    	    	infoList.add(e.getCommandSenderName());
	    	    }
	    	}
		}
		if (mc.currentScreen == null) {
			Point position = getConfigPoint(Keys.C_POSITION);
			InfoBox.instance().drawBox(title, subTitle, health, maxHealth, armor, infoList, modName, is, position.x, position.y);
		}
    }
}
