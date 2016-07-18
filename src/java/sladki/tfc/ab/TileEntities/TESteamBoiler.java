package sladki.tfc.ab.TileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import sladki.tfc.ab.ModManager;

public class TESteamBoiler extends TileEntity {

	private boolean containsWater = false;
	private boolean isWorking = false;
	
	private boolean hasSteam = false;
	private int fuelAmount = 0;
	private int delayMode = 20;	//1, 3, 6, 10 seconds delay modes (in ticks)
	
	private int tickCounter = 0;
	
	@Override
	public void updateEntity() {
		if(worldObj.isRemote) {
			return;
		}
		
		if(!isWorking || hasSteam) {
			return;
		}
		
		if(fuelAmount == 0) {
			isWorking = false;
		}
		
		tickCounter++;
		
		if(tickCounter >= delayMode) {
			hasSteam = true;
			tickCounter = 0;
			fuelAmount--;
		}
	}
	
	//Switches delay modes and returns delay in seconds
	public int changeMode() {
		if(delayMode == 200) {
			delayMode = 120;
		} else if(delayMode == 120) {
			delayMode = 60;
		} else if(delayMode == 60) {
			delayMode = 20;
		} else if(delayMode == 20) {
			delayMode = 200;
		}
		return (delayMode / 20);
	}
	
	//mode: true - fuel, false - water
	public boolean fuelBoiler(boolean mode, int fuel) {
		if(mode) {
			if((fuelAmount + fuel) <= 1000) {
				fuelAmount += fuel;
				return true;
			}
		} else {
			if(!containsWater) {
				containsWater = true;
				return true;
			}
		}
		return false;
	}
	
	public boolean getSteam() {
		if(hasSteam) {
			hasSteam = false;
			return true;
		}
		return false;
	}
	
	public boolean launch() {
		if (isWorking) {
			return false;
		}
		if (containsWater && fuelAmount > 0) {
			isWorking = true;
			return true;
		}
		return false;
	}
	
	public void showInfo(EntityPlayer player) {
		if (isWorking) {
			if (fuelAmount > 100) {
				ModManager.sendMessage(player, new ChatComponentText(
						"The steam boiler contains some fuel and is running perfectly"));
			} else {
				ModManager.sendMessage(player, new ChatComponentText(
						"The steam boiler is running and contains low fuel"));
			}
		} else {
			if (fuelAmount > 0 && containsWater) {
				ModManager.sendMessage(player, new ChatComponentText(
						"The steam boiler contains fuel and is ready to work"));
			} else if (!containsWater) {
				ModManager.sendMessage(player, new ChatComponentText(
						"The steam boiler requires clean water for work"));
			} else {
				ModManager.sendMessage(player, new ChatComponentText(
						"Put logs or coal to the burning chamber"));
			}
		}

		// System.out.println("W: " + containsWater + " L: " + isWorking + " S:"
		// + fuelAmount);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		containsWater = nbt.getBoolean("containsWater");
		isWorking = nbt.getBoolean("isWorking");
		fuelAmount = nbt.getInteger("fuelAmount");
		delayMode = nbt.getInteger("delayMode");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("containsWater", containsWater);
		nbt.setBoolean("isWorking", isWorking);
		nbt.setInteger("fuelAmount", fuelAmount);
		nbt.setInteger("delayMode", delayMode);
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

}
