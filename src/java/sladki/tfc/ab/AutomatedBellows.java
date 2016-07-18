package sladki.tfc.ab;

import com.bioxx.tfc.TerraFirmaCraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import sladki.tfc.ab.Config.ModConfig;
import sladki.tfc.ab.Handlers.ContainerHandler;
import sladki.tfc.ab.Handlers.PlayerAttackedEventHandler;
import sladki.tfc.ab.Handlers.PlayerUpdateEventHandler;
import sladki.tfc.ab.Handlers.Network.PlayerEquipUpdatePacket;

@Mod(name = AutomatedBellows.MODNAME, modid = AutomatedBellows.MODID, version = AutomatedBellows.VERSION, dependencies = "required-after:terrafirmacraft")
public class AutomatedBellows {

	public static final String MODID = "tfcautomatedbellows";
	public static final String MODNAME = "AutomatedBellowsAddon";
	public static final String VERSION = "1.1b";
	
	@Instance(AutomatedBellows.MODID)
    public static AutomatedBellows instance;
	
	@SidedProxy(clientSide = "sladki.tfc.ab.ClientProxy", serverSide = "sladki.tfc.ab.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModConfig.loadConfig(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if(ModConfig.CHEnabled) {
			MinecraftForge.EVENT_BUS.register(new ContainerHandler());
		}
		
		if(ModConfig.shieldsEnabled) {
			MinecraftForge.EVENT_BUS.register(new PlayerAttackedEventHandler());
			MinecraftForge.EVENT_BUS.register(new PlayerUpdateEventHandler());
			
			TerraFirmaCraft.PACKET_PIPELINE.registerPacket(PlayerEquipUpdatePacket.class);
		}
		
		ModManager.loadBlocks();
		ModManager.registerBlocks();
		
		ModManager.loadItems();
		ModManager.registerItems();
		
		ModManager.registerTileEntities();
		ModManager.registerRecipes();
		
		proxy.registerRenderInformation();
		proxy.registerGuiHandler();
		
		proxy.registerWailaHandler();
	}
}