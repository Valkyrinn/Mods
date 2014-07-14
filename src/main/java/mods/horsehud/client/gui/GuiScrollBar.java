package mods.horsehud.client.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;

@SideOnly(Side.CLIENT)
public class GuiScrollBar extends GuiButton {
	public double barY=0; 
	public int clickStart = 0;
	public int height = 15, width = 8;
	public int maxY=0, minY=0;
	public boolean isPressed = false;
	
	public GuiScrollBar(int id, int xPos, int yPos, int width, int height) {
		super(id, xPos, yPos, width, height, "");
	}
	
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            
            // top left and right
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, 3);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, 3);

            // middle left and right
            if (this.height > 8)
            for (int i=0; i<=(this.height-8)/3; i++) {
	            this.drawTexturedModalRect(this.xPosition, this.yPosition+3*(i+1), 0, 46 + k * 20+2, this.width / 2, 3);
	            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition+3*(i+1), 200 - this.width / 2, 46 + k * 20+2, this.width / 2, 3);
            }

            // bottom left and right
            this.drawTexturedModalRect(this.xPosition, this.yPosition+this.height-4, 0, 46 + k * 20+15, this.width / 2, 3);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition+this.height-4, 200 - this.width / 2, 46 + k * 20+15, this.width / 2, 3);

            this.mouseDragged(mc, mouseX, mouseY);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 0xA0A0A0;
            }
            else if (this.field_146123_n)
            {
                l = 0xFFFFA0;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
//    	if (isPressed)
//    	int k = Math.min(Math.max(mouseY, 0), mc.currentScreen.height-this.height-3);
    	if (Mouse.isButtonDown(0)) {
    		if (!isPressed && mouseX >= xPosition && mouseX <= xPosition+width && mouseY >= yPosition && mouseY <= yPosition+height) {
    			isPressed = true;
    			clickStart = mouseY - yPosition;
    		}
            int h = (new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight)).getScaledHeight();
            if (isPressed)
    			barY = Math.min(Math.max(mouseY-clickStart-minY, 0), maxY);
//    			barY = Math.min(Math.max(mouseY-clickStart-17, 0), h-this.height-37-14);
    	} else {
    		isPressed = false;
    		clickStart = 0;
    	}
    }
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
//    	System.out.println("Click!");
//    	this.isPressed = this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    	return this.isPressed;
    }
    public void mouseReleased(int mouseX, int mouseY) { isPressed = false; }

}
