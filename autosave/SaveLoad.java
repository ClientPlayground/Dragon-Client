package Benz.autosave;

import Benz.Client;
import Benz.module.Module;
import Benz.settings.Setting;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;

public class SaveLoad {

    private File dir;
    private File dataFile;

    public SaveLoad() {
        this.dir = new File(Minecraft.getMinecraft().mcDataDir, "Client");
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }

        this.dataFile = new File(this.dir, "Config.txt");
        if (!this.dataFile.exists()) {
            try {
                this.dataFile.createNewFile();
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        }

        this.load();
    }

    public void save() {
        if (!Client.instance.destructed) {
            ArrayList toSave = new ArrayList();
            Iterator e = Client.instance.moduleManager.modules.iterator();

            while (e.hasNext()) {
                Module filenotfoundexception = (Module) e.next();

                toSave.add("MOD:" + filenotfoundexception.getName() + ":" + filenotfoundexception.isToggled() + ":" + filenotfoundexception.getKey());
            }

            e = Client.instance.settingsManager.getSettings().iterator();

            while (e.hasNext()) {
                Setting filenotfoundexception1 = (Setting) e.next();

                if (filenotfoundexception1.isCheck()) {
                    toSave.add("SET:" + filenotfoundexception1.getName() + ":" + filenotfoundexception1.getParentMod().getName() + ":" + filenotfoundexception1.getValDouble());
                }

                if (filenotfoundexception1.isCombo()) {
                    toSave.add("SET:" + filenotfoundexception1.getName() + ":" + filenotfoundexception1.getParentMod().getName() + ":" + filenotfoundexception1.getValString());
                }

                if (filenotfoundexception1.isSlider()) {
                    toSave.add("SET:" + filenotfoundexception1.getName() + ":" + filenotfoundexception1.getParentMod().getName() + ":" + filenotfoundexception1.getValDouble());
                }
            }

            try {
                PrintWriter filenotfoundexception2 = new PrintWriter(this.dataFile);
                Iterator set2 = toSave.iterator();

                while (set2.hasNext()) {
                    String str = (String) set2.next();

                    filenotfoundexception2.println(str);
                }

                filenotfoundexception2.close();
            } catch (FileNotFoundException filenotfoundexception) {
                filenotfoundexception.printStackTrace();
            }
        }

    }

    public void load() {
        if (!Client.instance.destructed) {
            ArrayList lines = new ArrayList();

            String s;

            try {
                BufferedReader e1 = new BufferedReader(new FileReader(this.dataFile));

                for (s = e1.readLine(); s != null; s = e1.readLine()) {
                    lines.add(s);
                }

                e1.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            Iterator e11 = lines.iterator();

            while (e11.hasNext()) {
                s = (String) e11.next();
                String[] args = s.split(":");
                Module m;

                if (s.toLowerCase().startsWith("mod:")) {
                    m = Client.instance.moduleManager.getModule(args[1]);
                    if (m != null) {
                        m.setToggled(Boolean.parseBoolean(args[2]));
                        m.setKey(Integer.parseInt(args[3]));
                    }
                } else if (s.toLowerCase().startsWith("set:")) {
                    m = Client.instance.moduleManager.getModule(args[2]);
                    if (m != null) {
                        Setting set = Client.instance.settingsManager.getSettingByNameAutoSave(m, args[1]);

                        if (set != null) {
                            if (set.isCheck()) {
                                set.setValBoolean(Boolean.parseBoolean(args[3]));
                            }

                            if (set.isCombo()) {
                                set.setValString(args[3]);
                            }

                            if (set.isSlider()) {
                                set.setValDouble(Double.parseDouble(args[3]));
                            }
                        }
                    }
                }
            }
        }

    }
}
