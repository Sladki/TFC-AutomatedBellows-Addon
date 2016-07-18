package sladki.tfc.ab;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import sladki.tfc.ab.Blocks.BlockBellowsDriver;
import sladki.tfc.ab.Blocks.BlockPotteryKiln;
import sladki.tfc.ab.Blocks.BlockPotteryKilnChamber;
import sladki.tfc.ab.Blocks.BlockSteamBoiler;
import sladki.tfc.ab.Blocks.BlockWaterFilter;
import sladki.tfc.ab.Config.ModConfig;
import sladki.tfc.ab.Items.ItemBlockAutomatedBellows;
import sladki.tfc.ab.Items.ItemBlockPotteryKiln;
import sladki.tfc.ab.Items.ItemBlockPotteryKilnChamber;
import sladki.tfc.ab.Items.ItemBlockSteamBoiler;
import sladki.tfc.ab.Items.ItemBlockWaterFilter;
import sladki.tfc.ab.Items.Armor.ItemRoundShield;
import sladki.tfc.ab.TileEntities.TEPotteryKiln;
import sladki.tfc.ab.TileEntities.TESteamBoiler;

public class ModManager {

	//Blocks
	public static Block bellowsDriverBlock;
	public static Block steamBoilerBlock;
	
	public static Block potteryKilnBlock;
	public static Block potteryKilnChamberBlock;
	
	public static Block waterFilterBlock;
	
	//Items
	public static Item wroughtIronRoundShield;
	
	
	
	public static void loadBlocks() {
		if(ModConfig.ABEnabled) {
			bellowsDriverBlock = new BlockBellowsDriver(Material.iron).setBlockName("BellowsDriver").setHardness(8).setResistance(8);
			steamBoilerBlock = new BlockSteamBoiler(Material.iron).setBlockName("SteamBoiler").setHardness(8).setResistance(8);
		}
		
		if(ModConfig.PKEnabled) {
			potteryKilnBlock = new BlockPotteryKiln(Material.rock).setBlockName("PotteryKiln").setHardness(8).setResistance(8);
			potteryKilnChamberBlock = new BlockPotteryKilnChamber(Material.rock).setBlockName("PotteryKilnChamber").setHardness(8).setResistance(8);
		}
		
		if(ModConfig.WFEnabled) {
			waterFilterBlock = new BlockWaterFilter(Material.iron).setBlockName("WaterFilter").setHardness(8).setResistance(8);
		}
	}
	
	public static void registerBlocks() {
		if(ModConfig.ABEnabled) {
			GameRegistry.registerBlock(bellowsDriverBlock, ItemBlockAutomatedBellows.class, "BellowsDriver");
			GameRegistry.registerBlock(steamBoilerBlock, ItemBlockSteamBoiler.class, "SteamBoiler");
		}
		
		if(ModConfig.PKEnabled) {
			GameRegistry.registerBlock(potteryKilnBlock, ItemBlockPotteryKiln.class, "PotteryKiln");
			GameRegistry.registerBlock(potteryKilnChamberBlock, ItemBlockPotteryKilnChamber.class, "PotteryKilnChamber");
		}
		
		if(ModConfig.WFEnabled) {
			GameRegistry.registerBlock(waterFilterBlock, ItemBlockWaterFilter.class, "WaterFilter");
		}
	}
	
	public static void registerTileEntities() {
		if(ModConfig.ABEnabled) {
			GameRegistry.registerTileEntity(TESteamBoiler.class, "SteamBoiler");
		}
		
		if(ModConfig.PKEnabled) {
			GameRegistry.registerTileEntity(TEPotteryKiln.class, "PotteryKiln");
		}
	}
	
	public static void loadItems() {
		if(ModConfig.shieldsEnabled) {
			wroughtIronRoundShield = new ItemRoundShield().setUnlocalizedName("WroughtIronRoundShield");
		}
	}
	
	public static void registerItems() {
		if(ModConfig.shieldsEnabled) {
			GameRegistry.registerItem(wroughtIronRoundShield, "WroughtIronRoundShield");
		}
	}
	
	public static void registerRecipes() {
		if(ModConfig.ABEnabled) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bellowsDriverBlock, 1, 0), new Object[] {
					"   ", "SPS", "   ",
					Character.valueOf('S'), new ItemStack(TFCItems.wroughtIronSheet),
					Character.valueOf('P'),	new ItemStack(Blocks.piston)
			}));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steamBoilerBlock, 1, 0), new Object[] {
					" C ", "SBS", " C ",
					Character.valueOf('C'), new ItemStack(TFCItems.clayBall, 0, 1),
					Character.valueOf('S'), new ItemStack(TFCItems.wroughtIronSheet),
					Character.valueOf('B'), new ItemStack(TFCBlocks.fireBrick)
			}));
		}
		
		if(ModConfig.PKEnabled) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(potteryKilnBlock, 1, 0), new Object[] {
				"BBB", "C C", "BBB",
				Character.valueOf('C'), new ItemStack(TFCItems.clayBall, 0, 1),
				Character.valueOf('B'), new ItemStack(TFCItems.fireBrick, 0, 1)
			}));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(potteryKilnChamberBlock, 1, 0), new Object[] {
				"B B", "C C", "B B",
				Character.valueOf('C'), new ItemStack(TFCItems.clayBall, 0, 1),
				Character.valueOf('B'), new ItemStack(TFCItems.fireBrick, 0, 1)
			}));
		}
		
		if(ModConfig.WFEnabled) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(waterFilterBlock, 1, 0), new Object[] {
				" S ", "BGB", " C ",
				Character.valueOf('S'), "blockSand",
				Character.valueOf('B'), new ItemStack(TFCItems.bronzeSheet),
				Character.valueOf('G'), "blockGravel",
				Character.valueOf('C'), "cobblestone"
			}));
		}
		
		if(ModConfig.shieldsEnabled) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wroughtIronRoundShield, 1, 0), new Object[] {
				" P ", "PIP", "PPP",
				Character.valueOf('P'), "woodLumber",
				Character.valueOf('I'), new ItemStack(TFCItems.wroughtIronIngot)
			}));
		}
	}
	
	public static void sendMessage(EntityPlayer player, IChatComponent message) {
		message.getChatStyle().setColor(EnumChatFormatting.GRAY);
		player.addChatComponentMessage(message);
	}
	
}