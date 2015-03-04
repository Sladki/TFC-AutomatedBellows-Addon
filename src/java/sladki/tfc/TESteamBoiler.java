package sladki.tfc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

public class TESteamBoiler extends TileEntity {

	private boolean containsWater = false;
	private int fuelAmount = 0;
	private boolean isWorking = false;

	public boolean addFuel(int fuel) {
		if ((fuelAmount + fuel) <= 1000) {
			fuelAmount += fuel;
			return true;
		}
		return false;
	}

	public boolean addWater() {
		if (containsWater) {
			return false;
		}
		containsWater = true;
		return true;
	}

	public boolean consumeSteam(int steam) {
		if (isWorking) {
			if ((fuelAmount - steam) <= 0) {
				isWorking = false;
				fuelAmount = 0;
				return true;
			}
			fuelAmount -= steam;
			return true;
		}
		return false;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		writeToNBT(tagCompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tagCompound);
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

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		containsWater = nbt.getBoolean("containsWater");
		isWorking = nbt.getBoolean("isWorking");
		fuelAmount = nbt.getInteger("fuelAmount");
	}

	public void showInfo(EntityPlayer player) {
		if (isWorking) {
			if (fuelAmount > 100) {
				player.addChatMessage(new ChatComponentText(
						"The steam boiler contains some fuel and is running perfectly"));
			} else {
				player.addChatMessage(new ChatComponentText("The steam boiler contains low fuel and is running"));
			}
		} else {
			if (fuelAmount > 0 && containsWater) {
				player.addChatMessage(new ChatComponentText("The steam boiler contains fuel and is ready to work"));
			} else if (!containsWater) {
				player.addChatMessage(new ChatComponentText("The steam boiler requires clean water for work"));
			} else {
				player.addChatMessage(new ChatComponentText("Put logs or coal to the burning chamber"));
			}
		}

		// System.out.println("W: " + containsWater + " L: " + isWorking + " S:"
		// + fuelAmount);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("containsWater", containsWater);
		nbt.setBoolean("isWorking", isWorking);
		nbt.setInteger("fuelAmount", fuelAmount);
	}
}
