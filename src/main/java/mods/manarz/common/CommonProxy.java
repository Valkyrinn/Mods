package mods.manarz.common;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class CommonProxy {
        
        // Client stuff
        public void registerRenderers() {
                // Nothing here as the server doesn't render graphics!
        }
        
        public int addArmor(String armor) {
        	return 0;
        }
}