package sladki.tfc.ab.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sladki.tfc.ab.Containers.Slots.SlotPotteryKilnChamber;
import sladki.tfc.ab.Containers.Slots.SlotPotteryKilnFuel;
import sladki.tfc.ab.TileEntities.TEPotteryKiln;

public class ContainerPotteryKiln extends Container {

	private TEPotteryKiln tileEntity;
	private int mode = 0;	//0 - fuel container, 1 - chamber with pottery
	
	//Modes: 0 - fuel container, 1 - chamber with pottery
	public ContainerPotteryKiln(InventoryPlayer player, TEPotteryKiln tileEntity, int mode) {
		this.mode = mode;
		
		if(mode == 0) {
			addSlotToContainer(new SlotPotteryKilnFuel(tileEntity, 0, 71, 25));
			addSlotToContainer(new SlotPotteryKilnFuel(tileEntity, 1, 89, 25));
			addSlotToContainer(new SlotPotteryKilnFuel(tileEntity, 2, 71, 43));
			addSlotToContainer(new SlotPotteryKilnFuel(tileEntity, 3, 89, 43));
		} else if(mode == 1) {
			addSlotToContainer(new SlotPotteryKilnChamber(tileEntity, 4, 53, 25));
			addSlotToContainer(new SlotPotteryKilnChamber(tileEntity, 5, 71, 25));
			addSlotToContainer(new SlotPotteryKilnChamber(tileEntity, 6, 89, 25));
			addSlotToContainer(new SlotPotteryKilnChamber(tileEntity, 7, 107, 25));
			addSlotToContainer(new SlotPotteryKilnChamber(tileEntity, 8, 53, 43));
			addSlotToContainer(new SlotPotteryKilnChamber(tileEntity, 9, 71, 43));
			addSlotToContainer(new SlotPotteryKilnChamber(tileEntity, 10, 89, 43));
			addSlotToContainer(new SlotPotteryKilnChamber(tileEntity, 11, 107, 43));
		}
		
		bindPlayerInventory(player);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);
		
		if(slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			                    
			if(slot < 8) {
				if(!this.mergeItemStack(stackInSlot, 8, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				return null;
			}
			
			if(stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}
			
			if(stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}
	
	@Override
	public boolean mergeItemStack(ItemStack is, int slotStart, int slotFinish, boolean backward) {
		if(!backward) {
			if(this.getSlot(slotStart).isItemValid(is)) {
				return super.mergeItemStack(is, slotStart, slotFinish, backward);
			}
			return false;
		}
		return super.mergeItemStack(is, slotStart, slotFinish, backward);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                        addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 18, 84 + i * 18));
                }
        }

        for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
	}
	
}