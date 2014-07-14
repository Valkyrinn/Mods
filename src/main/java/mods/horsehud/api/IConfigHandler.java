package mods.horsehud.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

public interface IConfigHandler {
	void      register(Class c, IDataProvider i);
	String  getModName(Entity en);
	String  getModName(ItemStack is);
	void   setupConfig(String category, Object key, Object defaultValue, int count, boolean isConfigurable, Configuration cfg);
	void     addConfig(String category, Object key, IConfigOption option);
}
