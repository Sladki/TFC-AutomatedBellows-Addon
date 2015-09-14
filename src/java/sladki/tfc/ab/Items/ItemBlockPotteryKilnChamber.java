package sladki.tfc.ab.Items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.api.Enums.EnumSize;

public class ItemBlockPotteryKilnChamber extends ItemTerraBlock {
	
	public ItemBlockPotteryKilnChamber(Block block) {
		super(block);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.HUGE;
	}

}