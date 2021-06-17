package Benz.module.misc;

import Benz.module.Category;
import Benz.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class VoidFix extends Module {

    public VoidFix() {
        super("VoidFix", "Always holds down the sprint key", Category.FPSBOOST);
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    }
}
