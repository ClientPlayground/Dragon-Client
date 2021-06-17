package Benz.module.render;

import Benz.Client;
import Benz.module.Category;
import Benz.module.Module;
import Benz.settings.Setting;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Ping extends Module {

    int posXPing = 0;
    int posYPing = 0;

    public Ping() {
        super("Ping", "Pingdisplay", Category.PVP);
        Client.instance.settingsManager.rSetting(new Setting("Ping: X", this, 0.0D, 0.0D, 900.0D, false));
        Client.instance.settingsManager.rSetting(new Setting("Ping: Y", this, 0.0D, 0.0D, 550.0D, false));
    }

    @SubscribeEvent
    public void onRender(Text event) {
        int ping = Ping.mc.getNetHandler().getPlayerInfo(Ping.mc.thePlayer.getUniqueID()).getResponseTime();

        this.posXPing = (int) Client.instance.settingsManager.getSettingByName("Ping: X").getValDouble() + 2;
        this.posYPing = (int) Client.instance.settingsManager.getSettingByName("Ping: Y").getValDouble() + 2;
        Ping.fr.drawStringWithShadow("Ping: " + ping, (float) this.posXPing, (float) this.posYPing, -1);
    }
}
