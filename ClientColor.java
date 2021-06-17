package Benz;

import java.awt.Color;

public class ClientColor {

    public static Color getClientColorNormal() {
        return new Color((int) Client.instance.settingsManager.getSettingByName("Red").getValDouble(), (int) Client.instance.settingsManager.getSettingByName("Green").getValDouble(), (int) Client.instance.settingsManager.getSettingByName("Blue").getValDouble(), 170);
    }

    public static Color getClientColorText() {
        return new Color((int) Client.instance.settingsManager.getSettingByName("Red").getValDouble(), (int) Client.instance.settingsManager.getSettingByName("Green").getValDouble(), (int) Client.instance.settingsManager.getSettingByName("Blue").getValDouble());
    }

    public static Color getClientBackground() {
        return new Color(0, 0, 0, 150);
    }

    public static int getRainbow(float secounds, float saturation, float brightness, long index) {
        float hue = (float) ((System.currentTimeMillis() + index) % (long) ((int) (secounds * 1000.0F))) / (secounds * 1000.0F);
        int color = Color.HSBtoRGB(hue, saturation, brightness);

        return color;
    }
}
