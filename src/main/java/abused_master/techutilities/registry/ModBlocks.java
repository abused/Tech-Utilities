package abused_master.techutilities.registry;

import abused_master.abusedlib.registry.RegistryHelper;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.blocks.crystals.BlockEnergyCrystal;
import abused_master.techutilities.blocks.decoration.BlockGlassBase;
import abused_master.techutilities.blocks.generators.EnumSolarPanelTypes;
import abused_master.techutilities.blocks.machines.BlockEnergyFurnace;
import abused_master.techutilities.blocks.machines.BlockQuarry;

public class ModBlocks {

    public static BlockEnergyFurnace RF_FURNACE = new BlockEnergyFurnace();
    public static BlockQuarry QUARRY = new BlockQuarry();
    public static BlockEnergyCrystal ENERGY_CRYSTAL = new BlockEnergyCrystal();

    //Decoration
    public static BlockGlassBase GLASS_BLOCK = new BlockGlassBase("glass_block");
    public static BlockGlassBase BLACK_GLASS_BLOCK = new BlockGlassBase("black_glass_block");

    public static void registerBlocks() {
        RegistryHelper.registerBlock(TechUtilities.MODID, RF_FURNACE);
        RegistryHelper.registerBlock(TechUtilities.MODID, QUARRY);
        RegistryHelper.registerBlock(TechUtilities.MODID, ENERGY_CRYSTAL);

        RegistryHelper.registerBlock(TechUtilities.MODID, GLASS_BLOCK);
        RegistryHelper.registerBlock(TechUtilities.MODID, BLACK_GLASS_BLOCK);

        for (EnumSolarPanelTypes panel : EnumSolarPanelTypes.values()) {
            RegistryHelper.registerBlock(TechUtilities.MODID, panel.getBlockSolar());
        }

        for (BlockResources.EnumResourceOres ore : BlockResources.EnumResourceOres.values()) {
            RegistryHelper.registerBlock(TechUtilities.MODID, ore.getBlockOres());
        }

        for (BlockResources.EnumResourceBlocks ore : BlockResources.EnumResourceBlocks.values()) {
            RegistryHelper.registerBlock(TechUtilities.MODID, ore.getBlockOres());
        }
    }
}
