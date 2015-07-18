package sladki.tfc.ab.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import sladki.tfc.ab.AutomatedBellows;
import sladki.tfc.ab.ModManager;
import sladki.tfc.ab.TileEntities.TESteamBoiler;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.api.TFCItems;

public class BlockSteamBoiler extends Block implements ITileEntityProvider {

	public static IIcon textureSide;
	public static IIcon textureBottom;
	public static IIcon textureFront;
	public static IIcon textureTop;

	public BlockSteamBoiler(Material material) {
		super(material);
		this.setCreativeTab(TFCTabs.TFCDevices);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TESteamBoiler();
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == 0) {
			return textureBottom;
		}
		
		if(side == 1) {
			return textureTop;
		}
		
		if(side == meta) {
			return textureFront;
		}
		
		if(side == 3 && meta == 0) {
			return textureFront;
		}
		
		return textureSide;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
			float hitY, float hitZ) {
		super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);

		if (world.isRemote) {
			return true;
		}

		TESteamBoiler steamBoiler = null;

		ItemStack equippedItem = player.getCurrentEquippedItem();

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null && tileEntity instanceof TESteamBoiler) {
			steamBoiler = (TESteamBoiler) tileEntity;
		} else {
			return true;
		}

		if (equippedItem == null) {
			steamBoiler.showInfo(player);
			return true;
		}
		
		if(equippedItem.getItem() instanceof ItemHammer) {
			int delay = steamBoiler.changeMode();
		
			ModManager.sendMessage(player, new ChatComponentText(delay + " seconds delay"));
			return true;
		}

		if(equippedItem.getItem() == TFCItems.FireStarter || equippedItem.getItem() == TFCItems.FlintSteel) {
			if(steamBoiler.launch()) {
				int stack = equippedItem.stackSize;
				int damage = equippedItem.getItemDamage() + 1;
				player.inventory.setInventorySlotContents(player.inventory.currentItem,
						new ItemStack(equippedItem.getItem(), stack, damage));
			}
			world.markBlockForUpdate(steamBoiler.xCoord, steamBoiler.yCoord, steamBoiler.zCoord);
			return true;
		}

		if(equippedItem.getItem() == TFCItems.WoodenBucketWater) {
			if(steamBoiler.fuelBoiler(false, 0)) {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(
						TFCItems.WoodenBucketEmpty));
			}
			world.markBlockForUpdate(steamBoiler.xCoord, steamBoiler.yCoord, steamBoiler.zCoord);
			return true;
		}

		if(equippedItem.getItem() == TFCItems.Coal) {
			if(steamBoiler.fuelBoiler(true, 200)) {
				if (equippedItem.stackSize == 1) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				} else {
					equippedItem.stackSize--;
					player.inventory.setInventorySlotContents(player.inventory.currentItem, equippedItem);
				}
			}
			world.markBlockForUpdate(steamBoiler.xCoord, steamBoiler.yCoord, steamBoiler.zCoord);
			return true;
		}

		if(equippedItem.getItem() == TFCItems.Logs) {
			if(steamBoiler.fuelBoiler(true, 40)) {
				if(equippedItem.stackSize == 1) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				} else {
					equippedItem.stackSize--;
					player.inventory.setInventorySlotContents(player.inventory.currentItem, equippedItem);
				}
			}
			world.markBlockForUpdate(steamBoiler.xCoord, steamBoiler.yCoord, steamBoiler.zCoord);
			return true;
		}

		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is) {
		int l = MathHelper.floor_double((double) (entityliving.rotationYaw * 4F / 360F) + 0.5D) & 3;
		
		if(l == 0) {
			world.setBlockMetadataWithNotify(i, j, k, 2, 0x2);
		} else if(l == 1) {
			world.setBlockMetadataWithNotify(i, j, k, 5, 0x2);
		} else if(l == 2) {
			world.setBlockMetadataWithNotify(i, j, k, 3, 0x2);
		} else if(l == 3) {
			world.setBlockMetadataWithNotify(i, j, k, 4, 0x2);
		}
		
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer) {
		textureSide = registerer.registerIcon(AutomatedBellows.MODID + ":" + "steamBoilerSide");
		textureFront = registerer.registerIcon(AutomatedBellows.MODID + ":" + "steamBoilerFront");
		textureTop = registerer.registerIcon(AutomatedBellows.MODID + ":" + "bellowsDriverSide");
		textureBottom = registerer.registerIcon(AutomatedBellows.MODID + ":" + "steamBoilerBottom");
	}

}
