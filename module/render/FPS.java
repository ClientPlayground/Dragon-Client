package Benz.module.render;

import Benz.Client;
import Benz.module.Category;
import Benz.module.Module;
import Benz.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FPS extends Module {

    int posXFPS = 0;
    int posYFPS = 0;

    public FPS() {
        super("FPS", "RenderFramerate", Category.PVP);
        Client.instance.settingsManager.rSetting(new Setting("FPS: X", this, 0.0D, 0.0D, 900.0D, false));
        Client.instance.settingsManager.rSetting(new Setting("FPS: Y", this, 0.0D, 0.0D, 550.0D, false));
    }

    @SubscribeEvent
    public void onRender(Text event) {
        this.posXFPS = (int) Client.instance.settingsManager.getSettingByName("FPS: X").getValDouble() + 2;
        this.posYFPS = (int) Client.instance.settingsManager.getSettingByName("FPS: Y").getValDouble() + 2;
        FPS.fr.drawStringWithShadow("FPS[Boost]: " + Minecraft.getDebugFPS(), (float) this.posXFPS, (float) this.posYFPS, -1);
    }
}
