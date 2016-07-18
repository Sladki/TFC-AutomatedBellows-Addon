package sladki.tfc.ab.Gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sladki.tfc.ab.Containers.ContainerPotteryKiln;
import sladki.tfc.ab.TileEntities.TEPotteryKiln;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity;
		
		if(ID == 0) {
			tileEntity = world.getTileEntity(x, y, z);
			return new ContainerPotteryKiln(player.inventory, (TEPotteryKiln) tileEntity, 0);
		}
		
		if(ID == 1) {
			tileEntity = world.getTileEntity(x, y, z);
			return new ContainerPotteryKiln(player.inventory, (TEPotteryKiln) tileEntity, 1);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity;
		
		if(ID == 0) {
			tileEntity = world.getTileEntity(x, y, z);
			return new GuiPotteryKiln(player.inventory, (TEPotteryKiln) tileEntity, 0);
		}
		
		if(ID == 1) {
			tileEntity = world.getTileEntity(x, y, z);
			return new GuiPotteryKiln(player.inventory, (TEPotteryKiln) tileEntity, 1);
		}
		return null;
	}	

}