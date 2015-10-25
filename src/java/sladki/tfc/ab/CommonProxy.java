package sladki.tfc.ab;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {

	public boolean isRemote() {
		return false;
	}
	
	public void registerGuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(AutomatedBellows.instance, new sladki.tfc.ab.Gui.GuiHandler());
	}
	
	public void registerRenderInformation()	{
	}
	
	public void registerWailaHandler() {
		FMLInterModComms.sendMessage("Waila", "register", "sladki.tfc.ab.WailaHandler.callbackRegister");
	}
	
}