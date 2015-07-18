package sladki.tfc.ab;

import sladki.tfc.ab.Blocks.BlockPotteryKilnChamber;
import sladki.tfc.ab.Render.RenderPotteryKilnChamber;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public boolean isRemote() {
		return true;
	}
	
	public void registerRenderInformation()	{
		RenderingRegistry.registerBlockHandler(BlockPotteryKilnChamber.renderId
				= RenderingRegistry.getNextAvailableRenderId(), new RenderPotteryKilnChamber());
	}

}
