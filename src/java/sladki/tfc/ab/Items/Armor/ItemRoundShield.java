package sladki.tfc.ab.Items.Armor;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Armor;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IEquipable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import sladki.tfc.ab.AutomatedBellows;
import sladki.tfc.ab.ModManager;

public class ItemRoundShield extends ItemTerra implements IEquipable {

	private float[] damageTypesResistances = new float[4];	//Piercing, Slashing, Crushing, Generic
	private boolean isWooden;
	private int shieldId;
	private int colour;
	
	private ItemRoundShield(int id, int durability, int[] armourRating, float[] armourColour) {
		super();
		setCreativeTab(TFCTabs.TFC_ARMOR);
		setWeight(EnumWeight.HEAVY);
		setSize(EnumSize.LARGE);
		maxStackSize = 1;

		shieldId = id;
		setMaxDamage(durability);
		damageTypesResistances[0] = (armourRating[0] == -1000) ? 1000.0f: (1000.0f / (1000.0f + armourRating[0]));
		damageTypesResistances[1] = (armourRating[1] == -1000) ? 1000.0f: (1000.0f / (1000.0f + armourRating[1]));
		damageTypesResistances[2] = (armourRating[2] == -1000) ? 1000.0f: (1000.0f / (1000.0f + armourRating[2]));
		damageTypesResistances[3] = (float) ((damageTypesResistances[0] + damageTypesResistances[1] + damageTypesResistances[2]) / 3 - 0.25);

		int r = ((int) (armourColour[0] * 255)) << 16;
		int g = ((int) (armourColour[1] * 255)) << 8;
		int b = ((int) (armourColour[2] * 255));
		colour = r + g + b;
	}
	
	public ItemRoundShield(int id, Armor originArmor, float red, float green, float blue) {
		this(id, originArmor.getSlashingAR(),
			new int[]{
				originArmor.getPiercingAR(),
				originArmor.getSlashingAR(),
				originArmor.getCrushingAR()
			}, new float[]{red, green, blue });
	}
	
	public ItemRoundShield(int id, int durability, boolean madeFromWood) {
		this(id, durability, new int[]{50, 100, 0}, new float[]{1.0f, 1.0f, 1.0f});
		
		isWooden = madeFromWood;
	}

	@Override
	public int getMaxDamage(ItemStack itemStack) {
		if(isWooden(itemStack)) {
			return super.getMaxDamage(itemStack);
		} else {
			return (int) (super.getMaxDamage(itemStack) + (super.getMaxDamage(itemStack) * AnvilManager.getDurabilityBuff(itemStack)));
		}
	}

	public float getResistanceMult(int damageTypeIndex) {
		return damageTypesResistances[damageTypeIndex];
	}
	
	public static ItemStack getShieldFromId(int id) {
		return (ModManager.shieldRegistry[id] != null) ? new ItemStack(ModManager.shieldRegistry[id]) : null;
	}
	
	public int getShieldId() {
		return shieldId;
	}
	
	public static boolean isWooden(ItemStack itemStack) {
		return ((ItemRoundShield) itemStack.getItem()).isWooden;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int damage) {
        return ((ItemRoundShield) itemStack.getItem()).colour;
    }
	
	@Override
	public void registerIcons(IIconRegister registerer) {
		if(isWooden) {
			itemIcon = registerer.registerIcon(AutomatedBellows.MODID + ":armor/woodenShield");
		} else {
			itemIcon = registerer.registerIcon(AutomatedBellows.MODID + ":armor/metalShield");
		}
	}

	@Override
	public EquipType getEquipType(ItemStack is) {
		return EquipType.BACK;
	}

	@Override
	public boolean getTooHeavyToCarry(ItemStack is) {
		return true;
	}
	
	@Override
	public boolean canStack() {
		return false;
	}

	@Override
	public void onEquippedRender() {
		GL11.glScalef(0.0f, 0.0f, 0.0f);
	}

}
