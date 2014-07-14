package mods.manarz.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import mods.manarz.Manarz;
import mods.manarz.ManarzEnums;
import mods.manarz.ManarzEnums.Blade;
import mods.manarz.ManarzEnums.Hilt;
import mods.manarz.ManarzEnums.Addition;
import mods.manarz.ManarzEnums.LootWeapon;
import mods.manarz.entity.EntityGleamingDagger;
import mods.manarz.entity.EntityGnats;
import mods.manarz.entity.EntityProjectile;
import mods.manarz.entity.EntityRustyDagger;
import mods.manarz.entity.EntityShadowedDagger;
import mods.manarz.entity.EntitySilverDagger;
import mods.manarz.entity.models.ModelGnats;
import mods.manarz.entity.renders.RenderGnats;
import mods.manarz.item.renders.CustomRenderer;
import mods.manarz.item.renders.ItemRenderMaul;
import mods.manarz.item.renders.ItemRendererWarhammer;
import mods.manarz.item.renders.ProjectileRenderer;
import mods.manarz.item.tomes.EntitySpell;
import mods.manarz.item.tomes.EntitySummon;
import mods.manarz.item.tomes.EntitySummonLightning;
import mods.manarz.item.tomes.ItemRenderTome;
import mods.manarz.item.tomes.RenderSpell;
import mods.manarz.item.tomes.TomeManager;
import mods.manarz.common.CommonProxy;
import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
        
        public void registerRenderers() {
    		RenderingRegistry.registerEntityRenderingHandler(EntityGleamingDagger.class, new ProjectileRenderer(EntityProjectile.EP_DAGGER, LootWeapon.WEAPON_GLEAMINGDAGGER.getName()));
    		RenderingRegistry.registerEntityRenderingHandler(EntityRustyDagger.class, new ProjectileRenderer(EntityProjectile.EP_DAGGER, LootWeapon.WEAPON_RUSTYDAGGER.getName()));
    		RenderingRegistry.registerEntityRenderingHandler(EntityShadowedDagger.class, new ProjectileRenderer(EntityProjectile.EP_DAGGER, LootWeapon.WEAPON_SHADOWEDDAGGER.getName()));
    		RenderingRegistry.registerEntityRenderingHandler(EntitySilverDagger.class, new ProjectileRenderer(EntityProjectile.EP_DAGGER, LootWeapon.WEAPON_SILVERDAGGER.getName()));
    		RenderingRegistry.registerEntityRenderingHandler(EntitySpell.class, new ProjectileRenderer(EntityProjectile.EP_SPELL, "Spell"));
    		RenderingRegistry.registerEntityRenderingHandler(EntitySummonLightning.class, new ProjectileRenderer(EntityProjectile.EP_SPELL, "Spell"));
    		
            RenderingRegistry.registerEntityRenderingHandler(EntityGnats.class, new RenderGnats((ModelBase)(new ModelGnats()), 0.45F));
            for (int i = 0; i < Addition.values().length; i++)
            	MinecraftForgeClient.registerItemRenderer(Manarz.augWeapons[LootWeapon.WEAPON_WARHAMMER.index()][i], (IItemRenderer) new ItemRendererWarhammer());
            for (int i = 0; i < Addition.values().length; i++)
            	MinecraftForgeClient.registerItemRenderer(Manarz.augWeapons[LootWeapon.WEAPON_MAUL.index()][i], new ItemRenderMaul());
//    		for (int i = 0; i < ManarzEnums.Hilt.values().length; i++)
//    			for (int j = 0; j < ManarzEnums.Blade.values().length; j++) {
            for (Hilt hil : Hilt.values())
            	for (Blade bla : Blade.values()) {
    				MinecraftForgeClient.registerItemRenderer(Manarz.tools[Manarz.I_SWORD][hil.index()][bla.index()], new CustomRenderer(ManarzEnums.TYPE_SWORD,hil,bla));
    				MinecraftForgeClient.registerItemRenderer(Manarz.tools[Manarz.I_PICKAXE][hil.index()][bla.index()], new CustomRenderer(ManarzEnums.TYPE_PICKAXE,hil,bla));
    				MinecraftForgeClient.registerItemRenderer(Manarz.tools[Manarz.I_SPADE][hil.index()][bla.index()], new CustomRenderer(ManarzEnums.TYPE_SHOVEL,hil,bla));
    				MinecraftForgeClient.registerItemRenderer(Manarz.tools[Manarz.I_AXE][hil.index()][bla.index()], new CustomRenderer(ManarzEnums.TYPE_AXE,hil,bla));
    			}
//            RenderingRegistry.registerEntityRenderingHandler(EntityProjectile.class, new ProjectileRenderer());
//    		for (int i = 0; i < ManarzEnums.Hilt.values().length; i++)
//    			for (int j = 0; j < ManarzEnums.Blade.values().length; j++)
//    				for (int k = 0; k < 3; k++)
//    					MinecraftForgeClient.registerItemRenderer(SwordToolManager.tools[i][j][k].itemID, new CustomSwordToolRenderer(k+1,j,i));
//    		MinecraftForgeClient.registerItemRenderer(TomeManager.tomeList[TomeManager.TOME_LIGHTNING].itemID, new ItemRenderTome());
        }
        
        public int addArmor(String armor) {
        	return RenderingRegistry.addNewArmourRendererPrefix(armor);
        }
}