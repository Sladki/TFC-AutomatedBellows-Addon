package sladki.tfc.ab.Handlers.Client;

import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.Items.Tools.ItemCustomAxe;
import com.bioxx.tfc.Items.Tools.ItemWeapon;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import sladki.tfc.ab.Items.Armor.ItemRoundShield;
import sladki.tfc.ab.Render.RenderShield;

public class PlayerRenderHandler {
	
	public static RenderShield RENDER_SHIELD = new RenderShield();
	
	private enum EnumShieldStatus { NONE, IN_HAND, ON_BACK }
	
	
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerRenderTick(RenderPlayerEvent.Specials.Pre event) {
		if(event.entityPlayer == null) {
			return;
		}
		
		EntityPlayer player = (EntityPlayer) event.entityLiving;
		EnumShieldStatus shieldStatus = checkShield(player);
		
		if(shieldStatus != EnumShieldStatus.NONE) {
			RENDER_SHIELD.render(player, event.renderer, (shieldStatus == EnumShieldStatus.IN_HAND), player.isBlocking());
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerRenderHand(RenderHandEvent event) {
		if(Minecraft.getMinecraft().gameSettings.thirdPersonView != 0
				|| Minecraft.getMinecraft().renderViewEntity.isPlayerSleeping()
				|| Minecraft.getMinecraft().gameSettings.hideGUI) {
			
			return;
		}
		
		if(Minecraft.getMinecraft().thePlayer == null) {
			return;
		}
		
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		EnumShieldStatus shieldStatus = checkShield(player);
		
		if(shieldStatus == EnumShieldStatus.IN_HAND) {
			RENDER_SHIELD.renderFirstPerson(player, event.partialTicks, player.isBlocking());
		}
	}
	
	private EnumShieldStatus checkShield(EntityPlayer player) {
		//Checking for an equipped shield 
		if(player.inventory instanceof InventoryPlayerTFC) {
			ItemStack[] extraEquipInv = ((InventoryPlayerTFC) player.inventory).extraEquipInventory;
			boolean hasShield = false;
			
			for(ItemStack itemStack : extraEquipInv) {
				if(itemStack != null && itemStack.getItem() instanceof ItemRoundShield) {
					hasShield = true;
					break;
				}
			}
			
			if(!hasShield) {
				return EnumShieldStatus.NONE;
			}
		}
		
		//Checking for a shield mount (TRUE if a player is holding weapon in the main hand)
		if(player.getCurrentEquippedItem() != null) {
			if(player.getCurrentEquippedItem().getItem() instanceof ItemWeapon
				|| player.getCurrentEquippedItem().getItem() instanceof ItemCustomAxe) {
				return EnumShieldStatus.IN_HAND;
			}
		}
		
		return EnumShieldStatus.ON_BACK;
	}
	
	/*@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerRenderTickPre(RenderPlayerEvent.Pre event) {
		if(event.entityPlayer == null || !(event.entityPlayer instanceof EntityPlayer)) {
			return;
		}
		
		if(event.entityPlayer.getItemInUseCount() > 0) {
			//event.entityPlayer.clearItemInUse();
		}
	}
	
	@SubscribeEvent
	public void onPlayerStartUsingItem(PlayerUseItemEvent.Start event) {
		if(event.entityPlayer.worldObj.isRemote) {
			return;
		}
		
		//event.duration = 0;
		//event.setCanceled(true);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerRenderHand(RenderHandEvent event) {
		if(Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getItemInUseCount() > 0) {
			prevUseState = Minecraft.getMinecraft().thePlayer.getItemInUseCount();
			usedItem = Minecraft.getMinecraft().thePlayer.getItemInUse();
			
			Minecraft.getMinecraft().thePlayer.clearItemInUse();
		}
		
		//event.setCanceled(true);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderGameOverlay(RenderGameOverlayEvent event) {
		if(prevUseState > 0) {
			Minecraft.getMinecraft().thePlayer.setItemInUse(usedItem, prevUseState);
			
			prevUseState = 0;
			usedItem = null;
		}
	}*/
}
