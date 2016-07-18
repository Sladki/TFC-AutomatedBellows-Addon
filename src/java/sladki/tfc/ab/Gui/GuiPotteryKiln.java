package sladki.tfc.ab.Gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import sladki.tfc.ab.AutomatedBellows;
import sladki.tfc.ab.Containers.ContainerPotteryKiln;
import sladki.tfc.ab.TileEntities.TEPotteryKiln;

public class GuiPotteryKiln extends GuiContainer {

	private int type = 0;	//0 - fuel, 1 - chamber;
	private TEPotteryKiln tileEntity;
	
	private static ResourceLocation textureKiln= new ResourceLocation(
			AutomatedBellows.MODID , "textures/gui/guiPotteryKiln.png"
	);
	
	private static ResourceLocation textureKilnChamber = new ResourceLocation(
			AutomatedBellows.MODID , "textures/gui/guiPotteryKilnChamber.png"
	);
	
	public GuiPotteryKiln(InventoryPlayer inventoryPlayer, TEPotteryKiln tileEntity, int type) {
		super(new ContainerPotteryKiln(inventoryPlayer, tileEntity, type));
		this.type = type;
		this.tileEntity = tileEntity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name;	
		if(type == 0) {
			name = StatCollector.translateToLocal("container.PotteryKiln.name"); 
		} else {
			name = StatCollector.translateToLocal("container.PotteryKilnChamber.name"); 
		}
		fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		if(type == 0) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(textureKiln);
		} else {
			Minecraft.getMinecraft().getTextureManager().bindTexture(textureKilnChamber);
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
}