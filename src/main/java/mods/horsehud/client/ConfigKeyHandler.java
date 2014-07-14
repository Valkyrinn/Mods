package mods.horsehud.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import scala.reflect.internal.Trees.This;

import mods.horsehud.HorseHUD;
import mods.horsehud.client.gui.ConfigScreen;
import mods.horsehud.client.gui.infobox.GuiInfoBox;
import mods.horsehud.client.gui.itembox.GuiItemDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.Slot;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConfigKeyHandler {

	public final static Map<String, KeyBinding> keyList = new HashMap();
	
	public ConfigKeyHandler() {
//		keyList.put("hide", new KeyBinding("Toggle Tooltips",    Keyboard.KEY_NUMPAD0, "key.horsehud.config"));
//		keyList.put("fluid",new KeyBinding("Toggle Fluids",      Keyboard.KEY_NUMPAD1, "key.horsehud.config"));
//		keyList.put("meta", new KeyBinding("Toggle ID/Meta",     Keyboard.KEY_NUMPAD2, "key.horsehud.config"));
//		keyList.put("mod",  new KeyBinding("Toggle Mod Name",    Keyboard.KEY_NUMPAD3, "key.horsehud.config"));
//		keyList.put("mine", new KeyBinding("Cycle Mining Tip",   Keyboard.KEY_NUMPAD4, "key.horsehud.config"));
		keyList.put("infobar.key.menu", new KeyBinding(Lang.local(GuiInfoBox.CATEGORY + ".key.menu"), Keyboard.KEY_H, HorseHUD.NAME));
		keyList.put("itemdisplay.key.visible", new KeyBinding(Lang.local(GuiItemDisplay.CATEGORY + ".key.visible"), Keyboard.KEY_O, HorseHUD.NAME));
		keyList.put("itemdisplay.key.recipe",  new KeyBinding(Lang.local(GuiItemDisplay.CATEGORY + ".key.recipe"),  Keyboard.KEY_R, HorseHUD.NAME));
		keyList.put("itemdisplay.key.usage",   new KeyBinding(Lang.local(GuiItemDisplay.CATEGORY + ".key.usage"),   Keyboard.KEY_U, HorseHUD.NAME));
		
		for (Entry<String, KeyBinding> k : keyList.entrySet()) ClientRegistry.registerKeyBinding(k.getValue());
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		
		if (mc.currentScreen != null) return;
		
//		if (this.keyList.get("hide").isPressed()) {
//			System.out.println("Toggle Tooltip Visibility");
//			ConfigHandler.instance().toggleVisibility();
//		}
//		
//		if (this.keyList.get("fluid").isPressed()) {
//			System.out.println("Toggle Fluid Visibility");
//			ConfigHandler.instance().toggleFluidVisibility();
//		}
//
//		if (this.keyList.get("meta").isPressed()) {
//			System.out.println("Toggle ID:Meta Visibility");
//			ConfigHandler.instance().toggleMetaVisibility();
//		}
//
//		if (this.keyList.get("mod").isPressed()) {
//			System.out.println("Toggle Mod Name Visibility");
//			ConfigHandler.instance().toggleModVisibility();
//		}
//
//		if (this.keyList.get("mine").isPressed()) {
//			System.out.println("Cycle Mining Tip Visibility");
//			ConfigHandler.instance().cycleMiningTipVisibility();
//		}

		if (this.keyList.get("infobar.key.menu").isPressed()) {
//			System.out.println("Config Screen! ");
			mc.displayGuiScreen(new ConfigScreen(mc.currentScreen));
		}
	}
}
