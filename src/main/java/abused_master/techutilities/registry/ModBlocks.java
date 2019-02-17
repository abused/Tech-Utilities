package abused_master.techutilities.registry;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.blocks.crystals.BlockEnergyCollector;
import abused_master.techutilities.blocks.crystals.BlockEnergyCrystal;
import abused_master.techutilities.blocks.crystals.BlockItemReceiverCrystal;
import abused_master.techutilities.blocks.crystals.BlockItemTransferCrystal;
import abused_master.techutilities.blocks.decoration.BlockGlassBase;
import abused_master.techutilities.blocks.generators.BlockLavaGenerator;
import abused_master.techutilities.blocks.generators.EnumSolarPanelTypes;
import abused_master.techutilities.blocks.machines.*;

public class ModBlocks {

    public static BlockEnergyFurnace ENERGY_FURNACE = new BlockEnergyFurnace();
    public static BlockPulverizer PULVERIZER = new BlockPulverizer();
    public static BlockQuarry QUARRY = new BlockQuarry();
    public static BlockEnergyCrystal ENERGY_CRYSTAL = new BlockEnergyCrystal();
    public static BlockEnergyCollector ENERGY_CRYSTAL_COLLECTOR = new BlockEnergyCollector();
    public static BlockLavaGenerator LAVA_GENERATOR = new BlockLavaGenerator();
    public static BlockEnergyCharger ENERGY_CHARGER = new BlockEnergyCharger();
    public static BlockFluidPump FLUID_PUMP = new BlockFluidPump();
    public static BlockFarmer FARMER = new BlockFarmer();
    public static BlockMobGrinder MOB_GRINDER = new BlockMobGrinder();
    public static BlockVacuum VACUUM = new BlockVacuum();
    public static BlockConveyorBelt CONVEYOR_BELT = new BlockConveyorBelt();
    public static BlockItemTransferCrystal ITEM_TRANSFER = new BlockItemTransferCrystal();
    public static BlockItemReceiverCrystal ITEM_RECEIVER = new BlockItemReceiverCrystal();
    public static BlockPhaseCell PHASE_CELL = new BlockPhaseCell();

    //Decoration
    public static BlockGlassBase GLASS_BLOCK = new BlockGlassBase("glass_block");
    public static BlockGlassBase BLACK_GLASS_BLOCK = new BlockGlassBase("black_glass_block");

    public static void registerBlocks() {
        RegistryHelper.registerBlock(TechUtilities.MODID, ENERGY_FURNACE);
        RegistryHelper.registerBlock(TechUtilities.MODID, PULVERIZER);
        RegistryHelper.registerBlock(TechUtilities.MODID, QUARRY);
        RegistryHelper.registerBlock(TechUtilities.MODID, ENERGY_CRYSTAL);
        RegistryHelper.registerBlock(TechUtilities.MODID, ENERGY_CRYSTAL_COLLECTOR);
        RegistryHelper.registerBlock(TechUtilities.MODID, LAVA_GENERATOR);
        RegistryHelper.registerBlock(TechUtilities.MODID, ENERGY_CHARGER);
        RegistryHelper.registerBlock(TechUtilities.MODID, FLUID_PUMP);
        RegistryHelper.registerBlock(TechUtilities.MODID, FARMER);
        RegistryHelper.registerBlock(TechUtilities.MODID, MOB_GRINDER);
        RegistryHelper.registerBlock(TechUtilities.MODID, VACUUM);
        RegistryHelper.registerBlock(TechUtilities.MODID, CONVEYOR_BELT);
        RegistryHelper.registerBlock(TechUtilities.MODID, ITEM_TRANSFER);
        RegistryHelper.registerBlock(TechUtilities.MODID, ITEM_RECEIVER);
        RegistryHelper.registerBlock(TechUtilities.MODID, PHASE_CELL);

        RegistryHelper.registerBlock(TechUtilities.MODID, GLASS_BLOCK);
        RegistryHelper.registerBlock(TechUtilities.MODID, BLACK_GLASS_BLOCK);

        for (EnumSolarPanelTypes panel : EnumSolarPanelTypes.values()) {
            RegistryHelper.registerBlock(TechUtilities.MODID, panel.getBlockSolar());
        }
    }
}
