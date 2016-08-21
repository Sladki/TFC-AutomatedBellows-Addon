package sladki.tfc.ab.Config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class ModConfig {
	
	//Automated bellows
	public static boolean ABEnabled;
	
	//Water filter
	public static boolean WFEnabled;
	
	//Pottery kiln
	public static boolean PKEnabled;
	
	//Container handler
	public static boolean CHEnabled;
	
	//Shields
	public static boolean shieldsEnabled;
	public static int shieldsBlockChance;

	//Herd staff
	public static boolean shepherdStaffEnabled;
	public static boolean herdMuted;
	

	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();
		
		//Automated bellows
		ABEnabled = config.getBoolean("enabled", "automated_bellows", true, "");
		
		//Water filter
		WFEnabled = config.getBoolean("enabled", "water_filter", true, "");
		
		//Pottery kiln
		PKEnabled = config.getBoolean("enabled", "pottery_kiln", true, "");
		
		//Container handler
		CHEnabled = config.getBoolean("enabled", "container_handler", true
				, "Ejects all food fron non TFC containers on opening by a player. 'True' if enabled.");
		
		//Shields
		shieldsEnabled = config.getBoolean("enabled", "shields", true, "");
		shieldsBlockChance = config.getInt("block_chance", "shields", 25, 0, 100
				, "The chance to block an attack with 90 angle while not blocking. "
						+ "The chance to block while blocking is 4 times greater.");
			
		//Herd staff
		shepherdStaffEnabled = config.getBoolean("enabled", "shepherd_staff", true, "");
		herdMuted = config.getBoolean("herd_keeps_silence", "shepherd_staff", true, "");

		config.save();
	}

}
