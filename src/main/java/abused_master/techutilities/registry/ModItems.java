package abused_master.techutilities.registry;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.items.EnumResourceItems;
import abused_master.techutilities.items.ItemLinker;
import abused_master.techutilities.items.ItemQuarryRecorder;
import abused_master.techutilities.items.tools.ItemEnergizedSword;
import abused_master.techutilities.utils.RegistryHelper;

public class ModItems {

    public static ItemQuarryRecorder RECORDER = new ItemQuarryRecorder();
    public static ItemLinker LINKER = new ItemLinker();
    public static ItemEnergizedSword ENERGIZED_SWORD = new ItemEnergizedSword();

    public static void registerItems() {
        RegistryHelper.registerItem(TechUtilities.MODID, RECORDER);
        RegistryHelper.registerItem(TechUtilities.MODID, LINKER);
        RegistryHelper.registerItem(TechUtilities.MODID, "energized_sword", ENERGIZED_SWORD);

        for (EnumResourceItems item : EnumResourceItems.values()) {
            RegistryHelper.registerItem(TechUtilities.MODID, item.getItemIngot());
        }
    }
}
