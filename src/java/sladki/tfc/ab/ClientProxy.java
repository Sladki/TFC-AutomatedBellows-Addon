package sladki.tfc.ab;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
import sladki.tfc.ab.Blocks.BlockPotteryKilnChamber;
import sladki.tfc.ab.Config.ModConfig;
import sladki.tfc.ab.Handlers.Client.PlayerRenderHandler;
import sladki.tfc.ab.Render.RenderPotteryKilnChamber;


public class ClientProxy extends CommonProxy {
	
	@Override
	public boolean isRemote() {
		return true;
	}
	
	public void registerRenderInformation()	{
		if(ModConfig.PKEnabled) {
			RenderingRegistry.registerBlockHandler(BlockPotteryKilnChamber.renderId
					= RenderingRegistry.getNextAvailableRenderId(), new RenderPotteryKilnChamber());
		}

		if(ModConfig.shieldsEnabled) {
			PlayerRenderHandler pRHandler = new PlayerRenderHandler();
			MinecraftForge.EVENT_BUS.register(pRHandler);
			FMLCommonHandler.instance().bus().register(pRHandler);
		}
	}

}
