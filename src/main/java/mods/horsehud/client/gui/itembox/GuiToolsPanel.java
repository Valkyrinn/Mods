package mods.horsehud.client.gui.itembox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import mods.horsehud.client.AConfigOption;
import mods.horsehud.client.Lang;
import mods.horsehud.client.gui.GuiScrollBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.storage.WorldInfo;

import org.lwjgl.input.Mouse;

public class GuiToolsPanel extends GuiScreen {
	
	private GuiItemDisplay parent;
	private int xPos, yPos, listHeight;
	private EntityPlayerMP playerMP;
	private LinkedHashMap<String, TextOption> optionsList;
	private TextOption theOption;
	private GuiScrollBar gsb;
	private double barMod;
    private boolean hasScrollbar = false;
	
	public GuiToolsPanel(int xPos, int yPos, int width, int height, GuiItemDisplay parent) {
		this.parent = parent;
		this.mc = parent.mc;
		this.fontRendererObj = mc.fontRenderer;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
//		this.playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(mc.thePlayer.getCommandSenderName());
////		boolean isOp = playerMP.mcServer.getConfigurationManager().isPlayerOpped(playerMP.getCommandSenderName());
////		int opLevel = playerMP.mcServer.getOpPermissionLevel();
//
		optionsList = new LinkedHashMap();
		AConfigOption option;
		TextOption textOption;
		String category = "tools";
		String key;
		int y = 6;
		
		key = "VanillaMC";
		option = new AConfigOption(category, key, -1, 2, true, null ) {
			private static final int TOGGLEDOWNFALL = 0, HEALPLAYER=1, MAGNETMODE=2;
			@Override
			public void set(Object value) {
				int v = (Integer)value;
				
				switch(v) {
				case TOGGLEDOWNFALL:
					WorldInfo worldinfo = MinecraftServer.getServer().worldServers[0].getWorldInfo();
			        worldinfo.setRaining(!worldinfo.isRaining());
			        break;
				case HEALPLAYER:
					EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(Minecraft.getMinecraft().thePlayer.getCommandSenderName());
					playerMP.clearActivePotions();
					playerMP.heal(60.0F);
					playerMP.getFoodStats().addStats(20, 5.0F);
					break;
				case MAGNETMODE:
					// Not yet implemented
					break;
				}
			}
			@Override
			public void configure() { }
		};
		textOption = new TextOption(mc, option, xPos, yPos+y, width-6, null){
			private static final int TOGGLEDOWNFALL = 0, HEALPLAYER=1, MAGNETMODE=2;
			@Override
			protected void getLabels() {
				super.getLabels();
				String k;
				String label;
				
				k = key() + "_" + TOGGLEDOWNFALL;
				label = Lang.local(k) + ": ";
				label += (MinecraftServer.getServer().worldServers[0].getWorldInfo().isRaining() ? "" : "Not ") + "Raining";
				labels.put(k, label);
				
				k = key() + "_" + MAGNETMODE;
				label = Lang.local(k) + ": ";
				label += false; // is MagnetMode on
//				labels.put(k, label);
			}
		};
		optionsList.put(category + "." + key, textOption);
		y += textOption.height + 3;

		key = "infobox.additionalinfo";
		option = new AConfigOption(category, key, -1, 4, true, null ) {
			private static final int ADDITIONALINFO_BIOME = 0, ADDITIONALINFO_COORDS=1, ADDITIONALINFO_TIME=2, ADDITIONALINFO_FPS=3;
			@Override
			public void set(Object value) {
				int v = (Integer)value;
				
				switch(v) {
				case ADDITIONALINFO_BIOME:
					// Add the current biome to the additional info section of the InfoBox
			        break;
				case ADDITIONALINFO_COORDS:
					// Add the player's coordinates to the additional info section of the InfoBox
					break;
				case ADDITIONALINFO_TIME:
					// Add the in-game time to the additional info section of the InfoBox
					break;
				case ADDITIONALINFO_FPS:
					// Add the current frames per second to the additional info section of the InfoBox
					break;
				}
			}
			@Override
			public void configure() { }
		};
		textOption = new TextOption(mc, option, xPos, yPos+y, width-6, null);
		optionsList.put(category + "." + key, textOption);
		y += textOption.height + 3;
//		optionsList.put("VanillaMC", option);
//		String category = "VanillaMC";
//		String key;
//
//		Map<String, Boolean> permissions = new HashMap();
//		List<ICommand> commands = playerMP.mcServer.getCommandManager().getPossibleCommands(playerMP);
//		for (ICommand command : commands) {
//			permissions.put(command.getCommandName(), true);
////			if (!permissions.containsKey(command.getCommandName()))
////				permissions.put(command.getCommandName(), false);
////
////			if (command instanceof CommandGameMode)
////				permissions.put(command.getCommandName(), true);
////			
//		}
//
//		key = "gamemode";
//		if (permissions.get(key)) {
//			WorldSettings.GameType gt = GameType.NOT_SET;
//			try { gt = ObfuscationReflectionHelper.getPrivateValue(PlayerControllerMP.class, mc.playerController, "currentGameType"); }
//			catch (Exception e) { System.out.println("Error: " + e); }
//			option = new AConfigOption(category, key, gt.getID(), 4, false, null){
//				@Override
//				public void configure() {
//					WorldSettings.GameType gt = WorldSettings.getGameTypeById((Integer)value());
//					String name = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
//					EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(name);
////					player.setGameType(gt);
//
//					player.theItemInWorldManager.setGameType(gt);
//					S2BPacketChangeGameState packet = new S2BPacketChangeGameState(3, (float)gt.getID());
//					packet.field_149142_a[3] = null;
//			        player.playerNetServerHandler.sendPacket(packet);
//				}
//			};
//			TextOption textOption = new TextOption(mc, option, xPos, yPos+y, width-6);
//			optionsList.put(category + "." + key, textOption);
//			y += textOption.height + 3;
//		}
//		
//
//		key = "defaultgamemode";
//		if (permissions.get(key)) {
//			WorldSettings.GameType gt = MinecraftServer.getServer().getGameType();//GameType.NOT_SET;
////			try { gt = ObfuscationReflectionHelper.getPrivateValue(PlayerControllerMP.class, mc.playerController, "currentGameType"); }
////			catch (Exception e) { System.out.println("Error: " + e); }
//			option = new AConfigOption(category, key, gt.getID(), 4, false, null){
//				@Override
//				public void configure() {
////					WorldSettings.GameType type = MinecraftServer.getServer().getConfigurationManager().
////					String name = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
////					EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(name);
////					player.setGameType((GameType)value());
////					MinecraftServer.getServer().setGameType(GameType.getByID((Integer)value()));
//			        MinecraftServer minecraftserver = MinecraftServer.getServer();
//			        GameType gameType = GameType.getByID((Integer)value());
//			        minecraftserver.setGameType(gameType);
//			        EntityPlayerMP entityplayermp;
//
////			        System.out.println("getForceGamemode(): " + minecraftserver.getForceGamemode());
//			        if (minecraftserver.getForceGamemode())
//			        {
//			            for (Iterator iterator = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator(); iterator.hasNext(); entityplayermp.fallDistance = 0.0F)
//			            {
//			                entityplayermp = (EntityPlayerMP)iterator.next();
//			                entityplayermp.setGameType(gameType);
//			            }
//			        }
//				}
//			};
//			TextOption textOption = new TextOption(mc, option, xPos, yPos+y, width-6);
//			optionsList.put(category + "." + key, textOption);
//			y += textOption.height + 3;
//		}
//		
//		key = "difficulty";
//		if (permissions.get(key)) {
////			EnumDifficulty difficulty = playerMP.mcServer.func_147135_j();
//			EnumDifficulty difficulty = MinecraftServer.getServer().func_147135_j();
//			option = new AConfigOption(category, key, difficulty.getDifficultyId(), 4, false, null) {
//				@Override
//				public void configure() {
////					String name = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
////					EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(name);
//					
//					EnumDifficulty d = EnumDifficulty.getDifficultyEnum((Integer)value());
////					player.mcServer.func_147139_a(d);
////					System.out.println("Difficulty: " + d);
//					MinecraftServer.getServer().func_147139_a(d);
//					Minecraft.getMinecraft().gameSettings.difficulty = d;
////					Minecraft.getMinecraft().theWorld.difficultySetting = d;
//				}
//			};
//			optionsList.put(category + "." + key, new TextOption(mc, option, xPos, yPos+y, width-6));
//			y += optionsList.get(category + "." + key).height + 3;
//		}
//
//		key = "gamerule";
//		if (permissions.get("gamerule")) {
//			GameRules rules = MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
//			String subKey;
//			boolean value;
//
//			subKey = "doDaylightCycle";
//			value = rules.getGameRuleBooleanValue(subKey);
//			option = new AConfigOption(category, key + "." + subKey, value, 2, false, null){
//				@Override
//				public void configure() {
//					GameRules rules = MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
//					String key = "doDaylightCycle";
//		            if (rules.hasRule(key)) {
//						rules.setOrCreateGameRule(key, value().toString());
//						HorseHUD.instance.log.info(key + " set to " + value());
//		            }
//					
//				}
//			};
//			optionsList.put(category + "." + key + "." + subKey, new TextOption(mc, option, xPos, yPos+y, width-6));
//			y += optionsList.get(category + "." + key + "." + subKey).height + 3;
//
//			subKey = "keepInventory";
//			value = rules.getGameRuleBooleanValue(subKey);
//			option = new AConfigOption(category, key + "." + subKey, value, 2, false, null){
//				@Override
//				public void configure() {
//					MinecraftServer.getServer().worldServerForDimension(0).getGameRules().setOrCreateGameRule("keepInventory", value().toString());
//				}
//			};
//			optionsList.put(category + "." + key + "." + subKey, new TextOption(mc, option, xPos, yPos+y, width-6));
//			y += optionsList.get(category + "." + key + "." + subKey).height + 3;
//
//			subKey = "mobGriefing";
//			value = rules.getGameRuleBooleanValue(subKey);
//			option = new AConfigOption(category, key + "." + subKey, value, 2, false, null){
//				@Override
//				public void configure() {
//					MinecraftServer.getServer().worldServerForDimension(0).getGameRules().setOrCreateGameRule("mobGriefing", value().toString());
//				}
//			};
//			optionsList.put(category + "." + key + "." + subKey, new TextOption(mc, option, xPos, yPos+y, width-6));
//			y += optionsList.get(category + "." + key + "." + subKey).height + 3;
//
//			subKey = "naturalRegeneration";
//			value = rules.getGameRuleBooleanValue(subKey);
//			option = new AConfigOption(category, key + "." + subKey, value, 2, false, null){
//				@Override
//				public void configure() {
//					MinecraftServer.getServer().worldServerForDimension(0).getGameRules().setOrCreateGameRule("naturalRegeneration", value().toString());
//				}
//			};
//			option.set(value, null);
//			optionsList.put(category + "." + key + "." + subKey, new TextOption(mc, option, xPos, yPos+y, width-6));
//			y += optionsList.get(category + "." + key + "." + subKey).height + 3;
//		}
		
		// TODO Set time
		// TODO Toggle Downfall
		// TODO Heal Player

		listHeight = y;
	}
	
	public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiScrollBar(0, 1,1, 8,10));
        gsb = (GuiScrollBar) this.buttonList.get(0);
		gsb.xPosition = xPos+width-9;
		gsb.minY = yPos+1;
		hasScrollbar = (listHeight > height);
		barMod = Math.min((double)(height-1)/(listHeight), 1.0D);
		gsb.height = (int) ((height-1)*barMod);
		gsb.maxY = height-gsb.height-1;
		gsb.barY = 0;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int width = this.width;

		// Draw Scrollbar
		if (hasScrollbar) {
			gsb.barY = Math.max(Math.min(gsb.barY, height - gsb.height-1), 0);
			gsb.yPosition = (int) (gsb.barY+yPos+1);
//			setConfig(Keys.C_ITEM_BARY, gsb.barY);
			this.drawGradientRect(xPos+width-10, yPos+1, xPos+width-9, yPos+height, 0x33DDDDDD, 0x55DDDDDD);
			gsb.drawButton(mc, mouseX, mouseY);
			width -= 9;
		}

		int b = (int)(gsb.barY/barMod);
		for (Entry<String, TextOption> entry : optionsList.entrySet()) {
			TextOption text = entry.getValue();
//			if (text.yPos + text.height - (int)(gsb.barY/barMod) > yPos + height || text.yPos - (int)(gsb.barY/barMod) < yPos) continue;
			boolean flag = false;
	    	if (mouseX > xPos && mouseX < xPos+width-1 && mouseY > yPos && mouseY < yPos+height)
			if (text.isMouseOver(mouseX, mouseY + b)) {
				flag = true;

				int y = text.yPos - b;
				int top    = Math.max(y - 4, yPos+2);
				int bottom = Math.min(y + text.height, yPos + height - 1);

				this.drawGradientRect(xPos+3, top,   xPos+width-2, bottom, 0x88CCCCCC, 0x88CCCCCC);
				this.drawGradientRect(xPos+3, top+1, xPos+width-3, bottom-1, 0xAA000000, 0xAA000000);

				String s = text.getKeyAt(mouseX, mouseY + b);
				if (s != null) {
					List<String> l = new ArrayList();
					l.add(EnumChatFormatting.GOLD + text.labels.get(s));
					l.add("");
					l.addAll(fontRendererObj.listFormattedStringToWidth(Lang.local(s + ".desc"), 160));

					int bottom2 = bottom;
					if (top +  text.height + 5 > yPos+height-1 || l.size() > text.height/10)
						bottom2 = top + l.size()*10 + 5;

					this.drawGradientRect(xPos-174, top,     xPos+3, bottom2,                                 0x88CCCCCC, 0x88CCCCCC);
					this.drawGradientRect(xPos-173, top + 1, xPos+3, bottom2 - 1,                             0xAA000000, 0xAA000000);
					this.drawGradientRect(xPos+2,   Math.min(bottom-1, bottom2),  xPos+3, Math.max(bottom, bottom2-1), 0x88CCCCCC, 0x88CCCCCC);

					y = 0;
					for (String ls : l) {
						drawString(fontRendererObj, ls, xPos - 170, top + 4 + (y++)*10, 0xCCCCCC);
					}
				}
			}
			int y = 0;
			text.getLabels();
			for (Entry<String, String> entry2 : text.labels.entrySet()) {
				String label = entry2.getValue();
				
				if (text.isSelected(entry2.getKey())) label = EnumChatFormatting.ITALIC + "- " + label;
				if (y==0) { 
					label = EnumChatFormatting.UNDERLINE + label;
					if (!flag)
						label = EnumChatFormatting.GRAY + label;
				} else {
					if (flag) {
						if (mouseY > text.yPos + y*10 - b && mouseY < text.yPos + (y+1)*10 - b && y>0) {
							theOption = text;

							this.drawGradientRect(xPos+4, text.yPos+y*10-1-b, xPos+width-4, text.yPos+(y+1)*10-1-b, 0x88CCCCCC, 0x88CCCCCC);
							this.drawGradientRect(xPos+4, text.yPos+y*10-1-b, xPos+width-4, text.yPos+(y+1)*10-1-b, 0xC0000000, 0xC0000000);
	
						} else {
							label = EnumChatFormatting.GRAY + label;
						}
					} else {
						label = EnumChatFormatting.DARK_GRAY + label;
					}
				}
				int x = width - (y>0?12:8);
				int x1 = fontRendererObj.getStringWidth("...");
				if (fontRendererObj.getStringWidth(label) > x) {
					label = fontRendererObj.trimStringToWidth(label, x-x1).trim() + "...";
//					List<String> ls = fontRendererObj.listFormattedStringToWidth(label, x);
//					if (text.isSelected(entry2.getKey())) {
//						int w = fontRendererObj.getStringWidth(ls.get(0) + " ");
//						label = ls.get(0) + " " + fontRendererObj.listFormattedStringToWidth(ls.get(1), x - w).get(0);
//					} else
//						label = ls.get(0);
				}
				int ijx = text.xPos + 2    + (y>0? 7 : 2);
				int ijy = text.yPos + y*10 - (y>0? 0 : 1) - b;
				if (ijy > yPos && ijy+10 < yPos+height)
					drawString(fontRendererObj, label, ijx, ijy, 0xFFFFFF);
				y++;
			}
		}
	}
    protected void mouseClicked(int mouseX, int mouseY, int button) {
    	int width = this.width - (hasScrollbar?10:0);
		int b = (int)(gsb.barY/barMod);
    	if (mouseX > xPos && mouseX < xPos+width && mouseY > yPos && mouseY < yPos+height)
    	if (theOption != null && theOption.isMouseOver(mouseX, mouseY+b)) {
//    		System.out.println(theOption.getKeyAt(mouseX, mouseY) + ", " + theOption.getValueAt(mouseX, mouseY));
    		theOption.set(theOption.getValueAt(mouseX, mouseY));
//			if (parent.parent instanceof GuiContainerCreative || parent.parent instanceof GuiInventory)
//				mc.displayGuiScreen(parent.parent);

//    		ConfigHandler.instance().setConfig(theOption.category, theOption.key, theOption.getValueAt(mouseX, mouseY));
    	}
    }
    public void handleMouseInput() {
    	int m = Mouse.getEventDWheel();
    	if (m != 0 && hasScrollbar) gsb.barY -= (m>0 ? 1:-1)*10*barMod;
    }

//	private class TextOption extends ATextOption {
//		TextOption(Minecraft mc, AConfigOption config, int xPos, int yPos, int width) {
//			super(mc, config, xPos, yPos, width);
//		}
//		@Override
//		public void set(Object value) {
//			this.config.set(value, null);
//			this.config.configure();
//		}
//	}

//	public static class TextOption {
//		private Minecraft mc;
//		public String category, key;//, value;
//		private Object[] values;
//		private int xPos, yPos, width, height;
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
