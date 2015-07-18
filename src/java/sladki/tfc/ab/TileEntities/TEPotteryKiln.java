package sladki.tfc.ab.TileEntities;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import sladki.tfc.ab.Blocks.BlockPotteryKiln;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.Crafting.KilnCraftingManager;
import com.bioxx.tfc.api.Crafting.KilnRecipe;

public class TEPotteryKiln extends TileEntity implements IInventory {
	
	private ItemStack[] inventory = new ItemStack[12];	//12 slots: 1-4 for logs, 5-12 for raw pottery
	private long launchTime;
	
	private int tickCounter = 100;
	
	public TEPotteryKiln() {
	}
	
	@Override
	public void updateEntity() {
		if(worldObj.isRemote) {
			return;
		}
		
		//Fire sound
		if(launchTime > 0) {
			Random random = new Random();
			if(random.nextInt(20) == 0) {
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "fire.fire", 
						0.2f + random.nextFloat() * 0.3f, 0.4f + random.nextFloat() * 0.1f);
			}	
		}
		
		TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord);
		
		tickCounter++;
		if(tickCounter < 100) {
			return;
		}
		tickCounter = 0;
		
		//Burn 1 log each 1 IG-hour, when all logs are burnt check time (5 hours delay, 3+5=8 hours total)
		if(launchTime > 0) {
			
			if(inventory[3] == null) {
				if(TFC_Time.getTotalTicks() >= launchTime + 5 * TFC_Time.hourLength) {
					cookItems();
					stopProcessing();
				}
				return;
			}
			
			if(TFC_Time.getTotalTicks() >= launchTime + TFC_Time.hourLength) {
				for(int i = 1; i < 4; i++) {
					if(inventory[i] != null) {
						inventory[i] = null;
						launchTime = TFC_Time.getTotalTicks();
						return;
					}
				}
			}	
		}	
	}
	
	private void cookItems() {
		KilnCraftingManager kilnManager = KilnCraftingManager.getInstance();
		
		for(int i = 4; i < 12; i++) {
			if(inventory[i] != null) {
				inventory[i] = kilnManager.findCompleteRecipe(new KilnRecipe(inventory[i], 0)).copy();
			}
		}
	}
	
	public void stopProcessing() {
		launchTime = 0;
		
		Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
		if(block instanceof BlockPotteryKiln) {
			BlockPotteryKiln.kilnSetWorking(false, worldObj, xCoord, yCoord, zCoord);
		}
	}
	
	public boolean light() {
		if(launchTime == 0) {
			
			//Check fuel slots
			//And check clay slots (need at least one item to start work
			for(int i = 0; i < 12; i++) {
				if(inventory[i] == null) {
					if(i < 4 || i == 11) {
						return false;
					}
				} else {
					if(i >=4) {
						break;
					}
				}
			}
			//Burn the first log
			inventory[0] = null;
			
			launchTime = TFC_Time.getTotalTicks();
			return true;
		}
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		launchTime = tagCompound.getLong("launchTime");
		NBTTagList tagList = tagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound tag = tagList.getCompoundTagAt(i);
                byte slot = tag.getByte("Slot");
                if (slot >= 0 && slot < getSizeInventory()) {
                        inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
                }
        }
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		
		tagCompound.setLong("launchTime", launchTime);
		NBTTagList tagList = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++) {
			if(inventory[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(tag);
				tagList.appendTag(tag);
			}
		}
		tagCompound.setTag("Items", tagList);
	}
	
	public void dropItems(boolean allItems) {
		for (int i = 4; i < 12; i++) {
			if (inventory[i] != null) {
				EntityItem entityItem = new EntityItem(worldObj,
						xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, inventory[i]);
				worldObj.spawnEntityInWorld(entityItem);
				inventory[i] = null;
			}
		}
		
		if(allItems) {
			for (int i = 0; i < 4; i++) {
				if (inventory[i] != null) {
					EntityItem entityItem = new EntityItem(worldObj,
							xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, inventory[i]);
					worldObj.spawnEntityInWorld(entityItem);
					inventory[i] = null;
				}
			}
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		writeToNBT(tagCompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tagCompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}
	
	@Override
	public int getSizeInventory() {
		return 12;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = inventory[slot];
		if(stack != null) {
			if(stack.stackSize <= amount) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amount);
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
	}

	@Override
	public String getInventoryName() {
		return "Pottery Kiln";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return false;
	}

}
