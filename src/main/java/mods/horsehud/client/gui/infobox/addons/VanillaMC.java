package mods.horsehud.client.gui.infobox.addons;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mods.horsehud.api.IConfigHandler;
import mods.horsehud.api.IDataAccessor;
import mods.horsehud.api.IDataProvider;
import mods.horsehud.client.AConfigOption;
import mods.horsehud.client.Lang;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPotato;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.command.CommandDefaultGameMode;
import net.minecraft.command.CommandDifficulty;
import net.minecraft.command.CommandGameMode;
import net.minecraft.command.CommandTime;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.biome.BiomeCache.Block;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class VanillaMC implements IDataProvider {

	@Override
	public void register(IConfigHandler ich) {
		
		// Entities
		ich.register(EntityHorse.class, this);
		ich.register(EntityItemFrame.class, this);

		// Plants
		ich.register(BlockDoublePlant.class, this);
		ich.register(BlockCarrot.class, this);
		ich.register(BlockCocoa.class, this);
		ich.register(BlockCrops.class, this);
		ich.register(BlockNetherWart.class, this);
		ich.register(BlockPotato.class, this);
		ich.register(BlockStem.class, this);
		ich.register(IGrowable.class, this);
		
		// Redstone
		ich.register(BlockLever.class, this);
		ich.register(BlockRedstoneComparator.class, this);
		ich.register(BlockRedstoneRepeater.class, this);
		ich.register(BlockRedstoneWire.class, this);
		
		// Other Blocks
		ich.register(BlockJukebox.class, this);
		ich.register(BlockMobSpawner.class, this);
		ich.register(BlockSilverfish.class, this);
		ich.register(BlockOldLog.class, this);
		ich.register(BlockFarmland.class, this);
		ich.register(BlockAnvil.class, this);

		AConfigOption option = null;
		String category = "VanillaMC", key;

		key = "gamemode";
			option = new AConfigOption(category, key, GameType.NOT_SET.getID(), 3, true, null){
				@Override
				public Object value() {
					Minecraft mc = Minecraft.getMinecraft();
					WorldSettings.GameType gt = GameType.NOT_SET;
					try { gt = ObfuscationReflectionHelper.getPrivateValue(PlayerControllerMP.class, mc.playerController, "currentGameType"); }
					catch (Exception e) { System.out.println("Error: " + e); }
					return gt.getID();
				}
				@Override
				public boolean isConfigurable() {
					Minecraft mc = Minecraft.getMinecraft();
					EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(mc.thePlayer.getCommandSenderName());
					playerMP.mcServer.getCommandManager().getPossibleCommands(playerMP).contains(new CommandGameMode());
					return true;
				}
				@Override
				public void configure() {
					WorldSettings.GameType gt = WorldSettings.getGameTypeById((Integer)value);
					String name = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
					EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(name);

					player.theItemInWorldManager.setGameType(gt);
					S2BPacketChangeGameState packet = new S2BPacketChangeGameState(3, (float)gt.getID());
					packet.field_149142_a[3] = null;
			        player.playerNetServerHandler.sendPacket(packet);
				}
			};
			ich.addConfig(category, key, option);
		

		key = "defaultgamemode";
			option = new AConfigOption(category, key, GameType.NOT_SET.getID(), 3, true, null){
				@Override
				public Object value() {
					return MinecraftServer.getServer().getGameType().getID();
				}
				@Override
				public boolean isConfigurable() {
					Minecraft mc = Minecraft.getMinecraft();
					EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(mc.thePlayer.getCommandSenderName());
					playerMP.mcServer.getCommandManager().getPossibleCommands(playerMP).contains(new CommandDefaultGameMode());
					return true;
				}
				@Override
				public void configure() {
			        MinecraftServer minecraftserver = MinecraftServer.getServer();
			        GameType gameType = GameType.getByID((Integer)value);
			        minecraftserver.setGameType(gameType);
			        EntityPlayerMP entityplayermp;

			        if (minecraftserver.getForceGamemode()) {
			            for (Iterator iterator = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator(); iterator.hasNext(); entityplayermp.fallDistance = 0.0F) {
			                entityplayermp = (EntityPlayerMP)iterator.next();
			                entityplayermp.setGameType(gameType);
			            }
			        }
				}
			};
			ich.addConfig(category, key, option);
		
		key = "difficulty";
			option = new AConfigOption(category, key, EnumDifficulty.NORMAL.getDifficultyId(), 4, true, null) {
				@Override
				public Object value() {
					this.value = MinecraftServer.getServer().func_147135_j().getDifficultyId();
					return value;
				}
				@Override
				public boolean isConfigurable() {
					Minecraft mc = Minecraft.getMinecraft();
					EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(mc.thePlayer.getCommandSenderName());
					playerMP.mcServer.getCommandManager().getPossibleCommands(playerMP).contains(new CommandDifficulty());
					return true;
				}
				@Override
				public void configure() {
					EnumDifficulty d = EnumDifficulty.getDifficultyEnum((Integer)value);
					MinecraftServer.getServer().func_147139_a(d);
					Minecraft.getMinecraft().gameSettings.difficulty = d;
				}
			};
			ich.addConfig(category, key, option);

			key = "time";
				option = new AConfigOption(category, key, 0, 4, true, null) {
					@Override
					public Object value() {
						return -1;
					}
					@Override
					public boolean isConfigurable() {
						Minecraft mc = Minecraft.getMinecraft();
						EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(mc.thePlayer.getCommandSenderName());
						playerMP.mcServer.getCommandManager().getPossibleCommands(playerMP).contains(new CommandTime());
						return true;
					}
					@Override
					public void configure() {
						long time = ((Integer)value)*6000;
						for (int j = 0; j < MinecraftServer.getServer().worldServers.length; ++j)
				            MinecraftServer.getServer().worldServers[j].setWorldTime(time);
					}
				};
				ich.addConfig(category, key, option);

		key = "gamerule";
			String subKey;

			subKey = "doDaylightCycle";
			option = new AConfigOption(category, key + "." + subKey, true, 2, true, null){
				@Override
				public Object value() {
					GameRules rules = MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
					return rules.getGameRuleBooleanValue("doDaylightCycle");
				}
				@Override
				public boolean isConfigurable() {
					Minecraft mc = Minecraft.getMinecraft();
					EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(mc.thePlayer.getCommandSenderName());
					playerMP.mcServer.getCommandManager().getPossibleCommands(playerMP).contains(new CommandGameMode());
					return true;
				}
				@Override
				public void configure() {
					GameRules rules = MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
					String key = "doDaylightCycle";
		            if (rules.hasRule(key)) {
						rules.setOrCreateGameRule(key, value+"");
//						HorseHUD.instance.log.info(key + " set to " + value);
		            }
					
				}
			};
			ich.addConfig(category, key + "." + subKey, option);

			subKey = "keepInventory";
			option = new AConfigOption(category, key + "." + subKey, false, 2, true, null){
				@Override
				public Object value() {
					GameRules rules = MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
					return rules.getGameRuleBooleanValue("keepInventory");
				}
				@Override
				public boolean isConfigurable() {
					Minecraft mc = Minecraft.getMinecraft();
					EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(mc.thePlayer.getCommandSenderName());
					playerMP.mcServer.getCommandManager().getPossibleCommands(playerMP).contains(new CommandGameMode());
					return true;
				}
				@Override
				public void configure() {
					MinecraftServer.getServer().worldServerForDimension(0).getGameRules().setOrCreateGameRule("keepInventory", value.toString());
				}
			};
			ich.addConfig(category, key + "." + subKey, option);

			subKey = "mobGriefing";
			option = new AConfigOption(category, key + "." + subKey, true, 2, true, null){
				@Override
				public Object value() {
					GameRules rules = MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
					return rules.getGameRuleBooleanValue("mobGriefing");
				}
				@Override
				public boolean isConfigurable() {
					Minecraft mc = Minecraft.getMinecraft();
					EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(mc.thePlayer.getCommandSenderName());
					playerMP.mcServer.getCommandManager().getPossibleCommands(playerMP).contains(new CommandGameMode());
					return true;
				}
				@Override
				public void configure() {
					MinecraftServer.getServer().worldServerForDimension(0).getGameRules().setOrCreateGameRule("mobGriefing", value.toString());
				}
			};
			ich.addConfig(category, key + "." + subKey, option);

			subKey = "naturalRegeneration";
			option = new AConfigOption(category, key + "." + subKey, true, 2, true, null){
				@Override
				public Object value() {
					GameRules rules = MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
					return rules.getGameRuleBooleanValue("naturalRegeneration");
				}
				@Override
				public boolean isConfigurable() {
					Minecraft mc = Minecraft.getMinecraft();
					EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(mc.thePlayer.getCommandSenderName());
					playerMP.mcServer.getCommandManager().getPossibleCommands(playerMP).contains(new CommandGameMode());
					return true;
				}
				@Override
				public void configure() {
					MinecraftServer.getServer().worldServerForDimension(0).getGameRules().setOrCreateGameRule("naturalRegeneration", value.toString());
				}
			};
			ich.addConfig(category, key + "." + subKey, option);

	}

	@Override
	public ItemStack getItemStack(ItemStack is, IDataAccessor acc, IConfigHandler ich) {
//		ItemStack ret = null;
		
		if (acc.getBlock() instanceof BlockSilverfish) {
			int metadata = acc.getMetadata();
			switch(metadata) {
			case 1:  return new ItemStack(Blocks.cobblestone);
			case 2:  return new ItemStack(Blocks.stonebrick);
            case 3:  return new ItemStack(Blocks.stonebrick, 1, 1);
            case 4:  return new ItemStack(Blocks.stonebrick, 1, 2);
            case 5:  return new ItemStack(Blocks.stonebrick, 1, 3);
			default: return new ItemStack(Blocks.stone);
			}
		}

		if (acc.getBlock() instanceof BlockPotato)      return new ItemStack(Items.potato);
		if (acc.getBlock() instanceof BlockCarrot)      return new ItemStack(Items.carrot);
		if (acc.getBlock() instanceof BlockCocoa)       return new ItemStack(Blocks.cocoa);
		if (acc.getBlock() instanceof BlockNetherWart)  return new ItemStack(Items.nether_wart);
		if (acc.getBlock() instanceof BlockStem)        return acc.getDropped();
		if (acc.getBlock() instanceof BlockCrops)       return new ItemStack(Items.wheat);
		if (acc.getBlock() instanceof BlockDoublePlant) return acc.getPickedBlock();
		
		if (acc.getBlock() instanceof BlockFarmland)    return new ItemStack(Blocks.farmland);
		if (acc.getBlock() instanceof BlockAnvil)       return acc.getPickedBlock();
		
//		if (acc.getBlock() instanceof BlockRedstoneWire) return new ItemStack(Items.redstone);
//		if (acc.getBlock() instanceof BlockRedstoneComparator) return new ItemStack(Items.comparator);
		
		if (acc.getEntity() instanceof EntityItemFrame) return new ItemStack(Items.item_frame);
		
//		if (acc.getBlock() instanceof BlockJukebox) return new ItemStack(Blocks.jukebox);
		
		return is;
	}

	@Override
	public String getTitle(ItemStack is, IDataAccessor acc, IConfigHandler ich, String currentTitle) {
		if (acc.getEntityClass() == EntityHorse.class) {
			EntityHorse en = (EntityHorse) acc.getEntityLiving();
			if (en.hasCustomNameTag()) return EnumChatFormatting.BOLD + en.getCommandSenderName();
			return EnumChatFormatting.BOLD + getHorseName(en);
		}
		
		if (acc.getBlock() instanceof BlockMobSpawner) {
			String mobName = ((TileEntityMobSpawner)acc.getTileEntity()).func_145881_a().getEntityNameToSpawn();
			return currentTitle + " (" + mobName + ")";
		}
		if (acc.getBlock() instanceof BlockOldLog) {
			return acc.getPickedBlock().getDisplayName();
		}
		
		if (acc.getBlock() instanceof BlockStem) {
			if (acc.getDropped().getItem() == Items.melon_seeds)
				return Lang.local("tile.melonStem.name");
			if (acc.getDropped().getItem() == Items.pumpkin_seeds)
				return acc.getBlock().getLocalizedName();
		}
		
		return currentTitle;
	}

	@Override
	public String getSubTitle(ItemStack is, IDataAccessor acc, IConfigHandler ich, String currentSubTitle) {
		if (acc.getEntityClass() == EntityHorse.class) {
			EntityHorse en = (EntityHorse) acc.getEntityLiving();
			if (en.hasCustomNameTag()) return getHorseName(en);
		}
		
		return currentSubTitle;
	}

	@Override
	public List<String> getInfo(ItemStack is, IDataAccessor acc, IConfigHandler ich, List<String> currentInfo) {
		if (acc.getEntityClass() == EntityHorse.class) {
			List<String> infoList = currentInfo;//new ArrayList();
   			
			EntityHorse horse = (EntityHorse) acc.getEntityLiving();
            DecimalFormat df = new DecimalFormat("#.#");//###");
            
            double speed = horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue(), 
            	   baseSpeed = 0.22499999403953552D,
            	   ratio = (speed/baseSpeed)*100;
            
        	infoList.add("Jump Power: " + df.format(horse.getHorseJumpStrength()*50) + "%");
            infoList.add("Speed Rating: " + df.format(ratio) + "%");
            if (horse.isTame()) infoList.add("Owned by: " + horse.getOwnerName());

    		return infoList;
		}

		/*
		 * Redstone
		 */
		if (acc.getBlock() instanceof BlockRedstoneWire) {
			currentInfo.add("Power: " + acc.getMetadata());
			return currentInfo;
		}

		if (acc.getBlock() instanceof BlockLever) {
			String state = ((acc.getMetadata() & 8) == 0) ? "Off" : "On";
			currentInfo.add("Lever State" + ": " + state);
			return currentInfo;
		}
		
		if (acc.getBlock() instanceof BlockRedstoneRepeater) {
			int tick = (acc.getMetadata() >> 2) + 1;
			currentInfo.add("Delay" + ": " + tick + " tick" + (tick==1?"":"s"));
			return currentInfo;
		}
		
		if (acc.getBlock() instanceof BlockRedstoneComparator) {
			String mode = ((acc.getMetadata() >> 2) & 1 ) == 0 ? "Comparator" : "Subtractor";
			currentInfo.add("Mode" + ": " + mode);
			return currentInfo;
		}

		if (acc.getBlock() instanceof BlockJukebox) {
			NBTTagCompound tag = acc.getNBTData();
			if (tag == null) return currentInfo;
			ItemStack record = null;
			if (tag.hasKey("RecordItem")) {
				record = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("RecordItem"));
				currentInfo.add(((ItemRecord)record.getItem()).getRecordNameLocal());
			} else {
				currentInfo.add("Empty");
			}
			return currentInfo;
		}
		
		/*
		 * Plants
		 */
		float growth = -1.0F;
        DecimalFormat df = new DecimalFormat("#.#");

		if (acc.getBlock() instanceof BlockCrops || 
			acc.getBlock() instanceof BlockStem || 
			acc.getBlock() instanceof BlockPotato || 
			acc.getBlock() instanceof BlockCarrot) {
			
			growth = (acc.getMetadata() / 7.0F) * 100.0F;
		}
		
		if (acc.getBlock() instanceof BlockCocoa) {
			growth = ((acc.getMetadata() >> 2) / 2.0F) * 100.0F;
		}
		
		if (acc.getBlock() instanceof BlockNetherWart) {
			growth = (acc.getMetadata() / 3.0F) * 100.0F;
		}
		
//		if (acc.getBlock() instanceof IGrowable && growth == -1.0F && !(acc.getBlock() instanceof BlockDoublePlant)) {
//			IGrowable plant = (IGrowable)acc.getBlock();
//			growth = acc.getIsPlantGrown() ? 100.0F : acc.getMetadata();
//		}

		if (growth >= 0.0F) {
			if (growth < 100.0F) {
				currentInfo.add("Growth" + ": " + df.format(growth) + "%");
			} else {
				currentInfo.add("Growth" + ": " + "Mature");
			}
			return currentInfo;
		}

		return currentInfo;
	}

	@Override
	public int getHealth(IDataAccessor acc, IConfigHandler ich, int currentHealth) {
		if (acc.getEntityClass() == EntityHorse.class) {
			return (int) acc.getEntityLiving().getHealth();
		}
		return currentHealth;
	}

	@Override
	public int getMaxHealth(IDataAccessor acc, IConfigHandler ich, int currentMaxHealth) {
		if (acc.getEntityClass() == EntityHorse.class) {
			return (int) acc.getEntityLiving().getMaxHealth();
		}
		return currentMaxHealth;
	}

	@Override
	public int getArmor(IDataAccessor acc, IConfigHandler ich, int currentArmor) {
		if (acc.getEntityClass() == EntityHorse.class) {
			EntityHorse horse = (EntityHorse) acc.getEntityLiving();
			int[] armorValues = new int[] {0, 5, 7, 11};
	    	return armorValues[horse.func_110241_cb()];
		}
		return currentArmor;
	}
	
	private String getHorseName(EntityHorse horse) {
        String horseType = "";
    	horseType = (horse.isTame()?"Tamed ":"Wild ");
    	if (!horse.isAdultHorse()) horseType += "Baby ";
        switch (horse.getHorseType()) {
            case 0: default: horseType += StatCollector.translateToLocal("entity.horse.name"); break;
            case 1: horseType += StatCollector.translateToLocal("entity.donkey.name"); break;
            case 2: horseType += StatCollector.translateToLocal("entity.mule.name"); break;
            case 3: horseType += StatCollector.translateToLocal("entity.zombiehorse.name"); break;
            case 4: horseType += StatCollector.translateToLocal("entity.skeletonhorse.name"); break;
        }
		return horseType;
	}

	@Override
	public String getModName(ItemStack is, IDataAccessor acc, IConfigHandler ich, String currentModName) {
		if (acc.getEntity() instanceof EntityItemFrame) return ich.getModName(acc.getPickedBlock());
		return currentModName;
	}

}
