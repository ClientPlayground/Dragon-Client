package Benz.settings;

import Benz.Client;
import Benz.module.Module;
import java.util.ArrayList;

public class Setting {

    private String name;
    private Module parent;
    private String mode;
    private String sval;
    private ArrayList options;
    private boolean bval;
    private double dval;
    private double min;
    private double max;
    private boolean onlyint = false;

    public Setting(String name, Module parent, String sval, ArrayList options) {
        this.name = name;
        this.parent = parent;
        this.sval = sval;
        this.options = options;
        this.mode = "Combo";
    }

    public Setting(String name, Module parent, boolean bval) {
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        this.mode = "Check";
    }

    public Setting(String name, Module parent, double dval, double min, double max, boolean onlyint) {
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        this.mode = "Slider";
    }

    public String getName() {
        return this.name;
    }

    public Module getParentMod() {
        return this.parent;
    }

    public String getValString() {
        return this.sval;
    }

    public void setValString(String in) {
        this.sval = in;
        if (Client.instance.saveLoad != null) {
            Client.instance.saveLoad.save();
        }

    }

    public ArrayList getOptions() {
        return this.options;
    }

    public boolean getValBoolean() {
        if (Client.instance.saveLoad != null) {
            Client.instance.saveLoad.save();
        }

        return this.bval;
    }

    public boolean getUnValBoolean() {
        if (Client.instance.saveLoad != null) {
            Client.instance.saveLoad.save();
        }

        return !this.bval;
    }

    public void setValBoolean(boolean in) {
        this.bval = in;
        if (Client.instance.saveLoad != null) {
            Client.instance.saveLoad.save();
        }

    }

    public double getValDouble() {
        if (this.onlyint) {
            this.dval = (double) ((int) this.dval);
        }

        return this.dval;
    }

    public void setValDouble(double in) {
        this.dval = in;
        if (Client.instance.saveLoad != null) {
            Client.instance.saveLoad.save();
        }

    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public boolean isCombo() {
        return this.mode.equalsIgnoreCase("Combo");
    }

    public boolean isCheck() {
        return this.mode.equalsIgnoreCase("Check");
    }

    public boolean isSlider() {
        return this.mode.equalsIgnoreCase("Slider");
    }

    public boolean onlyInt() {
        return this.onlyint;
    }
}
