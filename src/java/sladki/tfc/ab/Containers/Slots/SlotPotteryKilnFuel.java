package sladki.tfc.ab.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class SlotPotteryKilnFuel extends Slot {
	
	public SlotPotteryKilnFuel(IInventory inventory, int id, int x, int y)
	{
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack)
	{
		if(itemStack.getItem() == TFCItems.Logs 
				|| itemStack.getItem() == Item.getItemFromBlock(TFCBlocks.Peat))
		{
			return true;
		}
		return false;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	@Override
	public void putStack(ItemStack itemStack)
	{
		if(itemStack != null) {
			itemStack.stackSize = 1;
		}
		super.putStack(itemStack);
	}

}
