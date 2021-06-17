package Benz.module.misc;

import Benz.module.Category;
import Benz.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class MemoryFix extends Module {

    private int messageDelay = 0;
    private IChatComponent updateMessage;

    @SubscribeEvent
    public void MessageDelay(ClientTickEvent clienttickevent) {
        if (this.updateMessage != null && Minecraft.getMinecraft().thePlayer != null && ++this.messageDelay == 80) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(this.updateMessage);
            this.updateMessage = null;
        }

    }

    public MemoryFix() {
        super("MemoryFix", "MemoryFix", Category.FPSBOOST);
    }
}
