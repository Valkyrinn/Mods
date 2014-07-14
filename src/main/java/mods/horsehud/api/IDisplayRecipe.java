package mods.horsehud.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public interface IDisplayRecipe {
	boolean contains(ItemStack in);
	boolean yields(ItemStack out);
	Object  input();
	Object output();
	
	String title();
	int page();
	void setVars(Minecraft mc, int x, int y, int w, int h, int index);
	IRecipeSlot draw(GuiContainer gui, int mouseX, int mouseY, int index);
	ItemStack tabIcon();
}
