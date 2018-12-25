package abused_master.techutilities.registry;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.blocks.machines.EnergyFurnace;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static EnergyFurnace RF_FURNACE = new EnergyFurnace();

    public static void registerBlocks(Registry<Block> registry) {
        Registry.register(registry, new Identifier(TechUtilities.MODID, "energy_furnace"), RF_FURNACE);

        for (BlockResources.EnumResourceOres ore : BlockResources.EnumResourceOres.values()) {
            Registry.register(registry, ore.getOresIdentifier(), ore.getBlockOres());
        }

        for (BlockResources.EnumResoueceBlocks ore : BlockResources.EnumResoueceBlocks.values()) {
            Registry.register(registry, ore.getOresIdentifier(), ore.getBlockOres());
        }
    }
}
