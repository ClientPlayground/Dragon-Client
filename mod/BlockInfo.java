package Benz.mod;

import Benz.Client;
import Benz.ClientColor;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockInfo extends Gui {

    private int slideIn = 40;

    @SubscribeEvent
    public void onRender(Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        ScaledResolution sr = new ScaledResolution(mc);

        if (Client.instance.moduleManager.getModule("BlockInfo").isToggled() && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK) {
            BlockPos pos = mc.objectMouseOver.getBlockPos();
            IBlockState state = mc.theWorld.getBlockState(pos);
            Block block = state.getBlock();

            if (!block.equals(Blocks.portal) && !block.equals(Blocks.end_portal)) {
                if (this.slideIn < 40) {
                    ++this.slideIn;
                }

                RenderHelper.enableGUIStandardItemLighting();
                mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(block), sr.getScaledWidth() / 2 - 8, this.slideIn - 27);
                RenderHelper.disableStandardItemLighting();
                GlStateManager.pushMatrix();
                GlStateManager.scale(0.7D, 0.7D, 0.7D);
                if (this.slideIn > 0) {
                    --this.slideIn;
                }

                GlStateManager.popMatrix();
                Gui.drawRect(sr.getScaledWidth() / 2 - 50, this.slideIn, sr.getScaledWidth() / 2 + 50, 0, ClientColor.getClientBackground().getRGB());
                GlStateManager.pushMatrix();
                GlStateManager.scale(0.8D, 0.8D, 0.8D);
                this.drawCenteredString(fr, block.getLocalizedName(), (int) ((float) (sr.getScaledWidth() / 2) * 1.25F), this.slideIn - 35, -1);
                GlStateManager.popMatrix();
            }
        }

    }
}
