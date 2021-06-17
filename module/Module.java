package Benz.module;

import Benz.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.MinecraftForge;

public class Module {

    protected static Minecraft mc = Minecraft.getMinecraft();
    protected static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    protected static ScaledResolution sr = new ScaledResolution(Module.mc);
    private String name;
    private String description;
    private int key;
    private Category category;
    private boolean toggled;
    public boolean visible = true;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.key = 0;
        this.category = category;
        this.toggled = false;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
        if (Client.instance.saveLoad != null) {
            Client.instance.saveLoad.save();
        }

    }

    public boolean isToggled() {
        return this.toggled;
    }

    public boolean isDisabled() {
        return !this.toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }

        if (Client.instance.saveLoad != null) {
            Client.instance.saveLoad.save();
        }

    }

    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }

        if (Client.instance.saveLoad != null) {
            Client.instance.saveLoad.save();
        }

    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }
}
