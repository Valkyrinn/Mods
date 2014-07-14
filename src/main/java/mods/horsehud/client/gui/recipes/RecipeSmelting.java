package mods.horsehud.client.gui.recipes;

import java.util.ArrayList;
import java.util.List;

import mods.horsehud.api.IDisplayRecipe;
import mods.horsehud.api.IRecipeSlot;
import mods.horsehud.client.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RecipeSmelting extends GuiScreen implements IDisplayRecipe {
	private ArrayList<ItemStack> input = null;
	private ArrayList<ItemStack> fuel = null;
	private ArrayList<ItemStack> output = null;
//	ItemStack[][] inputC = null;
//	ItemStack inputS = null;
//	ItemStack output;
//	String type;
	private boolean shapeless = false;
	
	public RecipeSmelting(ItemStack in, ItemStack out, ArrayList<ItemStack> fuel) {
		this.input = new ArrayList();
		this.output = new ArrayList();
		this.input.add(in);
		this.output.add(out);
		this.fuel = fuel; 
		this.mc = Minecraft.getMinecraft();
		this.fontRendererObj = this.mc.fontRenderer;
	}
	
//	public String type() { return this.type; }
//	public boolean shapeless() { return this.shapeless; }
	public Object output() { return this.output; }
	public Object input() {
//		if (type == CRAFTING) return inputC;
//		if (type == SMELTING) return inputS;
		return null;
	}
	
	public boolean contains(ItemStack in) {
		if (in == null) return false;
		for (ItemStack is : input) {
			if (is.getItemDamage() == Short.MAX_VALUE) is.setItemDamage(0);
			if (is.isItemEqual(in))// && ItemStack.areItemStackTagsEqual(inputS, in))
				return true;
		}
		return false;
	}
	
	public boolean yields(ItemStack out) {
		if (!output.get(0).isItemEqual(out)) return false;
//		if (output.getHasSubtypes() && output.getItemDamage() != out.getItemDamage()) return false;
		if (!ItemStack.areItemStackTagsEqual(output.get(0), out)) return false;
		return true;
	}
	
	public String toString() {
		String returnString = "";

		returnString += "Smelting Recipe:\n";
		if (!input.isEmpty())
			returnString += input.get(0).getDisplayName();
		
		if (output != null) {
			returnString += " ==> ";
			if (output.get(0).stackSize > 1) returnString += output.get(0).stackSize + "x ";
			returnString += output.get(0).getDisplayName();
		}
		
		return returnString;
	}
	
	/*
	 * Methods used by GuiRecipeCrafting
	 */
	private int xPos, yPos, page;
	private List<IRecipeSlot> slots;
//	IRecipeSlot fuelSlot;
//	IRecipeSlot inputSlot;
//	IRecipeSlot outputSlot;
	@Override public String title() { return Lang.local("container.furnace"); }
	@Override public ItemStack tabIcon() { return new ItemStack(Blocks.furnace); }
	@Override public int page() { return this.page; }
	@Override
	public void setVars(Minecraft mc, int x, int y, int w, int h, int index) {
		this.mc = mc;
		int slot = 0;
		this.xPos = x;
		this.yPos = y;
		this.page = index/2;
		this.width = w;
		this.height = h;

		this.xPos += 29;
		this.yPos += 20 + (index%2)*70;
//		title = I18n.format("container.furnace", new Object[0]);
		this.slots = new ArrayList();
		slots.add(new RecipeSlot(slot++, input,  xPos+28, yPos+2 )); // input
		slots.add(new RecipeSlot(slot++, output, xPos+88, yPos+20)); // output
		slots.add(new RecipeSlot(slot++, fuel,   xPos+28, yPos+38)); // fuel
		initGui();
	}
	
	public void initGui() { }

//	private long time = System.currentTimeMillis();
//	private int index = 0;
	@Override
	public IRecipeSlot draw(GuiContainer gui, int mouseX, int mouseY, int index) {
//		String title = "";
//		if (type == CRAFTING) title = I18n.format("container.crafting", new Object[0]) + (shapeless?" (Shapeless)":"");
//		if (type == SMELTING) title = I18n.format("container.furnace", new Object[0]);
//
//		long t = System.currentTimeMillis();
//		if (t - time > 1000) {
//			index = (index+1)%Integer.MAX_VALUE;
//			time = t;
//		}

		IRecipeSlot theSlot = null;
		this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/furnace.png")); 
		this.drawTexturedModalRect(xPos, yPos, 28,15, 117,56);
		int j = Math.min(13, (index%13));
		this.drawTexturedModalRect(this.xPos+29,this.yPos+22+j, 176,j, 15, 15-j);
		
		j = Math.min(22, (index%13)*2-1);
		this.drawTexturedModalRect(xPos+52, yPos+20, 177,14, j, 20);
		for (IRecipeSlot slot : slots) {
			if (slot.isMouseOverSlot(mouseX, mouseY)) {
				theSlot = slot;
				this.drawGradientRect(slot.xPos(), slot.yPos(), slot.xPos()+16, slot.yPos()+16, 0x66FFFFFF, 0x66FFFFFF);
			}
			ItemStack is = slot.getItems().get((index/5)%slot.getItems().size());
			String size = is.stackSize>1 ? is.stackSize+"" : "";
			if (is != null) drawItemStack(is, slot.xPos(), slot.yPos(), size);
		}

//		ItemStack is = inputSlot.getItems().get(0);
//		if ( mouseX >= inputSlot.xPos() && mouseX <= inputSlot.xPos()+16 && mouseY >= inputSlot.yPos() && mouseY <= inputSlot.yPos()+16 ) {
//			theSlot = inputSlot;
//			this.drawGradientRect(inputSlot.xPos(), inputSlot.yPos(), inputSlot.xPos()+16, inputSlot.yPos()+16, 0x66FFFFFF, 0x66FFFFFF);
//		}
//		String size = is.stackSize>1 ? is.stackSize+"" : "";
//		if (is != null) drawItemStack(is, inputSlot.xPos(), inputSlot.yPos(), size);
////		drawItemStack(new ItemStack(Items.coal), inputSlotS.xPos, inputSlotS.yPos+36, "");
//        
//		is = outputSlot.getItems().get(0);
//		if ( mouseX >= outputSlot.xPos() && mouseX <= outputSlot.xPos()+16 && mouseY >= outputSlot.yPos() && mouseY <= outputSlot.yPos()+16 ) {
//			theSlot = outputSlot;
//			this.drawGradientRect(outputSlot.xPos(), outputSlot.yPos(), outputSlot.xPos()+16, outputSlot.yPos()+16, 0x66FFFFFF, 0x66FFFFFF);
//		}
//		size = is.stackSize>1 ? is.stackSize+"" : "";
//		if (is != null) drawItemStack(is, outputSlot.xPos(), outputSlot.yPos(), size);

		return theSlot;
	}

	private void drawItemStack(ItemStack itemstack, int xPos, int yPos, String count) {
        FontRenderer font = null;
        if (itemstack != null) font = itemstack.getItem().getFontRenderer(itemstack);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(font, mc.getTextureManager(), itemstack, xPos, yPos);
        itemRender.renderItemOverlayIntoGUI(font, mc.getTextureManager(), itemstack, xPos, yPos, count);
    }
}
