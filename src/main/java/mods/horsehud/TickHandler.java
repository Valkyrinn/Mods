/* File: mods/horsehud/TickHandler.java
 * Module: InfoBox
 */
package mods.horsehud;

import mods.horsehud.client.gui.infobox.DataAccessor;
import mods.horsehud.client.gui.infobox.InfoBoxHighlight;
import mods.horsehud.client.gui.infobox.RayTrace;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHandler {
	private Minecraft mc = Minecraft.getMinecraft();
	private InfoBoxHighlight highlight;

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END ) {
			RayTrace rt = RayTrace.instance();
			rt.trace();
			if (rt.getTarget() != null) {
				DataAccessor acc = DataAccessor.instance;
			}
			// Fire raytrace
			// if raytrace has target {
			//   get accessor
			//   set accessor
			//   get target stack
			//   if stack !null {
			//     clear tip
			//     set tip
			//   }
			// }
		}
	}
}
