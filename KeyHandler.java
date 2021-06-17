package Benz;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyHandler {

    public static Minecraft mc = Minecraft.getMinecraft();
    public static float defaultfov = KeyHandler.mc.gameSettings.fovSetting;
    public static final float amount = 0.1F;

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent event) {
        if (event.phase == Phase.START && KeyHandler.mc.thePlayer != null && KeyHandler.mc.theWorld != null && KeyHandler.mc.inGameHasFocus) {
            GameSettings gamesettings = KeyHandler.mc.gameSettings;

            if (GameSettings.isKeyDown(Client.in)) {
                if (KeyHandler.mc.thePlayer.isSneaking()) {
                    --KeyHandler.mc.gameSettings.fovSetting;
                } else {
                    KeyHandler.mc.gameSettings.fovSetting -= 0.1F;
                }
            }

            gamesettings = KeyHandler.mc.gameSettings;
            if (GameSettings.isKeyDown(Client.out)) {
                if (KeyHandler.mc.thePlayer.isSneaking()) {
                    ++KeyHandler.mc.gameSettings.fovSetting;
                } else {
                    KeyHandler.mc.gameSettings.fovSetting += 0.1F;
                }
            }

            gamesettings = KeyHandler.mc.gameSettings;
            if (GameSettings.isKeyDown(Client.center)) {
                KeyHandler.mc.gameSettings.fovSetting = KeyHandler.defaultfov;
            }
        }

    }
}
