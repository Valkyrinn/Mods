package mods.horsehud.api;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IRecipeSlot {

	boolean      isMouseOverSlot(int mouseX, int mouseY);
	List<String>      getTooltip();
	List<ItemStack>     getItems();
	int                slotIndex();
	int                     xPos();
	int                     yPos();
}
