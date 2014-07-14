package mods.horsehud.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IEntityAccessor {
	World getWorld();
	Entity getEntity();
	EntityLiving getEntityLiving();
	boolean isEntityLiving();
	MovingObjectPosition getPosition();
	Class getEntityClass();
	Vec3 getRenderingPosition();
	double getPartialFrame();
	NBTTagCompound getNBTData();
	int getNBTInt(NBTTagCompound tag, String key);
	ForgeDirection getSide();
}
