package sladki.tfc.ab.Blocks;

import java.util.List;

import sladki.tfc.ab.AutomatedBellows;
import sladki.tfc.ab.ModManager;
import sladki.tfc.ab.TileEntities.TEPotteryKiln;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPotteryKilnChamber extends Block {
	
	public static IIcon textureTop;
	public static IIcon textureSide;
	
	public static int renderId = 0;

	
	public BlockPotteryKilnChamber(Material material) {
		super(material);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
			float hitY, float hitZ) {
		super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);

		if(world.isRemote) {
			return true;
		}
		
		int meta = world.getBlockMetadata(x, y - 1, z);
		if(meta > 8) {
			return true;
		}
		
		TEPotteryKiln tileEntity = (TEPotteryKiln) world.getTileEntity(x, y - 1, z);
		if(tileEntity != null) {
			player.openGui(AutomatedBellows.instance, 1, world, x, y - 1, z);
			return true;
		}

		return true;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		
		if(world.getBlock(x, y - 1, z) != ModManager.PotteryKilnBlock) {
			int meta = world.getBlockMetadata(x, y, z);
			world.getBlock(x, y, z).dropBlockAsItem(world, x, y, z, 0, 0);
			world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if(world.getBlock(x, y - 1, z) == ModManager.PotteryKilnBlock) {
			return true;
		}
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		TileEntity tileEntity = world.getTileEntity(x, y - 1, z);
		if (tileEntity instanceof TEPotteryKiln) {
			((TEPotteryKiln) tileEntity).dropItems(false);
			((TEPotteryKiln) tileEntity).stopProcessing();
		}
		super.breakBlock(world, x, y, z, block, metadata);
	}
	
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 1) {
			return textureTop;
		}
		return textureSide;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister registerer) {
		textureSide = registerer.registerIcon(AutomatedBellows.MODID + ":" + "potteryKilnSide");
		textureTop = registerer.registerIcon(AutomatedBellows.MODID + ":" + "potteryKilnChamberTop");
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return renderId;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

}
