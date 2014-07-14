package mods.horsehud.client.gui.itembox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Mouse;

import cpw.mods.fml.common.ObfuscationReflectionHelper;

import mods.horsehud.HorseHUD;
import mods.horsehud.client.AConfigOption;
import mods.horsehud.client.ConfigHandler;
import mods.horsehud.api.IConfigOption;
import mods.horsehud.api.Keys;
import mods.horsehud.api.Type;
import mods.horsehud.client.Lang;
import mods.horsehud.client.gui.GuiScrollBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.config.Configuration;

public class GuiOptionsPanel extends GuiScreen {
	protected Minecraft mc;
	protected GuiScreen parent;
	private int xPos, yPos;
	private List<OptionContainer> containers;
//	private List<TextOption> options;
	private TextOption theOption;
	private OptionContainer theContainer;
	private GuiScrollBar gsb;
	private double barMod;
    private boolean hasScrollbar = false;
//	private Map<String, List<String>> configs = new HashMap();
//	private Map<Integer, String> buttonLabels = new HashMap();
//    private int xOffset = 200;

	public GuiOptionsPanel(int xPos, int yPos, int width, int height, GuiItemDisplay parent) {
		this.mc = parent.mc;
		this.parent = parent;
		this.fontRendererObj = mc.fontRenderer;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}
	public void initGui() {
//		options = new ArrayList();
		containers = new ArrayList();

		Map<String, Boolean> collapsed = (Map<String, Boolean>) GuiItemDisplay.getSetting(Keys.S_CATEGORIES);
		int yy = 3, y = 3;
		for (Entry<String, Map<String, IConfigOption>> entry : ConfigHandler.instance().activeConfigs.entrySet()) {
			OptionContainer cont = new OptionContainer(entry.getKey(), xPos+3, yPos+yy, width-11);
			y=15;

			if (collapsed.containsKey(entry.getKey())) cont.isCollapsed = collapsed.get(entry.getKey());
			else                                                          collapsed.put(entry.getKey(), true);

			for (Entry<String, IConfigOption> entry2 : entry.getValue().entrySet()) {
				IConfigOption config = entry2.getValue();
				if (config.isConfigurable()) {
//					TextOption option = new TextOption(mc, entry.getKey(), entry2.getKey(), config.type(), config.count(), xPos+3, yPos+6+y, config.formattedValue(), width-2);
					TextOption option = new TextOption(mc, config, 0, y, width-13, cont);
					cont.add(option);
					y += option.height+3;
				}
			}
//			if (cont.list.isEmpty()) y -= 15;
//			else  
				containers.add(cont);
				yy += cont.height()+3;
		}
		GuiItemDisplay.setSetting(Keys.S_CATEGORIES, collapsed);
        this.buttonList.clear();
        this.buttonList.add(new GuiScrollBar(0, 1,1, 8,10));
        gsb = (GuiScrollBar) this.buttonList.get(0);
		gsb.xPosition = xPos+width-9;
		gsb.minY = yPos+1;
		hasScrollbar = (yy > height);
		barMod = Math.min((double)(height-2)/(yy), 1.0D);
		gsb.height = (int) ((height-2)*barMod);
		gsb.maxY = height-gsb.height-2;
		gsb.barY = (Double) GuiItemDisplay.getSetting(Keys.S_OPTIONS_BARY);
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//		int width = this.width;
		int w = 6;
		gsb.barY = Math.max(Math.min(gsb.barY, height - gsb.height-2), 0);
		gsb.yPosition = (int) (gsb.barY+yPos+1);
		// Draw Scrollbar
		if (hasScrollbar) {
			GuiItemDisplay.setSetting(Keys.S_OPTIONS_BARY, gsb.barY);
			this.drawGradientRect(xPos+width-10, yPos+1, xPos+width-9, yPos+height, 0x33DDDDDD, 0x55DDDDDD);
			this.drawGradientRect(xPos+width-9, yPos+height-1, xPos+width-1, yPos+height, 0x55DDDDDD, 0x55DDDDDD);
			gsb.drawButton(mc, mouseX, mouseY);
			w -= 9;
		}
		int b = (int)(gsb.barY/barMod);
		theOption = null;
		theContainer = null;
		int yy = 0;
		int yc=0, xc = 0;
		for (OptionContainer cont : containers) {
			yc = cont.yPos - b;
			xc = cont.xPos;
			String title = /*EnumChatFormatting.BLUE +*/ Lang.local(cont.category);
			title = fontRendererObj.trimStringToWidth(title, cont.width+w-2);
			if (mouseY > yPos && mouseY < yPos+height)
			if (cont.isMouseOver(mouseX, mouseY+b) && (!hasScrollbar || mouseX < xPos+width-12)) { 
				theContainer = cont;

				int top      = Math.max(yc-1, yPos+1);
				int topInner = Math.max(yc,   yPos+1);
				int bottom      = Math.min(yc+cont.height()+1, yPos+height-1);
				int bottomInner = Math.min(yc+cont.height()+0, yPos+height-1);
//				if (bottom > yPos+height-1) bottom = bottomInner = yPos+height-1;

				drawGradientRect(xc-1, top,    xc+cont.width+w,   bottom,      0x88888888, 0x88888888);
				drawGradientRect(xc, topInner, xc+cont.width+w-1, bottomInner, 0xAA000000, 0xAA000000);
				
				if (mouseY < yc+14) {
					List<String> l = new ArrayList();
					l.add(EnumChatFormatting.GOLD + Lang.local(cont.category));
					if (cont.isCollapsed()) l.add(EnumChatFormatting.ITALIC + "Click to expand options");
					else l.add(EnumChatFormatting.ITALIC + "Click to collapse options");
					l.add("");
					l.addAll(fontRendererObj.listFormattedStringToWidth(Lang.local(cont.category + ".desc"), 160));
					int rows = l.size();
					
					this.drawGradientRect(xPos-174, top,      cont.xPos-1, yc + rows*10+1, 0x88888888, 0x88888888);
					this.drawGradientRect(xPos-173, topInner, cont.xPos,   yc + rows*10,   0xAA000000, 0xAA000000);
					drawGradientRect(cont.xPos-1,   bottom-1, cont.xPos,   yc + rows*10+1, 0x88888888, 0x88888888);

					int y = 0;
					for (String ls : l) {
						drawString(fontRendererObj, ls, xPos - 170, yc + (y++)*10+1, 0xCCCCCC);
					}
				}
			}
			if (yc+10 < yPos + height && yc > yPos)
				drawString(fontRendererObj, title, cont.xPos+2, yc+2, 0xFFFFFF);
			
			if (!cont.isCollapsed())
			for (TextOption text : cont.getOptions()) {
				int yb = yc + text.yPos;
				int xb = xc + text.xPos;
				if (yb + text.height > yPos + height || yb < yPos+3) continue;
				boolean flag = false;
				if (text.isMouseOver(mouseX, mouseY+b) && (!hasScrollbar || mouseX < xPos+width-12)) {
					flag = true;
					this.drawGradientRect(xPos-1, yb-4, xb+text.width+w,   yb + text.height+1, 0x88CCCCCC, 0x88CCCCCC);
					this.drawGradientRect(xPos-1, yb-3, xb+text.width+w-1, yb + text.height,   0xAA000000, 0xAA000000);
	
					String s = text.getKeyAt(mouseX, mouseY+b);
					if (s != null) {
						List<String> l = new ArrayList();
						l.add(EnumChatFormatting.GOLD + text.labels.get(s));
						l.add("");
						l.addAll(fontRendererObj.listFormattedStringToWidth(Lang.local(s + ".desc"), 160));
						int rows = Math.max(l.size(), text.height/10);
						
						this.drawGradientRect(xPos-174, yb-4,             xPos-1, yb + rows*10+1, 0x88CCCCCC, 0x88CCCCCC);
						this.drawGradientRect(xPos-173, yb-3,             xPos-1, yb + rows*10,   0xAA000000, 0xAA000000);
						this.drawGradientRect(xPos-2,   yb + text.height, xPos-1, yb + rows*10,   0x88CCCCCC, 0x88CCCCCC);
	
						int y = 0;
						for (String ls : l) {
							drawString(fontRendererObj, ls, xPos - 170, yb + (y++)*10, 0xCCCCCC);
						}
					}
				}
				int y = 0;
				for (Entry<String, String> entry : text.labels.entrySet()) {
					String label = entry.getValue();
					int x = xb + (y>0? 7 : 2);
					
					if (text.isSelected(entry.getKey())) label = EnumChatFormatting.ITALIC + "- " + label;
					if (y==0) { 
						label = EnumChatFormatting.UNDERLINE + label;
						if (!flag)
							label = EnumChatFormatting.GRAY + label;
					} else {
						if (flag) {
							if (mouseY > yb + y*10 && mouseY < yb + (y+1)*10) {
								theOption = text;
	
								this.drawGradientRect(xPos+4, yb+y*10-1, xb+text.width+w-1, yb+(y+1)*10-1, 0x88CCCCCC, 0x88CCCCCC);
								this.drawGradientRect(xPos+4, yb+y*10-1, xb+text.width+w-1, yb+(y+1)*10-1, 0xC0000000, 0xC0000000);
		
							} else {
								label = EnumChatFormatting.GRAY + label;
//								theOption = null;
							}
						} else {
							label = EnumChatFormatting.DARK_GRAY + label;
						}
						
					}
					int xw = xb+text.width+w-xPos-(y>0?11:5);
					int x1 = fontRendererObj.getStringWidth("...");
					if (fontRendererObj.getStringWidth(label) > xw) {
						label = fontRendererObj.trimStringToWidth(label, xw-x1).trim() + "...";
//						List<String> ls = fontRendererObj.listFormattedStringToWidth(label, xw);
//						if (text.isSelected(entry.getKey())) {
//							int lw = fontRendererObj.getStringWidth(ls.get(0) + " ");
//							label = ls.get(0) + " " + fontRendererObj.listFormattedStringToWidth(ls.get(1), x - lw).get(0);
//						} else
//							label = ls.get(0);
					}
					int ijy = yb+y*10-(y>0?0:1);
					drawString(fontRendererObj, label, x, ijy, 0xFFFFFF);
					y++;
				}
			}
			yy += cont.height();
		}
	}
    protected void mouseClicked(int mouseX, int mouseY, int button) {
    	int width = this.width - (hasScrollbar?10:0);
		int b = (int)(gsb.barY/barMod);
    	if (mouseX > xPos && mouseX < xPos+width && mouseY > yPos && mouseY < yPos+height) {
    		if (theContainer != null)
    			if (mouseY+b < theContainer.yPos + 12) {
    				theContainer.toggle();
    				((Map<String, Boolean>)GuiItemDisplay.getSetting(Keys.S_CATEGORIES)).put(theContainer.category, theContainer.isCollapsed);
    				int y = yPos+3;
    				for (OptionContainer cont : containers) {
    					cont.yPos = y;
    					y += cont.height() + 3;
    				}
    				hasScrollbar = (y > height);
    				barMod = Math.min((double)(height-2)/(y), 1.0D);
    				gsb.height = (int) ((height-2)*barMod);
    				gsb.maxY = height-gsb.height-2;
    			}
	    	if (theOption != null) {
	    		Object value = theOption.getValueAt(mouseX, mouseY+b);
//	    		System.out.println(theOption.category + "." + theOption.key + ": " + value.toString());
	    		theOption.set(value);
//	    		ConfigHandler.instance().setConfig(theOption.category, theOption.key, theOption.getValueAt(mouseX, mouseY+b));
	    	}
    	}
    }
    public void handleMouseInput() {
	  	if (hasScrollbar) gsb.barY -= Mouse.getEventDWheel()/120*10*barMod;
    }
	
//	private String formatConfigValue(Config config) {
//		String retString = config.category() + "." + config.key() + "_";
//		
//		if (config.type() == Type.BOOLEAN) retString += (Boolean) config.value();
//		if (config.type() == Type.INTEGER)     retString += (Integer) config.value();
//		if (config.type() == Type.STRING)  retString += (String)  config.value();
//		
//		return retString;
//	}

    public class OptionContainer {
    	private ArrayList<TextOption> list;
    	private String category;
    	private boolean isCollapsed;
    	public int xPos, yPos, width, height;

    	public OptionContainer(String category, int xPos, int yPos, int width) {
    		this.category = category;
    		this.list = new ArrayList();
    		this.height = 15;
    		this.isCollapsed = true;
    		this.xPos = xPos;
    		this.yPos = yPos;
    		this.width = width;
    	}
    	public void add(TextOption option) {
    		list.add(option);
    		height += option.height + 3;
    	}
    	public boolean isCollapsed() { return this.isCollapsed; }
    	public void toggle() { isCollapsed = !isCollapsed; }
    	public ArrayList<TextOption> getOptions() { return this.list; }
    	public int height() { return isCollapsed ? 12 : height; }
    	public boolean isMouseOver(int mouseX, int mouseY) {
    		return (mouseX >= xPos && mouseX <= xPos+width && mouseY > yPos-1 && mouseY < yPos + height()+1);
    	}
    }

//    private class TextOption extends ATextOption {
//		TextOption(Minecraft mc, IConfigOption config, int xPos, int yPos, int width) {
//			super(mc, config, xPos, yPos, width);
//		}
////		@Override
////		public void set(Object value) {
////			this.config.set(value);
////		}
//	}

//	public static class TextOption {
//		private Minecraft mc;
//		public String category, key;//, value;
//		private Object[] values;
//		protected int xPos, yPos, width, height;
//		public int optionCount;
//		public LinkedHashMap<String, String> labels;
//		public Type type;
//		private AConfigOption config;
//		
//		public TextOption(Minecraft mc, AConfigOption config, int xPos, int yPos, int width) {
//			this.mc = mc;
//			this.config = config;
//			this.category = config.category();
//			this.key = config.key();
//			this.type = config.type();
//			this.optionCount = config.count();
//			this.xPos = xPos;
//			this.yPos = yPos;
//			this.width = width;
//			this.height = (optionCount+1)*10;
//			
//			getLabels();
//		}
////		public TextOption(Minecraft mc, String category, String key, Type type, int optionCount, int xPos, int yPos, String value, int width) {
////			this.mc = mc;
////			this.category = category;
////			this.key = key;
////			this.value = value;
////			this.xPos = xPos;
////			this.yPos = yPos;
////			this.optionCount = optionCount;
////			this.height = (optionCount+1)*10;
////			this.type = type;
////			this.width = width;
////
////			getLabels();
////		}
//		
//		private void getLabels() {
//			labels = new LinkedHashMap();
//			labels.put(key(), Lang.local(key()));
//
//			values = new Object[optionCount];
//			if (type == Type.BOOLEAN) {
//				values[0] = true;
//				values[1] = false;
//			} else {
//				for (int i=0; i<optionCount; i++)
//					values[i] = i;
//			}
//			for (int i=0; i<optionCount; i++)
//				labels.put(key() + "_" + values[i].toString(), Lang.local(key() + "_" + values[i].toString()));
//			
////			if (type == Type.BOOLEAN) {
////				labels.put(key() + "_true",  Lang.local(key() + "_true"));
////				labels.put(key() + "_false", Lang.local(key() + "_false"));
////			} else {
////				for (int i=0; i<optionCount; i++)
////					labels.put(key() + "_" + i, Lang.local(key() + "_" + i));
////			}
//			
////			width = 0;
////			for (String label : labels.values())
////				width = Math.max(mc.fontRenderer.getStringWidth(label), width);
//		}
//		
//		public void set(Object value) {
//			config.set(value, HorseHUD.instance.config);
//		}
//		
//		public String key() { return category + "." + key; }
//		public boolean isSelected(String k) { return k.equalsIgnoreCase(config.formattedValue()); }
//		
////		public static TextOption getOptionFromKey(String key, List<TextOption> list) {
////			for(TextOption text : list) {
////				if (text.key().equalsIgnoreCase(key))
////					return text;
////			}
////			return null;
////		}
////		public static TextOption getOptionAt(List<TextOption> list, int mouseX, int mouseY) {
////			for (TextOption text : list) {
////				if (text.isMouseOver(mouseX, mouseY)) return text;
////			}
////			return null;
////		}
//		
//		public boolean isMouseOver(int mouseX, int mouseY) {
//			return (mouseX >= xPos && mouseX <= xPos+width && mouseY > yPos-3 && mouseY < yPos + height);
//		}
//		
//		public Object getValueAt(int mouseX, int mouseY) {
//			int y = (mouseY - yPos)/10 - 1;
//			
//			if (y >= 0 && y < optionCount)
//				return values[y];
//			return null;
//		}
//		public String getKeyAt(int mouseX, int mouseY) {
//			int y = (mouseY - yPos)/10;
//			
//			String retString = key();
//			Object value = getValueAt(mouseX, mouseY);
//			if (value != null) retString += "_" + value.toString();
////			int var = 0;
////			if (y <= optionCount)
////				for (String k : labels.keySet()) {
////					if (var == 0) { var++; retString = k; continue; }
////					if (var++ == y) return k;
////				}
//			return retString;
//		}
//	}
}
