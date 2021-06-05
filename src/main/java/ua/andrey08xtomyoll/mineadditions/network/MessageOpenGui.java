package ua.andrey08xtomyoll.mineadditions.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ua.andrey08xtomyoll.mineadditions.ModMain;

/**
 * Клас серверного повідомлення для GUI
 */
public class MessageOpenGui implements IMessage {

	private int id;
	
    public MessageOpenGui() { }
    
    public MessageOpenGui(int id) {
    	this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
    }

    public static class ServerHandler implements IMessageHandler<MessageOpenGui, IMessage> {

        @Override
        public IMessage onMessage(MessageOpenGui message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.openGui(ModMain.instance, message.id, player.getEntityWorld(), (int)player.posX, (int)player.posY, (int)player.posZ);
            return null;
        }
    }
}