package mods.horsehud.client.gui.infobox;

import java.util.ArrayList;
import java.util.List;

import mods.horsehud.api.IConfigHandler;
import mods.horsehud.api.IDataAccessor;
import mods.horsehud.api.IDataProvider;
import mods.horsehud.api.Keys;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DataProvider implements IDataProvider {

//    public static final String C_FLUID = GuiInfoBar.C_FLUID, C_META = GuiInfoBar.C_META, C_MINE = GuiInfoBar.C_MINE;
	public static boolean getConfigBool(Object key) { return GuiInfoBox.getConfigBool(key); }
	public static int     getConfigInt( Object key) { return GuiInfoBox.getConfigInt(key); }


	/*
	 * Block Handling
	 */
	@Override
	public ItemStack getItemStack(ItemStack is, IDataAccessor acc, IConfigHandler ich) {
		if (acc.isEntityLiving()) return null;

//		if (acc.getTileEntity() != null && (System.currentTimeMillis() - acc.getTimeLastUpdate() >= 250)) {
//			acc.setTimeLastUpdate(System.currentTimeMillis());
//			NBTTagCompound compound = new NBTTagCompound();
////			ByteBufUtils
//		}
		ItemStack ret = null;
		
		try {
			if (acc.getEntity() != null) ret = acc.getPickedBlock();//new ItemStack(acc.getBlock(), 1, acc.getMetadata());
			if (ret != null) return ret;
		} catch (Exception e) {}
		
		try {
			ret = new ItemStack(acc.getBlock(), 1, acc.getMetadata());
			ret.getDisplayName();
			if (ret != null) return ret;
		} catch (Exception e) {}

		try {
			ret = acc.getPickedBlock();
			if (ret != null) return ret;
		} catch (Exception e) {}

		try {
			ret = acc.getDropped();
			if (ret != null) return ret;
		} catch (Exception e) {}
		
		try {
			ret = acc.getShearable();
			if (ret != null) return ret;
		} catch (Exception e) {}
		
		return new ItemStack(acc.getBlock(), 1, acc.getMetadata());

//		if (acc.getEntity() != null) return acc.getPickedBlock();
//
//		if (acc.getTileEntity() != null)
//			ret = acc.getPickedBlock();
//
//		// For some reason ret.hasDisplayName() isn't accurate
//		try { ret.getDisplayName(); } 
//		catch (NullPointerException e) { 
//			try { ret = acc.getPickedBlock(); }
//			catch (Exception ex) { ret = null; } 
//		}
//		
//		return ret;


		// tileentity
		// pickblock
		// getblockdropped
		// IShearable
		// else mouseoverblock

	}
	
	@Override
	public String getTitle(ItemStack is, IDataAccessor acc, IConfigHandler ich, String currentTitle) {
		Entity en = acc.getEntity();
		if (acc.isEntityLiving()) return EnumChatFormatting.BOLD + en.getCommandSenderName();
		else if (en != null) try { return EnumChatFormatting.BOLD + en.getPickedResult(acc.getPosition()).getDisplayName(); } catch (NullPointerException e) { return EnumChatFormatting.BOLD + en.getCommandSenderName(); }//return "Unhandled NullPointerException"; }

//		ItemStack is = getItemStack(acc, ich, null);
		if (acc.getTileEntity() != null) try { return EnumChatFormatting.BOLD + acc.getPickedBlock().getDisplayName(); } catch (NullPointerException e) { }//return "Unhandled NullPointerException"; }
		if (is == null) return EnumChatFormatting.BOLD + "<Error>";

		if (getConfigBool(Keys.C_FLUID) && acc.getFluid() != null && acc.getBlock() == acc.getFluid()) {
			String t = EnumChatFormatting.BOLD + is.getDisplayName();
			if (acc.getFluid() == Blocks.lava) t = EnumChatFormatting.RED + t; else t = EnumChatFormatting.DARK_AQUA + t;
			return t;
	    }

		try { return EnumChatFormatting.BOLD + is.getDisplayName(); } catch (NullPointerException e) { return EnumChatFormatting.BOLD + acc.getBlock().getUnlocalizedName(); }
	}

	@Override
	public String getSubTitle(ItemStack is, IDataAccessor acc, IConfigHandler ich, String currentSubTitle) {
		if (acc.isEntityLiving())
			if (acc.getEntityLiving().hasCustomNameTag()) 
				return StatCollector.translateToLocal("entity." + EntityList.getEntityString(acc.getEntityLiving()) + ".name");
		if (getConfigBool(Keys.C_META) && acc.getEntity() == null)
			return "ID: " + acc.getBlockID() + ":" + acc.getMetadata();
		return "";
	}
	
	@Override
	public List<String> getInfo(ItemStack is, IDataAccessor acc, IConfigHandler ich, List<String> currentInfo) {
		List infoList = new ArrayList();
		
	    if (getConfigBool(Keys.C_FLUID) && acc.getFluid() != null && acc.getBlock() != acc.getFluid()) {
	    	String i = EnumChatFormatting.ITALIC + acc.getFluid().getLocalizedName();
	    	if (acc.getFluid() == Blocks.lava) i = EnumChatFormatting.DARK_RED + i; else i = EnumChatFormatting.DARK_AQUA + i;
 			infoList.add(i);
	    }
	    
		if (getConfigInt(Keys.C_MINE) != 0 && !acc.isEntityLiving())
    	if (getTitle(is, acc, ich, "").contains("Ore") || getConfigInt(Keys.C_MINE) == 1) {
    		if (acc.getBlock() == acc.getFluid()) { infoList.add((new ItemStack(Items.bucket)).getDisplayName()); return infoList; }
    		if (acc.getEntity() != null) { infoList.add("Any Sword"); return infoList; }
    		if (acc.getShearable() != null) { infoList.add(new ItemStack(Items.shears).getDisplayName()); return infoList; }
//    	    ItemStack is = getItemStack(acc, ich, null);
    		String[] t = { "Any", "Stone", "Iron", "Diamond" };
    		int tool = acc.getBlock().getHarvestLevel(is.getItemDamage());
    		if (tool < 0) tool = 0;
    		String tname = acc.getBlock().getHarvestTool(acc.getMetadata());
    		if (tname == null && acc.getBlock().getMaterial() == Material.rock) tname = "pickaxe";
    		if (tname == null) tname = "Tool"; else tname = tname.substring(0,1).toUpperCase() + tname.substring(1);
    		infoList.add(t[tool] + " " + tname);
    	}
		
		TileEntity te = acc.getTileEntity();
		if (te != null) {
		NBTTagCompound tag = new NBTTagCompound();//acc.getNBTData();//is.stackTagCompound;
//		is.writeToNBT(tag);
		te.writeToNBT(tag);
		if (tag != null)
			for (Object o : tag.func_150296_c()) {
//				infoList.add(o.toString() + ": " + tag.getTag(o.toString()));
			}
		}
		
		Entity e = acc.getEntity();
		if (e != null) {
			NBTTagCompound tag = new NBTTagCompound();
			e.writeToNBT(tag);
			if (tag != null) {
				String tags = "";
				for (Object o : tag.func_150296_c()) {
//					tags += o.toString() + ", ";
//					if (tags.length() > 55) {
//						infoList.add(tags);
//						tags = "";
//					}
					
					int w = 75;
					String s = o.toString() + ": " + tag.getTag(o.toString());
					int j = (int) Math.ceil(s.length()/w);
					String[] ss = new String[j];
					for (int i=0; i<=j; i++) {
						int k = Math.min((i+1)*w, s.length());
//						infoList.add(s.substring(i*w, k));
					}
//					String[] ss = s.split(",");
//					for (int i=0; i<ss.length; i++)
//						infoList.add(ss[i]);
//					infoList.add(o.toString() + ": " + tag.getTag(o.toString()));
				}
//				infoList.add(tags);
			}
		}

		return infoList;
	}

	@Override
	public int getHealth(IDataAccessor acc, IConfigHandler ich, int currentHealth) {
		int health = 0;
		
		if (acc.isEntityLiving())
			health = (int)acc.getEntityLiving().getHealth();
		
		return health;
	}

	@Override
	public int getMaxHealth(IDataAccessor acc, IConfigHandler ich, int currentHealth) {
		int maxHealth = 0;
		
		if (acc.isEntityLiving())
			maxHealth = (int)acc.getEntityLiving().getMaxHealth();
		
		return maxHealth;
	}

	@Override
	public int getArmor(IDataAccessor acc, IConfigHandler ich, int currentArmor) {
		int armor = 0;

		if (acc.isEntityLiving()) {
			EntityLiving entityLiving = acc.getEntityLiving();
			for (int i=0; i<4; i++) 
				try {armor += ((ItemArmor)entityLiving.getEquipmentInSlot(i).getItem()).getArmorMaterial().getDamageReductionAmount(i); } catch (Exception e) {}
		}

		return armor;
	}
	
	@Override
	public String getModName(ItemStack is, IDataAccessor acc, IConfigHandler ich, String currentModName) {
		return (acc.getEntity() != null) ? ich.getModName(acc.getEntity()) : ich.getModName(is);
	}

	@Override
	public void register(IConfigHandler ich) { }
}