package abused_master.techutilities.registry;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.blocks.crystals.BlockEnergyCrystal;
import abused_master.techutilities.blocks.decoration.BlockGlassBase;
import abused_master.techutilities.blocks.generators.EnumSolarPanelTypes;
import abused_master.techutilities.blocks.machines.BlockEnergyFurnace;
import abused_master.techutilities.blocks.machines.BlockQuarry;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static BlockEnergyFurnace RF_FURNACE = new BlockEnergyFurnace();
    public static BlockQuarry QUARRY = new BlockQuarry();
    public static BlockEnergyCrystal ENERGY_CRYSTAL = new BlockEnergyCrystal();

    //Decoration
    public static BlockGlassBase GLASS_BLOCK = new BlockGlassBase();
    public static BlockGlassBase BLACK_GLASS_BLOCK = new BlockGlassBase();

    public static void registerBlocks(Registry<Block> registry) {
        Registry.register(registry, new Identifier(TechUtilities.MODID, "energy_furnace"), RF_FURNACE);
        Registry.register(registry, new Identifier(TechUtilities.MODID, "quarry"), QUARRY);
        Registry.register(registry, new Identifier(TechUtilities.MODID, "energy_crystal"), ENERGY_CRYSTAL);

        Registry.register(registry, new Identifier(TechUtilities.MODID, "glass_block"), GLASS_BLOCK);
        Registry.register(registry, new Identifier(TechUtilities.MODID, "black_glass_block"), BLACK_GLASS_BLOCK);

        for (EnumSolarPanelTypes panel : EnumSolarPanelTypes.values()) {
            Registry.register(registry, panel.getIdentifier(), panel.getBlockSolar());
        }

        for (BlockResources.EnumResourceOres ore : BlockResources.EnumResourceOres.values()) {
            Registry.register(registry, ore.getOresIdentifier(), ore.getBlockOres());
        }

        for (BlockResources.EnumResourceBlocks ore : BlockResources.EnumResourceBlocks.values()) {
            Registry.register(registry, ore.getOresIdentifier(), ore.getBlockOres());
        }
    }
}
