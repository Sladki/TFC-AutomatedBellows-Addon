package sladki.tfc.ab.Handlers.Anvil;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import sladki.tfc.ab.ModManager;


public class AnvilPlayerTickEventHandler {

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        ModManager.registerAnvilRecipes();
        FMLCommonHandler.instance().bus().unregister(ModManager.getAnvilPlayerTickEventHandler());
    }
}
