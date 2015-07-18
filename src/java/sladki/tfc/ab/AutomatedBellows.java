package sladki.tfc.ab;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(name = AutomatedBellows.MODNAME, modid = AutomatedBellows.MODID, version = AutomatedBellows.VERSION, dependencies = "required-after:terrafirmacraft")
public class AutomatedBellows {

	public static final String MODID = "tfcautomatedbellows";
	public static final String MODNAME = "AutomatedBellowsAddon";
	public static final String VERSION = "1.01";
	
	@Instance("tfcautomatedbellows")
    public static AutomatedBellows instance;
	
	@SidedProxy(clientSide = "sladki.tfc.ab.ClientProxy", serverSide = "sladki.tfc.ab.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ModManager.loadBlocks();
		ModManager.registerBlocks();
		
		ModManager.registerTileEntities();
		ModManager.registerRecipes();
		
		proxy.registerRenderInformation();
		proxy.registerGuiHandler();
	}

}