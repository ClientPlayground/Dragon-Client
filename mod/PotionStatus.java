package Benz.mod;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionStatus extends Gui {

    private static final ResourceLocation potionInventory = new ResourceLocation("textures/gui/container/inventory.png");

    public void renderPotionStatus(int posX, int posY) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

        GlStateManager.enableAlpha();
        Collection activePotions = mc.thePlayer.getActivePotionEffects();

        if (!activePotions.isEmpty()) {
            int defaultposY = 3;

            for (Iterator itr = activePotions.iterator(); itr.hasNext(); defaultposY += 20) {
                PotionEffect potionEffect = (PotionEffect) itr.next();
                Potion potion = Potion.potionTypes[potionEffect.getPotionID()];

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(PotionStatus.potionInventory);
                if (potion.hasStatusIcon()) {
                    int potionName1 = potion.getStatusIconIndex();

                    this.drawTexturedModalRect(posX, posY + defaultposY, 0 + potionName1 % 8 * 18, 198 + potionName1 / 8 * 18, 18, 18);
                }

                String potionName11 = I18n.format(potion.getName(), new Object[0]);

                fr.drawStringWithShadow(potionName11, (float) (posX + 20), (float) (posY + defaultposY + 1), Color.WHITE.getRGB());
                fr.drawStringWithShadow(Potion.getDurationString(potionEffect), (float) (posX + 20), (float) (posY + defaultposY + 10), Color.LIGHT_GRAY.getRGB());
            }
        }

    }
}
