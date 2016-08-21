package sladki.tfc.ab.Handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import sladki.tfc.ab.Entities.Mobs.Extended.EntityLivestock;

public class PlaySoundAtEntityEventHandler {

    @SubscribeEvent
    public void onPlaySoundAtEntityEvent(PlaySoundAtEntityEvent event) {
        if(!(event.entity instanceof EntityAnimal)) {
            return;
        }

        if(!EntityLivestock.isDomesticated(event.entity)) {
            return;
        }

        if(EntityLivestock.hasShepherd(event.entity)) {
            event.setCanceled(true);
        }
    }
}
