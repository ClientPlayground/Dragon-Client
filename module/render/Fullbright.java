package Benz.module.render;

import Benz.module.Category;
import Benz.module.Module;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Fullbright extends Module {

    public Fullbright() {
        super("Fullbright", "Fullbright", Category.PVP);
    }

    @SubscribeEvent
    public void onRender(Text event) {
        Fullbright.mc.gameSettings.gammaSetting = 10.0F;
    }

    public void onDisable() {
        super.onDisable();
        Fullbright.mc.gameSettings.gammaSetting = 0.0F;
    }
}
