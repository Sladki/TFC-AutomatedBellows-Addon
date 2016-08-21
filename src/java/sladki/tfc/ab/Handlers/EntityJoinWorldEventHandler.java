package sladki.tfc.ab.Handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import sladki.tfc.ab.Entities.Mobs.Extended.EntityLivestock;

public class EntityJoinWorldEventHandler {

    @SubscribeEvent
    public void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
        if(event.world.isRemote || !(event.entity instanceof EntityAnimal)) {
            return;
        }

        if(!EntityLivestock.isDomesticated(event.entity)) {
            return;
        }

        if(EntityLivestock.hasShepherd(event.entity)) {
            EntityLivestock.setHerdAI(event.entity);
        }
    }
}
