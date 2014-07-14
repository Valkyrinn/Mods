package mods.horsehud.client;

import java.awt.Point;

import mods.horsehud.api.IConfigOption;
import mods.horsehud.api.Type;
import net.minecraftforge.common.config.Configuration;

public abstract class AConfigOption implements IConfigOption {
	protected String category, key;
	protected int count;
	protected Object value, defaultValue;
	protected Type type;
	protected boolean isConfigurable;
	protected Configuration cfg;

	public AConfigOption(String category, String key, Object defaultValue, int count, boolean isConfigurable, Configuration cfg) {
		this.category = category;
		this.key = key;
		this.value = defaultValue;
		this.defaultValue = defaultValue;
		this.count = count;
		this.isConfigurable = isConfigurable;
		this.cfg = cfg;

		if (defaultValue instanceof String) type = Type.STRING;
		else if (defaultValue instanceof Boolean) type = Type.BOOLEAN;
		else if (defaultValue instanceof Integer) type = Type.INTEGER;
		else if (defaultValue instanceof Double)  type = Type.DOUBLE;
		else if (defaultValue instanceof Point)   type = Type.POINT;
		else type = Type.OTHER;
		
	}
	
	@Override public String        category() { return this.category; }
	@Override public String             key() { return this.key; }
	@Override public Object           value() { return this.value; }
	@Override public String  formattedValue() { return category + "." + key + "_" + value().toString(); }
	@Override public Object    defaultValue() { return this.defaultValue; }
	@Override public int              count() { return this.count; }
	@Override public Type              type() { return this.type; }
	@Override public boolean isConfigurable() { return this.isConfigurable; }
	@Override public void setConfigurable(boolean b) { this.isConfigurable = b; }

	@Override
	public void   set(Object value) { 
		if (value == null) return;
		switch (type) {
			case STRING  : if (value instanceof String ) this.value = value; break;
			case BOOLEAN : if (value instanceof Boolean) this.value = value; break;
			case INTEGER : if (value instanceof Integer) this.value = value; break;
			case DOUBLE  : if (value instanceof Double)  this.value = value; break;
			case POINT   : if (value instanceof Point  ) this.value = value; break;

			default: this.value = value;
		}

		if (cfg != null)
		switch (type) {
			case STRING:  cfg.get(category, key, (String)  defaultValue).set((String)  value); break;
			case BOOLEAN: cfg.get(category, key, (Boolean) defaultValue).set((Boolean) value); break;
			case INTEGER: cfg.get(category, key, (Integer) defaultValue).set((Integer) value); break;
			case DOUBLE:  cfg.get(category, key, (Double)  defaultValue).set((Double)  value); break;
			case POINT:
				if (value instanceof Point) {
					int[]   dxy = {((Point)defaultValue).x,    ((Point)defaultValue).y};
					String[] xy = {((Point)       value).x+"", ((Point)       value).y+""};
					cfg.get(category, key, dxy).set(xy);
//					cfg.get(category, key+"X", ((Point)defaultValue).x).set(((Point)value).x);
//					cfg.get(category, key+"Y", ((Point)defaultValue).y).set(((Point)value).y);
				}
			default: break;
		}
		
		configure();
	}
	
	@Override public abstract void configure();
}
