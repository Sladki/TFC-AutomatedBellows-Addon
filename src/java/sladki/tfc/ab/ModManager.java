package sladki.tfc.ab;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import sladki.tfc.ab.Blocks.BlockBellowsDriver;
import sladki.tfc.ab.Blocks.BlockPotteryKiln;
import sladki.tfc.ab.Blocks.BlockPotteryKilnChamber;
import sladki.tfc.ab.Blocks.BlockSteamBoiler;
import sladki.tfc.ab.Items.ItemBlockAutomatedBellows;
import sladki.tfc.ab.Items.ItemBlockPotteryKiln;
import sladki.tfc.ab.Items.ItemBlockPotteryKilnChamber;
import sladki.tfc.ab.Items.ItemBlockSteamBoiler;
import sladki.tfc.ab.TileEntities.TEPotteryKiln;
import sladki.tfc.ab.TileEntities.TESteamBoiler;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModManager {

	//Blocks
	public static Block BellowsDriverBlock;
	public static Block SteamBoilerBlock;
	
	public static Block PotteryKilnBlock;
	public static Block PotteryKilnChamberBlock;
	
	public static void loadBlocks() {
		BellowsDriverBlock = new BlockBellowsDriver(Material.iron).setBlockName("BellowsDriver").setHardness(8).setResistance(8);
		SteamBoilerBlock = new BlockSteamBoiler(Material.iron).setBlockName("SteamBoiler").setHardness(8).setResistance(8);
		
		PotteryKilnBlock = new BlockPotteryKiln(Material.rock).setBlockName("PotteryKiln").setHardness(8).setResistance(8);
		PotteryKilnChamberBlock = new BlockPotteryKilnChamber(Material.rock).setBlockName("PotteryKilnChamber").setHardness(8).setResistance(8);
	}
	
	public static void registerBlocks() {
		GameRegistry.registerBlock(BellowsDriverBlock, ItemBlockAutomatedBellows.class, "BellowsDriver");
		GameRegistry.registerBlock(SteamBoilerBlock, ItemBlockSteamBoiler.class, "SteamBoiler");
		
		GameRegistry.registerBlock(PotteryKilnBlock, ItemBlockPotteryKiln.class, "PotteryKiln");
		GameRegistry.registerBlock(PotteryKilnChamberBlock, ItemBlockPotteryKilnChamber.class, "PotteryKilnChamber");
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TESteamBoiler.class, "SteamBoiler");
		GameRegistry.registerTileEntity(TEPotteryKiln.class, "PotteryKiln");
	}
	
	public static void registerRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BellowsDriverBlock, 1, 0), new Object[] {
				"   ", "SPS", "   ",
				Character.valueOf('S'), new ItemStack(TFCItems.WroughtIronSheet),
				Character.valueOf('P'),	new ItemStack(Blocks.piston)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(SteamBoilerBlock, 1, 0), new Object[] {
				" C ", "SBS", " C ",
				Character.valueOf('C'), new ItemStack(TFCItems.ClayBall, 0, 1),
				Character.valueOf('S'), new ItemStack(TFCItems.WroughtIronSheet),
				Character.valueOf('B'), new ItemStack(TFCBlocks.FireBrick)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PotteryKilnBlock, 1, 0), new Object[] {
			"BBB", "C C", "BBB",
			Character.valueOf('C'), new ItemStack(TFCItems.ClayBall, 0, 1),
			Character.valueOf('B'), new ItemStack(TFCItems.FireBrick, 0, 1) 
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PotteryKilnChamberBlock, 1, 0), new Object[] {
			"B B", "C C", "B B",
			Character.valueOf('C'), new ItemStack(TFCItems.ClayBall, 0, 1),
			Character.valueOf('B'), new ItemStack(TFCItems.FireBrick, 0, 1)
		}));
	}
	
	public static void sendMessage(EntityPlayer player, IChatComponent message) {
		message.getChatStyle().setColor(EnumChatFormatting.GRAY);
		player.addChatComponentMessage(message);
	}
	
}