package Benz.module;

import Benz.module.misc.MemoryFix;
import Benz.module.misc.NoBreakParticle;
import Benz.module.misc.NoParticles;
import Benz.module.misc.ResourceExploitFix;
import Benz.module.misc.VoidFix;
import Benz.module.render.ArmorStatus;
import Benz.module.render.BlockInfo;
import Benz.module.render.ClickGUI;
import Benz.module.render.Coords;
import Benz.module.render.FPS;
import Benz.module.render.Fullbright;
import Benz.module.render.PotionStatus;
import java.util.ArrayList;
import java.util.Iterator;

public class ModuleManager {

    public ArrayList modules;

    public ModuleManager() {
        (this.modules = new ArrayList()).clear();
        this.modules.add(new MemoryFix());
        this.modules.add(new NoParticles());
        this.modules.add(new FPS());
        this.modules.add(new ArmorStatus());
        this.modules.add(new BlockInfo());
        this.modules.add(new Coords());
        this.modules.add(new ClickGUI());
        this.modules.add(new PotionStatus());
        this.modules.add(new Fullbright());
        this.modules.add(new NoBreakParticle());
        this.modules.add(new ResourceExploitFix());
        this.modules.add(new VoidFix());
    }

    public Module getModule(String name) {
        Iterator iterator = this.modules.iterator();

        while (iterator.hasNext()) {
            Module m = (Module) iterator.next();

            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }

        return null;
    }

    public ArrayList getModuleList() {
        return this.modules;
    }

    public ArrayList getModulesInCategory(Category c) {
        ArrayList mods = new ArrayList();
        Iterator iterator = this.modules.iterator();

        while (iterator.hasNext()) {
            Module m = (Module) iterator.next();

            if (m.getCategory() == c) {
                mods.add(m);
            }
        }

        return mods;
    }
}
