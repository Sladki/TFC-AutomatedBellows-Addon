package sladki.tfc.ab.Blocks;

import java.util.Random;

import sladki.tfc.ab.AutomatedBellows;
import sladki.tfc.ab.ModManager;
import sladki.tfc.ab.TileEntities.TESteamBoiler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.TileEntities.TEBellows;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class BlockWaterFilter extends Block {

	public static IIcon textureFace;
	public static IIcon textureTop;
	public static IIcon textureSide;

	public BlockWaterFilter(Material material) {
		super(material);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random a) {
		if(!world.isRemote) {
			this.makeWater(world, x, y, z);
		}
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!world.isRemote) {	
	    	this.makeWater(world, x, y, z);
        }
    }
	
	private void makeWater(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		
		//Offset of the front block, multiply by -1 to get the back block offset
		int oX = 0;	int oZ = 0;
		if (meta == 2) {
			oZ++;
		} else if (meta == 5) {
			oX--;
		} else if (meta == 3) {
			oZ--;
		} else {
			oX++;
		}
		
		Block frontBlock = world.getBlock(x + oX, y, z + oZ);
		
		//Both side can be "in" and "out", so need to know where is the source
		if(frontBlock == TFCBlocks.hotWaterStationary) {
			frontBlock = world.getBlock(x - oX, y, z - oZ);	//Swapping	
			oX = -oX;	
			oZ = -oZ;
		}

		if(world.getBlock(x - oX, y, z - oZ) != TFCBlocks.hotWaterStationary ) {
			if(frontBlock == TFCBlocks.freshWaterStationary) {
				world.setBlockMetadataWithNotify(x + oX, y, z + oZ, 2, 3);
			}
			world.scheduleBlockUpdate(x, y, z, this, 4);
			
			return;
		}
		
		if(frontBlock == TFCBlocks.freshWater) {
			world.setBlock(x + oX, y, z + oZ, TFCBlocks.freshWaterStationary, 1, 2);
			world.scheduleBlockUpdate(x, y, z, this, 1);
			return;
		}
		
		if(frontBlock == TFCBlocks.freshWaterStationary) {
			if(world.getBlockMetadata(x + oX, y, z + oZ) != 1) {
				world.setBlockMetadataWithNotify(x + oX, y, z + oZ, 1, 2);
			}
			return;
		}
		
		if(world.isAirBlock(x + oX, y, z + oZ)) {
			world.setBlock(x + oX, y, z + oZ, TFCBlocks.freshWater, 0, 2);
			world.scheduleBlockUpdate(x, y, z, this, 3);
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {		
		if(side == meta) {
			return textureFace;
		}
		
		if(side == 1) {
			return textureTop;
		}
		
		if(side == 2 || side == 3) {
			if(meta == 2 || meta == 3) {
				return textureFace;
			}
		}
		
		if(side == 4 || side == 5) {
			if(meta == 4 || meta == 5) {
				return textureFace;
			}
		}
		
		if(side == 3 && meta == 0) {
			return textureFace;
		}
		
		return textureSide;
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
		textureSide = registerer.registerIcon(AutomatedBellows.MODID + ":" + "waterFilterSide");
		textureFace = registerer.registerIcon(AutomatedBellows.MODID + ":" + "waterFilterFace");
		textureTop = registerer.registerIcon(AutomatedBellows.MODID + ":" + "waterFilterTop");
	}
}