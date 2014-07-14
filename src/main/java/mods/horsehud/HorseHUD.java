package mods.horsehud;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
//import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;

import mods.horsehud.client.ConfigHandler;
import mods.horsehud.client.gui.infobox.GuiInfoBox;
import mods.horsehud.client.gui.itembox.GuiItemDisplay;
import mods.horsehud.common.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = HorseHUD.ID, name = HorseHUD.NAME, version = HorseHUD.VERSION, canBeDeactivated = true, dependencies = "required-after:Forge@[10.12.1.1060,)")
public class HorseHUD {
    public final static String ID = "HorseHUD";
    public final static String NAME = "HorseHUD";
    public final static String VERSION = "0.2.1.2";
      
    // The instance of the mod that Forge uses.
    @Instance(ID)
    public static HorseHUD  instance;
    public static Logger log = LogManager.getLogger(NAME);//Logger.getLogger("HorseHUD");
    public static Configuration config = null;
    
    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide = "mods.horsehud.client.ClientProxy", serverSide = "mods.horsehud.common.CommonProxy")
    public static CommonProxy proxy;
    
    // Mod entry points
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	config = new Configuration(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
      try {
    	  Field eventBus = FMLModContainer.class.getDeclaredField("eventBus");
    	  eventBus.setAccessible(true);
    	  EventBus FMLbus = (EventBus) eventBus.get(FMLCommonHandler.instance().findContainerFor(this));
    	  FMLbus.register(this);
      } catch(Throwable t) {}
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.registerHandlers();
//    	GuiInfoBar.init();
//    	GuiItemDisplay.init();
//    	ConfigHandler.instance().loadConfigs();
//    	MinecraftForge.EVENT_BUS.register(new GuiInfoBar(Minecraft.getMinecraft()));
//    	MinecraftForge.EVENT_BUS.register(new GuiItemDisplay(Minecraft.getMinecraft()));
    }
}
