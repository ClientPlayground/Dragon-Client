package Benz.notification;

import Benz.ClientColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class Notification {

    private NotificationType type;
    private String title;
    private String messsage;
    private long start;
    private long fadedIn;
    private long fadeOut;
    private long end;

    public Notification(NotificationType type, String title, String messsage, int length) {
        this.type = type;
        this.title = title;
        this.messsage = messsage;
        this.fadedIn = (long) (200 * length);
        this.fadeOut = this.fadedIn + (long) (500 * length);
        this.end = this.fadeOut + this.fadedIn;
    }

    public void show() {
        this.start = System.currentTimeMillis();
    }

    public boolean isShown() {
        return this.getTime() <= this.end;
    }

    private long getTime() {
        return System.currentTimeMillis() - this.start;
    }

    public void render() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sr = new ScaledResolution(mc);
        double offset = 0.0D;
        byte width = 120;
        byte height = 30;
        long time = this.getTime();

        if (time < this.fadedIn) {
            offset = Math.tanh((double) time / (double) this.fadedIn * 3.0D) * (double) width;
        } else if (time > this.fadeOut) {
            offset = Math.tanh(3.0D - (double) (time - this.fadeOut) / (double) (this.end - this.fadeOut) * 3.0D) * (double) width;
        } else {
            offset = (double) width;
        }

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

        drawRect((double) sr.getScaledWidth() - offset, (double) (sr.getScaledHeight() - 5 - height), (double) sr.getScaledWidth(), (double) (sr.getScaledHeight() - 5), ClientColor.getClientBackground().getRGB());
        drawRect((double) sr.getScaledWidth() - offset, (double) (sr.getScaledHeight() - 5 - height), (double) sr.getScaledWidth() - offset + 4.0D, (double) (sr.getScaledHeight() - 5), ClientColor.getClientColorNormal().getRGB());
        fontRenderer.drawString(this.title, (int) ((double) sr.getScaledWidth() - offset + 8.0D), sr.getScaledHeight() - 2 - height, -1);
        fontRenderer.drawString(this.messsage, (int) ((double) sr.getScaledWidth() - offset + 8.0D), sr.getScaledHeight() - 15, -1);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        double f3;

        if (left < right) {
            f3 = left;
            left = right;
            right = f3;
        }

        if (top < bottom) {
            f3 = top;
            top = bottom;
            bottom = f3;
        }

        float f31 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f31);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(int mode, double left, double top, double right, double bottom, int color) {
        double f3;

        if (left < right) {
            f3 = left;
            left = right;
            right = f3;
        }

        if (top < bottom) {
            f3 = top;
            top = bottom;
            bottom = f3;
        }

        float f31 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f31);
        worldrenderer.begin(mode, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
