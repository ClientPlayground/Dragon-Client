package Benz.module.render;

import Benz.Client;
import Benz.module.Category;
import Benz.module.Module;
import Benz.settings.Setting;

public class ClickGUI extends Module {

    public ClickGUI() {
        super("ClickGUI", "Allows you to enable and disable modules", Category.PVP);
        this.setKey(54);
        Client.instance.settingsManager.rSetting(new Setting("Red", this, 0.0D, 0.0D, 255.0D, true));
        Client.instance.settingsManager.rSetting(new Setting("Green", this, 0.0D, 0.0D, 255.0D, true));
        Client.instance.settingsManager.rSetting(new Setting("Blue", this, 0.0D, 0.0D, 255.0D, true));
    }

    public void onEnable() {
        super.onEnable();
        ClickGUI.mc.displayGuiScreen(Client.instance.clickGui);
        this.setToggled(false);
    }
}
