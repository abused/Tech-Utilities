package abused_master.techutilities.registry;

import abused_master.abusedlib.registry.RegistryHelper;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.blocks.crystals.BlockWirelessTransmitter;
import abused_master.techutilities.blocks.crystals.BlockWirelessController;
import abused_master.techutilities.blocks.decoration.BlockGlassBase;
import abused_master.techutilities.blocks.generators.BlockLavaGenerator;
import abused_master.techutilities.blocks.generators.EnumSolarPanelTypes;
import abused_master.techutilities.blocks.machines.*;

public class ModBlocks {

    public static BlockEnergyFurnace ENERGY_FURNACE = new BlockEnergyFurnace();
    public static BlockPulverizer PULVERIZER = new BlockPulverizer();
    public static BlockQuarry QUARRY = new BlockQuarry();
    public static BlockWirelessController ENERGY_CRYSTAL = new BlockWirelessController();
    public static BlockWirelessTransmitter ENERGY_CRYSTAL_COLLECTOR = new BlockWirelessTransmitter();
    public static BlockLavaGenerator LAVA_GENERATOR = new BlockLavaGenerator();
    public static BlockEnergyCharger ENERGY_CHARGER = new BlockEnergyCharger();
    public static BlockFluidPump FLUID_PUMP = new BlockFluidPump();
    public static BlockFarmer FARMER = new BlockFarmer();
    public static BlockMobGrinder MOB_GRINDER = new BlockMobGrinder();
    public static BlockVacuum VACUUM = new BlockVacuum();
    public static BlockConveyorBelt CONVEYOR_BELT = new BlockConveyorBelt();
    public static BlockEnergyCell PHASE_CELL = new BlockEnergyCell();
    public static BlockMachineFrame MACHINE_FRAME = new BlockMachineFrame();

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
        RegistryHelper.registerBlock(TechUtilities.MODID, PHASE_CELL);
        RegistryHelper.registerBlock(TechUtilities.MODID, MACHINE_FRAME);

        RegistryHelper.registerBlock(TechUtilities.MODID, GLASS_BLOCK);
        RegistryHelper.registerBlock(TechUtilities.MODID, BLACK_GLASS_BLOCK);

        for (EnumSolarPanelTypes panel : EnumSolarPanelTypes.values()) {
            RegistryHelper.registerBlock(TechUtilities.MODID, panel.getBlockSolar());
        }
    }
}
