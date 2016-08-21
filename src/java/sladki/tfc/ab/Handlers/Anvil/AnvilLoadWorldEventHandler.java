package sladki.tfc.ab.Handlers.Anvil;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;
import sladki.tfc.ab.ModManager;

public class AnvilLoadWorldEventHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLoadWorld(WorldEvent.Load event) {
        if(event.world.provider.dimensionId == 0) {
            if(event.world.isRemote) {
                FMLCommonHandler.instance().bus().register(ModManager.getAnvilPlayerTickEventHandler());
            } else {
                ModManager.registerAnvilRecipes();
            }
        }
    }
}
