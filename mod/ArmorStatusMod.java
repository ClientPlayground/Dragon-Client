package Benz.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class ArmorStatusMod {

    public static void renderArmorStatus(ScaledResolution sr, int pos, ItemStack itemStack) {
        if (itemStack != null) {
            boolean posX = false;
            boolean posY = false;
            int posXAdd = -16 * pos + 48;

            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack, sr.getScaledWidth() / 2 + 20 + posXAdd, sr.getScaledHeight() - 55);
            GlStateManager.popMatrix();
        }

    }
}
