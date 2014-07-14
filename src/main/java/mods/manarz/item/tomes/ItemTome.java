package mods.manarz.item.tomes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.manarz.Manarz;
import mods.manarz.entity.EntityGleamingDagger;
import mods.manarz.entity.EntityProjectile;
import mods.resourcemod.common.ResourceMod;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemTome extends Item {
	
    private String[] spellPullIconNameArray = new String[] {"tome_1", "tome_2", "tome_3"};
    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;
    
	private boolean spellReady = false;
	private int maxUseDuration = 7200;
	private boolean isCharged = false;
	
	protected String name, spellType;
	protected Block block;
	protected int draw[] = { 25, 50 };
	protected ItemStack[] cost = {};
	protected int consumptionChance = 100;
	
	public ItemTome(String par1Name, String par2Type, Block par3Block, int[] par4Draw, ItemStack[] par5Items, int par6ConsumptionChance) {
		super();
		this.name = par1Name;
		this.spellType = par2Type;
		this.block = par3Block;
		this.draw = par4Draw;
		this.cost = par5Items;
		this.consumptionChance = par6ConsumptionChance;
		
//		this.setUnlocalizedName("tome" + spellType + name);
		this.setUnlocalizedName("tome" + name);
		this.setCreativeTab(Manarz.tabTomes);
	}
	@Override
	 public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer par3EntityPlayer) {
		if (!spellReady) {
			ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer, par1ItemStack);
			MinecraftForge.EVENT_BUS.post(event);
	        if (event.isCanceled())
	            return event.result;
			spellReady = false;
	
			if(checkInventory(par3EntityPlayer) || par3EntityPlayer.capabilities.isCreativeMode)
			{
				par3EntityPlayer.setItemInUse(par1ItemStack, this.maxUseDuration);
			}
			
		} else if (isCharged || par3EntityPlayer.capabilities.isCreativeMode){
			if(checkInventory(par3EntityPlayer) || par3EntityPlayer.capabilities.isCreativeMode)
			{
				if(!par3EntityPlayer.capabilities.isCreativeMode) 
				{
					for (int n = 0; n < cost.length; n++)
						for (int m = 0; m < cost[n].stackSize; m++)
							if (this.itemRand.nextInt(100) < this.consumptionChance)
								if(!par3EntityPlayer.inventory.consumeInventoryItem(cost[n].getItem())) ;//System.out.println("No items!");
					spellReady = false;
					isCharged = false;
				}
				if (!par2World.isRemote) {
					if (name == "Lightning") {
						EntitySummonLightning entityspell = new EntitySummonLightning(par2World, par3EntityPlayer, 2.0F, new ItemStack(Manarz.blankManaStone), "Spell");
						par2World.spawnEntityInWorld(entityspell);
					} else {
						EntitySpell entityspell = new EntitySpell(par2World, par3EntityPlayer, 2.0F, new ItemStack(Manarz.blankManaStone), "Spell");
						entityspell.setEffect(spellType);
						entityspell.setModifier(name);
						entityspell.setBlock(block);
						par2World.spawnEntityInWorld(entityspell);
					}
				}
			}
		}
		return par1ItemStack;
	}

	/**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return this.maxUseDuration;
    }
	
	private boolean checkInventory(EntityPlayer par3EntityPlayer) {
		for (int n = 0; n < cost.length; n++) {
			int count = 0;
			for (int index = 0; index<par3EntityPlayer.inventory.getSizeInventory(); index++) {
	            if(par3EntityPlayer.inventory.getStackInSlot(index) == null) 
	                continue;
	            if(par3EntityPlayer.inventory.getStackInSlot(index).getItem() == cost[n].getItem()) 
                    count += par3EntityPlayer.inventory.getStackInSlot(index).stackSize;
	        }
			if (count < cost[n].stackSize) return false;
		}
		return true;
	}
	
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
		isCharged = true;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(Manarz.modID + ":tomes/" + this.getUnlocalizedName().substring(5));
        this.iconArray = new IIcon[spellPullIconNameArray.length];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = par1IconRegister.registerIcon(Manarz.modID + ":tomes/" + spellPullIconNameArray[i]);
        }
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }
    
	@Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
		int ticksElapsed = this.maxUseDuration - useRemaining;
		
    	if (useRemaining ==  0) return this.itemIcon;
    	int n;
    	
    	for(n = 0; n < draw.length-1; n++)
    		if (!spellReady && ticksElapsed <= draw[n]) return iconArray[n];
    	if (!spellReady && ticksElapsed < draw[n]) return iconArray[n];

    	if (!spellReady) { 
    		spellReady = true; 
//    		player.stopUsingItem();
    		player.playSound("random.orb", 1.2F, ((itemRand.nextFloat() - itemRand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
    	}
    	return iconArray[++n];
    }
}
