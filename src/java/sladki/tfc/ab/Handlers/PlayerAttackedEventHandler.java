package sladki.tfc.ab.Handlers;

import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.Handlers.EntityDamageHandler;
import com.bioxx.tfc.Items.Tools.ItemCustomAxe;
import com.bioxx.tfc.Items.Tools.ItemWeapon;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import sladki.tfc.ab.Config.ModConfig;
import sladki.tfc.ab.Items.Armor.ItemRoundShield;

import java.util.Random;

public class PlayerAttackedEventHandler {
	
	private static final Random rand = new Random();
	private static EntityDamageHandler entityDamageHandler = null;


	@SubscribeEvent(priority=EventPriority.LOW)
	public void onPlayerAttackedEvent(LivingAttackEvent event) {
		if(event.entityLiving.worldObj.isRemote || !(event.entityLiving instanceof EntityPlayer)) {
			return;
		}
		
		if(!event.source.getDamageType().equals("mob")
				&& !event.source.getDamageType().equals("arrow")
				&& !event.source.getDamageType().equals("player")) {
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
			if(player.getCurrentEquippedItem().getItem() instanceof ItemWeapon ||
					player.getCurrentEquippedItem().getItem() instanceof ItemCustomAxe) {
				shieldInHand = true;
			}
		}
		
		//Calculating the chance to block
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
				* (event.source.damageType.equals("arrow") ? 2 : 1)) {

			playShieldSound(player, false);
			player.hurtResistantTime = (int) (player.maxHurtResistantTime * 1.3);
			
			//Damage the shield
			ItemStack[] extraEquipInv = ((InventoryPlayerTFC) player.inventory).extraEquipInventory;
			for(int i = 0; i < extraEquipInv.length; i++) {
				ItemStack itemStack = extraEquipInv[i];
				if(itemStack != null && itemStack.getItem() instanceof ItemRoundShield) {
					if(itemStack.attemptDamageItem(applyResistanceToDamage(event.ammount, event.source, itemStack), rand)) {
						playShieldSound(player, true);
						extraEquipInv[i].stackSize = 0;
						extraEquipInv[i] = null;
					}
					break;
				}
			}
			
			event.setCanceled(true);
		}
	}

	private int applyResistanceToDamage(float damage, DamageSource sourceDamage, ItemStack shieldStack) {
		int damageTypeIndex = 3;
		for(EnumDamageType damageType : EnumDamageType.values()) {
			if(damageType == getDamageType(sourceDamage)) {
				damageTypeIndex = damageType.damageID;
				break;
			}
		}

		damageTypeIndex = (damageTypeIndex == -1) ? 3 : damageTypeIndex;
		return (int) (damage * ((ItemRoundShield) shieldStack.getItem()).getResistanceMult(damageTypeIndex));
	}

	private void playShieldSound(EntityPlayer player, boolean isBroken) {
		ItemStack itemStack = ((InventoryPlayerTFC) player.inventory).extraEquipInventory[0];

		if(ItemRoundShield.isWooden(itemStack)) {
			if(isBroken) {
				player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "mob.zombie.woodbreak", 0.4f, 0.8f);
			} else {
				player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "mob.zombie.wood", 0.3f, 1.4f);
			}

		} else {
			if (isBroken) {
				player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "random.break", 1.0f, 0.5f);
			} else {
				player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "mob.blaze.hit", 0.8f, 0.9f);
			}
		}
	}

	//Because original method is private and it's better to copy-paste rather than use the reflect
	private EnumDamageType getDamageType(DamageSource source) {
		if(source.getSourceOfDamage() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)source.getSourceOfDamage();
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ICausesDamage) {
				return ((ICausesDamage)player.getCurrentEquippedItem().getItem()).getDamageType();
			}
		}

		if(source.getSourceOfDamage() instanceof EntityLiving) {
			EntityLiving entityLiving = (EntityLiving)source.getSourceOfDamage();
			if(entityLiving.getHeldItem() != null && entityLiving.getHeldItem().getItem() instanceof ICausesDamage) {
				return ((ICausesDamage)entityLiving.getHeldItem().getItem()).getDamageType();
			}
		}

		if(source.getSourceOfDamage() instanceof ICausesDamage) {
			return ((ICausesDamage)source.getSourceOfDamage()).getDamageType();
		}

		return EnumDamageType.GENERIC;
	}
	
}
