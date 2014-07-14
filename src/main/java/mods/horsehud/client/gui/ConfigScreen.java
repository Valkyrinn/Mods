package mods.horsehud.client.gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mods.horsehud.HorseHUD;
import mods.horsehud.api.Keys;
import mods.horsehud.client.ConfigHandler;
import mods.horsehud.client.Lang;
import mods.horsehud.client.gui.infobox.GuiInfoBox;
import mods.horsehud.client.gui.infobox.InfoBox;
import mods.horsehud.client.gui.itembox.GuiItemDisplay;
import mods.horsehud.client.gui.recipes.RecipeList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConfigScreen extends GuiScreen {
//    public static final String C_POSX = "posX", C_POSY = "posY", C_VISIBILITY = "tooltipVisibility", C_FLUID = GuiInfoBar.C_FLUID, C_META = GuiInfoBar.C_META, C_MINE = GuiInfoBar.C_MINE, C_MODVISIBLE = GuiInfoBar.C_MODVISIBLE;
//	public static void setConfig(String key, String  value) { GuiInfoBar.setConfig(key, value); }
//	public static void setConfig(String key, boolean value) { GuiInfoBar.setConfig(key, value); }
//	public static void setConfig(String key, int     value) { GuiInfoBar.setConfig(key, value); }
//	public static boolean getConfigBool(String key) { return GuiInfoBar.getConfigBool(key); }
//	public static int     getConfigInt( String key) { return GuiInfoBar.getConfigInt(key); }
//	public static String cycleConfig(String category, String key) { return ConfigHandler.instance().cycleConfig(category, key); }

	protected Minecraft mc;
	protected GuiScreen parent;
    protected List buttonList = new ArrayList();
    private List displayStrings = new ArrayList();
    private GuiButton selectedButton;
    private int xOffset = 200;
    
    private List infoList = new ArrayList();
	private Map<String, List<String>> configs = new HashMap();
	private Map<Integer, String> buttonLabels = new HashMap();
//    private String subTitle = "", mod = "Minecraft";
    private int mX=0, mY=0;
//    private int posX=2, posY=2;
    private Point position = new Point(2,2);

//    private String title, subTitle, modName;
//	private int health, maxHealth, armor;
//	private List infoList;
//	private boolean hasIcon;
//	private ItemStack is;
	
//	public void clearAll() {
//		infoList = new ArrayList(); 
//		title = "";
//		subTitle = "";
//		modName = "";
//		health = 0;
//		armor = 0;
//		maxHealth = 0;
//		is = null;
//		hasIcon = false;
//	}

	public ConfigScreen(GuiScreen parent) {
//		super();
		this.mc = Minecraft.getMinecraft();
		this.parent = parent;
		ConfigHandler chi = ConfigHandler.instance();

//		for (Entry<String, Map<String, Config>> entry : chi.activeConfigs.entrySet()) {
//			String category = entry.getKey();
//			List<String> keys = new ArrayList();
//			int var = 0;
//			for (Entry<String, Config> entry2 : entry.getValue().entrySet()) {
//				String key = entry2.getKey();
//				Config config = entry2.getValue();
//				if (entry2.getValue().isConfigurable) {
//					String label = category + "." + key + "_";
//					if (config.type == Type.STRING)  label += config.strValue;
//					if (config.type == Type.BOOLEAN) label += config.boolValue;
//					if (config.type == Type.INT)     label += config.intValue;
//					label = Lang.local(label);
//					this.buttonList.add(new GuiButton(var, xOffset,20*(var+1), 100,14, label));
//					buttonLabels.put(var, category + "." + key);
//					var++;
//					keys.add(key);
//				}
//			}
//			configs.put(category, keys);
//		}
		
//		this.displayStrings.add(new String[]{"Tooltips", "Hidden", "Visible", "Only Mobs"});
//		this.displayStrings.add(new String[]{"Liquids", "Hidden", "Visible"});
//		this.displayStrings.add(new String[]{"Block ID:Metadata", "Hidden", "Visible"});
//		this.displayStrings.add(new String[]{"Mod Name", "Hidden", "Visible"});
//		this.displayStrings.add(new String[]{"Harvest Info", "Hidden", "Visible", "Only Ores"});

//		int[] settings = {
//				getConfigInt(C_VISIBILITY)+1,
//				getConfigBool(C_FLUID)?2:1,
//				getConfigBool(C_META)?2:1,
//				getConfigBool(C_MODVISIBLE)?2:1,
//				getConfigInt(C_MINE)+1
//		};
//		for (int k=0; k<displayStrings.size(); k++)
//			this.buttonList.add(new GuiButton(k, xOffset,20*(k+1), 100,20, ((String[])displayStrings.get(k))[settings[k]]));

		position = GuiInfoBox.getConfigPoint(Keys.C_POSITION);
//		posX = position.x;
//		posY = position.y;
//        posX = getConfigInt(C_POSX);
//        posY = getConfigInt(C_POSY);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
		xOffset = this.width/2;
		int yOffset = (this.height-120)/2;
		this.drawDefaultBackground();

		this.drawCenteredString(mc.fontRenderer, EnumChatFormatting.BOLD + HorseHUD.NAME + " Config Screen", xOffset, yOffset, 0xAA4040);
		ConfigHandler chi = ConfigHandler.instance();

//        for (int k = 0; k < this.buttonList.size(); ++k) {
//        	GuiButton button = ((GuiButton)this.buttonList.get(k));
//        		button.xPosition = xOffset;
//        		button.yPosition = yOffset + 20*(k+1)-6;
//        		button.drawButton(this.mc, mouseX, mouseY);
//        	String s = ((String[])displayStrings.get(k))[0];
//        		this.drawString(mc.fontRenderer, s, xOffset-mc.fontRenderer.getStringWidth(s)-4, 20*(k+1)+yOffset, 0xFFFFFF);
//        }

		Iterator<GuiButton> it = buttonList.iterator();
		for (Entry<String, List<String>> entry : configs.entrySet()) {
			String category = entry.getKey();
			for (String key : entry.getValue()) {
				GuiButton button = it.next();
        		button.xPosition = xOffset;
        		button.yPosition = yOffset + 20*(button.id+1)-3;
				button.drawButton(mc, mouseX, mouseY);
//				String s = LanguageRegistry.instance().getStringLocalization("horsehud:"+category + "." + key);
				String s = Lang.local(category + "." + key);
        		this.drawString(mc.fontRenderer, s, xOffset-mc.fontRenderer.getStringWidth(s)-4, 20*(button.id+1)+yOffset, 0xFFFFFF);
			}
		}
		
//		chi.clearAll();
        String title = EnumChatFormatting.BOLD + Blocks.stone.getLocalizedName();
        String subTitle = (GuiInfoBox.getConfigBool(Keys.C_META)) ? "ID: 1:0" : "";
        ItemStack is = new ItemStack(Blocks.stone);
        List<String> infoList = new ArrayList();
        if (GuiInfoBox.getConfigBool(Keys.C_FLUID)) infoList.add(EnumChatFormatting.AQUA +""+ EnumChatFormatting.ITALIC + "Water");
        if (GuiInfoBox.getConfigInt(Keys.C_MINE) == 1) infoList.add("Any Pickaxe");
        String modName = (GuiInfoBox.getConfigBool(Keys.C_MODVISIBLE))?"Minecraft":"";

//        Point position = GuiInfoBar.getConfigPoint(Keys.C_POSITION);
        InfoBox.instance().drawBox(title, subTitle, 0, 0, 0, infoList, modName, is, position.x, position.y);


//        chi.setTitle(EnumChatFormatting.BOLD + Blocks.stone.getLocalizedName());//+((chi.isMetaVisible())?" "+InfoBox.DGRAY+"1:0":""));
//        if (chi.isMetaVisible()) chi.setSubTitle("ID: 1:0");
//		chi.setItemStack(new ItemStack(Blocks.stone));
//		chi.setHasIcon(true);
//        if (chi.isFluidVisible()) chi.addInfo(EnumChatFormatting.AQUA +""+ EnumChatFormatting.ITALIC + "Water");
//        if (chi.miningTip() == 1) chi.addInfo("Any Pickaxe");
//        chi.setModName("Minecraft");
//        chi.drawBox(mc, this);

		
	}
	
	@Override
    protected void mouseClicked(int par1, int par2, int par3) {
		mX = par1; mY = par2;
        if (par3 == 0) {
            for (int l = 0; l < this.buttonList.size(); ++l) {
                GuiButton guibutton = (GuiButton)this.buttonList.get(l);

                if (guibutton.mousePressed(this.mc, par1, par2))
                {
                    this.selectedButton = guibutton;
                    guibutton.func_146113_a(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
//                    String[] s = ((String[])displayStrings.get(guibutton.id));
//                    System.out.println("Button Pressed: " + s[0]);
//                    ConfigHandler chi = ConfigHandler.instance();
//                    System.out.println(guibutton.displayString);
//                    String[] s = buttonLabels.get(guibutton.id).split("_");
//                    String[] s = buttonLabels.get(guibutton.id).split("\\.", 2);
//                    String state = cycleConfig(s[0], s[1]);
//                    guibutton.displayString = Lang.local(s[0] + "." + s[1] + "_" + state);
                    
//                    for (int i=0; i<s.length; ++i) {
//                    	System.out.println(s[i]);
//                    }
/*                    if (s[0] == "Tooltips") {
                    	int visibility = (getConfigInt(C_VISIBILITY)+1)%3;
                    	setConfig(C_VISIBILITY, visibility);
                    	guibutton.displayString = s[visibility+1];
//                    	chi.toggleVisibility();
//                    	guibutton.displayString = s[chi.visibility()+1];
                    } else if (s[0] == "Liquids") {
                    	boolean fluid = !getConfigBool(C_FLUID);
                    	setConfig(C_FLUID, fluid);
                    	guibutton.displayString = s[fluid?2:1];
//                    	chi.toggleFluidVisibility();
//                    	guibutton.displayString = s[chi.isFluidVisible()?2:1];
                    } else if (s[0] == "Block ID:Metadata") {
                    	boolean meta = !getConfigBool(C_META);
                    	setConfig(C_META, meta);
                    	guibutton.displayString = s[meta?2:1];
//                    	chi.toggleMetaVisibility();
//                    	guibutton.displayString = s[chi.isMetaVisible()?2:1];
                    } else if (s[0] == "Mod Name") {
                    	boolean mod = !getConfigBool(C_MODVISIBLE);
                    	setConfig(C_MODVISIBLE, mod);
                    	guibutton.displayString = s[mod?2:1];
//                    	chi.toggleModVisibility();
//                    	guibutton.displayString = s[chi.isModVisible()?2:1];
                    } else if (s[0] == "Harvest Info") {
                    	int mine = (getConfigInt(C_MINE)+1)%3;
                    	setConfig(C_MINE, mine);
                    	guibutton.displayString = s[mine+1];
//                    	chi.cycleMiningTipVisibility();
//                    	guibutton.displayString = s[chi.miningTip()+1];
                    }*/
                }
            }
        }
    }
	protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long time) {
		int rows = infoList.size()+1;
		ConfigHandler chi = ConfigHandler.instance();
		if (GuiInfoBox.getConfigBool(Keys.C_FLUID)) rows++;
		if (GuiInfoBox.getConfigBool(Keys.C_META)) rows++;
		if (GuiInfoBox.getConfigInt( Keys.C_MINE) == 1) rows++;
		if (GuiInfoBox.getConfigBool(Keys.C_MODVISIBLE)) rows++;
		int h = rows*10 + 10;
		if ((mouseX >= position.x && mouseX <= position.x+120) && (mouseY >= position.y && mouseY <= position.y+h)) {
//			posX = mouseX-30;
//			posY = mouseY-10;
			position = new Point(mouseX-30, mouseY-10);
//			GuiInfoBar.setConfig(Keys.C_POSITION, position);
//			setConfig(C_POSX, posX);
//			setConfig(C_POSY, posY);
//			chi.setPos(posX, posY);
		}
	}
    public void keyTyped(char par1, int par2) {
        if (par2 == mc.gameSettings.keyBindInventory.getKeyCode() || par2 == 1) {
        	GuiInfoBox.setConfig(Keys.C_POSITION, position);
        	mc.displayGuiScreen(parent);
        }
    }
//    public void onGuiClosed() { GuiInfoBar.setConfig(Keys.C_POSITION, position); }
}
