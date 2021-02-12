package ua.andrey08xtomyoll.mineadditions.network;

import net.minecraftforge.fml.relauncher.Side;
import ua.andrey08xtomyoll.mineadditions.ModMain;

public class NetworkUtils {
	private static int id = 0;
	
	/* Тут регистрируются пакеты */
	public static void registerMessages() {
		ModMain.network.registerMessage(MessageOpenGui.ServerHandler.class, MessageOpenGui.class, id++, Side.SERVER);
	}
}