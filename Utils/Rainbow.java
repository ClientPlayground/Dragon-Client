package Benz.Utils;

import java.awt.Color;
import net.minecraft.client.Minecraft;

public class Rainbow {

    public static void drawChromaString(String string, float f, float g, boolean b) {
        Minecraft mc = Minecraft.getMinecraft();
        int xTmp = (int) f;
        char[] achar = string.toCharArray();
        int i = achar.length;

        for (int j = 0; j < i; ++j) {
            char textChar = achar[j];
            long l = (long) ((float) System.currentTimeMillis() - ((float) (xTmp * 10) - g * 10.0F));
            int i1 = Color.HSBtoRGB((float) (l % 2000L) / 2000.0F, 0.8F, 0.8F);
            String tmp = String.valueOf(textChar);

            mc.fontRendererObj.drawString(tmp, xTmp, (int) g, i1);
            xTmp += mc.fontRendererObj.getCharWidth(textChar);
        }

    }

    public static int getChromaStringWidth(String string) {
        return getChromaStringWidth((String) null);
    }
}
