package Benz;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import org.lwjgl.opengl.GL11;

public class ChunkEdgeRenderer {

    private int chunkEdgeState = 0;

    @SubscribeEvent
    public void resetOverlay(Load event) {
        this.chunkEdgeState = 0;
    }

    @SubscribeEvent
    public void getKeyPress(ClientTickEvent event) {
        if (Minecraft.getMinecraft().theWorld != null && Client.keyBindChunkOverlay.isPressed()) {
            this.chunkEdgeState = (this.chunkEdgeState + 1) % 3;
        }

    }

    @SubscribeEvent
    public void renderChunkEdges(RenderWorldLastEvent event) {
        if (this.chunkEdgeState != 0) {
            EntityPlayerSP entity = Minecraft.getMinecraft().thePlayer;
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();

            GL11.glPushMatrix();
            float frame = event.partialTicks;
            double inChunkPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) frame;
            double inChunkPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) frame;
            double inChunkPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) frame;

            GL11.glTranslated(-inChunkPosX, -inChunkPosY, -inChunkPosZ);
            GL11.glDisable(3553);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(2.0F);
            worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
            GL11.glTranslatef((float) (entity.chunkCoordX * 16), 0.0F, (float) (entity.chunkCoordZ * 16));
            double x = 0.0D;
            double z = 0.0D;
            float redColourR = 0.9F;
            float redColourG = 0.0F;
            float redColourB = 0.0F;
            float redColourA = 1.0F;
            float greenColourR = 0.0F;
            float greenColourG = 0.9F;
            float greenColourB = 0.0F;
            float greenColourA = 0.4F;

            int eyeHeightBlock;

            for (int f = -2; f <= 2; ++f) {
                for (eyeHeightBlock = -2; eyeHeightBlock <= 2; ++eyeHeightBlock) {
                    if (Math.abs(eyeHeightBlock) != 2 || Math.abs(f) != 2) {
                        x = (double) (eyeHeightBlock * 16);
                        z = (double) (f * 16);
                        redColourA = (float) Math.round(Math.pow(1.25D, (double) (-(eyeHeightBlock * eyeHeightBlock + f * f))) * 6.0D) / 6.0F;
                        worldrenderer.pos(x, 0.0D, z).color(redColourR, redColourG, redColourB, redColourA).endVertex();
                        worldrenderer.pos(x, 256.0D, z).color(redColourR, redColourG, redColourB, redColourA).endVertex();
                        worldrenderer.pos(x + 16.0D, 0.0D, z).color(redColourR, redColourG, redColourB, redColourA).endVertex();
                        worldrenderer.pos(x + 16.0D, 256.0D, z).color(redColourR, redColourG, redColourB, redColourA).endVertex();
                        worldrenderer.pos(x, 0.0D, z + 16.0D).color(redColourR, redColourG, redColourB, redColourA).endVertex();
                        worldrenderer.pos(x, 256.0D, z + 16.0D).color(redColourR, redColourG, redColourB, redColourA).endVertex();
                        worldrenderer.pos(x + 16.0D, 0.0D, z + 16.0D).color(redColourR, redColourG, redColourB, redColourA).endVertex();
                        worldrenderer.pos(x + 16.0D, 256.0D, z + 16.0D).color(redColourR, redColourG, redColourB, redColourA).endVertex();
                    }
                }
            }

            z = 0.0D;
            x = 0.0D;
            if (this.chunkEdgeState == 2) {
                float f = (float) ((double) entity.getEyeHeight() + entity.posY);

                eyeHeightBlock = (int) Math.floor((double) f);
                int minY = Math.max(0, eyeHeightBlock - 16);
                int maxY = Math.min(256, eyeHeightBlock + 16);
                boolean renderedSome = false;

                for (int y = 0; y < 257; ++y) {
                    greenColourA = 0.4F;
                    if (y < minY) {
                        greenColourA = (float) ((double) greenColourA - Math.pow((double) (minY - y), 2.0D) / 500.0D);
                    }

                    if (y > maxY) {
                        greenColourA = (float) ((double) greenColourA - Math.pow((double) (y - maxY), 2.0D) / 500.0D);
                    }

                    if (greenColourA < 0.01F) {
                        if (renderedSome) {
                            break;
                        }
                    } else {
                        if (y < 256) {
                            for (int n = 1; n < 16; ++n) {
                                worldrenderer.pos((double) n, (double) y, z).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                                worldrenderer.pos((double) n, (double) (y + 1), z).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                                worldrenderer.pos(x, (double) y, (double) n).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                                worldrenderer.pos(x, (double) (y + 1), (double) n).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                                worldrenderer.pos((double) n, (double) y, z + 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                                worldrenderer.pos((double) n, (double) (y + 1), z + 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                                worldrenderer.pos(x + 16.0D, (double) y, (double) n).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                                worldrenderer.pos(x + 16.0D, (double) (y + 1), (double) n).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                            }
                        }

                        worldrenderer.pos(0.0D, (double) y, 0.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                        worldrenderer.pos(16.0D, (double) y, 0.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                        worldrenderer.pos(0.0D, (double) y, 0.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                        worldrenderer.pos(0.0D, (double) y, 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                        worldrenderer.pos(16.0D, (double) y, 0.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                        worldrenderer.pos(16.0D, (double) y, 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                        worldrenderer.pos(0.0D, (double) y, 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                        worldrenderer.pos(16.0D, (double) y, 16.0D).color(greenColourR, greenColourG, greenColourB, greenColourA).endVertex();
                        renderedSome = true;
                    }
                }
            }

            tessellator.draw();
            GL11.glPopMatrix();
            GL11.glEnable(3553);
            GL11.glDisable(3042);
        }

    }
}
