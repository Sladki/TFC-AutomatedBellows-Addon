package sladki.tfc.ab.Handlers;

import com.bioxx.tfc.Containers.ContainerPlayerTFC;
import com.bioxx.tfc.Containers.ContainerTFC;
import com.bioxx.tfc.Containers.Slots.SlotCookableFoodOnly;
import com.bioxx.tfc.Containers.Slots.SlotSize;
import com.bioxx.tfc.api.Enums.EnumSize;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;

public class ContainerHandler {
	
	private static SlotCookableFoodOnly foodSlot = (SlotCookableFoodOnly) new SlotCookableFoodOnly(null, 0, 0, 0).setSize(EnumSize.HUGE);

	@SubscribeEvent
	public void onPlayerOpenContainer(PlayerOpenContainerEvent event) {		
		Container container = event.entityPlayer.openContainer;
		
		if(container instanceof ContainerPlayerTFC
				|| container instanceof ContainerTFC) {

		} else {
			checkContainer(container, event.entityPlayer);
		}
	}
	
	private void checkContainer(Container container, EntityPlayer player) {
		int containerSize = container.inventorySlots.size();
		
		for(int slotIndex = 0; slotIndex < containerSize; slotIndex++) {
			
			Slot currentSlot = container.getSlot(slotIndex);
			ItemStack stackInSlot = currentSlot.getStack();
			
			if(stackInSlot == null
					|| currentSlot.inventory instanceof InventoryPlayer
					|| currentSlot instanceof SlotSize) {
				
				return;
			}
			
			if(stackInSlot != null && foodSlot.isItemValid(stackInSlot)) {
				dropItemStack(stackInSlot, player);
				currentSlot.putStack(null);
			}
		}
	}
	
	private void dropItemStack(ItemStack itemStack, EntityPlayer player) {
		EntityItem entityItem = new EntityItem(player.worldObj
				, player.posX, player.posY, player.posZ, itemStack);
		
		player.worldObj.spawnEntityInWorld(entityItem);
	}
	
}