package Benz.clickgui.component.components;

import Benz.Client;
import Benz.ClientColor;
import Benz.clickgui.component.Component;
import Benz.clickgui.component.Frame;
import Benz.clickgui.component.components.sub.Checkbox;
import Benz.clickgui.component.components.sub.Keybind;
import Benz.clickgui.component.components.sub.ModeButton;
import Benz.clickgui.component.components.sub.Slider;
import Benz.clickgui.component.components.sub.VisibleButton;
import Benz.module.Module;
import Benz.settings.Setting;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class Button extends Component {

    public Module mod;
    public Frame parent;
    public int offset;
    private boolean isHovered;
    private ArrayList subcomponents;
    public boolean open;
    private int height;

    public Button(Module mod, Frame parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList();
        this.open = false;
        this.height = 12;
        int opY = offset + 12;

        if (Client.instance.settingsManager.getSettingsByMod(mod) != null) {
            Iterator iterator = Client.instance.settingsManager.getSettingsByMod(mod).iterator();

            while (iterator.hasNext()) {
                Setting s = (Setting) iterator.next();

                if (s.isCombo()) {
                    this.subcomponents.add(new ModeButton(s, this, mod, opY));
                    opY += 12;
                }

                if (s.isSlider()) {
                    this.subcomponents.add(new Slider(s, this, opY));
                    opY += 12;
                }

                if (s.isCheck()) {
                    this.subcomponents.add(new Checkbox(s, this, opY));
                    opY += 12;
                }
            }
        }

        this.subcomponents.add(new Keybind(this, opY));
        this.subcomponents.add(new VisibleButton(this, mod, opY));
    }

    public void setOff(int newOff) {
        this.offset = newOff;
        int opY = this.offset + 12;

        for (Iterator iterator = this.subcomponents.iterator(); iterator.hasNext(); opY += 12) {
            Component comp = (Component) iterator.next();

            comp.setOff(opY);
        }

    }

    public void renderComponent() {
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, this.mod.isToggled() ? ClientColor.getClientColorNormal().getRGB() : ClientColor.getClientBackground().getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.getName(), (float) ((this.parent.getX() + 2) * 2), (float) ((this.parent.getY() + this.offset + 2) * 2 + 4), -1);
        if (this.subcomponents.size() > 2) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.open ? "-" : "+", (float) ((this.parent.getX() + this.parent.getWidth() - 10) * 2), (float) ((this.parent.getY() + this.offset + 2) * 2 + 4), -1);
        }

        GL11.glPopMatrix();
        if (this.open && !this.subcomponents.isEmpty()) {
            Iterator iterator = this.subcomponents.iterator();

            while (iterator.hasNext()) {
                Component comp = (Component) iterator.next();

                comp.renderComponent();
            }
        }

    }

    public int getHeight() {
        return this.open ? 12 * (this.subcomponents.size() + 1) : 12;
    }

    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = this.isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            Iterator iterator = this.subcomponents.iterator();

            while (iterator.hasNext()) {
                Component comp = (Component) iterator.next();

                comp.updateComponent(mouseX, mouseY);
            }
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }

        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }

        Iterator iterator = this.subcomponents.iterator();

        while (iterator.hasNext()) {
            Component comp = (Component) iterator.next();

            comp.mouseClicked(mouseX, mouseY, button);
        }

    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        Iterator iterator = this.subcomponents.iterator();

        while (iterator.hasNext()) {
            Component comp = (Component) iterator.next();

            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }

    }

    public void keyTyped(char typedChar, int key) {
        Iterator iterator = this.subcomponents.iterator();

        while (iterator.hasNext()) {
            Component comp = (Component) iterator.next();

            comp.keyTyped(typedChar, key);
        }

    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
    }
}
