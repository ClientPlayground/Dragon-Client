package Benz.clickgui.component.components.sub;

import Benz.ClientColor;
import Benz.clickgui.component.Component;
import Benz.clickgui.component.components.Button;
import Benz.module.Module;
import Benz.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class ModeButton extends Component {

    private boolean hovered;
    private Button parent;
    private Setting set;
    private int offset;
    private int x;
    private int y;
    private Module mod;
    private int modeIndex;

    public ModeButton(Setting set, Button button, Module mod, int offset) {
        this.set = set;
        this.parent = button;
        this.mod = mod;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
        this.modeIndex = 0;
    }

    public void setOff(int newOff) {
        this.offset = newOff;
    }

    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() * 1, this.parent.parent.getY() + this.offset + 12, ClientColor.getClientBackground().getRGB());
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, ClientColor.getClientBackground().getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Mode: " + this.set.getValString(), (float) ((this.parent.parent.getX() + 7) * 2), (float) ((this.parent.parent.getY() + this.offset + 2) * 2 + 5), -1);
        GL11.glPopMatrix();
    }

    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            int maxIndex = this.set.getOptions().size();

            ++this.modeIndex;
            if (this.modeIndex >= maxIndex) {
                this.modeIndex = 0;
            }

            this.set.setValString((String) this.set.getOptions().get(this.modeIndex));
        }

    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
    }
}
