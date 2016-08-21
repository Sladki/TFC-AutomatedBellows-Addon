package sladki.tfc.ab;

import com.bioxx.tfc.api.Armor;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;
import com.bioxx.tfc.api.Crafting.PlanRecipe;
import com.bioxx.tfc.api.Enums.RuleEnum;
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
import sladki.tfc.ab.Blocks.*;
import sladki.tfc.ab.Config.ModConfig;
import sladki.tfc.ab.Handlers.Anvil.AnvilPlayerTickEventHandler;
import sladki.tfc.ab.Items.Armor.ItemRoundShield;
import sladki.tfc.ab.Items.*;
import sladki.tfc.ab.TileEntities.TEPotteryKiln;
import sladki.tfc.ab.TileEntities.TESteamBoiler;

import java.util.Map;

public class ModManager {

	//Blocks
	public static Block bellowsDriverBlock;
	public static Block steamBoilerBlock;
	
	public static Block potteryKilnBlock;
	public static Block potteryKilnChamberBlock;
	
	public static Block waterFilterBlock;
	
	//Items
	public static Item[] shieldRegistry = new Item[16];	//16 - 2 is wooden

	public static Item shepherdStaff;

	private static AnvilPlayerTickEventHandler playerAnvilTickEventHandler = new AnvilPlayerTickEventHandler();
	
	
	
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
			int id = 0;
			shieldRegistry[id] = new ItemRoundShield(id++, Armor.bismuthBronzePlate, 0.5f, 0.6f, 0.4f).setUnlocalizedName("bismuthBronzeShield");
			shieldRegistry[id] = new ItemRoundShield(id++, Armor.blackBronzePlate, 0.55f, 0.40f, 0.6f).setUnlocalizedName("blackBronzeShield");
			shieldRegistry[id] = new ItemRoundShield(id++, Armor.blackSteelPlate, 0.25f, 0.25f, 0.25f).setUnlocalizedName("blackSteelShield");
			shieldRegistry[id] = new ItemRoundShield(id++, Armor.blueSteelPlate, 0.35f, 0.4f, 0.9f).setUnlocalizedName("blueSteelShield");
			shieldRegistry[id] = new ItemRoundShield(id++, Armor.bronzePlate, 0.6f, 0.55f, 0.45f).setUnlocalizedName("bronzeShield");
			shieldRegistry[id] = new ItemRoundShield(id++, Armor.copperPlate, 0.7f, 0.4f, 0.3f).setUnlocalizedName("copperShield");
			shieldRegistry[id] = new ItemRoundShield(id++, Armor.wroughtIronPlate, 0.85f, 0.85f, 0.85f).setUnlocalizedName("wroughtIronShield");
			shieldRegistry[id] = new ItemRoundShield(id++, Armor.redSteelPlate, 0.85f, 0.0f, 0.0f).setUnlocalizedName("redSteelShield");
			shieldRegistry[id] = new ItemRoundShield(id++, Armor.steelPlate, 0.45f, 0.5f, 0.55f).setUnlocalizedName("steelShield");

			shieldRegistry[14] = new ItemRoundShield(14, 400, true).setUnlocalizedName("woodenShield");
		}

		if(ModConfig.shepherdStaffEnabled) {
			shepherdStaff = new ItemShepherdStaff().setUnlocalizedName("shepherdStaff");
		}
	}
	
	public static void registerItems() {
		if(ModConfig.shieldsEnabled) {
			int id = 0;
			GameRegistry.registerItem(shieldRegistry[id++], "bismuthBronzeShield");
			GameRegistry.registerItem(shieldRegistry[id++], "blackBronzeShield");
			GameRegistry.registerItem(shieldRegistry[id++], "blackSteelShield");
			GameRegistry.registerItem(shieldRegistry[id++], "blueSteelShield");
			GameRegistry.registerItem(shieldRegistry[id++], "bronzeShield");
			GameRegistry.registerItem(shieldRegistry[id++], "copperShield");
			GameRegistry.registerItem(shieldRegistry[id++], "wroughtIronShield");
			GameRegistry.registerItem(shieldRegistry[id++], "redSteelShield");
			GameRegistry.registerItem(shieldRegistry[id++], "steelShield");
			
			GameRegistry.registerItem(shieldRegistry[14], "shepherdStaff");
		}

		if(ModConfig.shepherdStaffEnabled) {
			GameRegistry.registerItem(shepherdStaff, "woodenShield");
		}
	}
	
	public static void registerRecipes() {
		if(ModConfig.ABEnabled) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bellowsDriverBlock, 1, 0), "   ", "SPS", "   ",
					Character.valueOf('S'), new ItemStack(TFCItems.wroughtIronSheet),
					Character.valueOf('P'), new ItemStack(Blocks.piston)));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steamBoilerBlock, 1, 0), " C ", "SBS", " C ",
					Character.valueOf('C'), new ItemStack(TFCItems.clayBall, 0, 1),
					Character.valueOf('S'), new ItemStack(TFCItems.wroughtIronSheet),
					Character.valueOf('B'), new ItemStack(TFCBlocks.fireBrick)));
		}
		
		if(ModConfig.PKEnabled) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(potteryKilnBlock, 1, 0), "BBB", "C C", "BBB",
					Character.valueOf('C'), new ItemStack(TFCItems.clayBall, 0, 1),
					Character.valueOf('B'), new ItemStack(TFCItems.fireBrick, 0, 1)));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(potteryKilnChamberBlock, 1, 0), "B B", "C C", "B B",
					Character.valueOf('C'), new ItemStack(TFCItems.clayBall, 0, 1),
					Character.valueOf('B'), new ItemStack(TFCItems.fireBrick, 0, 1)));
		}
		
		if(ModConfig.WFEnabled) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(waterFilterBlock, 1, 0), " S ", "BGB", " C ",
					Character.valueOf('S'), "blockSand",
					Character.valueOf('B'), new ItemStack(TFCItems.bronzeSheet),
					Character.valueOf('G'), "blockGravel",
					Character.valueOf('C'), "cobblestone"));
		}
		
		if(ModConfig.shieldsEnabled) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shieldRegistry[14], 1, 0), " L ", "LAL", " L ",
					Character.valueOf('L'), "logWood",
					Character.valueOf('A'), "itemAxe"));
		}

		if(ModConfig.shepherdStaffEnabled) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shepherdStaff, 1, 0), " K ", " S ", " S ",
					Character.valueOf('S'), "stickWood",
					Character.valueOf('K'), "itemKnife"));
		}
	}

	public static void registerAnvilRecipes() {
		String metalShieldPlane = "metalShield";

		Map map = AnvilManager.getInstance().getPlans();
		if(map.containsKey(metalShieldPlane)) {
			return;
		}

		if(AnvilManager.world == null) {
			return;
		}

		AnvilManager manager = AnvilManager.getInstance();
		manager.addPlan(metalShieldPlane, new PlanRecipe(new RuleEnum[]{RuleEnum.DRAWANY, RuleEnum.HITSECONDFROMLAST, RuleEnum.PUNCHLAST}));

		int id = 0;
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot2x), null, metalShieldPlane, false, AnvilReq.BISMUTHBRONZE,
				new ItemStack(shieldRegistry[id++], 1)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot2x), null, metalShieldPlane, false, AnvilReq.BLACKBRONZE,
				new ItemStack(shieldRegistry[id++], 1)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot2x), null, metalShieldPlane, false, AnvilReq.BLACKSTEEL,
				new ItemStack(shieldRegistry[id++], 1)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot2x), null, metalShieldPlane, false, AnvilReq.BLUESTEEL,
				new ItemStack(shieldRegistry[id++], 1)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot2x), null, metalShieldPlane, false, AnvilReq.BRONZE,
				new ItemStack(shieldRegistry[id++], 1)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot2x), null, metalShieldPlane, false, AnvilReq.COPPER,
				new ItemStack(shieldRegistry[id++], 1)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot2x), null, metalShieldPlane, false, AnvilReq.WROUGHTIRON,
				new ItemStack(shieldRegistry[id++], 1)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot2x), null, metalShieldPlane, false, AnvilReq.REDSTEEL,
				new ItemStack(shieldRegistry[id++], 1)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot2x), null, metalShieldPlane, false, AnvilReq.STEEL,
				new ItemStack(shieldRegistry[id++], 1)).addRecipeSkill(Global.SKILL_ARMORSMITH));
	}

	public static AnvilPlayerTickEventHandler getAnvilPlayerTickEventHandler() {
		return playerAnvilTickEventHandler;
	}
	
	public static void sendMessage(EntityPlayer player, IChatComponent message) {
		message.getChatStyle().setColor(EnumChatFormatting.GRAY);
		player.addChatComponentMessage(message);
	}
	
}