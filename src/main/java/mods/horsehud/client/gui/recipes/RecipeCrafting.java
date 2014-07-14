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
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class RecipeCrafting extends GuiScreen implements IDisplayRecipe {
	ArrayList<ItemStack>[][] input = null;
	ArrayList<ItemStack> output;
	boolean shapeless = false;
	
	public RecipeCrafting(ItemStack out, boolean shapeless) {
		this.output = new ArrayList();
		this.output.add(out);
		this.shapeless = shapeless;
		this.mc = Minecraft.getMinecraft();
		this.fontRendererObj = this.mc.fontRenderer;
	}
	public RecipeCrafting(ShapelessOreRecipe recipe) {
		this(recipe.getRecipeOutput(), true);
		ArrayList<Object> in = recipe.getInput();
		int size = 3;//(in.size()>4)?3:2;
		input = new ArrayList[size][size];
//		System.out.println("ShapelessOreRecipe for " + recipe.getRecipeOutput().getDisplayName());
		
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				input[i][j] = new ArrayList();
				int k = i*size + j;
				if (k < in.size()) {
					if (in.get(k) instanceof ArrayList)
						input[i][j] = ((ArrayList<ItemStack>)in.get(k));
					else if (in.get(k) instanceof ItemStack)
						input[i][j].add((ItemStack)in.get(k));
				}
			}
		}
	}
	public RecipeCrafting(ShapedOreRecipe recipe) {
		this(recipe.getRecipeOutput(), false);
		Object[] in = recipe.getInput();
		int width = ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, recipe, "width");
		int height = ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, recipe, "height");
		int size = 3;//(width <= 2 && height <= 2)? 2 : 3;
		input = new ArrayList[size][size];
//		System.out.println("ShapedOreRecipe for " + recipe.getRecipeOutput().getDisplayName());

		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				input[i][j] = new ArrayList();
				if (i >= height || j >= width) continue;
				int k = i*width + j;
				if (k < in.length){
					if (in[k] instanceof ArrayList) {
						input[i][j] = ((ArrayList<ItemStack>)in[k]);
					} else if (in[k] instanceof ItemStack)
						input[i][j].add((ItemStack)in[k]);
				}
			}
		}
	}
	public RecipeCrafting(ShapelessRecipes recipe) {
		this(recipe.getRecipeOutput(), true);
		List<ItemStack> in = recipe.recipeItems;
		int size = 3;//(in.size()>4)?3:2;
		input = new ArrayList[size][size];
//		System.out.println("ShapelessRecipes for " + recipe.getRecipeOutput().getDisplayName());

		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				input[i][j] = new ArrayList();
				int k = i*size + j;
				if (k < in.size()) 
					input[i][j].add(in.get(k));
			}
		}
	}
	public RecipeCrafting(ShapedRecipes recipe) {
		this(recipe.getRecipeOutput(), false);
		ItemStack[] items = ((ShapedRecipes)recipe).recipeItems;
		int width = ((ShapedRecipes)recipe).recipeWidth;
		int height = ((ShapedRecipes)recipe).recipeHeight;
		int size = 3;//(width <= 2 && height <= 2) ? 2 : 3;
		input = new ArrayList[size][size];
//		System.out.println("ShapedRecipes for " + recipe.getRecipeOutput().getDisplayName());

		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				input[i][j] = new ArrayList();
				if (i>=height || j>=width) continue;
				int k = i*width + j;
				if (k < items.length) input[i][j].add(items[k]);
			}
		}
	}
	
	@Override public Object     output() { return this.output; }
	@Override public Object      input() { return input; }
	
	public boolean contains(ItemStack in) {
		if (in == null) return false;
		for (int i=0; i<input.length; i++)
			for (int j=0; j<input[i].length; j++) {
				if (input[i][j] != null) {
					for (ItemStack is : input[i][j]) {
						if (is == null) continue;
						// For some reason, damage values on some itemstacks is 32767 instead of zero. I don't know if this is
						// an error on my side or Forge or what, but this line corrects that if encountered
						if (is.getItemDamage() == Short.MAX_VALUE) is.setItemDamage(0);
						if (is.isItemEqual(in)) return true;
					}
				}
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

		returnString += "Crafting Recipe:";
		if (shapeless) returnString += " (Shapeless)";
		for (int i=0; i<3; i++) {
			returnString += "\n";
			if (i != 0) { for (int k = 0; k<(input.length*31); k++) returnString += "-"; returnString += "\n"; }
			for (int j=0; j<3; j++) {
				if (j != 0) returnString += "|";
				String name = (!input[i][j].isEmpty())? input[i][j].get(0).getDisplayName() : "";

				for (int k=0; k<Math.floor((30.0D-name.length())/2); k++) returnString += " ";
				returnString += name;
				for (int k=0; k<Math.ceil((30.0D-name.length())/2); k++) returnString += " ";
			}
		}
		
		if (!output.isEmpty()) {
			returnString += " ==> ";
			for (ItemStack is : output) {
				if (is.stackSize > 1) returnString += is.stackSize + "x ";
				returnString += is.getDisplayName();
			}
		}
		
		return returnString;
	}
	
	/*
	 * Methods used by GuiRecipeCrafting
	 */
	int xPos, yPos, page;
	IRecipeSlot[][] inputSlotC;
	IRecipeSlot inputSlotS;
	IRecipeSlot outputSlot;
	String title;
	@Override public String title() { return Lang.local("container.crafting"); }
	@Override public ItemStack tabIcon() { return new ItemStack(Blocks.crafting_table); }
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
//		title = I18n.format("container.crafting", new Object[0]) + (shapeless?" (Shapeless)":"");
		int ix = input.length;
		int iy = input[0].length;
		this.inputSlotC = new RecipeSlot[ix][iy];
		for (int i=0; i<ix; i++) {
			for (int j=0; j<iy; j++) {
				inputSlotC[i][j] = new RecipeSlot(slot++, input[i][j], xPos+2+j*18,yPos+2+i*18);
			}
		}
		this.outputSlot = new RecipeSlot(slot++, output, xPos+96,yPos+20);
		initGui();
//		System.out.println(toString());
	}
	
	public void initGui() { }

//	private long time = System.currentTimeMillis();
//	private int index = 0;
	@Override
	public IRecipeSlot draw(GuiContainer gui, int mouseX, int mouseY, int index) {
//		String title = "";
//		if (type == CRAFTING) title = I18n.format("container.crafting", new Object[0]) + (shapeless?" (Shapeless)":"");
//		if (type == SMELTING) title = I18n.format("container.furnace", new Object[0]);

//		long t = System.currentTimeMillis();
//		if (t - time > 1000) index++;

		IRecipeSlot theSlot = null;
		this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/crafting_table.png")); 
		this.drawTexturedModalRect(xPos, yPos, 28,15, 117,56);
        for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				IRecipeSlot slot = inputSlotC[i][j];
				List<ItemStack> slotList = slot.getItems();
//				if (slotList == null) continue;
				if (slotList.isEmpty()) continue;
				int s = (slotList.size()>0) ? index%slotList.size() : 0;
				ItemStack is = slotList.get(s);
				if ( mouseX >= slot.xPos() && mouseX <= slot.xPos()+16 && mouseY >= slot.yPos() && mouseY <= slot.yPos()+16 ) {
					theSlot = slot;
					drawGradientRect(slot.xPos(), slot.yPos(), slot.xPos()+16, slot.yPos()+16, 0x66FFFFFF, 0x66FFFFFF);
				}
				if (is != null) drawItemStack(is, slot.xPos(), slot.yPos(), "");
			}
			if (shapeless) {
				String title = "(Shapeless)";
				int w = fontRendererObj.getStringWidth(title);
		        fontRendererObj.drawString(title, xPos+(117-w)/2+30, yPos+45, 0x404040);
			}
			if (gui instanceof GuiRecipeDisplay) {
				GuiRecipeDisplay grd = (GuiRecipeDisplay)gui;
				if (grd.parent instanceof GuiCrafting)
					fontRendererObj.drawString("!!!", xPos-15, yPos+25, 0x404040);
			}
		}
        
		List<ItemStack> slotList = outputSlot.getItems();
		int s = (slotList.size()>0) ? index%slotList.size() : 0;
		ItemStack is = slotList.get(s);
		if ( mouseX >= outputSlot.xPos() && mouseX <= outputSlot.xPos()+16 && mouseY >= outputSlot.yPos() && mouseY <= outputSlot.yPos()+16 ) {
			theSlot = outputSlot;
			this.drawGradientRect(outputSlot.xPos(), outputSlot.yPos(), outputSlot.xPos()+16, outputSlot.yPos()+16, 0x66FFFFFF, 0x66FFFFFF);
		}
		String size = is.stackSize>1 ? is.stackSize+"" : "";
		if (is != null) drawItemStack(is, outputSlot.xPos(), outputSlot.yPos(), size);

		return theSlot;
	}

	public void drawItemStack(ItemStack itemstack, int xPos, int yPos, String par4) {
        FontRenderer font = null;
        if (itemstack != null) font = itemstack.getItem().getFontRenderer(itemstack);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(font, mc.getTextureManager(), itemstack, xPos, yPos);
        itemRender.renderItemOverlayIntoGUI(font, mc.getTextureManager(), itemstack, xPos, yPos, par4);
    }
}
