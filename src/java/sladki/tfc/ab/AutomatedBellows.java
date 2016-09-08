package sladki.tfc.ab;

import com.bioxx.tfc.TerraFirmaCraft;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import sladki.tfc.ab.Config.ModConfig;
import sladki.tfc.ab.Entities.Mobs.Extended.EntityLivestock;
import sladki.tfc.ab.Handlers.Anvil.AnvilCraftEventHandler;
import sladki.tfc.ab.Handlers.Anvil.AnvilLoadWorldEventHandler;
import sladki.tfc.ab.Handlers.*;
import sladki.tfc.ab.Handlers.Network.PlayerEquipUpdatePacket;

@Mod(name = AutomatedBellows.MODNAME, modid = AutomatedBellows.MODID, version = AutomatedBellows.VERSION, dependencies = "required-after:terrafirmacraft")
public class AutomatedBellows {

	public static final String MODID = "tfcautomatedbellows";
	public static final String MODNAME = "AutomatedBellowsAddon";
	public static final String VERSION = "1.1a";
	
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
		if(ModConfig.shieldsEnabled || ModConfig.shepherdStaffEnabled) {
			FMLCommonHandler.instance().bus().register(new ItemCraftedEventHandler());
		}

		if(ModConfig.CHEnabled) {
			MinecraftForge.EVENT_BUS.register(new ContainerHandler());
		}
		
		if(ModConfig.shieldsEnabled) {
			MinecraftForge.EVENT_BUS.register(new PlayerAttackedEventHandler());
			MinecraftForge.EVENT_BUS.register(new PlayerUpdateEventHandler());
			MinecraftForge.EVENT_BUS.register(new AnvilLoadWorldEventHandler());
			MinecraftForge.EVENT_BUS.register(new AnvilCraftEventHandler());

			TerraFirmaCraft.PACKET_PIPELINE.registerPacket(PlayerEquipUpdatePacket.class);
		}

		if(ModConfig.shepherdStaffEnabled) {
			EntityLivestock.setup();
			MinecraftForge.EVENT_BUS.register(new EntityConstructingEventHandler());
			MinecraftForge.EVENT_BUS.register(new PlayerInteractLivingEventHandler());
			MinecraftForge.EVENT_BUS.register(new EntityJoinWorldEventHandler());

			if(ModConfig.herdMuted) {
				MinecraftForge.EVENT_BUS.register(new PlaySoundAtEntityEventHandler());
			}
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