package sladki.tfc.ab.Items;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.api.Enums.EnumSize;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockAutomatedBellows extends ItemTerraBlock {

	public ItemBlockAutomatedBellows(Block block) {
		super(block);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.HUGE;
	}

}