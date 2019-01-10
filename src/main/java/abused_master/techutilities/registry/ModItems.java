package abused_master.techutilities.registry;

import abused_master.abusedlib.registry.RegistryHelper;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.items.EnumResourceItems;
import abused_master.techutilities.items.ItemQuarryRecorder;

public class ModItems {

    public static ItemQuarryRecorder recorder = new ItemQuarryRecorder();

    public static void registerItems() {
        RegistryHelper.registerItem(TechUtilities.MODID, recorder);

        for (EnumResourceItems item : EnumResourceItems.values()) {
            RegistryHelper.registerItem(TechUtilities.MODID, item.getItemIngot());
        }
    }
}
