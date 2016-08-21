package sladki.tfc.ab.Handlers.Network;

import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import sladki.tfc.ab.Items.Armor.ItemRoundShield;

public class PlayerEquipUpdatePacket extends AbstractPacket {
	
	private int playerId;
	private int equipId;
	
	
	
	public PlayerEquipUpdatePacket() {}
	
	public PlayerEquipUpdatePacket(int playerId, int equipId) {
		this.playerId = playerId;
		this.equipId = equipId;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(playerId);
		buffer.writeInt(equipId);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		playerId = buffer.readInt();
		equipId = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if(Minecraft.getMinecraft().thePlayer.getEntityId() == playerId) {
			return;
		}
		
		Entity playerToUpd = player.worldObj.getEntityByID(playerId);
		if(playerToUpd == null || !(playerToUpd instanceof EntityPlayer)) {
			return;
		}
		
		ItemStack itemStack = (equipId > 0) ? ItemRoundShield.getShieldFromId(equipId) : null;
		((InventoryPlayerTFC)((EntityPlayer) playerToUpd).inventory).extraEquipInventory[0] = itemStack;
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		// Nothing to do		
	}

}
