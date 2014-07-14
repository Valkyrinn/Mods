package mods.horsehud.client;

import cpw.mods.fml.common.FMLCommonHandler;
import mods.horsehud.client.gui.infobox.GuiInfoBox;
import mods.horsehud.client.gui.itembox.GuiItemDisplay;
import mods.horsehud.common.CommonProxy;

public class ClientProxy extends CommonProxy{

    public void registerHandlers() {
    	FMLCommonHandler.instance().bus().register(new ConfigKeyHandler());
    	GuiInfoBox.init();
    	GuiItemDisplay.init();
//    	ConfigHandler.instance().loadConfigs();

    }

}
