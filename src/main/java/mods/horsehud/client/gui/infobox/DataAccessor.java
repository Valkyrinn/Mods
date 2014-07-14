package mods.horsehud.client.gui.infobox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mods.horsehud.api.IDataAccessor;
import mods.horsehud.api.Keys;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;

public class DataAccessor implements IDataAccessor {
	public World world;
	public EntityPlayer player;
	public MovingObjectPosition mop, mopF;
	public Vec3 renderingVec = null;
	public Block block, fluid;
	public ItemStack pickedBlock;
	public int metadata;
	public Entity entity;
	public TileEntity tileEntity;
	public EntityLivingBase viewEntity;
	public NBTTagCompound remoteNBT = null;
	public long timeLastUpdate = System.currentTimeMillis();
	public double partialFrame;
	
	public static DataAccessor instance = new DataAccessor();

	// Block set() Methods
	public boolean set(World w, EntityPlayer p, double d, EntityLivingBase viewEntity) {
		this.viewEntity = viewEntity;
		this.player      = p;
		MovingObjectPosition m = rayTrace(w, d, false);
		MovingObjectPosition mF = (GuiInfoBox.getConfigBool(Keys.C_FLUID)?rayTrace(w, d, true):null);

		if (m == null) m = mF;
		if (m == null) return false;
		
		if (mF == null) mF = m;
		if (mF.typeOfHit == MovingObjectType.MISS) mF = m;
		
		this.set(w, p, m, mF, null, 0.0);
		
		return true;
	}
	
	public void set(World w, EntityPlayer p, MovingObjectPosition m, MovingObjectPosition mF, EntityLivingBase viewEntity, double partialTicks) {
		this.world       = w;
		this.player      = p;
		this.mop         = m;
		this.mopF        = mF;
		this.block       = w.getBlock(mop.blockX, mop.blockY, mop.blockZ);
		this.pickedBlock = block.getPickBlock(mop, w, mop.blockX, mop.blockY, mop.blockZ);
		this.metadata    = w.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
		this.fluid       = w.getBlock(mopF.blockX, mopF.blockY, mopF.blockZ);
		this.tileEntity  = w.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
		this.entity      = null;
//		this.viewEntity = viewEntity;

//		if (viewEntity != null) {
//			double px = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
//			double py = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
//			double pz = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
//			this.renderingVec = Vec3.createVectorHelper(mop.blockX - px, mop.blockY - py, mop.blockZ - pz);
//			this.partialFrame = partialTicks;
//		}
	}
	
	// Entity set() Methods
	public boolean set(World w, MovingObjectPosition m, EntityLivingBase viewEntity) {
		if (m == null) return false;
		if (m.entityHit == null) return false;
		this.set(w, m, viewEntity, 0.0);
		return true;
	}
	
	public void set(World w, MovingObjectPosition m, EntityLivingBase viewEntity, double partialTicks) {
		this.world   = w;
		this.mop = m;
		this.entity = m.entityHit;
		
		this.pickedBlock = this.isEntityLiving()?null:entity.getPickedResult(m);
//		this.viewEntity = viewEntity;

//		this.player      = null;
//		this.mopF        = null;
//		this.block       = null;
//		this.pickedBlock = null;
//		this.metadata    = 0;
//		this.fluid       = null;
//		this.tileEntity  = null;

//		if (viewEntity != null) {
//			double px = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
//			double py = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
//			double pz = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
//			this.renderingVec = Vec3.createVectorHelper(mop.blockX - px, mop.blockY - py, mop.blockZ - pz);
//			this.partialFrame = partialTicks;
//		}
	}
	
	private MovingObjectPosition rayTrace(World w, double distance, boolean fluids) {
		float f = 1.0F;
		Vec3 vec3  = this.viewEntity.getPosition(f);
	    Vec3 vec31 = this.viewEntity.getLook(f);
	    Vec3 vec32 = vec3.addVector(vec31.xCoord*distance, vec31.yCoord*distance, vec31.zCoord*distance);
		MovingObjectPosition mopt = null;
		mopt = w.rayTraceBlocks(vec3, vec32, fluids);
//		else mop = this.player.rayTrace(distance, f);
	    
	    return mopt;
	}
	public List getNearbyEntities(double d0) {
		float f = 1.0F;
		if (this.viewEntity == null) return new ArrayList();
	    Vec3 vec31 = this.viewEntity.getLook(f);
		return world.getEntitiesWithinAABBExcludingEntity(viewEntity, viewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0));//.expand((double)f1, (double)f1, (double)f1));
	}
	
	// Shared getters
	@Override public World getWorld() { return this.world; }
	@Override public MovingObjectPosition getPosition() { return this.mop; }
	@Override public Vec3 getRenderingPosition() { return this.renderingVec; }
	@Override public double getPartialFrame() { return this.partialFrame; }
	@Override public long getTimeLastUpdate() { return this.timeLastUpdate; }
	@Override public void setTimeLastUpdate(long t) { this.timeLastUpdate = t; }

	// Block getters
	@Override public EntityPlayer getPlayer() { return this.player; }
	@Override public Block getBlock() { return this.block; }
	@Override public int getBlockID() { return Block.getIdFromBlock(this.block); }
	@Override public int getMetadata() { return this.metadata; }
	@Override public ItemStack getPickedBlock() { return this.pickedBlock; }
	@Override public TileEntity getTileEntity() { return this.tileEntity; }
	@Override public MovingObjectType getHitType() { return mop.typeOfHit; }
	@Override 
	public Class getBlockClass() {
		if (this.block == null) return null;
		return this.block.getClass(); 
	}
	@Override
	public ItemStack getShearable() {
		if (block instanceof IShearable) {
			ItemStack shears = new ItemStack(Items.shears);
			IShearable shearable = (IShearable)block;
			if (shearable.isShearable(shears, world, mop.blockX, mop.blockY, mop.blockY))
				return handleItemList(shearable.onSheared(shears, world, mop.blockX, mop.blockY, mop.blockZ, 0));
		}
		return null;
	}
	@Override
	public ItemStack getDropped() {
		return handleItemList(block.getDrops(world, mop.blockX, mop.blockY, mop.blockZ, metadata, 0));
	}
	@Override
	public boolean getIsPlantGrown() {
		if (block instanceof IGrowable) {
			return !((IGrowable)block).func_149851_a(world, mop.blockX, mop.blockY, mop.blockZ, false);
		}
		return false;
	}
	private ItemStack handleItemList(ArrayList<ItemStack> itemList) {
		if (itemList.isEmpty()) return null;
		Collections.sort(itemList, new Comparator<ItemStack>() {
            @Override
            public int compare(ItemStack stack0, ItemStack stack1)
            {
                return stack1.getItemDamage() - stack0.getItemDamage();
            }
        });

		return itemList.get(0);
	}

	// Entity getters
	@Override public Entity getEntity() { return this.entity; }
	@Override public EntityLiving getEntityLiving() { return (this.entity instanceof EntityLiving)?(EntityLiving)this.entity:null; }
	@Override public boolean isEntityLiving() { return (this.entity instanceof EntityLiving); }
	
	@Override 
	public Class getEntityClass() {
		if (this.entity == null) return null;
		return this.entity.getClass(); 
	}

	public void setBlock(Block b) { this.block = b; }
	public void setMetadata(int i) { this.metadata = i; }

	@Override
	public Block getFluid() {
		if (this.fluid == null) return null;
		if (FluidRegistry.isFluidRegistered(this.fluid.getUnlocalizedName().substring(5)))
			return this.fluid;
		return null;
	}
	
	@Override
	public NBTTagCompound getNBTData() {
		if (this.entity == null) return null;
		
		if (this.isTagCorrect(this.remoteNBT)) return this.remoteNBT;
		
		NBTTagCompound tag = new NBTTagCompound();
		this.entity.writeToNBT(tag);
		return tag;
	}
	
	@Override
	public int getNBTInt(NBTTagCompound tag, String key) {
		NBTBase subtag = tag.getTag(key);
		if (subtag instanceof NBTTagInt)   return tag.getInteger(key);
		if (subtag instanceof NBTTagShort) return tag.getShort(key);
		if (subtag instanceof NBTTagByte)  return tag.getByte(key);
		return 0;
	}
	
	private boolean isTagCorrect(NBTTagCompound tag) {
		if (tag == null) {
			this.timeLastUpdate = System.currentTimeMillis() - 250;
			return false;
		}
		
		int x = tag.getInteger("x");
		int y = tag.getInteger("y");
		int z = tag.getInteger("z");
		
		if (x == this.mop.blockX && y == this.mop.blockY && z == this.mop.blockZ) return true;
		else {
			this.timeLastUpdate = System.currentTimeMillis() - 250;
			return false;
		}
	}
	
	@Override
	public ForgeDirection getSide() {
		switch (this.getPosition().sideHit){
			case 0: return ForgeDirection.DOWN;
			case 1: return ForgeDirection.UP;
			case 2: return ForgeDirection.EAST;
			case 3: return ForgeDirection.WEST;
			case 4: return ForgeDirection.NORTH;
			case 5: return ForgeDirection.SOUTH;
			default: return ForgeDirection.UNKNOWN;
		}
	}
}
