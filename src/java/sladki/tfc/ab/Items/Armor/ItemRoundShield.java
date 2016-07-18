package sladki.tfc.ab.Items.Armor;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Armor;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IEquipable;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import sladki.tfc.ab.AutomatedBellows;

public class ItemRoundShield extends ItemTerra implements IEquipable {
	
	public ItemRoundShield () {
		super();
		this.setCreativeTab(TFCTabs.TFC_ARMOR);
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.LARGE);
		this.setMaxDamage(Armor.wroughtIronPlate.getDurability(1));
		this.maxStackSize = 1;
	}
	
	public int getShieldId() {
		return 1;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer) {
		itemIcon = registerer.registerIcon(AutomatedBellows.MODID + ":armor/wroughtIronRoundShield");
	}

	@Override
	public EquipType getEquipType(ItemStack is) {
		return EquipType.BACK;
	}

	@Override
	public void onEquippedRender() {
		GL11.glScalef(0.0f, 0.0f, 0.0f);
	}

	@Override
	public boolean getTooHeavyToCarry(ItemStack is) {
		return true;
	}
	
	@Override
	public boolean canStack() {
		return false;
	}

}
