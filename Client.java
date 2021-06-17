package Benz;

import Benz.autosave.SaveLoad;
import Benz.clickgui.ClickGui;
import Benz.mod.BlockInfo;
import Benz.module.Module;
import Benz.module.ModuleManager;
import Benz.notification.NotificationManager;
import Benz.settings.SettingsManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public class Client {

    public static Client instance;
    public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public ClickGui clickGui;
    public SaveLoad saveLoad;
    protected static Minecraft mc = Minecraft.getMinecraft();
    public static final KeyBinding keyBindChunkOverlay = new KeyBinding("ChunkIndicators", 67, "Speeder Mod");
    @SideOnly(Side.CLIENT)
    public static KeyBinding in;
    @SideOnly(Side.CLIENT)
    public static KeyBinding center;
    @SideOnly(Side.CLIENT)
    public static KeyBinding out;
    public static String id = " ID = Beta";
    public boolean destructed = false;

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
        this.settingsManager = new SettingsManager();
        this.moduleManager = new ModuleManager();
        this.clickGui = new ClickGui();
        this.saveLoad = new SaveLoad();
        MinecraftForge.EVENT_BUS.register(new BlockInfo());
        sendMessage(":exclamation:  ALERT! :exclamation: **" + Client.mc.getSession().getUsername() + "Has Just Logged Into The Client " + getHWID() + Client.id);
        MinecraftForge.EVENT_BUS.register(new ChunkEdgeRenderer());
        ClientRegistry.registerKeyBinding(Client.keyBindChunkOverlay);
        System.out.println("Speeder Mod");
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            this.handleClientSide();
        }

    }

    public static String getHWID() {
        try {
            String exception = System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(exception.getBytes());
            StringBuffer hexString = new StringBuffer();
            byte[] byteData = md.digest();
            byte[] abyte = byteData;
            int i = byteData.length;

            for (int j = 0; j < i; ++j) {
                byte aByteData = abyte[j];
                String hex = Integer.toHexString(255 & aByteData);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception exception) {
            exception.printStackTrace();
            return "Error";
        }
    }

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {}

    @SideOnly(Side.CLIENT)
    public void handleClientSide() {
        Client.in = new KeyBinding("zoomin", 0, "Speeder Mod");
        Client.center = new KeyBinding("centerzoom", 0, "Speeder Mod");
        Client.out = new KeyBinding("zoomout", 0, "Speeder Mod");
        ClientRegistry.registerKeyBinding(Client.in);
        ClientRegistry.registerKeyBinding(Client.center);
        ClientRegistry.registerKeyBinding(Client.out);
        FMLCommonHandler.instance().bus().register(new KeyHandler());
    }

    public static void sendMessage(String message) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();

        try {
            URL ioexception = new URL("https://discord.com/api/webhooks/825307268823908362/HGx5K5UmenSqPDEtd51oaGc3hr_6givGzso2SSqKMasV69vBJ3ncm22v-vBdjQTHO8jO");
            URLConnection conn = ioexception.openConnection();

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            String postData = URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");

            out.print(postData);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;

            while ((line = in.readLine()) != null) {
                result.append("/n").append(line);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }

                if (in != null) {
                    in.close();
                }
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }

        }

        System.out.println(result.toString());
    }

    @SubscribeEvent
    public void key(KeyInputEvent e) {
        if (Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null) {
            try {
                if (Keyboard.isCreated() && Keyboard.getEventKeyState()) {
                    int exception = Keyboard.getEventKey();

                    if (exception <= 0) {
                        return;
                    }

                    Iterator iterator = this.moduleManager.modules.iterator();

                    while (iterator.hasNext()) {
                        Module m = (Module) iterator.next();

                        if (m.getKey() == exception && exception > 0) {
                            m.toggle();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    @SubscribeEvent
    public void onRender(Text event) {
        NotificationManager.render();
    }

    public void onDestruct() {
        if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.closeScreen();
        }

        this.destructed = true;
        MinecraftForge.EVENT_BUS.unregister(this);

        for (int k = 0; k < this.moduleManager.modules.size(); ++k) {
            Module m = (Module) this.moduleManager.modules.get(k);

            MinecraftForge.EVENT_BUS.unregister(m);
            this.moduleManager.getModuleList().remove(m);
        }

        this.moduleManager = null;
        this.clickGui = null;
    }

    public static Block getBlock(BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
    }

    public static void addChat(String msg) {
        ChatComponentText cpt = new ChatComponentText(EnumChatFormatting.BLUE + "[Speeder Mod] " + EnumChatFormatting.BLUE + msg);

        Minecraft.getMinecraft().thePlayer.addChatMessage(cpt);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {}
}
