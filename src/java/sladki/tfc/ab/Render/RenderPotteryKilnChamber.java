package sladki.tfc.ab.Render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import sladki.tfc.ab.Blocks.BlockPotteryKilnChamber;

public class RenderPotteryKilnChamber implements ISimpleBlockRenderingHandler {
	
	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)	{
		//Lower part
		renderer.overrideBlockTexture = BlockPotteryKilnChamber.textureSide;
		renderer.setRenderBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.875f, 1.0f);
		renderInvBlock(block, renderer);
		
		//Upper part
		renderer.overrideBlockTexture = BlockPotteryKilnChamber.textureTop;
		renderer.setRenderBounds(0.25f, 0.875f, 0.25f, 0.75f, 1.0f, 0.75f);
		renderInvBlock(block, renderer);

		renderer.clearOverrideBlockTexture();
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.renderAllFaces = true;
		
		//Lower part
		renderer.overrideBlockTexture = BlockPotteryKilnChamber.textureSide;
		renderer.setRenderBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
		renderer.renderStandardBlock(block, x, y, z);
		
		//Upper part
		renderer.overrideBlockTexture = BlockPotteryKilnChamber.textureTop;
		renderer.setRenderBounds(0.25f, 0.75f, 0.25f, 0.75f, 1.0f, 0.75f);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.clearOverrideBlockTexture();
		renderer.renderAllFaces = false;
		
		return true;
	}
	
	public void rotate(RenderBlocks renderer, int i) {
		renderer.uvRotateEast = i;
		renderer.uvRotateWest = i;
		renderer.uvRotateNorth = i;
		renderer.uvRotateSouth = i;
	}
		
	@Override
	public int getRenderId() {
		return 0;
	}
	
	public static void renderInvBlock(Block block, RenderBlocks renderer) {
		Tessellator var14 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 2));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 2));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 1));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 3));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
	
	public static void renderBlock(Block block, RenderBlocks renderer) {
		Tessellator var14 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 2));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 2));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 1));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 3));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

}