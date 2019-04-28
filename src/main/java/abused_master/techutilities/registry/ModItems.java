package abused_master.techutilities.registry;

import abused_master.abusedlib.registry.RegistryHelper;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.items.*;
import abused_master.techutilities.items.tools.ItemEnergizedSword;
import net.minecraft.util.Identifier;

public class ModItems {

    public static ItemQuarryRecorder RECORDER = new ItemQuarryRecorder();
    public static ItemLinker LINKER = new ItemLinker();
    public static ItemEnergizedSword ENERGIZED_SWORD = new ItemEnergizedSword();
    public static ItemSteelIngot STEEL_INGOT = new ItemSteelIngot();
    public static ItemWrench WRENCH = new ItemWrench();

    public static void registerItems() {
        RegistryHelper.registerItem(TechUtilities.MODID, RECORDER);
        RegistryHelper.registerItem(TechUtilities.MODID, LINKER);
        RegistryHelper.registerItem(new Identifier(TechUtilities.MODID, "energized_sword"), ENERGIZED_SWORD);
        RegistryHelper.registerItem(TechUtilities.MODID, STEEL_INGOT);
        RegistryHelper.registerItem(TechUtilities.MODID, WRENCH);

        for (EnumResourceItems item : EnumResourceItems.values()) {
            RegistryHelper.registerItem(TechUtilities.MODID, item.getItemIngot());
        }
    }
}
