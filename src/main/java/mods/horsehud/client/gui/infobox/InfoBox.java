package mods.horsehud.client.gui.infobox;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import sun.awt.image.PixelConverter.Rgba;

import mods.horsehud.api.Keys;
import mods.horsehud.api.Style;
import mods.horsehud.client.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class InfoBox extends GuiScreen {
	
	private static InfoBox instance = null;
	public InfoBox(Minecraft minecraft) {
		this.mc = minecraft;
		instance = this;
	}
	public static InfoBox instance() {
		if (instance == null)
			instance = new InfoBox(Minecraft.getMinecraft());
		return instance;
	}

    public void drawBox(String title, String subTitle, int health, int maxHealth, int armor, List infoList, String mod, ItemStack is, int posX, int posY) {
    	ConfigHandler chi = ConfigHandler.instance();
    	boolean hasIcon = (is != null)?true:false;
    	if (!GuiInfoBox.getConfigBool(Keys.C_MODVISIBLE)) mod = "";
        int topHeight = 5, bottomHeight = 5, rowHeight = 9, 
        	textY=posY+topHeight,
        	middle = posX + 60;
        
        int rows = 0, currentRow = 0;
    	if (title != null && title != "") rows++;
    	if (subTitle != null && subTitle != "") rows++;
    	if (maxHealth > 0) { if (maxHealth <= 40) rows += (int)Math.ceil((double)maxHealth/20.0D); else rows++; };
    	if (armor > 0) rows++;
    	if (infoList != null) rows += infoList.size();
    	int off = 0;
    	if (hasIcon) off = 10; 

    	// for testing purposes only
//        this.drawCenteredString(this.mc.fontRenderer, "Heart Rows: " + Math.ceil((double)maxHealth/20.0D), 61, textY+(currentRow+20)*rowHeight, 0xFFFFFF);
//        this.drawCenteredString(this.mc.fontRenderer, "Health: " + health + " / " + maxHealth, 61, textY+(currentRow+21)*rowHeight, 0xFF0000);

    	if (rows > 0) {
    		if (mod.length() > 0) rows++; // ModName
    		int bufferY = -1;
    		if (rows == 1) bufferY = 4;
    		
    		// Draw the box
//    		int w = Math.max((hasIcon?43:30)+len/2, 60);
//    		int w2 = Math.max(w*2-120, 20);
//    		hasIcon = false;
    		
            int len = 0;
            len = Math.max(len, this.mc.fontRenderer.getStringWidth(title));
            len = Math.max(len, this.mc.fontRenderer.getStringWidth(subTitle));
            len = Math.max(len, this.mc.fontRenderer.getStringWidth(mod));
            for (int i=0; i<infoList.size(); i++)
                len = Math.max(len, this.mc.fontRenderer.getStringWidth(infoList.get(i)+""));
//    		int w2 = Math.max((hasIcon?20:4) + (len-60)-30, 20);
//    		int w = (hasIcon?10:2) + Math.max(50+w2/2, 60);
    		int w2 = Math.max((hasIcon?30:8) + len, maxHealth>=20?10*9:maxHealth*5);
    		int w = (hasIcon?10:0) + Math.max(w2/2, 0);

//    		this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/inventory.png")); 
//	        this.drawTexturedModalRect(posX,    posY,  0,166, 110,topHeight+bufferY);
//	        this.drawTexturedModalRect(posX+w2/2, posY, 10,166, 100,topHeight+bufferY);
//	        this.drawTexturedModalRect(posX+w2,posY, 20,166, 100,topHeight+bufferY);
//	        for (int i=0; i<rows; i++) {
//	        	this.drawTexturedModalRect(posX,    textY+(i*rowHeight)+bufferY,  0,171, 110,rowHeight);
//	        	this.drawTexturedModalRect(posX+w2/2, textY+(i*rowHeight)+bufferY, 10,171, 100,rowHeight);
//	        	this.drawTexturedModalRect(posX+w2,textY+(i*rowHeight)+bufferY, 20,171, 100,rowHeight);
//	        }
//	        this.drawTexturedModalRect(posX,    textY+(rows*rowHeight)+bufferY,  0,193, 110,topHeight);
//	        this.drawTexturedModalRect(posX+w2/2, textY+(rows*rowHeight)+bufferY, 10,193, 100,topHeight);
//	        this.drawTexturedModalRect(posX+w2,textY+(rows*rowHeight)+bufferY, 20,193, 100,topHeight);
    		this.drawGradientRect(posX, posY, posX+w2,textY+(rows*rowHeight)+bufferY+5, 0x33DDDDDD, 0x55DDDDDD);
    		this.drawGradientRect(posX+1, posY+1, posX+w2-1,textY+(rows*rowHeight)+bufferY+4, 0x88000000, 0xAA000000);
	        middle = posX + w;

	        // Draw Title
	        this.drawCenteredString(this.mc.fontRenderer, title, middle, textY+currentRow*rowHeight+bufferY/2-1, 0xFFFFFF);
            currentRow++;
            
            // Draw Subtitle
            if (subTitle != null && subTitle != "") {
            	this.drawCenteredString(this.mc.fontRenderer, EnumChatFormatting.ITALIC + subTitle, middle, textY+currentRow*rowHeight, 0x999999);
	            currentRow++;
            	this.drawCenteredString(this.mc.fontRenderer, "", middle, textY+currentRow*rowHeight, 0xFFFFFF);
    		}
            
            // Draw Hearts
	        if (maxHealth > 0) {
	        if (maxHealth <= 40) {
	        	this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/icons.png"));

	        	double maxHealthPerRow = 20.0D; 
	        	int maxHeartsPerRow = (int)maxHealthPerRow/2;
	        	int maxHearts, offset, heartRows = (int) Math.ceil((double)maxHealth/maxHealthPerRow);
	        	
	        	for (int h=0; h<heartRows; h++) {
	            	maxHearts = Math.min((int) Math.floor((double)maxHealth/2-maxHeartsPerRow*h), maxHeartsPerRow);
	            	offset = middle-(maxHearts*8 + ((maxHearts<10)?(maxHealth%2)*5:0)+1)/2;
	            	
	            	// Draw empty heart containers
	            	for (int i=0; i<maxHearts; i++) {
	            		this.drawTexturedModalRect(offset+8*i,textY+(currentRow)*rowHeight-1, 25,0, 9,9);
	        			
	        			// Fill heart containers
	        			if ((i+1)*2+h*maxHeartsPerRow <= health) // if this heart container is full
	        				this.drawTexturedModalRect(offset+8*i,textY+(currentRow)*rowHeight-1, 52,0, 10,9);
	        			else if ((i+1)*2+h*maxHeartsPerRow-1 == health && h+1 == heartRows) // if this heart container is half full
	        				this.drawTexturedModalRect(offset+8*i,textY+(currentRow)*rowHeight-1, 52,0, 5,9);
	            	}
	            	
	            	// If health is odd, append a half heart container
	    			if (maxHealth%2 == 1 && h+1 == heartRows) {
	    				this.drawTexturedModalRect(offset+8*maxHearts,textY+(currentRow)*rowHeight-1, 25,0, 5,9);
	    				
	    				// If health is full, fill the half heart
		    			if (health == maxHealth)
		    				this.drawTexturedModalRect(offset+1+8*maxHearts,textY+(currentRow)*rowHeight-1, 62,0, 5,9);
	    			}
	    			
	    			currentRow++;
		        }
	        } else {
	        	this.drawCenteredString(this.mc.fontRenderer, "Health: " + health + " / " + maxHealth, 61, textY+(currentRow)*rowHeight, 0x992222);
	        	currentRow++;
	        }
	        }
	        	
        	// Draw the armor icons
			if (armor > 0) {
	        	this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/icons.png"));
    			int fullArmors = armor/2;
    			int halfArmors = armor%2;
    			int offset = middle-(fullArmors*8 + halfArmors*5+1)/2;

    			// Draw full armor icons
    			for (int i=0; i<fullArmors; i++) {
    				this.drawTexturedModalRect(offset+8*i,textY+currentRow*rowHeight, 34,9, 9,9);
            	}
    			
    			// If armor is odd, append a half armor icon
            	if (halfArmors > 0)
            		this.drawTexturedModalRect(offset+8*fullArmors,textY+currentRow*rowHeight, 25,9, 9,9);
            	
            	currentRow++;
			} // end if (armor > 0)

            // Print out custom infoList
            for (int i=0; i<infoList.size(); i++) {
            	this.drawCenteredString(this.mc.fontRenderer, infoList.get(i)+"", middle, textY+currentRow*rowHeight, 0x999999);
            	currentRow++;
            }
            if (mod.length() > 0)
            	this.drawCenteredString(this.mc.fontRenderer, EnumChatFormatting.ITALIC + mod, middle, textY+currentRow*rowHeight, 0x4040AA);

        	// Draw Icon
    		if (hasIcon) {
//    	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//    	        RenderHelper.disableStandardItemLighting();
//    	        GL11.glDisable(GL11.GL_LIGHTING);
//    	        GL11.glDisable(GL11.GL_DEPTH_TEST);
//    	        super.drawScreen(mouseX, mouseY, partialTick);
    	        RenderHelper.enableGUIStandardItemLighting();
    	        GL11.glPushMatrix();
//    	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    	        short short1 = 240;
    	        short short2 = 240;
    	        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)short1 / 1.0F, (float)short2 / 1.0F);
    	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	        
			    RenderItem ri = new RenderItem();
			    try { ri.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), is, posX+2, textY-2+(rows>1?2:0)); }
			    catch (Exception e) {}

    	        GL11.glPopMatrix();
//    	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	        GL11.glDisable(GL11.GL_LIGHTING);
    		}
//    		this.drawCenteredString(this.mc.fontRenderer, Style.WHITE+"", posX+5, posY+2, 0xFFFFFF);
    	} // end if (rows > 0)
    } // end drawBox(String, int, int, int, String[])
//    private void drawItemStack(ItemStack itemstack, int xPos, int yPos, String par4)
//    {
////        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
//        this.zLevel = 200.0F;
//        itemRender.zLevel = 200.0F;
//        FontRenderer font = null;
//        if (itemstack != null) font = itemstack.getItem().getFontRenderer(itemstack);
//        if (font == null) font = fontRendererObj;
//        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), itemstack, xPos, yPos);
//        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), itemstack, xPos, yPos, par4);
//        this.zLevel = 0.0F;
//        itemRender.zLevel = 0.0F;
//    }

}
