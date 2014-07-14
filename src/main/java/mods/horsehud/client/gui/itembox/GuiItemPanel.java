package mods.horsehud.client.gui.itembox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import mods.horsehud.api.Keys;
import mods.horsehud.client.ConfigHandler;
import mods.horsehud.client.gui.GuiScrollBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.google.common.collect.Multimap;

import scala.Char;
import scala.collection.mutable.MultiMap;

public class GuiItemPanel extends GuiContainer {
    private GuiItemDisplay parent;
	private ItemSlot theSlot = null;
	private List<ItemSlot> slotList;
	public int xPos, yPos;//, width, height;
	private int columns, rows, searchY;
	private GuiScrollBar gsb;
	private GuiTextField searchField;
	private double barMod;
    private boolean hasScrollbar = false;

    // Parent config methods reflected here for brevity
//	public static void        setConfig(Keys key, String  value) {        GuiItemDisplay.setConfig(key, value); }
	public static void        setConfig(Keys key, Object  value) {        GuiItemDisplay.setConfig(key, value); }
	public static String   getConfigStr(Keys key               ) { return GuiItemDisplay.getConfigStr( key); }
//	public static int      getConfigInt(Keys key               ) { return GuiItemDisplay.getConfigInt( key); }
	public static double   getConfigDbl(Keys key               ) { return GuiItemDisplay.getConfigDbl( key); }

	public GuiItemPanel(int xPos, int yPos, int width, int height, GuiItemDisplay parent) {
		this(new GuiItemDisplay.ItemContainer());
		this.parent = parent;
		this.mc = parent.mc;
		this.fontRendererObj = mc.fontRenderer;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.xSize = this.width;
		this.ySize = this.height;

		columns = (width-12)/19;
		rows = height/10;
	}
	public GuiItemPanel(Container par1Container) {
		super(par1Container);
		this.allowUserInput = true;
	}

	public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiScrollBar(0, 1,1, 8,10));
        gsb = (GuiScrollBar) this.buttonList.get(0);
		gsb.xPosition = xPos+width-9;
		gsb.minY = yPos+1;

        Keyboard.enableRepeatEvents(true);
        this.searchField = new GuiTextField(this.fontRendererObj, xPos+5, yPos+height-11, width-12, 20);
        this.searchField.setMaxStringLength(130);
        this.searchField.setEnableBackgroundDrawing(false);
        this.searchField.setVisible(true);
        this.searchField.setTextColor(0x888888);
        this.searchField.setCanLoseFocus(true);
        this.searchField.setFocused(false);
        this.searchField.setText(getConfigStr(Keys.C_ITEM_SEARCH));
        this.searchY = 14;

        this.setSelectedMod();
        gsb.barY = getConfigDbl(Keys.C_ITEM_BARY);
    }

	protected void setSelectedMod() {
		setSelectedMod(getConfigStr(Keys.C_KEY), this.searchField.getText());
    	setConfig(Keys.C_ITEM_SEARCH, this.searchField.getText());
	}
	protected void setSelectedMod(String mod, String searchText) {

		if (!((Map<String, String>) GuiItemDisplay.getSetting(Keys.S_MODNAMES)).containsKey(mod))
			setConfig(Keys.C_KEY, "minecraft");
    	
    	this.slotList = new ArrayList();

    	List<ItemStack> itemList = search(searchText);
    	
    	int ii = 0, ij = 0;
    	for (ItemStack is : itemList) {
    		int ijx = xPos + (width-columns*19-4)/2 + 19*ii;
    		int ijy = yPos + 19*ij + 3;
			ItemSlot slot = new ItemSlot(ij*columns+ii, is, ijx, ijy);
			this.slotList.add(slot);
			ii++;
			if (ii >= columns) {
				ii=0;
				ij++;
			}
    	}
    	ij++;

		barMod = Math.min((double)(height-searchY-1)/(ij*19), 1.0D);
		gsb.height = (int) ((height-searchY-1)*barMod);
		gsb.maxY = height - gsb.height - searchY - 1;
		hasScrollbar = (barMod < 1.0D);
    }
    public List<ItemStack> search(String search) {
    	ConfigHandler chi = ConfigHandler.instance();
    	String key = getConfigStr(Keys.C_KEY);
    	List<ItemStack> itemList = new ArrayList();
    	List<ItemStack> returnList = new ArrayList();
    	if (!chi.modItemsLists.containsKey(key) && !key.equalsIgnoreCase(GuiItemDisplay.ALL)) return returnList;//{ setConfig(C_KEY, "minecraft"); key = "minecraft"; }

    	if (key.equalsIgnoreCase(GuiItemDisplay.ALL)) {
    		itemList = new ArrayList();
    		LinkedHashMap<String, String> modMap = (LinkedHashMap<String, String>)GuiItemDisplay.getSetting(Keys.S_MODNAMES);
    		for (Entry<String, String> entry : modMap.entrySet()) {
    			String k = entry.getKey();
    			if (!k.equalsIgnoreCase(GuiItemDisplay.ALL) && chi.modItemsLists.containsKey(k))
    				itemList.addAll(chi.modItemsLists.get(k));
    		}
    	} else
    		itemList = chi.modItemsLists.get(key);
    	
    	if (search.length() == 0)
    		return itemList;
    	else {
    		Pattern p;
//    		System.out.println(search.toLowerCase());
    		try { p = Pattern.compile(search, Pattern.CASE_INSENSITIVE); }
    		catch(PatternSyntaxException e) { p = Pattern.compile(""); }
    		for (ItemStack is : itemList) {
    			List<String> tooltip = is.getTooltip(this.mc.thePlayer, false);
    			tooltip.add(chi.getModName(is));
    			for (String s : tooltip) {
        			Matcher m = p.matcher(s);
        			if (m.find() ){// || s.toLowerCase().contains(search.toLowerCase())) {
    					returnList.add(is);
    					continue;
    				}
    			}
    		}
    	}

    	return returnList;
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GuiItemDisplay.setupGfx();
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;

        // Draw Search Box
		this.drawGradientRect(xPos+2, yPos+height-searchY, xPos+width-2, yPos+height-1, 0x33DDDDDD, 0x44DDDDDD);
		this.drawGradientRect(xPos+3, yPos+height-searchY+1, xPos+width-3, yPos+height-2, 0x77000000, 0x88000000);
		if (this.searchField.getText().length() == 0 && !this.searchField.isFocused())
			this.drawString(fontRendererObj, EnumChatFormatting.ITALIC + "Search...", xPos+5, yPos+height-11, 0x888888);
		this.searchField.drawTextBox();

		// Draw Scrollbar
		if (hasScrollbar) {
			gsb.barY = Math.max(Math.min(gsb.barY, height - gsb.height-searchY-1), 0);
			gsb.yPosition = (int) (gsb.barY+yPos+1);
			setConfig(Keys.C_ITEM_BARY, gsb.barY);
			this.drawGradientRect(xPos+width-10, yPos+1, xPos+width-9, yPos+height-searchY, 0x33DDDDDD, 0x55DDDDDD);
			gsb.drawButton(mc, mouseX, mouseY);
		}
		
		// Draw Items
		for (ItemSlot slot : slotList) {
			int ijx = slot.xPos + (hasScrollbar?0:6);
			int ijy = (int)(slot.yPos-gsb.barY/barMod);
			if (ijy < yPos || ijy > yPos+height-16-searchY) continue;
			
			ItemStack is = slot.displayItem;
			if (is != null) {
				this.drawItemStack(is, ijx, ijy, "");
				if (isMouseOverSlot(slot, mouseX, mouseY)) {
					this.theSlot = slot;
				}
			}
		}

		this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
        GuiItemDisplay.closeGfx();
	}
    public void drawDefaultBackground() {}
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
//    	super.drawScreen(mouseX, mouseY, partialTick);

    	if (this.isMouseOverSlot(this.theSlot, mouseX, mouseY)) {
            ItemStack is = this.theSlot.displayItem;
    		if (is != null) {
    			this.zLevel = 100.0F;
    			itemRender.zLevel = 100.0F;
    	        this.fontRendererObj.drawString(" ", 0, 0, 0xFFFFFF);

    			int ijx = this.theSlot.xPos + (hasScrollbar?0:6);
    			int ijy = (int) (this.theSlot.yPos-gsb.barY/barMod);

				List<String> toolTip = new ArrayList();
    			if (isShiftKeyDown()) toolTip.add(EnumChatFormatting.DARK_RED +""+ EnumChatFormatting.BOLD + "Give Item");
				toolTip.addAll(is.getTooltip(mc.thePlayer, false));
				toolTip.add(Item.itemRegistry.getNameForObject(is.getItem()) + " " + (is.getItemDamage()>0?is.getItemDamage():""));
//				if (getConfigStr(Keys.C_KEY).equalsIgnoreCase(GuiItemDisplay.ALL))
				if (is.getItem().getShareTag()) {
					Multimap att = is.getAttributeModifiers(); 
					NBTTagCompound tag = new NBTTagCompound();
//					is.writeToNBT(tag);
					tag = is.getTagCompound();
					if (tag != null)
						for (Object o : tag.func_150296_c())
							toolTip.add(o.toString() + ": " + tag.getTag(o.toString()));
				}
					if (toolTip.size() > 1) toolTip.add("");
					toolTip.add(EnumChatFormatting.BLUE +""+ EnumChatFormatting.ITALIC +  ConfigHandler.instance().getModName(is));
 
				// Get the maximum width of the box
				int sw = 0;
				for (int i2 = 0; i2 < toolTip.size(); ++i2)
					sw = Math.max(fontRendererObj.getStringWidth(toolTip.get(i2))+2, sw);
				
				// Color the item name according to rarity
				if (isShiftKeyDown())
	                toolTip.set(1, is.getRarity().rarityColor + (String)toolTip.get(1));
				else
					toolTip.set(0, is.getRarity().rarityColor + (String)toolTip.get(0));

				// Draw box around item
				drawGradientRect(ijx,   ijy-1,  ijx+17, ijy,    0x33EEEEEE, 0x33EEEEEE); // top
				//drawGradientRect(ijx-1, ijy-1,  ijx,    ijy+17, 0x33EEEEEE, 0x66EEEEEE); // left
				drawGradientRect(ijx+17,ijy+17, ijx,    ijy+16, 0x55EEEEEE, 0x55EEEEEE); // bottom
				drawGradientRect(ijx+17,ijy+16, ijx+16, ijy,    0x66EEEEEE, 0x33EEEEEE); // right

				// Determine the tooltip height and move if it goes below the screen
				int h = 16+10*(toolTip.size()-1);
				int ijy2 = (ijy + h > height+40) ? height-h+40 : ijy;

				// Draw box around tooltip
				drawGradientRect(ijx-sw-2, ijy2-1, ijx-1, ijy2+h+1, 0x33EEEEEE, 0x55EEEEEE);
				drawGradientRect(ijx-sw-1, ijy2,   ijx-1, ijy2+h,   0xCC000000, 0xAA000000);
				drawGradientRect(ijx-1,    ijy2-1, ijx,   ijy,      0x33EEEEEE, 0x33EEEEEE); // top right
				drawGradientRect(ijx-1,    ijy+16, ijx,   ijy2+h+1, 0x33EEEEEE, 0x55EEEEEE); // bottom right

				int var = 0;
				for (int i2 = 0; i2 < toolTip.size(); ++i2) {
	                String s1 = (String)toolTip.get(i2);
	                //if (s1.length() < 1) continue;
	                int dy = ijy + (var++)*10+2 + ((i2 > 0)?4:0);
	                if (toolTip.size() == 1) dy += 2;
	                fontRendererObj.drawStringWithShadow(s1, ijx-sw, dy, -1);
	            }

				this.zLevel = 0.0F;
				itemRender.zLevel = 0.0F;
    		} else {
				int ijx = this.theSlot.xPos + (hasScrollbar?0:6);
				int ijy = (int)(this.theSlot.yPos-gsb.barY/barMod);
				drawGradientRect(ijx-1, ijy-1,  ijx,    ijy+17, 0x33EEEEEE, 0x66EEEEEE);
    		}
    	} else {
    		this.theSlot = null;
    	}
    }
    private void drawItemStack(ItemStack itemstack, int xPos, int yPos, String par4)
    {
        FontRenderer font = null;
        if (itemstack != null) font = itemstack.getItem().getFontRenderer(itemstack);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), itemstack, xPos, yPos);
        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), itemstack, xPos, yPos, par4);
    }
    protected boolean isMouseOverSlot(ItemSlot slot, int mouseX, int mouseY) {
    	if (slot == null) return false;
    	return (
    		   mouseX >= slot.xPos-1               && mouseX <= slot.xPos+16
    		&& mouseY >= slot.yPos-gsb.barY/barMod && mouseY <= slot.yPos+16-gsb.barY/barMod 
    		&& mouseY >= yPos                      && mouseY <= yPos+height-searchY 
    		&& slot.yPos-gsb.barY/barMod+15 <= yPos+height-searchY 
    	);
    }
    private ItemSlot getItemSlotAtPosition(int mouseX, int mouseY) {
    	for (ItemSlot slot : slotList) {
    		if (isMouseOverSlot(slot, mouseX, mouseY))
    			return slot;
    	}
    	return null;
    }
    public boolean isMouseOverPanel(int mouseX, int mouseY) {
    	return mouseX >= xPos && mouseX <= xPos + width && mouseY >= yPos && mouseY <= yPos + height;
    }
    public void handleMouseInput() {
//       	if (hasScrollbar) gsb.barY -= (Mouse.getEventDWheel()/30);
    	double y = gsb.barY/barMod;
//    	System.out.println(Mouse.getEventDWheel());
    	int m = Mouse.getEventDWheel();
    	if (m != 0 && hasScrollbar) gsb.barY -= (Mouse.getEventDWheel()>0 ? 1:-1)*barMod*19;
    }
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (mouseX > xPos+5 && mouseX < xPos+width+3 && mouseY >= yPos+height-searchY && mouseY <= yPos+height) {
        	if (button == 1) { 
        		this.searchField.setText("");
        		setSelectedMod();
        	}
        	this.searchField.setFocused(true);
        	parent.isSearching = true;
        } else {
        	this.searchField.setFocused(false);
        	parent.isSearching = false;
        }
    }
    protected void mouseMovedOrUp(int mouseX, int mouseY, int which) {
    	ItemSlot slot = getItemSlotAtPosition(mouseX, mouseY);
    	if (slot != null) {
    		if (isShiftKeyDown()) {
    			// Pick up items
    			if (slot.displayItem != (ItemStack)null) {
    				Item item = slot.displayItem.getItem();
    				int meta = slot.displayItem.getItemDamage();
    				int stackSize = which==0?1:slot.displayItem.getMaxStackSize();

    				if (mc.playerController.isInCreativeMode()) {
	    				ItemStack itemstack = slot.displayItem;//new ItemStack(item, stackSize, meta);
	    				itemstack.stackSize = stackSize;
	    				InventoryPlayer inventory = mc.thePlayer.inventory;
	    				int i, il = 0, ik = 35;
	    				for (i=il; i<ik; ++i) if (inventory.getStackInSlot(i) == null) break;
	    				if (inventory.mainInventory[i] != null) return;
	    				if (i<9) i+=36;
	    		        this.mc.playerController.sendSlotPacket(itemstack, i);
    				} else {
	    				String command = "/give " + mc.thePlayer.getCommandSenderName() + " " + Item.itemRegistry.getNameForObject(item) + " " + stackSize + " " + meta;

	    				Map<Integer, Integer> enchants = EnchantmentHelper.getEnchantments(slot.displayItem);
	    				if (!enchants.isEmpty()) {
	    					command += " {StoredEnchantments:[";
	    					for (Entry<Integer, Integer> entry : enchants.entrySet())
	    						command += "{id:" + entry.getKey() + ",lvl:" + entry.getValue() + "}";
	    					command += "]}";
	    				}
	    				
	    				this.mc.ingameGUI.getChatGUI().addToSentMessages(command);
	    		        if (ClientCommandHandler.instance.executeCommand(mc.thePlayer, command) == 1) return;
	    		        this.mc.thePlayer.sendChatMessage(command);
    				}
    			}
    		} else if (isCtrlKeyDown()) {
    			// Custom item
    		} else {
    			// Recipe Lookup
    			if (slot.displayItem != (ItemStack)null) {
    				if (which == 0) GuiItemDisplay.openCraftingRecipe(mc, parent.parent, slot.displayItem);
    				if (which == 1) GuiItemDisplay.openCraftingUsage( mc, parent.parent, slot.displayItem);
    			}
    		}
    	}
    }
    public void handleKeyboardInput() {
    	super.handleKeyboardInput();
    }
    public void keyTyped(char par1, int par2) {
        if (this.searchField.textboxKeyTyped(par1, par2)) {
        	setSelectedMod();
        } else if (par1 == 'r' && theSlot != null) {
			GuiItemDisplay.openCraftingRecipe(mc, parent.parent, theSlot.displayItem);
    	} else if (par1 == 'u' && theSlot != null) {
			GuiItemDisplay.openCraftingUsage(mc, parent.parent, theSlot.displayItem);
    	}
    }
    public static class ItemSlot {
    	public int slotIndex, xPos, yPos;
    	public ItemStack displayItem;
    	public ItemSlot(int slotNumber, ItemStack is, int xPos, int yPos) {
    		this.slotIndex = slotNumber;
    		this.displayItem = is;
    		this.xPos = xPos;
    		this.yPos = yPos;
    		this.slotIndex = slotNumber;
    	}
    }
}