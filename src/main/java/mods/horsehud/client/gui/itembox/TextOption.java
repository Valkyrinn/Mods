package mods.horsehud.client.gui.itembox;

import java.util.LinkedHashMap;

import mods.horsehud.api.IConfigOption;
import mods.horsehud.api.Type;
import mods.horsehud.client.Lang;
import mods.horsehud.client.gui.itembox.GuiOptionsPanel.OptionContainer;
import net.minecraft.client.Minecraft;

public class TextOption {
	private Minecraft mc;
	public String category, key;//, value;
	private Object[] values;
	protected int xPos, yPos, width, height;
	public int optionCount;
	public LinkedHashMap<String, String> labels;
	public Type type;
	protected IConfigOption config;
	protected OptionContainer cont;
	
	public TextOption(Minecraft mc, IConfigOption config, int xPos, int yPos, int width, OptionContainer cont) {
		this.mc = mc;
		this.config = config;
		this.category = config.category();
		this.key = config.key();
		this.type = config.type();
		this.optionCount = config.count();
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = (optionCount+1)*10;
		this.cont = cont;
		
		getLabels();
	}
	
	protected void getLabels() {
		labels = new LinkedHashMap();
		labels.put(key(), Lang.local(key()));

		values = new Object[optionCount];
		if (type == Type.BOOLEAN) {
			values[0] = true;
			values[1] = false;
		} else {
			for (int i=0; i<optionCount; i++)
				values[i] = i;
		}
		for (int i=0; i<optionCount; i++)
			labels.put(key() + "_" + values[i].toString(), Lang.local(key() + "_" + values[i].toString()));
		
	}
	
	public void set(Object value) {
		this.config.set(value);
	}
	
	public String key() { return category + "." + key; }
	public boolean isSelected(String k) { return k.equalsIgnoreCase(config.formattedValue()); }
	
	public boolean isMouseOver(int mouseX, int mouseY) {
		if (cont != null) return (mouseX >= cont.xPos + xPos && mouseX <= cont.xPos+xPos+width && mouseY > cont.yPos+yPos && mouseY < cont.yPos+yPos + height);
		else              return (mouseX >=             xPos && mouseX <=           xPos+width && mouseY >           yPos && mouseY <           yPos + height);
	}
	
	public Object getValueAt(int mouseX, int mouseY) {
		int y;
		if (cont != null) y = (mouseY - cont.yPos-yPos)/10 - 1;
		else              y = (mouseY -           yPos)/10 - 1;
		
		if (y >= 0 && y < optionCount)
			return values[y];
		return null;
	}
	public String getKeyAt(int mouseX, int mouseY) {
//		int y = (mouseY - cont.yPos-yPos)/10;
		
		String retString = key();
		Object value = getValueAt(mouseX, mouseY);
		if (value != null) retString += "_" + value.toString();
		return retString;
	}
}
