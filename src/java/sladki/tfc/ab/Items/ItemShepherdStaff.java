package sladki.tfc.ab.Items;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import net.minecraft.client.renderer.texture.IIconRegister;
import sladki.tfc.ab.AutomatedBellows;

public class ItemShepherdStaff extends ItemTerra {

    public ItemShepherdStaff() {
        super();
        setCreativeTab(TFCTabs.TFC_TOOLS);
        setWeight(EnumWeight.MEDIUM);
        setSize(EnumSize.MEDIUM);
        maxStackSize = 1;
    }

    @Override
    public void registerIcons(IIconRegister registerer) {
        itemIcon = registerer.registerIcon(AutomatedBellows.MODID + ":shepherdStaff");
    }

    @Override
    public boolean canStack() {
        return false;
    }
}
