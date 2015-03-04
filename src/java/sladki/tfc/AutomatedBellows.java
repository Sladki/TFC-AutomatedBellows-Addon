package sladki.tfc;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(name = AutomatedBellows.MODNAME, modid = AutomatedBellows.MODID, version = AutomatedBellows.VERSION, dependencies = "required-after:terrafirmacraft")
public class AutomatedBellows {
	public static Block BellowsDriverBlock;
	public static final String MODID = "tfcautomatedbellows";
	public static final String MODNAME = "AutomatedBellowsAddon";

	public static Block SteamBoilerBlock;
	public static final String VERSION = "1.0";

	@EventHandler
	public void init(FMLInitializationEvent event) {
		BellowsDriverBlock = new BlockBellowsDriver(Material.iron).setBlockName("BellowsDriver").setHardness(3);
		SteamBoilerBlock = new BlockSteamBoiler(Material.iron).setBlockName("SteamBoiler").setHardness(3);

		GameRegistry.registerBlock(BellowsDriverBlock, ItemBlockAutomatedBellows.class, "BellowsDriver");
		GameRegistry.registerBlock(SteamBoilerBlock, ItemBlockSteamBoiler.class, "SteamBoiler");

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BellowsDriverBlock, 1, 0), new Object[] { "   ",
				"SPS", "   ", Character.valueOf('S'), new ItemStack(TFCItems.WroughtIronSheet), Character.valueOf('P'),
				new ItemStack(Blocks.piston) }));
		GameRegistry
				.addRecipe(new ShapedOreRecipe(new ItemStack(SteamBoilerBlock, 1, 0), new Object[] { " C ", "SBS",
						" C ", Character.valueOf('C'), new ItemStack(TFCItems.ClayBall, 0, 1), Character.valueOf('S'),
						new ItemStack(TFCItems.WroughtIronSheet), Character.valueOf('B'),
						new ItemStack(TFCBlocks.FireBrick) }));

		GameRegistry.registerTileEntity(TESteamBoiler.class, "SteamBoiler");
	}

}