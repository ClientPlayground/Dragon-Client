package Benz.module.misc;

import Benz.module.Category;
import Benz.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoParticles extends Module {

    public NoParticles() {
        super("NoParticles", "Always holds down the sprint key", Category.FPSBOOST);
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

        this.setToggled(false);
    }

    @SubscribeEvent
    public void removeParticle(Text text) {
        MemoryFix.mc.gameSettings.particleSetting = 10;
    }

    public void onDisable() {
        super.onDisable();
        MemoryFix.mc.gameSettings.particleSetting = 100;
    }
}
