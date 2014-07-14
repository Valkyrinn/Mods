package mods.horsehud.client.gui.infobox;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class InfoBoxHighlight {
	private MovingObjectPosition target;
	private String title, subTitle, modName;
	private int health, maxHealth, armor;
	private boolean isHealthNumerical, isArmorNumerical;
	private List<String> infoList;
	private ItemStack displayStack;
	
	public void clear() {
		target = null;
		title = subTitle = modName = null;
		health = maxHealth = armor = 0;
		isHealthNumerical = isArmorNumerical = false;
		infoList = new ArrayList();
		displayStack = null;
	}

	public String       getTitle()             { return title;             }
	public String       getSubTitle()          { return subTitle;          }
	public String       getModName()           { return modName;           }
	public int          getHealth()            { return health;            }
	public int          getMaxHealth()         { return maxHealth;         }
	public int          getArmor()             { return armor;             }
	public boolean      getIsHealthNumerical() { return isHealthNumerical; }
	public boolean      getIsArmorNumerical()  { return isArmorNumerical;  }
	public List<String> getInfoList()          { return infoList;          }
	public ItemStack    getDisplayStack()      { return displayStack;      }
	public MovingObjectPosition getTarget()    { return target;            }
	
	public void setTitle(String title)              { this.title = title;           }
	public void setSubTitle(String subTitle)        { this.subTitle = subTitle;     }
	public void setModName(String modName)          { this.modName = modName;       }
	public void setHealth(int health)               { this.health = health;         }
	public void setMaxHealth(int maxHealth)         { this.maxHealth = maxHealth;   }
	public void setArmor(int armor)                 { this.armor = armor;           }
	public void setIsHealthNumerical(boolean ihn)   { this.isHealthNumerical = ihn; }
	public void setIsArmorNumerical(boolean ian)    { this.isArmorNumerical = ian;  }
	public void setInfoList(List<String> infoList)  { this.infoList = infoList;     }
	public void addAdditionalInfo(String info)      { this.infoList.add(info);      }
	public void setDisplayStack(ItemStack stack)    { this.displayStack = stack;    }
	public void setTarget(MovingObjectPosition mop) { this.target = mop;            }

	public List<String> formatHead() {
		List<String> highlight = new ArrayList();
		
		if (getTitle() != null)    highlight.add(getTitle());
		if (getSubTitle() != null) highlight.add(getSubTitle());
		
		return highlight;
	}
	
	public List<String> formatBody() {
		List<String> highlight = new ArrayList();

		if (getHealth() > 0) highlight.add(String.format("%s: %d/%d", getIsHealthNumerical()?"Health":"[HealthIcons]", getHealth(), getMaxHealth()));
		if (getArmor() > 0)  highlight.add(String.format("%s: %d",    getIsArmorNumerical() ?"Armor" :"[ArmorIcons]",  getArmor()));
		
		return highlight;
	}
	
	public List<String> formatFoot() {
		List<String> highlight = new ArrayList();

		if (getInfoList() != null) highlight.addAll(getInfoList());
		if (getModName() != null)  highlight.add(getModName());
		
		
		return highlight;
	}
}
