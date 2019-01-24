package abused_master.techutilities.registry;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.items.EnumResourceItems;
import abused_master.techutilities.items.ItemLinker;
import abused_master.techutilities.items.ItemQuarryRecorder;
import abused_master.techutilities.items.ItemTest;

public class ModItems {

    public static ItemQuarryRecorder RECORDER = new ItemQuarryRecorder();
    public static ItemLinker LINKER = new ItemLinker();

    public static void registerItems() {
        RegistryHelper.registerItem(TechUtilities.MODID, RECORDER);
        RegistryHelper.registerItem(TechUtilities.MODID, LINKER);
        RegistryHelper.registerItem(TechUtilities.MODID, new ItemTest());

        for (EnumResourceItems item : EnumResourceItems.values()) {
            RegistryHelper.registerItem(TechUtilities.MODID, item.getItemIngot());
        }
    }
}
