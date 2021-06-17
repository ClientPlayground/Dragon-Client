package Benz.clickgui.component;

import Benz.Client;
import Benz.clickgui.component.components.Button;
import Benz.module.Category;
import Benz.module.Module;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class Frame {

    public ArrayList components = new ArrayList();
    public Category category;
    private boolean open;
    private int width;
    private int y;
    private int x;
    private int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;

    public Frame(Category cat) {
        this.category = cat;
        this.width = 88;
        this.x = 5;
        this.y = 5;
        this.barHeight = 13;
        this.dragX = 0;
        this.open = true;
        this.isDragging = false;
        int tY = this.barHeight;

        for (Iterator iterator = Client.instance.moduleManager.getModulesInCategory(this.category).iterator(); iterator.hasNext(); tY += 12) {
            Module mod = (Module) iterator.next();
            Button modButton = new Button(mod, this, tY);

            this.components.add(modButton);
        }

    }

    public ArrayList getComponents() {
        return this.components;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void renderFrame(FontRenderer fontRenderer) {
        Color color = new Color(0, 0, 0);

        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, color.getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        fontRenderer.drawStringWithShadow(this.category.name(), (float) ((this.x + 2) * 2 + 5), ((float) this.y + 2.5F) * 2.0F + 5.0F, -1);
        fontRenderer.drawStringWithShadow(this.open ? "-" : "+", (float) ((this.x + this.width - 10) * 2 + 5), ((float) this.y + 2.5F) * 2.0F + 5.0F, -1);
        GL11.glPopMatrix();
        if (this.open && !this.components.isEmpty()) {
            Iterator iterator = this.components.iterator();

            while (iterator.hasNext()) {
                Component component = (Component) iterator.next();

                component.renderComponent();
            }
        }

    }

    public void refresh() {
        int off = this.barHeight;

        Component comp;

        for (Iterator iterator = this.components.iterator(); iterator.hasNext(); off += comp.getHeight()) {
            comp = (Component) iterator.next();
            comp.setOff(off);
        }

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }

    }

    public boolean isWithinHeader(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }
}
