package sladki.tfc.ab.Handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.event.entity.EntityEvent;
import sladki.tfc.ab.Entities.Mobs.Extended.EntityLivestock;


public class EntityConstructingEventHandler {

    @SubscribeEvent
    public void onEntityConstructingEvent(EntityEvent.EntityConstructing event) {
        if(!(event.entity instanceof EntityAnimal)) {
            return;
        }

        if(!EntityLivestock.isDomesticated(event.entity)) {
            return;
        }

        event.entity.registerExtendedProperties(EntityLivestock.extensionTagName, new EntityLivestock());
    }
}
