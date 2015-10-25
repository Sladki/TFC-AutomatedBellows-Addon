package sladki.tfc.ab;

import java.util.List;

import com.bioxx.tfc.Core.TFC_Time;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import sladki.tfc.ab.Blocks.BlockPotteryKiln;
import sladki.tfc.ab.Blocks.BlockSteamBoiler;
import sladki.tfc.ab.TileEntities.TEPotteryKiln;

public class WailaHandler implements IWailaDataProvider {
	
	public static void callbackRegister(IWailaRegistrar register) {
		WailaHandler instance = new WailaHandler();
		
		//Steam boiler
		register.registerBodyProvider(instance, BlockSteamBoiler.class);
		register.registerNBTProvider(instance, BlockSteamBoiler.class);
		
		//Pottery kiln
		register.registerBodyProvider(instance, BlockPotteryKiln.class);
		register.registerNBTProvider(instance, BlockPotteryKiln.class);
	}
	
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		
		Block block = accessor.getBlock();
		
		if(block == ModManager.SteamBoilerBlock) {
			
			NBTTagCompound tagCompound = accessor.getNBTData();
			
			boolean containsWater = tagCompound.getBoolean("containsWater");
			boolean isWorking = tagCompound.getBoolean("isWorking");
			int fuelAmount = tagCompound.getInteger("fuelAmount");
			int delayMode = tagCompound.getInteger("delayMode");
			
			String tip = (containsWater ? (EnumChatFormatting.GREEN + "Water")
					: (EnumChatFormatting.DARK_GRAY + "Water")) + EnumChatFormatting.GRAY + " | ";

			tip += (isWorking ? (EnumChatFormatting.GREEN + "Running")
					: (EnumChatFormatting.DARK_GRAY + "Running"));
			
			currenttip.add(tip);

			currenttip.add("Fuel: " + fuelAmount + " | Delay: " + (delayMode / 20));
			
		} else if(block == ModManager.PotteryKilnBlock) {
			
			TEPotteryKiln tileEntity = (TEPotteryKiln) accessor.getTileEntity();
			NBTTagCompound tagCompound = accessor.getNBTData();
			
			long launchTime = tagCompound.getLong("launchTime");
			
			int fuelSlots = 0;
			int chamberSlots = 0;
			
			NBTTagList tagList = tagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
	        for(int i = 0; i < tagList.tagCount(); i++) {
	        	
                NBTTagCompound tag = tagList.getCompoundTagAt(i);
                
                byte slot = tag.getByte("Slot");
                if(slot >= 0 && slot < tileEntity.getSizeInventory()
                		&& ItemStack.loadItemStackFromNBT(tag) != null) {
                	if(slot < 4) {
                		fuelSlots++;
                	} else {
                		chamberSlots++;
                	}
                }
	        }
	        
	        if(accessor.getMetadata() > 8) {
				int hoursRemaining = fuelSlots + 5;
				
				if(fuelSlots == 0) {
					hoursRemaining = (int) (5 - (TFC_Time.getTotalTicks() - launchTime) / TFC_Time.HOUR_LENGTH);
				}
				
				currenttip.add((EnumChatFormatting.GREEN + "Processing"));
				currenttip.add("Hours remaining: " + hoursRemaining);
			} else {
				currenttip.add("Chamber: " + chamberSlots + "/8");
				currenttip.add("Fuel: " + fuelSlots + "/4");
			}
			
		}
		
		return currenttip;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
			int y, int z) {
		
		if(te != null) {
			te.writeToNBT(tag);
		}
            
        return tag;
	}

}