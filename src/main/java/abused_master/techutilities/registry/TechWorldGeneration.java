package abused_master.techutilities.registry;

import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.utils.RegistryHelper;

public class TechWorldGeneration {

    public static void generateOres() {
        for (BlockResources.EnumResourceOres ore : BlockResources.EnumResourceOres.values()) {
            if (ore.generateOre()) {
                RegistryHelper.generateOreInStone(ore.getBlockOres(), ore.getVeinSize(), ore.getSpawnRate(), ore.getMaxHeight());
            }
        }
    }
}
