package Benz.clickgui;

import Benz.clickgui.component.Component;
import Benz.clickgui.component.Frame;
import Benz.module.Category;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiScreen;

public class ClickGui extends GuiScreen {

    public static ArrayList frames;
    public static int color = -1;

    public ClickGui() {
        ClickGui.frames = new ArrayList();
        int frameX = 5;
        Category[] acategory = Category.values();
        int i = acategory.length;

        for (int j = 0; j < i; ++j) {
            Category category = acategory[j];
            Frame frame = new Frame(category);

            frame.setX(frameX);
            ClickGui.frames.add(frame);
            frameX += frame.getWidth() + 1;
        }

    }

    public void initGui() {}

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Iterator iterator = ClickGui.frames.iterator();

        while (iterator.hasNext()) {
            Frame frame = (Frame) iterator.next();

            frame.renderFrame(this.fontRendererObj);
            frame.updatePosition(mouseX, mouseY);
            Iterator iterator1 = frame.getComponents().iterator();

            while (iterator1.hasNext()) {
                Component comp = (Component) iterator1.next();

                comp.updateComponent(mouseX, mouseY);
            }
        }

    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        Iterator iterator = ClickGui.frames.iterator();

        while (iterator.hasNext()) {
            Frame frame = (Frame) iterator.next();

            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }

            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }

            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                Iterator iterator1 = frame.getComponents().iterator();

                while (iterator1.hasNext()) {
                    Component component = (Component) iterator1.next();

                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }

    }

    protected void keyTyped(char typedChar, int keyCode) {
        Iterator iterator = ClickGui.frames.iterator();

        while (iterator.hasNext()) {
            Frame frame = (Frame) iterator.next();

            if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
                Iterator iterator1 = frame.getComponents().iterator();

                while (iterator1.hasNext()) {
                    Component component = (Component) iterator1.next();

                    component.keyTyped(typedChar, keyCode);
                }
            }
        }

        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen) null);
        }

    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        Iterator iterator = ClickGui.frames.iterator();

        Frame frame;

        while (iterator.hasNext()) {
            frame = (Frame) iterator.next();
            frame.setDrag(false);
        }

        iterator = ClickGui.frames.iterator();

        while (iterator.hasNext()) {
            frame = (Frame) iterator.next();
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                Iterator iterator1 = frame.getComponents().iterator();

                while (iterator1.hasNext()) {
                    Component component = (Component) iterator1.next();

                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }

    }

    public boolean doesGuiPauseGame() {
        return true;
    }
}
