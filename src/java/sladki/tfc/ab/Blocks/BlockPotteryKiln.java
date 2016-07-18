package sladki.tfc.ab.Blocks;

import java.util.Random;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sladki.tfc.ab.AutomatedBellows;
import sladki.tfc.ab.ModManager;
import sladki.tfc.ab.TileEntities.TEPotteryKiln;

public class BlockPotteryKiln extends BlockContainer {
	
	public static IIcon textureTop;
	public static IIcon textureFrontOn;
	public static IIcon textureFrontOff;
	public static IIcon textureSide;

	public BlockPotteryKiln(Material material) {
		super(material);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);

		if(world.isRemote) {
			return true;
		}
		
		int meta = world.getBlockMetadata(x, y, z);
		if(meta > 8) {
			return true;
		}

		ItemStack equippedItem = player.getCurrentEquippedItem();
		TEPotteryKiln tileEntity = (TEPotteryKiln) world.getTileEntity(x, y, z);
		
		if(equippedItem != null) {
			//If Kiln Chamber block
			if(side == 1) {
				if(equippedItem.getItem() == Item.getItemFromBlock(ModManager.potteryKilnChamberBlock)) {
					return false;
				}
			}
			
			//Fire starter of flint n' steel
			
			if(equippedItem.getItem() == TFCItems.fireStarter || equippedItem.getItem() == TFCItems.flintSteel) {
				if(world.getBlock(x, y + 1, z) == ModManager.potteryKilnChamberBlock) {
					if(tileEntity.light()) {
						kilnSetWorking(true, world, x, y, z);
						int stack = equippedItem.stackSize;
						int damage = equippedItem.getItemDamage() + 1;
						player.inventory.setInventorySlotContents(player.inventory.currentItem,
								new ItemStack(equippedItem.getItem(), stack, damage));
					}
				}
				return true;
			}
		}

		//Open gui
		if(tileEntity != null) {
			player.openGui(AutomatedBellows.instance, 0, world, x, y, z);
			return true;
		}

		return true;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);

		if(world.getBlock(x, y + 1, z) != ModManager.potteryKilnChamberBlock) {
			kilnSetWorking(false, world, x, y, z);
		}
	}
	
	public static void kilnSetWorking(boolean setWorking, World world, int x, int y, int z) {
		if(world.getBlock(x, y, z) instanceof BlockPotteryKiln) {
			int meta = world.getBlockMetadata(x, y, z);
			meta = setWorking ? meta | 8 : meta & ~8;
			world.setBlockMetadataWithNotify(x, y, z, meta, 0x2);
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (!(tileEntity instanceof TEPotteryKiln)) {
			return;
		}
		((TEPotteryKiln) tileEntity).dropItems(true);
		super.breakBlock(world, x, y, z, block, metadata);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TEPotteryKiln();
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta < 9) {
			return 0;
		} else {
			return 15;
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {		
		if(side == 1) {
			return textureTop;
		}
		
		if(side == (meta & 7)) {
			if(meta > 5) {
				return textureFrontOn;
			}
			return textureFrontOff;
		}
		
		if(side == 3 && meta == 0) {
			return textureFrontOff;
		}
		
		return textureSide;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is) {
		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;

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
		textureSide = registerer.registerIcon(AutomatedBellows.MODID + ":" + "potteryKilnSide");
		textureFrontOn = registerer.registerIcon(AutomatedBellows.MODID + ":" + "potteryKilnFrontLit");
		textureFrontOff = registerer.registerIcon(AutomatedBellows.MODID + ":" + "potteryKilnFront");
		textureTop = registerer.registerIcon(AutomatedBellows.MODID + ":" + "potteryKilnLowerTop");
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta < 8) {
			return;
		}
		
		double xT = x + 0.625 - random.nextFloat() * 0.25;
		double zT = z + 0.625 - random.nextFloat() * 0.25;
		
		world.spawnParticle("smoke", xT, y + 2.1, zT, 0.0D, 0.0D, 0.0D);
		
		double yT = y + 0.1 + random.nextFloat() * 0.2;
		xT = x + 0.75 - random.nextFloat() * 0.5;
		zT = z + 0.75 - random.nextFloat() * 0.5;
		
		if(meta == 10) {
			world.spawnParticle("flame", xT, yT, z - 0.04d, 0.0D, 0.0D, 0.0D);
		} else if(meta == 11) {
			world.spawnParticle("flame", xT, yT, z + 1.04d, 0.0D, 0.0D, 0.0D);
		} else if(meta == 12) {
			world.spawnParticle("flame", x - 0.04d, yT, zT, 0.0D, 0.0D, 0.0D);
		} else if(meta == 13) {
			world.spawnParticle("flame", x + 1.04d, yT, zT, 0.0D, 0.0D, 0.0D);
		}
		
	}

}
