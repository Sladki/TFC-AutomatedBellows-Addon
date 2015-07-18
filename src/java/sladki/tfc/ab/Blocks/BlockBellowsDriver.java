package sladki.tfc.ab.Blocks;

import java.util.Random;

import sladki.tfc.ab.AutomatedBellows;
import sladki.tfc.ab.TileEntities.TESteamBoiler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TEBellows;

public class BlockBellowsDriver extends Block {

	public static IIcon textureBack;
	public static IIcon textureFront;
	public static IIcon textureSide;

	public BlockBellowsDriver(Material material) {
		super(material);
		this.setCreativeTab(TFCTabs.TFCDevices);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random a) {
		if (!world.isRemote && world.isBlockIndirectlyGettingPowered(x, y, z)) {
			activateBellows(world, x, y, z);
		}
		world.scheduleBlockUpdate(x, y, z, this, 10);
	}

	private boolean activateBellows(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);

		TEBellows bellows = getBellows(world, x, y, z, meta);
		TESteamBoiler steamBoiler = getSteamBoiler(world, x, y, z, meta);

		if (world.isRemote) {
			return false;
		}

		if (bellows == null || steamBoiler == null) {
			return false;
		}

		if (!bellows.shouldBlow) {
			if (steamBoiler.getSteam()) {
				bellows.shouldBlow = true;
				world.markBlockForUpdate(bellows.xCoord, bellows.yCoord, bellows.zCoord);
				world.markBlockForUpdate(steamBoiler.xCoord, steamBoiler.yCoord, steamBoiler.zCoord);
			}
		}
		return true;
	}

	private TEBellows getBellows(World world, int x, int y, int z, int meta) {
		int bellowsX = x;
		int bellowsZ = z;

		if (meta == 2) {
			bellowsZ++;
		} else if (meta == 5) {
			bellowsX--;
		} else if (meta == 3) {
			bellowsZ--;
		} else {
			bellowsX++;
		}

		TileEntity tileEntity = world.getTileEntity(bellowsX, y, bellowsZ);
		if (tileEntity != null && tileEntity instanceof TEBellows) {
			return ((TEBellows) tileEntity);
		}
		return null;
	}

	@Override
	public IIcon getIcon(int side, int meta) {		
		if(side == meta) {
			return textureBack;
		}
		
		if(side == 2 || side == 3) {
			if(meta == 2 || meta == 3) {
				return textureFront;
			}
		}
		
		if(side == 4 || side == 5) {
			if(meta == 4 || meta == 5) {
				return textureFront;
			}
		}
		
		if(side == 3 && meta == 0) {
			return textureFront;
		}
		
		return textureSide;
	}

	private TESteamBoiler getSteamBoiler(World world, int x, int y, int z, int meta) {
		int steamBoilerX = x;
		int steamBoilerZ = z;

		if (meta == 2) {
			steamBoilerZ--;
		} else if (meta == 5) {
			steamBoilerX++;
		} else if (meta == 3) {
			steamBoilerZ++;
		} else {
			steamBoilerX--;
		}

		TileEntity tileEntity = world.getTileEntity(steamBoilerX, y, steamBoilerZ);
		if (tileEntity != null && tileEntity instanceof TESteamBoiler) {
			return ((TESteamBoiler) tileEntity);
		}
		return null;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (!world.isRemote) {
			world.scheduleBlockUpdate(x, y, z, this, 20);
		}
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
		textureSide = registerer.registerIcon(AutomatedBellows.MODID + ":" + "bellowsDriverSide");
		textureFront = registerer.registerIcon(AutomatedBellows.MODID + ":" + "bellowsDriverFront");
		textureBack = registerer.registerIcon(AutomatedBellows.MODID + ":" + "bellowsDriverBack");
	}
}