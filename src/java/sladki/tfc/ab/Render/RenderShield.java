package sladki.tfc.ab.Render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import sladki.tfc.ab.AutomatedBellows;
import sladki.tfc.ab.Render.Models.ModelShield;

public class RenderShield {
	
	private float equipProgress = 0.0f;
	
	private ModelShield shield = new ModelShield();
	private static final ResourceLocation SHIELD_TEXTURE = new ResourceLocation(AutomatedBellows.MODID, "textures/models/armor/shield.png");

	public RenderShield() {}
	
	public void render(EntityPlayer player, RenderPlayer renderModel, boolean shieldInHand, boolean isBlocking) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(SHIELD_TEXTURE);
		
		if(shieldInHand) {
			renderModel.modelBipedMain.bipedLeftArm.postRender(0.0625F);
			GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
			
			if(isBlocking) {
				GL11.glTranslatef(0.125f, 0.3125f, 0.275f);
			} else {
				GL11.glTranslatef(-0.125f, 0.5f, 0.2125f);
			}
		} else {
			renderModel.modelBipedMain.bipedBody.postRender(0.0625F);
			GL11.glTranslatef(0.0f, 0.3125f, 0.25f);
			
		}

		shield.render();
		GL11.glPopMatrix();
	}
	
	public void renderFirstPerson(EntityPlayer player, float tick, boolean isBlocking) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		Minecraft.getMinecraft().entityRenderer.enableLightmap(tick);
		GL11.glPushMatrix();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(SHIELD_TEXTURE);
		
		//Lighting
		int brightness = player.worldObj.getLightBrightnessForSkyBlocks(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ), 0);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) (brightness % 65536) / 1.0F, (float) (brightness / 65536) / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		//Rotate with camera
		GL11.glRotatef(player.rotationYaw, 0.0f, -1.0f, 0.0f);
		GL11.glRotatef(player.rotationPitch, 1.0f, 0.0f, 0.0f);
		
		//"Holding in a hand" effect
		EntityPlayerSP playerSP = (EntityPlayerSP) player;
		
        float shieldPitch = playerSP.prevRenderArmPitch + (playerSP.renderArmPitch - playerSP.prevRenderArmPitch) * tick;
        float shieldYaw = playerSP.prevRenderArmYaw + (playerSP.renderArmYaw - playerSP.prevRenderArmYaw) * tick;
        GL11.glRotatef((player.rotationPitch - shieldPitch) * 0.1F, -1.0F, 0.0F, 0.0F);
        GL11.glRotatef((player.rotationYaw - shieldYaw) * 0.1F, 0.0F, 1.0F, 0.0F);
        
        //"Blocked attack" effect
        /*if(player.hurtResistantTime > 4 ) {
        	GL11.glTranslatef(-0.3f, 0.15f, -0.15f);
        }*/

		//Positioning
        if(isBlocking) {
        	GL11.glTranslatef(0.35f, -0.65f, 0.45f);
    		GL11.glRotatef(180, 0.02f, 0.0f, 1.0f);
        } else {
        	GL11.glTranslatef(0.75f, -0.85f, 0.6f);
    		GL11.glRotatef(180, 0.05f, 0.0f, 1.0f);
        }

		shield.render();
		
		GL11.glPopMatrix();
		Minecraft.getMinecraft().entityRenderer.disableLightmap(tick);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}
