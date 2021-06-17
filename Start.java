package Benz;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
    modid = "Speeder Mod",
    version = "1.0",
    acceptedMinecraftVersions = "[1.8.9]"
)
public class Start {

    public static final String MODID = "Speeder Mod";
    public static final String VERSION = "1.0";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Client.instance = new Client();
        Client.instance.init();
    }
}
