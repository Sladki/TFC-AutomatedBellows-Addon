package sladki.tfc.ab.Containers.Slots;

import com.bioxx.tfc.Items.Pottery.ItemPotteryBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPotteryKilnChamber extends Slot {

	public SlotPotteryKilnChamber(IInventory inventory, int id, int x, int y)
	{
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack)
	{
        return itemStack.getItem() instanceof ItemPotteryBase;
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