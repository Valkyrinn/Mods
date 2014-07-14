package mods.horsehud.api;

import net.minecraftforge.common.config.Configuration;

public interface IConfigOption {
	public String        category();
	public String             key();
	public Object           value();
	public String  formattedValue();
	public Object    defaultValue();
	public int              count();
	public Type              type();
	public boolean isConfigurable();
	public void   setConfigurable(boolean b);

	public void set(Object value);
	public void configure();

}
