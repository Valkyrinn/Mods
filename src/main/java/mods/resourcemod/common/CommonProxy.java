package mods.resourcemod.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
     
        // Client stuff
        public void registerRenderers() {
                // Nothing here as the server doesn't render graphics!
        }

		@Override
		public Object getServerGuiElement(int ID, EntityPlayer player,
				World world, int x, int y, int z) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getClientGuiElement(int ID, EntityPlayer player,
				World world, int x, int y, int z) {
			// TODO Auto-generated method stub
			return null;
		}
}