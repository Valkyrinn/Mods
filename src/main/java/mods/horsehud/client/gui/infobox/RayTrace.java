package mods.horsehud.client.gui.infobox;

import mods.horsehud.api.Keys;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

public class RayTrace {
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	private static RayTrace instance;
	private RayTrace() { }
	public static RayTrace instance() {
		if (instance == null) instance = new RayTrace();
		return instance;
	}

	private boolean fluids = false;
	private MovingObjectPosition mop      = null;
	private MovingObjectPosition mopFluid = null;
	
	public MovingObjectPosition getTarget()      { return mop;      }
	public MovingObjectPosition getTargetFluid() { return mopFluid; }
	
	public void trace() {
		this.fluids = GuiInfoBox.getConfigBool(Keys.C_FLUID);
		this.mop = null;
		this.mopFluid = null;

		if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY){
			this.mop = mc.objectMouseOver;
			return;
		}

		EntityLivingBase viewEntity = mc.renderViewEntity; 
		double distance = mc.playerController.getBlockReachDistance();
		float f = 1.0F;
		
		Vec3 vec3  = viewEntity.getPosition(f);
	    Vec3 vec31 = viewEntity.getLook(f);
	    Vec3 vec32 = vec3.addVector(vec31.xCoord*distance, vec31.yCoord*distance, vec31.zCoord*distance);

	    MovingObjectPosition mopt = null;
	    World w = viewEntity.worldObj;
		mopt = viewEntity.worldObj.rayTraceBlocks(vec3, vec32, fluids);
		
		if (mopt.typeOfHit == MovingObjectType.MISS) return;

		if (mopt.typeOfHit == MovingObjectType.BLOCK) {
			Block b = w.getBlock(mopt.blockX, mopt.blockY, mopt.blockZ);
			if (FluidRegistry.lookupFluidForBlock(b) != null) { 
				mopFluid = mopt;
				mopFluid = null;
			} else 
				mop = mopt;
			
			if (mop == null) {
				mop = viewEntity.worldObj.rayTraceBlocks(vec3, vec32, false);
				if (mop.typeOfHit == MovingObjectType.MISS) mop = mopFluid;
			}
		}
	}
}
