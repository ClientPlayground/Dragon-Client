package Benz.settings;

import Benz.module.Module;
import java.util.ArrayList;
import java.util.Iterator;

public class SettingsManager {

    private ArrayList settings = new ArrayList();

    public void rSetting(Setting in) {
        this.settings.add(in);
    }

    public ArrayList getSettings() {
        return this.settings;
    }

    public ArrayList getSettingsByMod(Module mod) {
        ArrayList out = new ArrayList();
        Iterator iterator = this.getSettings().iterator();

        while (iterator.hasNext()) {
            Setting s = (Setting) iterator.next();

            if (s.getParentMod().equals(mod)) {
                out.add(s);
            }
        }

        if (out.isEmpty()) {
            return null;
        } else {
            return out;
        }
    }

    public Setting getSettingByName(String name) {
        Iterator iterator = this.getSettings().iterator();

        while (iterator.hasNext()) {
            Setting set = (Setting) iterator.next();

            if (set.getName().equalsIgnoreCase(name)) {
                return set;
            }
        }

        System.err.println("[BaseUtils] Error Setting NOT found: \'" + name + "\'!");
        return null;
    }

    public Setting getSettingByNameAutoSave(Module mod, String name) {
        Iterator iterator = this.getSettings().iterator();

        Setting set;

        do {
            if (!iterator.hasNext()) {
                System.err.println("[BaseUtils] Error Setting NOT found: \'" + name + "\'!");
                return null;
            }

            set = (Setting) iterator.next();
        } while (!set.getName().equalsIgnoreCase(name) || set.getParentMod() != mod);

        return set;
    }
}
