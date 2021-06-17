package Benz.module.render;

import Benz.Client;
import Benz.module.Category;
import Benz.module.Module;
import Benz.settings.Setting;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionStatus extends Module {

    int PotionStatusX = 0;
    int PotionStatusY = 0;
    protected float zLevel;
    private static final ResourceLocation potionInventory = new ResourceLocation("textures/gui/container/inventory.png");

    public PotionStatus() {
        super("PotionStatus", "RenderPotionStatus", Category.PVP);
        Client.instance.settingsManager.rSetting(new Setting("PotionStatus: X", this, 0.0D, 0.0D, 900.0D, false));
        Client.instance.settingsManager.rSetting(new Setting("PotionStatus: Y", this, 0.0D, 0.0D, 550.0D, false));
    }

    @SubscribeEvent
    public void onRender(Text event) {
        this.PotionStatusX = (int) Client.instance.settingsManager.getSettingByName("PotionStatus: X").getValDouble() + 2;
        this.PotionStatusY = (int) Client.instance.settingsManager.getSettingByName("PotionStatus: Y").getValDouble() + 2;
        GlStateManager.enableAlpha();
        Collection activePotions = PotionStatus.mc.thePlayer.getActivePotionEffects();

        if (!activePotions.isEmpty()) {
            int defaultposY = 3;

            for (Iterator itr = activePotions.iterator(); itr.hasNext(); defaultposY += 20) {
                PotionEffect potionEffect = (PotionEffect) itr.next();
                Potion potion = Potion.potionTypes[potionEffect.getPotionID()];

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                PotionStatus.mc.getTextureManager().bindTexture(PotionStatus.potionInventory);
                if (potion.hasStatusIcon()) {
                    int potionName1 = potion.getStatusIconIndex();

                    this.drawTexturedModalRect(this.PotionStatusX, this.PotionStatusY + defaultposY, 0 + potionName1 % 8 * 18, 198 + potionName1 / 8 * 18, 18, 18);
                }

                String potionName11 = I18n.format(potion.getName(), new Object[0]);

                PotionStatus.fr.drawStringWithShadow(potionName11, (float) (this.PotionStatusX + 20), (float) (this.PotionStatusY + defaultposY + 1), Color.WHITE.getRGB());
                PotionStatus.fr.drawStringWithShadow(Potion.getDurationString(potionEffect), (float) (this.PotionStatusX + 20), (float) (this.PotionStatusY + defaultposY + 10), Color.LIGHT_GRAY.getRGB());
            }
        }

    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double) (x + 0), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX + 0) * f), (double) ((float) (textureY + height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX + width) * f), (double) ((float) (textureY + height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + 0), (double) this.zLevel).tex((double) ((float) (textureX + width) * f), (double) ((float) (textureY + 0) * f1)).endVertex();
        worldrenderer.pos((double) (x + 0), (double) (y + 0), (double) this.zLevel).tex((double) ((float) (textureX + 0) * f), (double) ((float) (textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }
}
