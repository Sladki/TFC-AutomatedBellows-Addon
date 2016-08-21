package sladki.tfc.ab.Handlers;

import com.bioxx.tfc.Handlers.CraftingHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.oredict.OreDictionary;
import sladki.tfc.ab.ModManager;

public class ItemCraftedEventHandler {

    @SubscribeEvent
    public void onItemCraftedEventHandler(PlayerEvent.ItemCraftedEvent event) {
        if(event.craftMatrix == null) {
            return;
        }

        if(event.crafting.getItem() == ModManager.shieldRegistry[14]) {
            CraftingHandler.handleItem(event.player, event.craftMatrix, OreDictionary.getOres("itemAxe", false));
        }

        if(event.crafting.getItem() == ModManager.shepherdStaff) {
            CraftingHandler.handleItem(event.player, event.craftMatrix, OreDictionary.getOres("itemKnife", false));
        }
    }
}
