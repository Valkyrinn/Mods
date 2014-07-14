package mods.mechanica;

import mods.mechanica.handlers.ConfigHandler;
import mods.mechanica.proxy.IProxy;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Mechanica.MODID, name = Mechanica.MODNAME, version = Mechanica.VERSION)
public class Mechanica {

	public static final String 
		MODID = "mechanica",
		MODNAME = "Mechanica",
		VERSION = "1.7.2-0.0.1",
		CLIENT_PROXY = "mods.mechanica.proxy.ClientProxy",
		SERVER_PROXY = "mods.mechanica.proxy.ServerProxy";
	
	@Instance(MODID)
	public static Mechanica instance;
	
	@SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
	public static IProxy proxy;
	
	public static Configuration config = null;
	public static Logger log = LogManager.getLogger();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new ConfigHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
