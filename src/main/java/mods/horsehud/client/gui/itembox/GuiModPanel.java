package mods.horsehud.client.gui.itembox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

import mods.horsehud.api.Keys;
import mods.horsehud.client.ConfigHandler;
import mods.horsehud.client.gui.GuiScrollBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

public class GuiModPanel extends GuiScreen {
	private int xPos, yPos, searchY;
	private LinkedHashMap<String, String> searchMap;
	private GuiScrollBar gsb;
	private GuiTextField searchField;
	private double barMod;
	private boolean hasScrollbar;
	private GuiItemDisplay parent;

	public GuiModPanel(int xPos, int yPos, int width, int height, GuiItemDisplay parent) {
		this.parent = parent;
		this.mc = parent.mc;
		this.fontRendererObj = mc.fontRenderer;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}
	public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiScrollBar(0, 1,1, 8,10));
        gsb = (GuiScrollBar) this.buttonList.get(0);
		gsb.xPosition = xPos+width-9;
		gsb.barY = GuiItemDisplay.getConfigDbl(Keys.C_MODS_BARY);
		gsb.minY = yPos+1;

		Keyboard.enableRepeatEvents(true);
        this.searchField = new GuiTextField(this.fontRendererObj, xPos+5, yPos+height-11, width-12, 20);
        this.searchField.setMaxStringLength(130);
        this.searchField.setEnableBackgroundDrawing(false);
        this.searchField.setVisible(true);
        this.searchField.setTextColor(0x888888);
        this.searchField.setCanLoseFocus(true);
        this.searchField.setFocused(false);
        this.searchField.setText(GuiItemDisplay.getConfigStr(Keys.C_MODS_SEARCH));
        this.searchY = 14;
        this.search();
    }
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// Draw Search Box
		this.drawGradientRect(xPos+2, yPos+height-searchY, xPos+width-2, yPos+height-1, 0x33DDDDDD, 0x44DDDDDD);
		this.drawGradientRect(xPos+3, yPos+height-searchY+1, xPos+width-3, yPos+height-2, 0x77000000, 0x88000000);
		if (this.searchField.getText().length() == 0 && !this.searchField.isFocused())
			this.drawString(fontRendererObj, EnumChatFormatting.ITALIC + "Search...", xPos+5, yPos+height-11, 0x888888);
		this.searchField.drawTextBox();

		// Draw Scrollbar
		if (hasScrollbar) {
			gsb.barY = Math.max(Math.min(gsb.barY, gsb.maxY), 0);
			gsb.yPosition = (int) (gsb.barY+yPos+1);
			this.drawGradientRect(xPos+width-10, yPos+1, xPos+width-1, yPos+height-searchY, 0x33DDDDDD, 0x55DDDDDD);
			this.drawGradientRect(xPos+width-9,  yPos+1, xPos+width-1, yPos+height-searchY, 0xAA000000, 0x9F000000);
			gsb.drawButton(mc, mouseX, mouseY);
		}

		// Draw the list of mods
		int var = 0;
		for (Entry<String, String> entry : searchMap.entrySet()) {
			int ijy = (int)(yPos+var*10+5-gsb.barY/barMod);
			if (ijy <= yPos+1 || ijy > yPos+height-searchY-9) var++;
			else {
				String key = GuiItemDisplay.getConfigStr(Keys.C_KEY);
				String modName = entry.getValue();
				if (mouseX > xPos && mouseX<xPos+width-(hasScrollbar?10:0) && mouseY > ijy-1 && mouseY < ijy+9) {
					
					List<String> modInfo = new ArrayList();
					if (entry.getValue().equalsIgnoreCase("minecraft")) {
						modInfo.add(EnumChatFormatting.GOLD + "Minecraft");
						MinecraftServer server = MinecraftServer.getServer();
						modInfo.add("Version: " + EnumChatFormatting.GRAY + server.getMinecraftVersion());
						modInfo.add("");
						modInfo.add("Description: " + EnumChatFormatting.GRAY + "The Vanilla game");
						modInfo.add("");
						modInfo.add("Authors: " + EnumChatFormatting.GRAY + "Mojang");
						modInfo.add("URL: " + EnumChatFormatting.BLUE + "https://minecraft.net/");
					} else if (entry.getValue().equalsIgnoreCase(GuiItemDisplay.ALL)) {
						modInfo.add(EnumChatFormatting.GOLD + "View All Items");
						modInfo.add("Displays all items from all mods");
					} else {
						ModContainer mod = null; 
						for (ModContainer m : Loader.instance().getActiveModList()) {
							if (m.getModId().equalsIgnoreCase(entry.getKey())) { mod = m; break; }
						}
						if (mod != null) {
							modInfo.add(EnumChatFormatting.GOLD + mod.getName());
							if (ConfigHandler.instance().modItemsLists.get(entry.getKey()).size() == 0)
								modInfo.add(EnumChatFormatting.RED + "This mod adds no items!");
							modInfo.add("Version: " + EnumChatFormatting.GRAY + mod.getVersion());
							Map<String, String> p = mod.getSharedModDescriptor();
							if (p != null) {
								modInfo.add("");
								modInfo.addAll(fontRendererObj.listFormattedStringToWidth("Description: " + EnumChatFormatting.GRAY + p.get("description"), 196));
								modInfo.add("");
								modInfo.add("Authors: " + EnumChatFormatting.GRAY + p.get("authors"));
								if (p.get("url").length() > 0)
									modInfo.add(fontRendererObj.trimStringToWidth("URL: " + EnumChatFormatting.BLUE + p.get("url"), 196));
							}
						}
					}

					int w = xPos+width-2 - (hasScrollbar?8:0);
					int ijy1 = ijy;
					int ijy2 = modInfo.size()*10;
					if (ijy1+ijy2 > this.height) ijy1 = this.height - ijy2;
					
					this.drawGradientRect(xPos-201, ijy1-2, xPos-1,   ijy1+ijy2+2, 0xCC888888, 0xCC888888);
					this.drawGradientRect(xPos-200, ijy1-1, xPos-2,   ijy1+ijy2+1, 0x88000000, 0x88000000);
					this.drawGradientRect(xPos-2, ijy-2, w,   ijy+9, 0xCC888888, 0xCC888888);
					this.drawGradientRect(xPos-2, ijy-1, w-1, ijy+8, 0x88000000, 0x88000000);
						
					int y = 0;
					for (String s : modInfo)
						this.drawString(mc.fontRenderer, s, xPos-198, ijy1+1+(y++)*10, 0xFFFFFF);
						
//					this.drawString(mc.fontRenderer, entry.getValue(), xPos+4, ijy, 0xCCCCCC);
//					var++;
				} else
					modName = EnumChatFormatting.GRAY + modName;
				if (entry.getKey().equalsIgnoreCase(key)) {
					int w = xPos+width-2 - (hasScrollbar?8:0);
//					this.drawGradientRect(xPos+1, ijy-1, w-1, ijy+8, 0x66000000, 0x44000000);
					modName = "- " + EnumChatFormatting.ITALIC + entry.getValue();
//					this.drawString(mc.fontRenderer, EnumChatFormatting.ITALIC + entry.getValue(), xPos+4, ijy, 0x5555CC);
//					var++;
				}
//				} else {
					this.drawString(mc.fontRenderer, modName, xPos+4, ijy, 0xFFFFFF);
					var++;
//				}
			}
		}
	}
	private void search() {
		LinkedHashMap<String, String> modMap = (LinkedHashMap<String, String>) GuiItemDisplay.getSetting(Keys.S_MODNAMES);
		searchMap = new LinkedHashMap();
		String search = searchField.getText().toLowerCase();
		ArrayList<String> l = new ArrayList(modMap.keySet());
		Collections.sort(l, new Comparator<String>() {
			LinkedHashMap<String, String> modMap = (LinkedHashMap<String, String>) GuiItemDisplay.getSetting(Keys.S_MODNAMES);
			@Override
			public int compare(String s1, String s2) {
//				if (s1.equalsIgnoreCase("All")) return 1;
				if (s2.equalsIgnoreCase("All")) return 1;
//				if (s1.equalsIgnoreCase("minecraft")) return 1;
				if (s2.equalsIgnoreCase("minecraft")) return 1;
				return modMap.get(s1).toLowerCase().compareTo(modMap.get(s2).toLowerCase());
			}
		});
		

		// Filter modMap into searchMap
		if (search == "") {
			for (String s : l)
				searchMap.put(s, modMap.get(s));
//			searchMap.putAll(modMap);
		} else {
//			for (Entry<String, String> entry : modMap.entrySet()) {
//				if (entry.getKey().toLowerCase().contains(search) || entry.getValue().toLowerCase().contains(search))
//			searchMap.put(entry.getKey(), entry.getValue());
			for (String s : l) {
				if (s.toLowerCase().contains(search) || modMap.get(s).toLowerCase().contains(search))
					searchMap.put(s, modMap.get(s));
			}
		}
		
		// Update the scrollbar size
		hasScrollbar = (height < (searchMap.size())*10+6);
		barMod = Math.min((double)(height-searchY-1)/((searchMap.size()+1)*10), 1.0D);
		gsb.height = (int) ((height-searchY-1)*barMod);
		gsb.maxY = height - gsb.height - searchY - 1;
	}
    protected void mouseClicked(int mouseX, int mouseY, int button) {
    	// Search Bar
        if (mouseX > xPos+5 && mouseX < xPos+width+3 && mouseY >= yPos+height-searchY && mouseY <= yPos+height) {
        	if (button == 1) {
        		this.searchField.setText("");
        		search();
        	}
        	this.searchField.setFocused(true);
        	parent.isSearching = true;
        } else {
        	this.searchField.setFocused(false);
        	parent.isSearching = false;
        }

        // Select Mod
    	if (!hasScrollbar || mouseX < xPos+width-10) {
    		int k = (int) ((mouseY-yPos-5+gsb.barY/barMod)/10);
			Object[] keyArray = searchMap.keySet().toArray();
			if (k >= 0 && keyArray.length > k)
				GuiItemDisplay.setConfig(Keys.C_KEY, keyArray[k].toString());
    	}
    }
    public void keyTyped(char par1, int par2) {
        if (this.searchField.textboxKeyTyped(par1, par2))
        	search();
    }
    public void handleMouseInput() {
    	int m = Mouse.getEventDWheel();
    	if (m != 0 && hasScrollbar) gsb.barY -= (m>0 ? 1:-1)*10*barMod;
    }
    public boolean isMouseOverPanel(int mouseX, int mouseY) {
    	return mouseX >= xPos && mouseX <= xPos + width && mouseY >= yPos && mouseY <= yPos + height;
    }
}
