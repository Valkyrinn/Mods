package mods.horsehud.api;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IDataAccessor {
	// Shared getters
	World        		 getWorld();
	MovingObjectPosition getPosition();
	Vec3                 getRenderingPosition();
	NBTTagCompound       getNBTData();
	int                  getNBTInt(NBTTagCompound tag, String keyname);
	double               getPartialFrame();
	long                 getTimeLastUpdate();
	void                 setTimeLastUpdate(long t);
	ForgeDirection       getSide();

	// Block getters
	EntityPlayer 		 getPlayer();
	Block        		 getBlock();
	int          		 getBlockID();
	int          		 getMetadata();
	Block                getFluid();
	ItemStack            getPickedBlock();
	TileEntity           getTileEntity();
	MovingObjectType     getHitType();
	Class                getBlockClass();
	ItemStack            getDropped();
	ItemStack            getShearable();
	boolean              getIsPlantGrown();

	// Entity getters
	Entity               getEntity();
	EntityLiving         getEntityLiving();
	boolean              isEntityLiving();
	Class                getEntityClass();
}