package Benz.module.render;

import Benz.mod.ArmorStatusMod;
import Benz.module.Category;
import Benz.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArmorStatus extends Module {

    public ArmorStatus() {
        super("ArmorStatus", "RenderArmorStatus", Category.PVP);
    }

    @SubscribeEvent
    public void onRender(Text event) {
        for (int i21 = 0; i21 < Minecraft.getMinecraft().thePlayer.inventory.armorInventory.length; ++i21) {
            ItemStack is = Minecraft.getMinecraft().thePlayer.inventory.armorInventory[i21];

            ArmorStatusMod.renderArmorStatus(ArmorStatus.sr, i21, is);
        }

    }
}
