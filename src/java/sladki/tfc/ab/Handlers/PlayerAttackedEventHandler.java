package sladki.tfc.ab.Handlers;

import java.util.Random;

import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.Items.Tools.ItemWeapon;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import sladki.tfc.ab.Config.ModConfig;
import sladki.tfc.ab.Items.Armor.ItemRoundShield;

public class PlayerAttackedEventHandler {
	
	private static final Random rand = new Random();
	
	
	
	@SubscribeEvent(priority=EventPriority.LOW)
	public void onPlayerAttackedEvent(LivingAttackEvent event) {
		if(event.entityLiving.worldObj.isRemote || !(event.entityLiving instanceof EntityPlayer)) {
			return;
		}
		
		if(event.source.getDamageType() != "mob"
				&& event.source.getDamageType() != "arrow"
				&& event.source.getDamageType() != "player") {
			return;
		}
		
		EntityPlayer player = (EntityPlayer) event.entityLiving;
		
		//Checking the resistant timer (normally can't be damaged for some ticks after any hit with greater damage)
		if(player.hurtResistantTime > player.maxHurtResistantTime * 0.5f) {
			return;
		}
		
		//Checking for an equipped shield 
		if(player.inventory instanceof InventoryPlayerTFC) {
			ItemStack[] extraEquipInv = ((InventoryPlayerTFC) player.inventory).extraEquipInventory;
			boolean hasShield = false;
			
			for(ItemStack itemStack : extraEquipInv) {
				if(itemStack != null && itemStack.getItem() instanceof ItemRoundShield) {
					hasShield = true;
					break;
				}
			}
			
			if(!hasShield) {
				return;
			}
		}
		
		//Checking for a shield mount (TRUE if a player is holding weapon in the main hand)
		boolean shieldInHand = false;
		if(player.getCurrentEquippedItem() != null) {
			if(player.getCurrentEquippedItem().getItem() instanceof ItemWeapon) {
				shieldInHand = true;
			}
		}
		
		//Calculating a chance to block
		Entity dmgSource = event.source.getEntity();
		Vec3 playerVector = player.getLookVec();
		Vec3 monsterVector = Vec3.createVectorHelper(dmgSource.posX - player.posX, dmgSource.posY - player.posY - 0.3d, dmgSource.posZ - player.posZ).normalize();
		playerVector.yCoord *= 0.7; monsterVector.yCoord *= 0.7;	//To make attacks from up and bottom easier to block
		
		double blockChance = (playerVector.dotProduct(monsterVector) + playerVector.dotProduct(monsterVector) * 0.2 + 0.1) * (shieldInHand ? 1 : -1);
		if(blockChance < 0) {
			return;
		}
		
		blockChance = Math.min(1.0, blockChance);
		if(rand.nextInt(100) < blockChance * ModConfig.shieldsBlockChance
				* (player.isBlocking() ? 4 : 1)
				* (event.source.damageType == "arrow" ? 2 : 1)) {
			player.hurtResistantTime = (int) (player.maxHurtResistantTime * 1.3);
			player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "mob.blaze.hit", 0.8f, 0.7f);
			
			//event.entity.worldObj.setEntityState(event.entityLiving, (byte) 2);
			
			//Damage the shield
			ItemStack[] extraEquipInv = ((InventoryPlayerTFC) player.inventory).extraEquipInventory;
			for(int i = 0; i < extraEquipInv.length; i++) {
				ItemStack itemStack = extraEquipInv[i];
				if(itemStack != null && itemStack.getItem() instanceof ItemRoundShield) {
					if(itemStack.attemptDamageItem((int) (event.ammount), rand)) {
						player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "random.break", 1.0f, 0.9f);
						extraEquipInv[i].stackSize = 0;
						extraEquipInv[i] = null;
					}
					break;
				}
			}
			
			event.setCanceled(true);
		}
	}
	
}
