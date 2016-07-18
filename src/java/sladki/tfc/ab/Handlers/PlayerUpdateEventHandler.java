package sladki.tfc.ab.Handlers;

import java.util.HashMap;
import java.util.Map;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import sladki.tfc.ab.Handlers.Network.PlayerEquipUpdatePacket;
import sladki.tfc.ab.Items.Armor.ItemRoundShield;

public class PlayerUpdateEventHandler {
	
	private static HashMap<Integer, Integer> equipMap = new HashMap<Integer, Integer>();
	
	
	
	@SubscribeEvent()
	public void onPlayerUpdateEvent(LivingUpdateEvent event) {
		if(event.entity.worldObj.isRemote || !(event.entityLiving instanceof EntityPlayer)) {
			return;
		}
		
		EntityPlayer player = (EntityPlayer) event.entityLiving;
		int prevEquip = equipMap.containsKey(player.getEntityId()) ? equipMap.get(player.getEntityId()) : -1;
		int currentEquip = 0;
		
		//Check for "new" players
		if(prevEquip == -1) {
			for(Map.Entry<Integer, Integer> entry : equipMap.entrySet()) {
				Entity target = event.entity.worldObj.getEntityByID(entry.getKey());
				
				if(target != null && target.dimension == event.entity.dimension) {
					AbstractPacket packet = new PlayerEquipUpdatePacket(entry.getKey(), entry.getValue());
					TerraFirmaCraft.PACKET_PIPELINE.sendTo(packet, (EntityPlayerMP) player);
				}
			}
		}
		
		if(MinecraftServer.getServer().getTickCounter() % 20 == 0) {
			if(player.inventory instanceof InventoryPlayerTFC) {
				ItemStack[] extraEquipInv = ((InventoryPlayerTFC) player.inventory).extraEquipInventory;
				
				for(ItemStack itemStack : extraEquipInv) {
					if(itemStack != null && itemStack.getItem() instanceof ItemRoundShield) {
						currentEquip = ((ItemRoundShield) itemStack.getItem()).getShieldId();
						break;
					}
				}
			}
			
			if(prevEquip != currentEquip) {
				equipMap.put(player.getEntityId(), currentEquip);
				
				//Do not send information about "new empty" players
				if(prevEquip + currentEquip >= 0) {
					AbstractPacket packet = new PlayerEquipUpdatePacket(player.getEntityId(), currentEquip);
					TerraFirmaCraft.PACKET_PIPELINE.sendToDimension(packet, player.dimension);
				}
			}
			
			if(MinecraftServer.getServer().getTickCounter() % 12000 == 0) {
				equipMap.clear();
			}
		}
	}
}
