package sladki.tfc.ab.Handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import sladki.tfc.ab.Entities.Mobs.Extended.EntityLivestock;
import sladki.tfc.ab.Items.ItemShepherdStaff;

public class PlayerInteractLivingEventHandler {

    @SubscribeEvent
    public void onPlayerInteractLivingEvent(EntityInteractEvent event) {
        if(event.entityPlayer.worldObj.isRemote || !(event.target instanceof EntityAnimal)) {
            return;
        }

        if(!EntityLivestock.isDomesticated(event.target)) {
            return;
        }

        if(event.entityPlayer.getCurrentEquippedItem() != null &&
                event.entityPlayer.getCurrentEquippedItem().getItem() instanceof ItemShepherdStaff) {

            EntityLivestock.toggleHerd(event.target);
            EntityLivestock.setHerdAI(event.target);
        }
    }
}
