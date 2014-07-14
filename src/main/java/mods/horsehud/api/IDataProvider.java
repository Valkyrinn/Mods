package mods.horsehud.api;

import java.util.List;

import mods.horsehud.client.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IDataProvider {
//	static void  register(IConfigHandler ich);

	ItemStack    getItemStack(ItemStack is, IDataAccessor acc, IConfigHandler ich);
	String       getTitle(    ItemStack is, IDataAccessor acc, IConfigHandler ich, String       currentTitle    );
	String       getSubTitle( ItemStack is, IDataAccessor acc, IConfigHandler ich, String       currentSubTitle );
	List<String> getInfo(     ItemStack is, IDataAccessor acc, IConfigHandler ich, List<String> currentInfo     );
	int          getHealth(                 IDataAccessor acc, IConfigHandler ich, int          currentHealth   );
	int          getMaxHealth(              IDataAccessor acc, IConfigHandler ich, int          currentMaxHealth);
	int          getArmor(                  IDataAccessor acc, IConfigHandler ich, int          currentArmor    );
	String       getModName(  ItemStack is, IDataAccessor acc, IConfigHandler ich, String       currentModName  );
	void         register(                                     IConfigHandler ich);
}
