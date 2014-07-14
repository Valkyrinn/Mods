package mods.horsehud.client;

import net.minecraft.client.resources.I18n;

public class Lang {
	public static String local(String key) {
		return I18n.format(key, new Object[0]);
	}
}
