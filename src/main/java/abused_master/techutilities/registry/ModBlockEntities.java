package abused_master.techutilities.registry;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.*;
import abused_master.techutilities.tiles.crystal.BlockEntityAutoCrystal;
import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCollector;
import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCrystal;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static BlockEntityType<BlockEntityEnergyFurnace> ENERGY_FURNACE;
    public static BlockEntityType<BlockEntityQuarry> QUARRY;
    public static BlockEntityType<BlockEntitySolarPanel> SOLAR_PANEL;
    public static BlockEntityType<BlockEntityEnergyCrystal> ENERGY_CRYSTAL;
    public static BlockEntityType<BlockEntityEnergyCollector> ENERGY_CRYSTAL_COLLECTOR;
    public static BlockEntityType<BlockEntityAutoCrystal> AUTO_ENERGY_CRYSTAL;

    //Container Identifiers
    public static final Identifier ENERGY_FURNACE_CONTAINER = new Identifier(TechUtilities.MODID, "energy_furnace_container");

    public static void registerBlockEntities() {
        ENERGY_FURNACE = RegistryHelper.registerTile(TechUtilities.MODID, "energy_furnace", BlockEntityEnergyFurnace.class);
        QUARRY = RegistryHelper.registerTile(TechUtilities.MODID, "quarry", BlockEntityQuarry.class);
        SOLAR_PANEL = RegistryHelper.registerTile(TechUtilities.MODID, "solar_panel", BlockEntitySolarPanel.class);
        ENERGY_CRYSTAL = RegistryHelper.registerTile(TechUtilities.MODID, "energy_crystal", BlockEntityEnergyCrystal.class);
        ENERGY_CRYSTAL_COLLECTOR = RegistryHelper.registerTile(TechUtilities.MODID, "energy_crystal_collector", BlockEntityEnergyCollector.class);
        AUTO_ENERGY_CRYSTAL = RegistryHelper.registerTile(TechUtilities.MODID, "auto_energy_crystal", BlockEntityAutoCrystal.class);
    }
}
