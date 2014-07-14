package mods.horsehud.client.gui.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mods.horsehud.api.IDisplayRecipe;
import mods.horsehud.api.IRecipeSlot;
import mods.horsehud.client.ConfigHandler;
import mods.horsehud.client.gui.itembox.GuiItemDisplay;
import mods.horsehud.client.gui.itembox.GuiItemPanel.ItemSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiRecipeDisplay extends GuiContainer {

	public GuiScreen parent;
	private List<IDisplayRecipe> recipeList;
	private Map<String, RecipeTab> tabList;
	private Map<Integer, String> tabKeys;
	private String currentlySelectedTab = "";
	private IRecipeSlot theSlot;
	private int currentPage = 0;
	private Map<String, Integer> totalPages;

	public GuiRecipeDisplay(GuiScreen parent, List<IDisplayRecipe> recipes) {
		this(new GuiItemDisplay.ItemContainer());
		this.parent = parent;
		this.recipeList = recipes;
	}
	public GuiRecipeDisplay(Container par1Container) {
		super(par1Container);
		this.mc = Minecraft.getMinecraft();
		this.fontRendererObj = this.mc.fontRenderer;
	}
	
	@Override
	public void initGui() {
		ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        this.width  = scaledresolution.getScaledWidth();
        this.height = scaledresolution.getScaledHeight();
        this.guiLeft = (this.width-176)/2;
		this.guiTop = (this.height-166)/2;
		
		tabList = new HashMap();
		
		totalPages = new HashMap();
		int var = 0;
		for (IDisplayRecipe recipe : this.recipeList) {
			if (!tabList.containsKey(recipe.title())) tabList.put(recipe.title(), new RecipeTab(tabList.size(), recipe.title(), recipe.tabIcon()));
			tabList.get(recipe.title()).add(recipe);
			var = tabList.get(recipe.title()).recipeList().size() - 1;
			recipe.setVars(mc, guiLeft, guiTop, this.width, this.height, var++);
			
			if (!totalPages.containsKey(recipe.title()))
				totalPages.put(recipe.title(), 0);
			if (recipe.page() > totalPages.get(recipe.title()))
				totalPages.put(recipe.title(), recipe.page());
		}
		
		tabKeys = new HashMap();
		int key = 0;
		for (String value : tabList.keySet())
			tabKeys.put(key++, value);
		if (!tabKeys.containsKey(currentlySelectedTab)) currentlySelectedTab = tabKeys.get(0);

    	String buttonLabel = currentlySelectedTab + " Recipes";
		int w = fontRendererObj.getStringWidth(buttonLabel) + 10;
		
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, guiLeft+10, guiTop+150, 10, 10, "<"));
		this.buttonList.add(new GuiButton(1, guiLeft+154, guiTop+150, 10, 10, ">"));
		this.buttonList.add(new GuiButton(2, guiLeft+(176-w)/2, guiTop+4, w, 14, buttonLabel));
	}
    public void handleMouseInput()
    {
        super.handleMouseInput();
    	int mouseX = Mouse.getEventX() * (width) / this.mc.displayWidth;
    	int mouseY = (this.mc.displayHeight-Mouse.getEventY()) * (height) / this.mc.displayHeight;
//    	System.out.println("(" + (mouseX) + ", " + (mouseY) + ")");
//    	if (!this.isMouseOverPanel(mouseX, mouseY)) return;
    	
    	if (mouseX < guiLeft || mouseX > width - guiLeft || mouseY < guiTop || mouseY > height-guiTop) return;
        int wheelScroll = Mouse.getEventDWheel();
        if (wheelScroll > 0) currentPage = Math.max(currentPage-1, 0);
        if (wheelScroll < 0) currentPage = Math.min(currentPage+1, totalPages.get(currentlySelectedTab));
    }
    public void handleKeyboardInput() {
    	super.handleKeyboardInput();
    }
    public void keyTyped(char par1, int par2) {
    	if (par1 == 'r' && theSlot != null) {
    		GuiItemDisplay.openCraftingRecipe(mc, parent, theSlot.getItems().get(index%theSlot.getItems().size()));
    	}
    	if (par1 == 'u' && theSlot != null) {
    		GuiItemDisplay.openCraftingUsage(mc, parent, theSlot.getItems().get(index%theSlot.getItems().size()));
    	}
    	if (par1 == '-') { ConfigHandler.instance().buildMap(); RecipeList.instance().build(); }
        if (par2 == mc.gameSettings.keyBindInventory.getKeyCode() || par2 == 1) {
        	mc.displayGuiScreen(parent);
        }
    }
    protected void mouseMovedOrUp(int mouseX, int mouseY, int which) {
    	super.mouseMovedOrUp(mouseX, mouseY, which);
        if (which == 0) {
            for (int l = 0; l < this.buttonList.size(); ++l) {
                GuiButton guibutton = (GuiButton)this.buttonList.get(l);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY))
                {
                    this.actionPerformed(guibutton);
                    if (l == 0) {
                    	currentPage = Math.max(currentPage-1, 0);
                    } else if (l == 1) {
                    	currentPage = Math.min(currentPage+1, totalPages.get(currentlySelectedTab));
                    } else if (l == 2) {
                    	GuiItemDisplay.openRecipesFor(mc, parent, tabList.get(currentlySelectedTab).displayItem());
                    }
                }
            }
            
            if (mouseX > guiLeft && mouseX < width-guiLeft && mouseY < guiTop && mouseY > guiTop - 38) {
            	int x = (mouseX - guiLeft)/28;
            	if (tabKeys.containsKey(x)) currentlySelectedTab = tabKeys.get(x);
            	buttonList.remove(2);
            	String buttonLabel = currentlySelectedTab + " Recipes";
        		int w = fontRendererObj.getStringWidth(buttonLabel) + 10;
            	buttonList.add(new GuiButton(2, guiLeft+(176-w)/2, guiTop+4, w, 14, buttonLabel));
            	currentPage = 0;
            }

            if (theSlot != null)
			if (theSlot.getItems().get(index%theSlot.getItems().size()) != (ItemStack)null) {
				GuiItemDisplay.openCraftingRecipe(mc, parent, theSlot.getItems().get((index/5)%theSlot.getItems().size()));
			}
        } else if (which == 1) {
            if (theSlot != null)
			if (theSlot.getItems().get(index%theSlot.getItems().size()) != (ItemStack)null) {
				GuiItemDisplay.openCraftingUsage(mc, parent, theSlot.getItems().get((index/5)%theSlot.getItems().size()));
			}

        }

    }

    public void drawDefaultBackground() { 
//    	super.drawDefaultBackground(); 
    }
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//		super.drawScreen(mouseX, mouseY, partialTicks);

		try { if (theSlot != null) this.renderToolTip(theSlot.getItems().get((index/5)%theSlot.getItems().size()), mouseX, mouseY); } catch (NullPointerException e) {}

		if (!tooltip.isEmpty()) { 
			this.drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
			tooltip.clear();
		}
	}

	private long time = System.currentTimeMillis();
	private int index = 0;
	private List<String> tooltip = new ArrayList();
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		long t = System.currentTimeMillis();
		if (t - time > 250) {
			index = (index+1)%Integer.MAX_VALUE;
			time = t;
		}

		GL11.glDisable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        short short1 = 240;
        short short2 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)short1 / 1.0F, (float)short2 / 1.0F);

        this.fontRendererObj.drawString(" ", 0, 0, 0xFFFFFF);
		
		int var = 0, yOffset, ijxS=0, ijyS=0;
		String titleS = "";
		ItemStack itemS = null;
		for (Map.Entry<String, RecipeTab> entry : tabList.entrySet()) {
			// If there are no recipes in the tab, continue
			if (entry.getValue().recipeList().isEmpty()) continue;
			
			// Get the creative tabs image
			this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tabs.png"));
			
			// If no tab is selected, select this one
			if (currentlySelectedTab == "") currentlySelectedTab = entry.getKey();

			// Tab x,y locations
			int ijx = guiLeft + 28*var + 1;
			int ijy = guiTop - 28;

			// Draw the tab
			if (entry.getKey() == this.currentlySelectedTab) {
				ijxS = ijx;
				ijyS = ijy;
				titleS = entry.getValue().title();
				itemS = entry.getValue().displayItem();
			} else {
				drawTexturedModalRect(ijx,ijy, 1,0, 26,29);
				drawItemStack(entry.getValue().displayItem(), ijx+5, ijy+9, "");
			}
			
			
			if (mouseX>ijx && mouseX<ijx+25 && mouseY>ijy && mouseY<ijy+29)
				this.tooltip = entry.getValue().getHoveringText();
			
			var++;
		}
        this.mc.renderEngine.bindTexture(new ResourceLocation("horsehud", "textures/gui/blank.png")); 
		this.drawTexturedModalRect(guiLeft, guiTop, 0,0, 176,166);
		this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tabs.png"));
		drawTexturedModalRect(ijxS,ijyS, 1,32, 26,31);
		// Draw the tab icon
		if (itemS != null) drawItemStack(itemS, ijxS+5, ijyS+8, "");
		// Draw Title
//		int w = fontRendererObj.getStringWidth(titleS) + ((List<GuiButton>)buttonList).get(2).getButtonWidth();
//	    fontRendererObj.drawString(titleS, guiLeft+(176-w)/2, guiTop+6, 0x404040);

        theSlot = null;

        this.fontRendererObj.drawString(" ", 0, 0, 0xFFFFFF);
        if (tabList.containsKey(currentlySelectedTab))
        for (IDisplayRecipe r : this.tabList.get(currentlySelectedTab).recipeList()) {
        	if (r.page() != currentPage) continue;
        	IRecipeSlot slot = r.draw(this, mouseX, mouseY, index);
        	if (slot != null) theSlot = slot;
        }

		String title = "Page " + (currentPage+1) + "/" + (totalPages.get(currentlySelectedTab)+1);
		int w = fontRendererObj.getStringWidth(title);
	    fontRendererObj.drawString(title, guiLeft+(176-w)/2, guiTop+153, 0x404040);

    	if (currentPage > 0)
			((List<GuiButton>)buttonList).get(0).drawButton(mc, mouseX, mouseY);
		if (currentPage < totalPages.get(currentlySelectedTab))
			((List<GuiButton>)buttonList).get(1).drawButton(mc, mouseX, mouseY);
		
		GuiButton allButton = ((List<GuiButton>)buttonList).get(2);
		allButton.drawButton(mc, mouseX, mouseY);

		if (allButton.func_146115_a())
			this.tooltip.add("Show All Recipes");

		GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
	}

	public void drawItemStack(ItemStack itemstack, int xPos, int yPos, String par4)
    {
        FontRenderer font = null;
        if (itemstack != null) font = itemstack.getItem().getFontRenderer(itemstack);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(font, mc.getTextureManager(), itemstack, xPos, yPos);
        itemRender.renderItemOverlayIntoGUI(font, mc.getTextureManager(), itemstack, xPos, yPos, par4);
    }
	
	public class RecipeTab {
		int tabID;
		ItemStack displayItem;
		List<IDisplayRecipe> recipeList;
		String title;
		
		public RecipeTab(int tabID, String title, ItemStack displayItem) {
			this.tabID = tabID;
			this.title = title;
			this.displayItem = displayItem;
			this.recipeList = new ArrayList();
		}
		
		public String title() { return this.title; }
		public ItemStack displayItem() { return this.displayItem.copy(); }
		public List<IDisplayRecipe> recipeList() { return this.recipeList; }
		public void add(IDisplayRecipe recipe) { this.recipeList.add(recipe); }
		public List<String> getHoveringText() {
			List<String> hover = new ArrayList();
			hover.add(this.title + " Recipes");
			return hover;
		}
	}
}
