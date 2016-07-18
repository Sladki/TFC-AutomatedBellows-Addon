package sladki.tfc.ab.Containers.Slots;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotPotteryKilnFuel extends Slot {
	
	public SlotPotteryKilnFuel(IInventory inventory, int id, int x, int y)
	{
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack)
	{
		if(itemStack.getItem() == TFCItems.logs
				|| itemStack.getItem() == Item.getItemFromBlock(TFCBlocks.peat))
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
