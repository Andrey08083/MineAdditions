package ua.andrey08xtomyoll.mineadditions.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ua.andrey08xtomyoll.mineadditions.blocks.tiles.ThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.gui.GuiThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.gui.containers.ContainerThermalCrusher;

public class GuiHandler implements IGuiHandler {

	public static int id = 0;
	public static final int GUI_THERMAL_CRUSHER = id++;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_THERMAL_CRUSHER) {
				System.out.println("[S] crusher != null");
				return new ContainerThermalCrusher(player.inventory, (ThermalCrusher)world.getTileEntity(new BlockPos(x, y, z)));
        }
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_THERMAL_CRUSHER) {
			System.out.println("[C] GUI = ThermalCrusher");
			return new GuiThermalCrusher(player.inventory, (ThermalCrusher)world.getTileEntity(new BlockPos(x, y, z)));
        }
		return null;
	}

}
