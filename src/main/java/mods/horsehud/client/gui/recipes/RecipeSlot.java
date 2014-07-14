package mods.horsehud.client.gui.recipes;

import java.util.ArrayList;
import java.util.List;

import mods.horsehud.api.IRecipeSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RecipeSlot implements IRecipeSlot {
	private int slotIndex, xPos, yPos;
	private ArrayList<ItemStack> displayItem;
	private EntityPlayer player;

	public RecipeSlot(int slotNumber, ArrayList<ItemStack> is, int xPos, int yPos) {
		this.slotIndex = slotNumber;
		this.displayItem = is;
		this.xPos = xPos;
		this.yPos = yPos;
		this.player = Minecraft.getMinecraft().thePlayer;
	}

	@Override public int            slotIndex() { return this.slotIndex; }
	@Override public int                 xPos() { return this.xPos; }
	@Override public int                 yPos() { return this.yPos; }
	@Override public List<ItemStack> getItems() { return this.displayItem; }

	@Override
	public boolean isMouseOverSlot(int mouseX, int mouseY) {
		return mouseX>xPos && mouseX<xPos+16 && mouseY>yPos && mouseY<yPos+16;
	}
	
	@Override
	public List<String> getTooltip() {
		List<String> retList = new ArrayList();
		
		for (ItemStack is : displayItem) {
			retList.addAll(is.getTooltip(player, false));
		}
		
		return retList;
	}
}
